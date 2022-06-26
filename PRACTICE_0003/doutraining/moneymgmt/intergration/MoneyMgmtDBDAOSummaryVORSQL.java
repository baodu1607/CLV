/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : MoneyMgmtDBDAOSummaryVORSQL.java
*@FileTitle : 
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.25
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.25 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining.moneymgmt.intergration;

import java.util.HashMap;
import org.apache.log4j.Logger;
import com.clt.framework.support.db.ISQLTemplate;

/**
 *
 * @author phuoc
 * @see DAO 참조
 * @since J2EE 1.6
 */

public class MoneyMgmtDBDAOSummaryVORSQL implements ISQLTemplate{

	private StringBuffer query = new StringBuffer();
	
	Logger log =Logger.getLogger(this.getClass());
	
	/** Parameters definition in params/param elements */
	private HashMap<String,String[]> params = null;
	
	/**
	  * <pre>
	  *    
	  * </pre>
	  */
	public MoneyMgmtDBDAOSummaryVORSQL(){
		setQuery();
		params = new HashMap<String,String[]>();
		String tmp = null;
		String[] arrTmp = null;
		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("rlane_cd",new String[]{arrTmp[0],arrTmp[1]});

		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("date_to",new String[]{arrTmp[0],arrTmp[1]});

		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("trd_cd",new String[]{arrTmp[0],arrTmp[1]});

		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("date_fr",new String[]{arrTmp[0],arrTmp[1]});

		query.append("/*").append("\n"); 
		query.append("Path : com.clt.apps.opus.esm.clv.doutraining.moneymgmt.intergration").append("\n"); 
		query.append("FileName : MoneyMgmtDBDAOSummaryVORSQL").append("\n"); 
		query.append("*/").append("\n"); 
	}
	
	public String getSQL(){
		return query.toString();
	}
	
	public HashMap<String,String[]> getParams() {
		return params;
	}

	/**
	 * Query 생성
	 */
	public void setQuery(){
		query.append("SELECT INV.JO_CRR_CD" ).append("\n"); 
		query.append("     , INV.RLANE_CD" ).append("\n"); 
		query.append("     , INV.LOCL_CURR_CD" ).append("\n"); 
		query.append("     , INV.INV_NO" ).append("\n"); 
		query.append("     , INV.CSR_NO" ).append("\n"); 
		query.append("     , INV.APRO_FLG" ).append("\n"); 
		query.append("     , INV.CUST_VNDR_CNT_CD" ).append("\n"); 
		query.append("     , INV.CUST_VNDR_SEQ" ).append("\n"); 
		query.append("     , INV.PRNR_REF_NO" ).append("\n"); 
		query.append("     , INV.CUST_VNDR_ENG_NM" ).append("\n"); 
		query.append("     , SUM(INV.REV_ACT_AMT) AS INV_REV_ACT_AMT" ).append("\n"); 
		query.append("     , SUM(INV.EXP_ACT_AMT) AS INV_EXP_ACT_AMT" ).append("\n"); 
		query.append("FROM (" ).append("\n"); 
		query.append("    SELECT INV.JO_CRR_CD" ).append("\n"); 
		query.append("     , STL.RLANE_CD" ).append("\n"); 
		query.append("     , NVL(INV.INV_CURR_CD, INV.LOCL_CURR_CD) AS LOCL_CURR_CD" ).append("\n"); 
		query.append("     , DTL.ACT_AMT" ).append("\n"); 
		query.append("     , INV.INV_NO" ).append("\n"); 
		query.append("     , NVL(INV.SLP_TP_CD||INV.SLP_FUNC_CD||INV.SLP_OFC_CD||INV.SLP_ISS_DT||INV.SLP_SER_NO,'N/A') AS CSR_NO" ).append("\n"); 
		query.append("     , NVL(CSR.APRO_FLG, 'N') AS APRO_FLG" ).append("\n"); 
		query.append("     , DECODE(INV.RE_DIVR_CD,'R',SUBSTR(INV.PRNR_REF_NO,1,2), NULL) AS CUST_VNDR_CNT_CD" ).append("\n"); 
		query.append("     , DECODE(INV.RE_DIVR_CD,'R',SUBSTR(INV.PRNR_REF_NO,3), INV.PRNR_REF_NO) AS CUST_VNDR_SEQ" ).append("\n"); 
		query.append("     , INV.PRNR_REF_NO" ).append("\n"); 
		query.append("     , DECODE('R', STL.RE_DIVR_CD, NVL(DTL.LOCL_AMT, DTL.ACT_AMT), 0) AS REV_ACT_AMT" ).append("\n"); 
		query.append("     , DECODE('E', STL.RE_DIVR_CD, NVL(DTL.LOCL_AMT, DTL.ACT_AMT), 0) AS EXP_ACT_AMT" ).append("\n"); 
		query.append("     , CASE WHEN INV.RE_DIVR_CD = 'R' THEN" ).append("\n"); 
		query.append("                 ( SELECT M.CUST_LGL_ENG_NM" ).append("\n"); 
		query.append("                     FROM MDM_CUSTOMER M" ).append("\n"); 
		query.append("                    WHERE M.DELT_FLG = 'N'" ).append("\n"); 
		query.append("                      AND M.CUST_CNT_CD   = SUBSTR(INV.PRNR_REF_NO,1,2)" ).append("\n"); 
		query.append("                      AND M.CUST_SEQ      = SUBSTR(INV.PRNR_REF_NO,3) )" ).append("\n"); 
		query.append("            ELSE" ).append("\n"); 
		query.append("                 ( SELECT M.VNDR_LGL_ENG_NM" ).append("\n"); 
		query.append("                     FROM MDM_VENDOR M" ).append("\n"); 
		query.append("                    WHERE M.DELT_FLG = 'N'" ).append("\n"); 
		query.append("                      AND M.VNDR_SEQ      = INV.PRNR_REF_NO )" ).append("\n"); 
		query.append("       END AS CUST_VNDR_ENG_NM" ).append("\n"); 
		query.append("    FROM JOO_INVOICE INV" ).append("\n"); 
		query.append("     , JOO_INV_DTL DTL" ).append("\n"); 
		query.append("     , JOO_STL_TGT STL" ).append("\n"); 
		query.append("     , JOO_CSR     CSR" ).append("\n"); 
		query.append("    WHERE 1=1" ).append("\n"); 
		query.append("    AND NVL(STL.THEA_STL_FLG, 'N') = 'N'" ).append("\n"); 
		query.append("	AND INV.ACCT_YRMON   BETWEEN REPLACE(@[date_fr],'-','') AND REPLACE(@[date_to],'-','')" ).append("\n"); 
		query.append("	#if (${jo_crr_cd} != '' && ${jo_crr_cd} != 'All')" ).append("\n"); 
		query.append("		and INV.JO_CRR_CD in (" ).append("\n"); 
		query.append("			#foreach($key IN ${partnerCodes})" ).append("\n"); 
		query.append("				#if($velocityCount < $partnerCodes.size()) " ).append("\n"); 
		query.append("					'$key', " ).append("\n"); 
		query.append("				#else " ).append("\n"); 
		query.append("					'$key' " ).append("\n"); 
		query.append("				#end " ).append("\n"); 
		query.append("			#end" ).append("\n"); 
		query.append("		)" ).append("\n"); 
		query.append("	#end" ).append("\n"); 
		query.append("	#if (${rlane_cd}!='')" ).append("\n"); 
		query.append("		AND STL.RLANE_CD   = @[rlane_cd]" ).append("\n"); 
		query.append("	#end" ).append("\n"); 
		query.append("	#if (${trd_cd}!='')" ).append("\n"); 
		query.append("	   AND EXISTS   (   SELECT 'X'" ).append("\n"); 
		query.append("						  FROM JOO_CARRIER CRR" ).append("\n"); 
		query.append("						 WHERE 1=1" ).append("\n"); 
		query.append("						   AND CRR.DELT_FLG         = 'N'" ).append("\n"); 
		query.append("						   AND CRR.JO_CRR_CD        = STL.JO_CRR_CD" ).append("\n"); 
		query.append("						   AND CRR.RLANE_CD         = STL.RLANE_CD" ).append("\n"); 
		query.append("						   AND CRR.TRD_CD = @[trd_cd]" ).append("\n"); 
		query.append("					 )" ).append("\n"); 
		query.append("	#end" ).append("\n"); 
		query.append("    AND INV.RJCT_CMB_FLG  = 'N'" ).append("\n"); 
		query.append("    AND DTL.ACCT_YRMON    = INV.ACCT_YRMON" ).append("\n"); 
		query.append("    AND DTL.JO_CRR_CD     = INV.JO_CRR_CD" ).append("\n"); 
		query.append("    AND DTL.INV_NO        = INV.INV_NO" ).append("\n"); 
		query.append("    AND DTL.RE_DIVR_CD    = INV.RE_DIVR_CD" ).append("\n"); 
		query.append("    AND STL.THEA_STL_FLG  = INV.THEA_STL_FLG" ).append("\n"); 
		query.append("    AND STL.VSL_CD        = DTL.VSL_CD" ).append("\n"); 
		query.append("    AND STL.SKD_VOY_NO    = DTL.SKD_VOY_NO" ).append("\n"); 
		query.append("    AND STL.SKD_DIR_CD    = DTL.SKD_DIR_CD" ).append("\n"); 
		query.append("    AND STL.REV_DIR_CD    = DTL.REV_DIR_CD" ).append("\n"); 
		query.append("    AND STL.REV_YRMON     = DTL.REV_YRMON" ).append("\n"); 
		query.append("    AND STL.STL_VVD_SEQ   = DTL.STL_VVD_SEQ" ).append("\n"); 
		query.append("    AND INV.SLP_TP_CD     = CSR.SLP_TP_CD(+)" ).append("\n"); 
		query.append("    AND INV.SLP_FUNC_CD   = CSR.SLP_FUNC_CD(+)" ).append("\n"); 
		query.append("    AND INV.SLP_OFC_CD    = CSR.SLP_OFC_CD(+)" ).append("\n"); 
		query.append("    AND INV.SLP_ISS_DT    = CSR.SLP_ISS_DT(+)" ).append("\n"); 
		query.append("    AND INV.SLP_SER_NO    = CSR.SLP_SER_NO(+)" ).append("\n"); 
		query.append("    ) INV" ).append("\n"); 
		query.append(" WHERE 1=1" ).append("\n"); 
		query.append(" GROUP BY GROUPING SETS ((JO_CRR_CD, RLANE_CD, CSR_NO, APRO_FLG, CUST_VNDR_CNT_CD, CUST_VNDR_SEQ, PRNR_REF_NO, CUST_VNDR_ENG_NM, LOCL_CURR_CD, INV_NO)," ).append("\n"); 
		query.append("                         (LOCL_CURR_CD, INV_NO)," ).append("\n"); 
		query.append("                         (LOCL_CURR_CD))" ).append("\n"); 
		query.append(" ORDER BY INV_NO, JO_CRR_CD" ).append("\n"); 

	}
}