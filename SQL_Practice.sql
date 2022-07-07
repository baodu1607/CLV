--Câu 9: Cho số 8988.80 vui lòng xuất ra định dạng $8,988.800
SELECT
    to_char(8988.80, '$9,999.999')
FROM
    dual;

--Câu 10: Cho số 8988.80, 820988.80 
    --vui lòng xuất ra định dạng $8,000.000, $820,000.000
SELECT
    to_char(trunc(8988.80, - 3), '$9,999.999') num1
FROM
    dual;

SELECT
    to_char(trunc(820988.80, - 3), '$999,999.999') num2
FROM
    dual;

--Câu 12: Viết câu SQL xuất ra, Ngày hiện tại, này hôm qua, ngày mai
SELECT
    sysdate - 1 yesterday,
    sysdate     today,
    sysdate + 1 tomorrow
FROM
    dual;

--Câu 13: Ta có table (TB_ORD), yêu cầu viết câu SQL để generate ORD_NO 
    --có đô dài 10 tự với format sau: yyyymmdd000Seq, ví dụ hnay là 20191028 
    --và chưa có seq nào thì ORD_NO sẽ là 201910280001, và nếu đã tồn tại 
    --ORD_NO 201910280001 thì nó sẽ là 201910280002
SELECT
    to_char(sysdate, 'YYYYMMDD')
    || nvl(lpad(substr(MAX(ord_no), 9, 4) + 1, 4, '0'), '0001') AS ord_no
FROM
    tb_ord
WHERE
    ord_no LIKE to_char(sysdate, 'YYYYMMDD')
                || '%';

--Câu 14: Ta có table (MDM_CUSTOMER) và dữ liệu như bên dưới
--A) Viết câu SQL tìm CUST_GRP_ID sao cho: CUST_GRP_HRCHY_CD có I hoặc C nhưng không có G
SELECT
    cust_grp_id
FROM
    (
        SELECT
            cust_grp_id,
            COUNT(*)
            OVER(PARTITION BY cust_grp_id) volumn,
            cust_grp_hrchy_cd
        FROM
            mdm_customer
        GROUP BY
            cust_grp_id,
            cust_grp_hrchy_cd
    )
WHERE
        cust_grp_hrchy_cd != 'G'
    AND volumn = 1;
    
--B) Viết câu SQL tìm CUST_GRP_ID sao cho: CUST_GRP_HRCHY_CD có G và có I nhưng không có C
WITH temp AS (
    SELECT
        cust_grp_id,
        COUNT(*)
        OVER(PARTITION BY cust_grp_id) volumn,
        cust_grp_hrchy_cd
    FROM
        mdm_customer
    GROUP BY
        cust_grp_id,
        cust_grp_hrchy_cd
    ORDER BY
        cust_grp_id,
        cust_grp_hrchy_cd
)
SELECT
    cust_grp_id
FROM
    (
        SELECT
            cust_grp_id,
            cust_grp_hrchy_cd,
            LEAD(cust_grp_id)
            OVER(
                ORDER BY
                    cust_grp_id
            ) next_cust_grp_id,
            LEAD(cust_grp_hrchy_cd)
            OVER(
                ORDER BY
                    cust_grp_id
            ) next_cust_grp_hrchy_cd
        FROM
            temp
        WHERE
            volumn = 2
    )
WHERE
        cust_grp_id = next_cust_grp_id
    AND cust_grp_hrchy_cd != 'C'
    AND next_cust_grp_id != 'C'

--Câu 15: Ta có table (TB_PROD) và dữ liệu như bên dưới
--Viết cấu SQL để suất ra kêt quả như sau:
    --Lấy max(PROD_UNIT_AMT)
    --Lấy  giá trị min(PROD_UNIT_AMT)
    --Lấy giá trị trung bình PROD_UNIT_AMT
    --Lấy tên của sản phẩm có PROD_UNIT_AMT lớn nhất

SELECT
    MAX(prod_unit_amt)                                        prod_unit_amt,
    MIN(prod_unit_amt)                                        prod_unit_amt,
    AVG(prod_unit_amt)                                        prod_unit_amt,
    MIN(prod_nm) KEEP(DENSE_RANK LAST ORDER BY prod_unit_amt) AS prod_nm
FROM
    tb_prod
WHERE
    prod_unit_amt IS NOT NULL;

--Câu 16: Ta có table (TB_ORD) và dữ liệu như bên dưới
--A) Viết cấu SQL lấy ra top 3 sản phẩm đc bán nhiều nhất.
SELECT
    *
FROM
    (
        SELECT
            pro_cd,
            DENSE_RANK()
            OVER(
                ORDER BY
                    COUNT(*) DESC
            ) AS rank
        FROM
            tb_ord
        GROUP BY
            pro_cd
    ) temp
WHERE
    temp.rank <= 3;
  
--B) Viết cấu SQL lấy ra cái ORD_DT, ORD_TM, PROD_CD gần nhất theo CUST_NO
SELECT
    cust_no,
    ord_dttm,
    ord_no,
    pro_cd
FROM
    (
        SELECT
            cust_no,
            ord_dttm,
            ord_no,
            pro_cd,
            ROW_NUMBER()
            OVER(PARTITION BY cust_no
                 ORDER BY
                     ord_dttm DESC
            ) rank
        FROM
            tb_ord
    ) temp
WHERE
    temp.rank = 1;

--C) Viết câu SQL report xem trong tháng 06, 07, 08, 09 của 2019 sản phẩm có mã code là 00001 bán đc bao nhiêu cái.
WITH report AS (
    SELECT
        '201906' AS dt
    FROM
        dual
    UNION ALL
    SELECT
        '201907' AS dt
    FROM
        dual
    UNION ALL
    SELECT
        '201908' AS dt
    FROM
        dual
    UNION ALL
    SELECT
        '201909' AS dt
    FROM
        dual
)
SELECT
    report.dt,
    nvl(pro_cd,'00001') pro_cd,
    nvl(ord.total, 0) total
FROM
         report left
    JOIN (
        SELECT
            pro_cd,
            substr(ord_dttm, 1, 6) AS ord_dttm,
            COUNT(*)               total
        FROM
            tb_ord
        WHERE
            pro_cd = '00001'
        GROUP BY
            pro_cd,
            substr(ord_dttm, 1, 6)
    ) ord ON report.dt = ord.ord_dttm;

--D) Giả sử lúc đầu sản phẩn 00001 có 100 cái, viết report để tính số lương remain theo tháng 06, 07, 08, 09
WITH report AS (
    SELECT
        '201906' AS dt
    FROM
        dual
    UNION ALL
    SELECT
        '201907' AS dt
    FROM
        dual
    UNION ALL
    SELECT
        '201908' AS dt
    FROM
        dual
    UNION ALL
    SELECT
        '201909' AS dt
    FROM
        dual
)
SELECT
    report.dt,
    nvl(ord.total, 0) total,
    100 - nvl(SUM(ord.total)
              OVER(PARTITION BY ord.pro_cd
                   ORDER BY
                       report.dt
              ), 0)             AS remain
FROM
         report
    LEFT JOIN (
        SELECT
            pro_cd,
            substr(ord_dttm, 1, 6) AS ord_dttm, 
            COUNT(*)               total
        FROM
            tb_ord
        WHERE
            pro_cd = '00001'
        GROUP BY
            pro_cd,
            substr(ord_dttm, 1, 6)
    ) ord ON report.dt = ord.ord_dttm;


