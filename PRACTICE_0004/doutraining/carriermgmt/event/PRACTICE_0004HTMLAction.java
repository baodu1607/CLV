/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : PRACTICE_0001HTMLAction.java
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

import javax.servlet.http.HttpServletRequest;

import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.apps.opus.esm.clv.doutraining.carriermgmt.vo.CarrierVO;
import com.clt.apps.opus.esm.clv.doutraining.errmsgmgmt.vo.ErrMsgVO;

/**
 * HTTP Parser<br>
 * - com.clt.apps.opus.esm.clv.doutraining 화면을 통해 서버로 전송되는 HTML DOM 객체의 Value를 자바 변수로 Parsing<br>
 * - Parsing 한 정보를 Event로 변환, request에 담아 DouTrainingSC로 실행요청<br>
 * - DouTrainingSC에서 View(JSP)로 실행결과를 전송하는 EventResponse를 request에 셋팅<br>
 * @author phuoc
 * @see DouTrainingEvent 참조
 * @since J2EE 1.6
 */

public class PRACTICE_0004HTMLAction extends HTMLActionSupport {

	//The serialVersionUID attribute is an identifier that is used to serialize/deserialize an object
	private static final long serialVersionUID = 1L;
	/**
	 * PRACTICE_0001HTMLAction 객체를 생성
	 */
	public PRACTICE_0004HTMLAction() {}

	/**
	 * HTML DOM 객체의 Value를 자바 변수로 Parsing<br>
	 * HttpRequst의 정보를 DouTrainingEvent로 파싱하여 request에 셋팅<br>
	 * @param request HttpServletRequest HttpRequest
	 * @return Event Event interface를 구현한 객체
	 * @exception HTMLActionException
	 */
	public Event perform(HttpServletRequest request) throws HTMLActionException {
		//Get f_command
		FormCommand command = FormCommand.fromRequest(request);
		//Create event object
		Practice0004Event event = new Practice0004Event();
		//if f_command is SEARCH
		if(command.isCommand(FormCommand.SEARCH)) {
			//Create CarrierVO object
			CarrierVO carrierVO = new CarrierVO();
			
			//set parameter has name s_err_msg for ErrMsg
			carrierVO.setJoCrrCd(JSPUtil.getParameter(request, "s_carrier",""));
			carrierVO.setVndrSeq(JSPUtil.getParameter(request, "s_vndr_seq",""));
			carrierVO.setCreDtFr(JSPUtil.getParameter(request, "s_cre_dt_fr",""));
			carrierVO.setCreDtTo(JSPUtil.getParameter(request, "s_cre_dt_to",""));
			//set VO for event
			event.setCarrierVO(carrierVO);
		}else if(command.isCommand(FormCommand.MULTI)) {
			//mapping request to VO base on ErrMsgVO class
			//getVOs return array of VO (we use VOs not VO because we save/insert/delete many rows at same time not only one row)
			//set VOs for event
			event.setCarrierVOs((CarrierVO[])getVOs(request, CarrierVO.class,""));
		}else if(command.isCommand(FormCommand.SEARCH01)) {
			CarrierVO carrierVO = new CarrierVO();
			carrierVO.setCustCntCd(JSPUtil.getParameter(request, "s_cust_cnt_cd",""));
			carrierVO.setCustSeq(JSPUtil.getParameter(request, "s_cust_seq",""));
			//mapping request to VO base on ErrMsgVO class
			//getVOs return array of VO (we use VOs not VO because we save/insert/delete many rows at same time not only one row)
			//set VOs for event
			event.setCarrierVO(carrierVO);
		}
		return  event;
	}

	/**
	 * HttpRequest의 attribute에 업무시나리오 수행결과 값 저장<br>
	 * ServiceCommand에서 View(JSP)로 실행결과를 전송하는 ResultSet을 request에 셋팅<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param eventResponse EventResponse interface를 구현한 객체
	 */
	public void doEnd(HttpServletRequest request, EventResponse eventResponse) {
		//set attribute has key EventResponse for request
		request.setAttribute("EventResponse", eventResponse);
	}

	/**
	 * HttpRequest의 attribute에 HttpRequest 파싱 수행결과 값 저장<br>
	 * HttpRequest 파싱 수행결과 값 request에 셋팅<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param event Event interface를 구현한 객체
	 */
	public void doEnd(HttpServletRequest request, Event event) {
		//set attribute has key Event for request
		request.setAttribute("Event", event);
	}
}