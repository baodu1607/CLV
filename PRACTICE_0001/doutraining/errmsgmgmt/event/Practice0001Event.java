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
package com.clt.apps.opus.esm.clv.doutraining.errmsgmgmt.event;

import com.clt.framework.support.layer.event.EventSupport;
import com.clt.apps.opus.esm.clv.doutraining.errmsgmgmt.vo.ErrMsgVO;


/**
 * PRACTICE_0001 에 대한 PDTO(Data Transfer Object including Parameters)<br>
 * -  PRACTICE_0001HTMLAction에서 작성<br>
 * - ServiceCommand Layer로 전달하는 PDTO로 사용<br>
 *
 * @author phuoc
 * @see PRACTICE_0001HTMLAction 참조
 * @since J2EE 1.6
 */
public class Practice0001Event extends EventSupport {
	//The serialVersionUID attribute is an identifier that is used to serialize/deserialize an object
	private static final long serialVersionUID = 1L;
	
	/** Table Value Object 조회 조건 및 단건 처리  */
	//VO for single data
	ErrMsgVO errMsgVO = null;
	
	/** Table Value Object Multi Data 처리 */
	//VO for many data
	ErrMsgVO[] errMsgVOs = null;

	public Practice0001Event(){}
	//Setter
	public void setErrMsgVO(ErrMsgVO errMsgVO){
		this. errMsgVO = errMsgVO;
	}

	public void setErrMsgVOS(ErrMsgVO[] errMsgVOs){
		this. errMsgVOs = errMsgVOs;
	}
	//Getter
	public ErrMsgVO getErrMsgVO(){
		return errMsgVO;
	}

	public ErrMsgVO[] getErrMsgVOS(){
		return errMsgVOs;
	}

}