/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : CarrierMgmtDAOCarrierVORSQL.java
*@FileTitle : 
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.20
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.20 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining.carriermgmt.integration;

import java.util.HashMap;
import org.apache.log4j.Logger;
import com.clt.framework.support.db.ISQLTemplate;

/**
 *
 * @author phuoc
 * @see DAO 참조
 * @since J2EE 1.6
 */

public class CarrierMgmtDAOCarrierVORSQL implements ISQLTemplate{

	private StringBuffer query = new StringBuffer();
	
	Logger log =Logger.getLogger(this.getClass());
	
	/** Parameters definition in params/param elements */
	private HashMap<String,String[]> params = null;
	
	/**
	  * <pre>
	  * CarrierMgmt
	  * </pre>
	  */
	public CarrierMgmtDAOCarrierVORSQL(){
		setQuery();
		params = new HashMap<String,String[]>();
		String tmp = null;
		String[] arrTmp = null;
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
		params.put("cre_dt_to",new String[]{arrTmp[0],arrTmp[1]});

		tmp = java.sql.Types.VARCHAR + ",N";
		arrTmp = tmp.split(",");
		if(arrTmp.length !=2){
			throw new IllegalArgumentException();
		}
		params.put("cre_dt_fr",new String[]{arrTmp[0],arrTmp[1]});

		query.append("/*").append("\n"); 
		query.append("Path : com.clt.apps.opus.esm.clv.doutraining.carriermgmt.integration").append("\n"); 
		query.append("FileName : CarrierMgmtDAOCarrierVORSQL").append("\n"); 
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
		query.append("SELECT " ).append("\n"); 
		query.append("	MODI_COST_CTR_CD" ).append("\n"); 
		query.append(",	EDW_UPD_DT" ).append("\n"); 
		query.append(",	UPD_USR_ID" ).append("\n"); 
		query.append(",	UPD_DT" ).append("\n"); 
		query.append(",	CRE_USR_ID" ).append("\n"); 
		query.append(",	CRE_DT" ).append("\n"); 
		query.append(",	DELT_FLG" ).append("\n"); 
		query.append(",	JO_STL_OPT_CD" ).append("\n"); 
		query.append(",	TRD_CD" ).append("\n"); 
		query.append(",	CUST_SEQ" ).append("\n"); 
		query.append(",	CUST_CNT_CD" ).append("\n"); 
		query.append(",	VNDR_SEQ" ).append("\n"); 
		query.append(",	RLANE_CD" ).append("\n"); 
		query.append(",	JO_CRR_CD" ).append("\n"); 
		query.append("FROM JOO_CARRIER" ).append("\n"); 
		query.append("WHERE 1=1" ).append("\n"); 
		query.append("#if (${jo_crr_cd} != '' && ${jo_crr_cd} != 'All')" ).append("\n"); 
		query.append("and jo_crr_cd in (" ).append("\n"); 
		query.append("	#foreach($key IN ${crrCds})" ).append("\n"); 
		query.append("		#if($velocityCount < $crrCds.size()) " ).append("\n"); 
		query.append("			'$key', " ).append("\n"); 
		query.append("		#else " ).append("\n"); 
		query.append("			'$key' " ).append("\n"); 
		query.append("		#end " ).append("\n"); 
		query.append("	#end" ).append("\n"); 
		query.append(")" ).append("\n"); 
		query.append("#end" ).append("\n"); 
		query.append("" ).append("\n"); 
		query.append("" ).append("\n"); 
		query.append("#if (${rlane_cd} != '')" ).append("\n"); 
		query.append("and rlane_cd like @[rlane_cd]" ).append("\n"); 
		query.append("#end" ).append("\n"); 
		query.append("" ).append("\n"); 
		query.append("#if (${vndr_seq} != '')" ).append("\n"); 
		query.append("and vndr_seq like '%'||@[vndr_seq]|| '%'" ).append("\n"); 
		query.append("#end" ).append("\n"); 
		query.append("" ).append("\n"); 
		query.append("#if (${cre_dt_fr} != '' && ${cre_dt_to} == '')" ).append("\n"); 
		query.append("and cre_dt >= to_date(@[cre_dt_fr],'YYYY-MM-DD')" ).append("\n"); 
		query.append("#end" ).append("\n"); 
		query.append("" ).append("\n"); 
		query.append("#if (${cre_dt_fr} == '' && ${cre_dt_to} != '')" ).append("\n"); 
		query.append("and cre_dt <= to_date(@[cre_dt_to],'YYYY-MM-DD')" ).append("\n"); 
		query.append("#end" ).append("\n"); 
		query.append("" ).append("\n"); 
		query.append("#if (${cre_dt_fr} != '' && ${cre_dt_to} != '')" ).append("\n"); 
		query.append("and cre_dt between to_date(@[cre_dt_fr],'YYYY-MM-DD') and to_date(@[cre_dt_to],'YYYY-MM-DD') " ).append("\n"); 
		query.append("#end" ).append("\n"); 

	}
}