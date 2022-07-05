/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ConditionVO.java
*@FileTitle : ConditionVO
*Open Issues :
*Change history :
*@LastModifyDate : 2022.07.05
*@LastModifier : 
*@LastVersion : 1.0
* 2022.07.05  
* 1.0 Creation
=========================================================*/

package com.clt.apps.opus.esm.clv.doutraining.moneymgmt.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.clt.framework.component.common.AbstractValueObject;
import com.clt.framework.component.util.JSPUtil;

/**
 * Table Value Ojbect<br>
 * 관련 Event 에서 생성, 서버실행요청시 Data 전달역할을 수행하는 Value Object
 *
 * @author 
 * @since J2EE 1.6
 * @see AbstractValueObject
 */

public class ConditionVO extends AbstractValueObject {

	private static final long serialVersionUID = 1L;
	
	private Collection<ConditionVO> models = new ArrayList<ConditionVO>();
	
	/* VO Data Value( C:Creation, U:Update, D:Delete ) */
	private String ibflag = null;
	/* Column Info */
	private String sDateTo = null;
	/* Column Info */
	private String sDateFr = null;
	/* Column Info */
	private String sTradeCode = null;
	/* Column Info */
	private String sPartnerCode = null;
	/* Column Info */
	private String sLaneCode = null;
	/* Page Number */
	private String pagerows = null;

	/*	테이블 컬럼의 값을 저장하는 Hashtable */
	private HashMap<String, String> hashColumns = new LinkedHashMap<String, String>();

	/*	테이블 컬럼에 대응되는 멤버변수를 저장하는 Hashtable */
	private HashMap<String, String> hashFields = new LinkedHashMap<String, String>();
	
	public ConditionVO() {}

	public ConditionVO(String ibflag, String pagerows, String sPartnerCode, String sLaneCode, String sTradeCode, String sDateTo, String sDateFr) {
		this.ibflag = ibflag;
		this.sDateTo = sDateTo;
		this.sDateFr = sDateFr;
		this.sTradeCode = sTradeCode;
		this.sPartnerCode = sPartnerCode;
		this.sLaneCode = sLaneCode;
		this.pagerows = pagerows;
	}
	
	/**
	 * 테이블 컬럼에 저장할 값을 Hashtable<"column_name", "value"> 로 반환
	 * @return HashMap
	 */
	public HashMap<String, String> getColumnValues(){
		this.hashColumns.put("ibflag", getIbflag());
		this.hashColumns.put("date_to", getSDateTo());
		this.hashColumns.put("date_fr", getSDateFr());
		this.hashColumns.put("trd_cd", getSTradeCode());
		this.hashColumns.put("jo_crr_cd", getSPartnerCode());
		this.hashColumns.put("rlane_cd", getSLaneCode());
		this.hashColumns.put("pagerows", getPagerows());
		return this.hashColumns;
	}
	
	/**
	 * 컬럼명에 대응되는 멤버변수명을 저장하여 Hashtable<"column_name", "variable"> 로 반환   
	 * @return
	 */
	public HashMap<String, String> getFieldNames(){
		this.hashFields.put("ibflag", "ibflag");
		this.hashFields.put("s_date_to", "sDateTo");
		this.hashFields.put("s_date_fr", "sDateFr");
		this.hashFields.put("s_trade_code", "sTradeCode");
		this.hashFields.put("s_partner_code", "sPartnerCode");
		this.hashFields.put("s_lane_code", "sLaneCode");
		this.hashFields.put("pagerows", "pagerows");
		return this.hashFields;
	}
	
	/**
	 * VO Data Value( C:Creation, U:Update, D:Delete )
	 * @return ibflag
	 */
	public String getIbflag() {
		return this.ibflag;
	}
	
	/**
	 * Column Info
	 * @return sDateTo
	 */
	public String getSDateTo() {
		return this.sDateTo;
	}
	
	/**
	 * Column Info
	 * @return sDateFr
	 */
	public String getSDateFr() {
		return this.sDateFr;
	}
	
	/**
	 * Column Info
	 * @return sTradeCode
	 */
	public String getSTradeCode() {
		return this.sTradeCode;
	}
	
	/**
	 * Column Info
	 * @return sPartnerCode
	 */
	public String getSPartnerCode() {
		return this.sPartnerCode;
	}
	
	/**
	 * Column Info
	 * @return sLaneCode
	 */
	public String getSLaneCode() {
		return this.sLaneCode;
	}
	
	/**
	 * Page Number
	 * @return pagerows
	 */
	public String getPagerows() {
		return this.pagerows;
	}
	

	/**
	 * VO Data Value( C:Creation, U:Update, D:Delete )
	 * @param ibflag
	 */
	public void setIbflag(String ibflag) {
		this.ibflag = ibflag;
	}
	
	/**
	 * Column Info
	 * @param sDateTo
	 */
	public void setSDateTo(String sDateTo) {
		this.sDateTo = sDateTo;
	}
	
	/**
	 * Column Info
	 * @param sDateFr
	 */
	public void setSDateFr(String sDateFr) {
		this.sDateFr = sDateFr;
	}
	
	/**
	 * Column Info
	 * @param sTradeCode
	 */
	public void setSTradeCode(String sTradeCode) {
		this.sTradeCode = sTradeCode;
	}
	
	/**
	 * Column Info
	 * @param sPartnerCode
	 */
	public void setSPartnerCode(String sPartnerCode) {
		this.sPartnerCode = sPartnerCode;
	}
	
	/**
	 * Column Info
	 * @param sLaneCode
	 */
	public void setSLaneCode(String sLaneCode) {
		this.sLaneCode = sLaneCode;
	}
	
	/**
	 * Page Number
	 * @param pagerows
	 */
	public void setPagerows(String pagerows) {
		this.pagerows = pagerows;
	}
	
/**
	 * Request 의 데이터를 추출하여 VO 의 멤버변수에 설정.
	 * @param request
	 */
	public void fromRequest(HttpServletRequest request) {
		fromRequest(request,"");
	}

	/**
	 * Request 의 데이터를 추출하여 VO 의 멤버변수에 설정.
	 * @param request
	 */
	public void fromRequest(HttpServletRequest request, String prefix) {
		setIbflag(JSPUtil.getParameter(request, prefix + "ibflag", ""));
		setSDateTo(JSPUtil.getParameter(request, prefix + "s_date_to", ""));
		setSDateFr(JSPUtil.getParameter(request, prefix + "s_date_fr", ""));
		setSTradeCode(JSPUtil.getParameter(request, prefix + "s_trade_code", ""));
		setSPartnerCode(JSPUtil.getParameter(request, prefix + "s_partner_code", ""));
		setSLaneCode(JSPUtil.getParameter(request, prefix + "s_lane_code", ""));
		setPagerows(JSPUtil.getParameter(request, prefix + "pagerows", ""));
	}

	/**
	 * Request 의 데이터를 VO 배열로 변환하여 반환.
	 * @param request
	 * @return ConditionVO[]
	 */
	public ConditionVO[] fromRequestGrid(HttpServletRequest request) {
		return fromRequestGrid(request, "");
	}

	/**
	 * Request 넘어온 여러 건 DATA를 VO Class 에 담는다. 
	 * @param request
	 * @param prefix
	 * @return ConditionVO[]
	 */
	public ConditionVO[] fromRequestGrid(HttpServletRequest request, String prefix) {
		ConditionVO model = null;
		
		String[] tmp = request.getParameterValues(prefix + "ibflag");
  		if(tmp == null)
   			return null;

  		int length = request.getParameterValues(prefix + "ibflag").length;
  
		try {
			String[] ibflag = (JSPUtil.getParameter(request, prefix	+ "ibflag", length));
			String[] sDateTo = (JSPUtil.getParameter(request, prefix	+ "s_date_to", length));
			String[] sDateFr = (JSPUtil.getParameter(request, prefix	+ "s_date_fr", length));
			String[] sTradeCode = (JSPUtil.getParameter(request, prefix	+ "s_trade_code", length));
			String[] sPartnerCode = (JSPUtil.getParameter(request, prefix	+ "s_partner_code", length));
			String[] sLaneCode = (JSPUtil.getParameter(request, prefix	+ "s_lane_code", length));
			String[] pagerows = (JSPUtil.getParameter(request, prefix	+ "pagerows", length));
			
			for (int i = 0; i < length; i++) {
				model = new ConditionVO();
				if (ibflag[i] != null)
					model.setIbflag(ibflag[i]);
				if (sDateTo[i] != null)
					model.setSDateTo(sDateTo[i]);
				if (sDateFr[i] != null)
					model.setSDateFr(sDateFr[i]);
				if (sTradeCode[i] != null)
					model.setSTradeCode(sTradeCode[i]);
				if (sPartnerCode[i] != null)
					model.setSPartnerCode(sPartnerCode[i]);
				if (sLaneCode[i] != null)
					model.setSLaneCode(sLaneCode[i]);
				if (pagerows[i] != null)
					model.setPagerows(pagerows[i]);
				models.add(model);
			}

		} catch (Exception e) {
			return null;
		}
		return getConditionVOs();
	}

	/**
	 * VO 배열을 반환
	 * @return ConditionVO[]
	 */
	public ConditionVO[] getConditionVOs(){
		ConditionVO[] vos = (ConditionVO[])models.toArray(new ConditionVO[models.size()]);
		return vos;
	}
	
	/**
	 * VO Class의 내용을 String으로 변환
	 */
	public String toString() {
		   return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE );
	   }

	/**
	* 포맷팅된 문자열에서 특수문자 제거("-","/",",",":")
	*/
	public void unDataFormat(){
		this.ibflag = this.ibflag .replaceAll(",", "").replaceAll("-", "").replaceAll("/", "").replaceAll(":", "");
		this.sDateTo = this.sDateTo .replaceAll(",", "").replaceAll("-", "").replaceAll("/", "").replaceAll(":", "");
		this.sDateFr = this.sDateFr .replaceAll(",", "").replaceAll("-", "").replaceAll("/", "").replaceAll(":", "");
		this.sTradeCode = this.sTradeCode .replaceAll(",", "").replaceAll("-", "").replaceAll("/", "").replaceAll(":", "");
		this.sPartnerCode = this.sPartnerCode .replaceAll(",", "").replaceAll("-", "").replaceAll("/", "").replaceAll(":", "");
		this.sLaneCode = this.sLaneCode .replaceAll(",", "").replaceAll("-", "").replaceAll("/", "").replaceAll(":", "");
		this.pagerows = this.pagerows .replaceAll(",", "").replaceAll("-", "").replaceAll("/", "").replaceAll(":", "");
	}
}
