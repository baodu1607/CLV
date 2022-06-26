/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ESM_DOU_0108HTMLAction.java
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

import javax.servlet.http.HttpServletRequest;

import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.apps.opus.esm.clv.doutraining.carriermgmt.vo.CarrierVO;
import com.clt.apps.opus.esm.clv.doutraining.moneymgmt.vo.DetailVO;
import com.clt.apps.opus.esm.clv.doutraining.moneymgmt.vo.SummaryVO;

/**
 * HTTP Parser<br>
 * - com.clt.apps.opus.esm.clv.doutraining.moneymgmt 화면을 통해 서버로 전송되는 HTML DOM 객체의 Value를 자바 변수로 Parsing<br>
 * - Parsing 한 정보를 Event로 변환, request에 담아 moneymgmtSC로 실행요청<br>
 * - moneymgmtSC에서 View(JSP)로 실행결과를 전송하는 EventResponse를 request에 셋팅<br>
 * @author phuoc
 * @see moneymgmtEvent 참조
 * @since J2EE 1.6
 */

public class ESM_DOU_0108HTMLAction extends HTMLActionSupport {

	private static final long serialVersionUID = 1L;
	/**
	 * ESM_DOU_0108HTMLAction 객체를 생성
	 */
	public ESM_DOU_0108HTMLAction() {}

	/**
	 * HTML DOM 객체의 Value를 자바 변수로 Parsing<br>
	 * HttpRequst의 정보를 moneymgmtEvent로 파싱하여 request에 셋팅<br>
	 * @param request HttpServletRequest HttpRequest
	 * @return Event Event interface를 구현한 객체
	 * @exception HTMLActionException
	 */
	public Event perform(HttpServletRequest request) throws HTMLActionException {
		
    	FormCommand command = FormCommand.fromRequest(request);
		EsmDou0108Event event = new EsmDou0108Event();
		
		if(command.isCommand(FormCommand.MULTI)) {
			event.setSummaryVOS((SummaryVO[])getVOs(request, SummaryVO .class,""));
		}
		else if(command.isCommand(FormCommand.SEARCH)) {
			SummaryVO summaryVO = new SummaryVO();
			summaryVO.setJoCrrCd(JSPUtil.getParameter(request, "s_partner_code",""));
			summaryVO.setRlaneCd(JSPUtil.getParameter(request, "s_lane_code",""));
			summaryVO.setTrdCd(JSPUtil.getParameter(request, "s_trade_code",""));
			summaryVO.setDateFr(JSPUtil.getParameter(request, "s_date_fr",""));
			summaryVO.setDateTo(JSPUtil.getParameter(request, "s_date_to",""));
			event.setSummaryVO(summaryVO);
		}else if(command.isCommand(FormCommand.SEARCH01)) {
			DetailVO detailVO = new DetailVO();
			detailVO.setJoCrrCd(JSPUtil.getParameter(request, "s_partner_code",""));
			detailVO.setRlaneCd(JSPUtil.getParameter(request, "s_lane_code",""));
			detailVO.setTrdCd(JSPUtil.getParameter(request, "s_trade_code",""));
			detailVO.setDateFr(JSPUtil.getParameter(request, "s_date_fr",""));
			detailVO.setDateTo(JSPUtil.getParameter(request, "s_date_to",""));
			event.setDetailVO(detailVO);
		}else if(command.isCommand(FormCommand.SEARCH02)) {
			SummaryVO summaryVO = new SummaryVO();
			summaryVO.setJoCrrCd(JSPUtil.getParameter(request, "s_partner_code",""));
			event.setSummaryVO(summaryVO);
		}else if(command.isCommand(FormCommand.SEARCH03)) {
			SummaryVO summaryVO = new SummaryVO();
			summaryVO.setJoCrrCd(JSPUtil.getParameter(request, "s_partner_code",""));
			summaryVO.setRlaneCd(JSPUtil.getParameter(request, "s_lane_code",""));
			event.setSummaryVO(summaryVO);
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
		request.setAttribute("Event", event);
	}
}