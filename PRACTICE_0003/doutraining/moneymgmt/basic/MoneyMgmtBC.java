/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : MoneyMgmtBC.java
*@FileTitle : Money Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.22
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.22 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining.moneymgmt.basic;

import java.util.List;

import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.doutraining.carriermgmt.vo.CarrierVO;
import com.clt.apps.opus.esm.clv.doutraining.moneymgmt.vo.DetailVO;
import com.clt.apps.opus.esm.clv.doutraining.moneymgmt.vo.SummaryVO;

/**
 * ALPS-Moneymgmt Business Logic Command Interface<br>
 * - ALPS-Moneymgmt에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author phuoc
 * @since J2EE 1.6
 */

public interface MoneyMgmtBC {

	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param SummaryVO	summaryVO
	 * @return List<SummaryVO>
	 * @exception EventException
	 */
	public List<SummaryVO> searchSummaryVO(SummaryVO summaryVO) throws EventException;
	public List<DetailVO> searchDetailVO(DetailVO detailVO) throws EventException;
	
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param SummaryVO[] summaryVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void manageSummaryVO(SummaryVO[] summaryVO,SignOnUserAccount account) throws EventException;
	
	public List<SummaryVO> getPartnerCodes() throws EventException;
	
	public List<SummaryVO> getLaneCodes(SummaryVO summaryVO) throws EventException;
	
	public List<SummaryVO> getTradeCodes(SummaryVO summaryVO) throws EventException;
}