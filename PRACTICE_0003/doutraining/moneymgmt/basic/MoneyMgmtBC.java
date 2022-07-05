/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : MoneyMgmtBC.java
*@FileTitle : Money Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.07.05
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.22 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining.moneymgmt.basic;

import java.util.List;

import com.clt.apps.opus.esm.clv.doutraining.moneymgmt.vo.ConditionVO;
import com.clt.apps.opus.esm.clv.doutraining.moneymgmt.vo.DetailVO;
import com.clt.apps.opus.esm.clv.doutraining.moneymgmt.vo.SummaryVO;
import com.clt.framework.core.layer.event.EventException;

/**
 * ALPS-Moneymgmt Business Logic Command Interface<br>
 * - Interface to business logic for ALPS-Moneymgmt<br>
 *
 * @author phuoc
 * @since J2EE 1.6
 */

public interface MoneyMgmtBC {

	/**
	 * This method is used for searching Summary data 
	 * 
	 * @param SummaryVO	summaryVO
	 * @return List<SummaryVO>
	 * @exception EventException
	 */
	public List<SummaryVO> searchSummaryVO(ConditionVO conditionVO) throws EventException;
	/**
	 * This method is used for searching Detail data 
	 * @param detailVO
	 * @return List<DetailVO>
	 * @throws EventException
	 */
	public List<DetailVO> searchDetailVO(ConditionVO conditionVO) throws EventException;

	/**
	 * This method is used for getting data for Partner combo box
	 * @return List<SummaryVO>
	 * @throws EventException
	 */
	public List<SummaryVO> getPartnerCodes() throws EventException;
	
	/**
	 * This method is used for getting data for Lane combo box
	 * @param summaryVO
	 * @return List<SummaryVO>
	 * @throws EventException
	 */
	public List<SummaryVO> getLaneCodes(SummaryVO summaryVO) throws EventException;
	
	/**
	 * This method is used for getting data for Trade combo box
	 * @param summaryVO
	 * @return List<SummaryVO>
	 * @throws EventException
	 */
	public List<SummaryVO> getTradeCodes(SummaryVO summaryVO) throws EventException;
}