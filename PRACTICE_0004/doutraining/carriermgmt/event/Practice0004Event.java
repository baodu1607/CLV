/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : Practice0001Event.java
*@FileTitle : Error Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.11
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.11 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining.carriermgmt.event;

import com.clt.apps.opus.esm.clv.doutraining.carriermgmt.vo.CarrierVO;
import com.clt.apps.opus.esm.clv.doutraining.errmsgmgmt.vo.ErrMsgVO;
import com.clt.framework.support.layer.event.EventSupport;

public class Practice0004Event extends EventSupport {
	//The serialVersionUID attribute is an identifier that is used to serialize/deserialize an object
	private static final long serialVersionUID = 1L;
	
	/** Table Value Object 조회 조건 및 단건 처리  */
	//VO for single data
	CarrierVO carrierVO = null;
	
	CarrierVO[] carrierVOs = null;
	
	public CarrierVO[] getCarrierVOs() {
		return carrierVOs;
	}

	public void setCarrierVOs(CarrierVO[] carrierVOs) {
		this.carrierVOs = carrierVOs;
	}

	public Practice0004Event(){}

	public CarrierVO getCarrierVO() {
		return carrierVO;
	}


	public void setCarrierVO(CarrierVO carrierVO) {
		this.carrierVO = carrierVO;
	}


	

}