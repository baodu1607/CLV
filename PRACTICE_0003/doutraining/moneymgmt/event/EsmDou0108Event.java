/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : EsmDou0108Event.java
*@FileTitle : Money Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.22
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.22 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining.moneymgmt.event;

import com.clt.framework.support.layer.event.EventSupport;
import com.clt.apps.opus.esm.clv.doutraining.moneymgmt.vo.DetailVO;
import com.clt.apps.opus.esm.clv.doutraining.moneymgmt.vo.SummaryVO;


/**
 * ESM_DOU_0108 에 대한 PDTO(Data Transfer Object including Parameters)<br>
 * -  ESM_DOU_0108HTMLAction에서 작성<br>
 * - ServiceCommand Layer로 전달하는 PDTO로 사용<br>
 *
 * @author phuoc
 * @see ESM_DOU_0108HTMLAction 참조
 * @since J2EE 1.6
 */

public class EsmDou0108Event extends EventSupport {

	private static final long serialVersionUID = 1L;

	/** Table Value Object 조회 조건 및 단건 처리  */
	SummaryVO summaryVO = null;
	
	DetailVO detailVO = null;
	
	/** Table Value Object Multi Data 처리 */
	SummaryVO[] summaryVOs = null;

	public EsmDou0108Event(){}
	
	public void setSummaryVO(SummaryVO summaryVO){
		this. summaryVO = summaryVO;
	}

	public void setDetailVO(DetailVO detailVO) {
		this.detailVO = detailVO;
	}
	
	public void setSummaryVOS(SummaryVO[] summaryVOs){
		this. summaryVOs = summaryVOs;
	}

	public SummaryVO getSummaryVO(){
		return summaryVO;
	}

	public SummaryVO[] getSummaryVOS(){
		return summaryVOs;
	}

	public DetailVO getDetailVO() {
		return detailVO;
	}
}