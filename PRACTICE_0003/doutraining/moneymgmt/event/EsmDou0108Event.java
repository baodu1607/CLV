/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : EsmDou0108Event.java
*@FileTitle : Money Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.07.05
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.22 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining.moneymgmt.event;

import com.clt.apps.opus.esm.clv.doutraining.moneymgmt.vo.ConditionVO;
import com.clt.apps.opus.esm.clv.doutraining.moneymgmt.vo.DetailVO;
import com.clt.apps.opus.esm.clv.doutraining.moneymgmt.vo.SummaryVO;
import com.clt.framework.support.layer.event.EventSupport;


/**
 * ESM_DOU_0108 for PDTO(Data Transfer Object including Parameters)<br>
 * - Created from ESM_DOU_0108HTMLAction<br>
 * - Used as PDTO delivered to ServiceCommand Layer<br>
 *
 * @author phuoc
 * @see ESM_DOU_0108HTMLAction refer
 * @since J2EE 1.6
 */

public class EsmDou0108Event extends EventSupport {

	private static final long serialVersionUID = 1L;

	/** Table Value Object 조회 조건 및 단건 처리  */
	SummaryVO summaryVO = null;
	
	DetailVO detailVO = null;
	
	ConditionVO conditionVO = null;

	public ConditionVO getConditionVO() {
		return conditionVO;
	}

	public void setConditionVO(ConditionVO conditionVO) {
		this.conditionVO = conditionVO;
	}

	public EsmDou0108Event(){}
	
	public void setSummaryVO(SummaryVO summaryVO){
		this. summaryVO = summaryVO;
	}

	public void setDetailVO(DetailVO detailVO) {
		this.detailVO = detailVO;
	}

	public SummaryVO getSummaryVO(){
		return summaryVO;
	}

	public DetailVO getDetailVO() {
		return detailVO;
	}
}