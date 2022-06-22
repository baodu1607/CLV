/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : CarrierMgmtDAOCarrierVOCSQL.java
*@FileTitle : 
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.20
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.20 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining.carriermgmt.integration ;

import java.util.HashMap;
import org.apache.log4j.Logger;
import com.clt.framework.support.db.ISQLTemplate;

/**
 *
 * @author phuoc
 * @see DAO 참조
 * @since J2EE 1.6
 */

public class CarrierMgmtDAOCarrierVOCSQL implements ISQLTemplate{

	private StringBuffer query = new StringBuffer();
	
	Logger log =Logger.getLogger(this.getClass());
	
	/** Parameters definition in params/param elements */
	private HashMap<String,String[]> params = null;
	
	/**
	  * <pre>
	  * CarrierMgmt
	  * </pre>
	  */
	public CarrierMgmtDAOCarrierVOCSQL(){
		setQuery();
		params = new HashMap<String,String[]>();
		String tmp = null;
		String[] arrTmp = null;
		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("jo_crr_cd",new String[]{arrTmp[0],arrTmp[1]});

		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("vndr_seq",new String[]{arrTmp[0],arrTmp[1]});

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
		params.put("upd_usr_id",new String[]{arrTmp[0],arrTmp[1]});

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
		params.put("cust_seq",new String[]{arrTmp[0],arrTmp[1]});

		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("delt_flg",new String[]{arrTmp[0],arrTmp[1]});

		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("cre_usr_id",new String[]{arrTmp[0],arrTmp[1]});

		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("cust_cnt_cd",new String[]{arrTmp[0],arrTmp[1]});

		query.append("/*").append("\n"); 
		query.append("Path : com.clt.apps.opus.esm.clv.doutraining.carriermgmt.integration ").append("\n"); 
		query.append("FileName : CarrierMgmtDAOCarrierVOCSQL").append("\n"); 
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
		query.append("insert into joo_carrier " ).append("\n"); 
		query.append("(" ).append("\n"); 
		query.append("    jo_crr_cd, " ).append("\n"); 
		query.append("    rlane_cd, " ).append("\n"); 
		query.append("    vndr_seq, " ).append("\n"); 
		query.append("    cust_cnt_cd, " ).append("\n"); 
		query.append("    cust_seq, " ).append("\n"); 
		query.append("    trd_cd, " ).append("\n"); 
		query.append("    delt_flg, " ).append("\n"); 
		query.append("    cre_dt, " ).append("\n"); 
		query.append("    cre_usr_id, " ).append("\n"); 
		query.append("    upd_dt, " ).append("\n"); 
		query.append("    upd_usr_id" ).append("\n"); 
		query.append(")" ).append("\n"); 
		query.append("values" ).append("\n"); 
		query.append("(" ).append("\n"); 
		query.append("	@[jo_crr_cd]," ).append("\n"); 
		query.append("	@[rlane_cd]," ).append("\n"); 
		query.append("	@[vndr_seq]," ).append("\n"); 
		query.append("	@[cust_cnt_cd]," ).append("\n"); 
		query.append("	@[cust_seq]," ).append("\n"); 
		query.append("	@[trd_cd]," ).append("\n"); 
		query.append("	@[delt_flg]," ).append("\n"); 
		query.append("	sysdate," ).append("\n"); 
		query.append("	@[cre_usr_id]," ).append("\n"); 
		query.append("	sysdate," ).append("\n"); 
		query.append("	@[upd_usr_id]	   " ).append("\n"); 
		query.append(")	" ).append("\n"); 

	}
}