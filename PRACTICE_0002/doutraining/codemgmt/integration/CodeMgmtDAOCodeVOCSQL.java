/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : CodeMgmtDAOCodeVOCSQL.java
*@FileTitle : 
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.14
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.14 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining.codemgmt.integration ;

import java.util.HashMap;
import org.apache.log4j.Logger;
import com.clt.framework.support.db.ISQLTemplate;

/**
 *
 * @author phuoc
 * @see DAO 참조
 * @since J2EE 1.6
 */

public class CodeMgmtDAOCodeVOCSQL implements ISQLTemplate{

	private StringBuffer query = new StringBuffer();
	
	Logger log =Logger.getLogger(this.getClass());
	
	/** Parameters definition in params/param elements */
	private HashMap<String,String[]> params = null;
	
	/**
	  * <pre>
	  * CodeMgmt
	  * </pre>
	  */
	public CodeMgmtDAOCodeVOCSQL(){
		setQuery();
		params = new HashMap<String,String[]>();
		String tmp = null;
		String[] arrTmp = null;
		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("intg_cd_desc",new String[]{arrTmp[0],arrTmp[1]});

		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("mng_tbl_nm",new String[]{arrTmp[0],arrTmp[1]});

		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("intg_cd_nm",new String[]{arrTmp[0],arrTmp[1]});

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
		params.put("intg_cd_tp_cd",new String[]{arrTmp[0],arrTmp[1]});

		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("intg_cd_use_flg",new String[]{arrTmp[0],arrTmp[1]});

		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("ownr_sub_sys_cd",new String[]{arrTmp[0],arrTmp[1]});

		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("intg_cd_id",new String[]{arrTmp[0],arrTmp[1]});

		tmp = java.sql.Types.NUMERIC + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("intg_cd_len",new String[]{arrTmp[0],arrTmp[1]});

		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("cre_usr_id",new String[]{arrTmp[0],arrTmp[1]});

		query.append("/*").append("\n"); 
		query.append("Path : com.clt.apps.opus.esm.clv.doutraining.codemgmt.integration ").append("\n"); 
		query.append("FileName : CodeMgmtDAOCodeVOCSQL").append("\n"); 
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
		query.append("INSERT INTO COM_INTG_CD (" ).append("\n"); 
		query.append("	intg_cd_id," ).append("\n"); 
		query.append("    intg_cd_nm," ).append("\n"); 
		query.append("    intg_cd_desc," ).append("\n"); 
		query.append("    intg_cd_tp_cd," ).append("\n"); 
		query.append("    intg_cd_dat_tp_nm," ).append("\n"); 
		query.append("    intg_cd_len," ).append("\n"); 
		query.append("    ownr_sub_sys_cd," ).append("\n"); 
		query.append("    mng_tbl_nm," ).append("\n"); 
		query.append("    intg_cd_use_flg," ).append("\n"); 
		query.append("    cre_usr_id," ).append("\n"); 
		query.append("    cre_dt," ).append("\n"); 
		query.append("    upd_usr_id," ).append("\n"); 
		query.append("    upd_dt" ).append("\n"); 
		query.append(") VALUES( " ).append("\n"); 
		query.append("	@[intg_cd_id]," ).append("\n"); 
		query.append("	@[intg_cd_nm]," ).append("\n"); 
		query.append("	@[intg_cd_desc]," ).append("\n"); 
		query.append("	@[intg_cd_tp_cd]," ).append("\n"); 
		query.append("	'VARCHAR2', " ).append("\n"); 
		query.append("	@[intg_cd_len], " ).append("\n"); 
		query.append("	@[ownr_sub_sys_cd], " ).append("\n"); 
		query.append("	@[mng_tbl_nm], " ).append("\n"); 
		query.append("	@[intg_cd_use_flg], " ).append("\n"); 
		query.append("	@[cre_usr_id]," ).append("\n"); 
		query.append("	sysdate," ).append("\n"); 
		query.append("	@[upd_usr_id]," ).append("\n"); 
		query.append("	sysdate" ).append("\n"); 
		query.append(")" ).append("\n"); 

	}
}