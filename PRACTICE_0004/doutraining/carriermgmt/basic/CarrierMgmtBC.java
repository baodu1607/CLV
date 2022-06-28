/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName       : CarrierMgmtBC.java
*@FileTitle      : Interface Carrier Management Business Component
*Open Issues     :
*Change history  :
*@LastModifyDate : 2022.06.28
*@LastModifier   : 
*@LastVersion    : 1.0
* 2022.06.28 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining.carriermgmt.basic;

import java.util.List;

import com.clt.apps.opus.esm.clv.doutraining.carriermgmt.vo.CarrierVO;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.support.view.signon.SignOnUserAccount;

/**
 * ALPS-CarrierManagement Business Logic Command Interface<br>
 * - Interface to business logic for ALPS-SguTranning<br>
 * @author Thien Phuoc
 * @since J2EE 1.6
 */
public interface CarrierMgmtBC {
	/**
	 * This method is used for searching carrier code 
	 * @return List<CarrierVO>
	 * @throws EventException
	 */
	public List<CarrierVO> getCrrCds() throws EventException;
	
	/**
	 * This method is used for searching lane code 
	 * @return
	 * @throws EventException
	 */
	public List<CarrierVO> getLnCds() throws EventException;
	
	/**
	 * This method is used for searching carrier VO 
	 * @param carrierVO
	 * @return List<CarrierVO>
	 * @throws EventException
	 */
	public List<CarrierVO> searchCarrierVO(CarrierVO carrierVO) throws EventException;
	
	/**
	 * This method is used for searching Custom popup
	 * @param carrierVO
	 * @return List<CarrierVO>
	 * @throws EventException
	 */
	public List<CarrierVO> searchCust(CarrierVO carrierVO) throws EventException;
	
	/**
	 * This method is used for saving date
	 * @param carrierVO
	 * @param account
	 * @throws EventException
	 */
	public void manageCarrierVO(CarrierVO[] carrierVO,SignOnUserAccount account) throws EventException;
}