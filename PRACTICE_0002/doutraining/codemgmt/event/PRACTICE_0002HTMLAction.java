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
package com.clt.apps.opus.esm.clv.doutraining.codemgmt.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.apps.opus.esm.clv.doutraining.codemgmt.vo.CodeDetailVO;
import com.clt.apps.opus.esm.clv.doutraining.codemgmt.vo.CodeVO;
import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;


public class PRACTICE_0002HTMLAction extends HTMLActionSupport {

	//The serialVersionUID attribute is an identifier that is used to serialize/deserialize an object
	private static final long serialVersionUID = 1L;
	/**
	 * PRACTICE_0001HTMLAction 객체를 생성
	 */
	public PRACTICE_0002HTMLAction() {}

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
		Practice0002Event event = new Practice0002Event();
		//if f_command is SEARCH
		if(command.isCommand(FormCommand.SEARCH)) {
			//Create CodeVO object
			CodeVO codeVO = new CodeVO();
			
			//set parameter has name s_intg_cd_id for codeVO
			codeVO.setIntgCdId(JSPUtil.getParameter(request, "s_intg_cd_id",""));
			//set parameter has name s_ownr_sub_sys_cd for codeVO
			codeVO.setOwnrSubSysCd(JSPUtil.getParameter(request, "s_ownr_sub_sys_cd",""));
			//set VO for event
			event.setCodeVO(codeVO);
		}else if(command.isCommand(FormCommand.SEARCH01)) {
			CodeDetailVO codeDetailVO = new CodeDetailVO();
			
			//set parameter has name s_intg_cd_id for codeVO
			codeDetailVO.setIntgCdId(JSPUtil.getParameter(request, "s_intg_cd_id",""));
			//set VO for event
			event.setCodeDetailVO(codeDetailVO);
		}else if(command.isCommand(FormCommand.MULTI)) {
			//mapping request to VO base on ErrMsgVO class
			//getVOs return array of VO (we use VOs not VO because we save/insert/delete many rows at same time not only one row)
			//set VOs for event
			event.setCodeVOs((CodeVO[])getVOs(request, CodeVO.class,""));
		}else if(command.isCommand(FormCommand.MULTI01)) {
			event.setCodeDetailVOs((CodeDetailVO[])getVOs(request, CodeDetailVO.class,""));
		}
		
		//return event
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