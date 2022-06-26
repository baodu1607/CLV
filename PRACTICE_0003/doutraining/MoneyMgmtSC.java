/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : moneymgmtSC.java
*@FileTitle : Money Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.22
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.22 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining;

import java.util.List;

import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.GeneralEventResponse;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.framework.support.layer.service.ServiceCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.doutraining.carriermgmt.basic.CarrierMgmtBC;
import com.clt.apps.opus.esm.clv.doutraining.carriermgmt.basic.CarrierMgmtBCImpl;
import com.clt.apps.opus.esm.clv.doutraining.carriermgmt.vo.CarrierVO;
import com.clt.apps.opus.esm.clv.doutraining.moneymgmt.basic.MoneyMgmtBC;
import com.clt.apps.opus.esm.clv.doutraining.moneymgmt.basic.MoneyMgmtBCImpl;
import com.clt.apps.opus.esm.clv.doutraining.moneymgmt.event.EsmDou0108Event;
import com.clt.apps.opus.esm.clv.doutraining.moneymgmt.vo.DetailVO;
import com.clt.apps.opus.esm.clv.doutraining.moneymgmt.vo.SummaryVO;


/**
 * ALPS-moneymgmt Business Logic ServiceCommand - ALPS-moneymgmt 대한 비지니스 트랜잭션을 처리한다.
 * 
 * @author phuoc
 * @see MoneyMgmtDBDAO
 * @since J2EE 1.6
 */

public class MoneyMgmtSC extends ServiceCommandSupport {
	// Login User Information
	private SignOnUserAccount account = null;

	/**
	 * moneymgmt system 업무 시나리오 선행작업<br>
	 * 업무 시나리오 호출시 관련 내부객체 생성<br>
	 */
	public void doStart() {
		log.debug("MoneymgmtSC 시작");
		try {
			// 일단 comment --> 로그인 체크 부분
			account = getSignOnUserAccount();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	/**
	 * moneymgmt system 업무 시나리오 마감작업<br>
	 * 업무 시나리오 종료시 관련 내부객체 해제<br>
	 */
	public void doEnd() {
		log.debug("MoneymgmtSC 종료");
	}

	/**
	 * 각 이벤트에 해당하는 업무 시나리오 수행<br>
	 * ALPS-moneymgmt system 업무에서 발생하는 모든 이벤트의 분기처리<br>
	 * 
	 * @param e Event
	 * @return EventResponse
	 * @exception EventException
	 */
	public EventResponse perform(Event e) throws EventException {
		// RDTO(Data Transfer Object including Parameters)
		EventResponse eventResponse = null;

		// SC가 여러 이벤트를 처리하는 경우 사용해야 할 부분
		if (e.getEventName().equalsIgnoreCase("EsmDou0108Event")) {
			if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				eventResponse = searchSummaryVO(e);
			}else if (e.getFormCommand().isCommand(FormCommand.SEARCH01)) {
				eventResponse = searchDetailVO(e);
			}else if (e.getFormCommand().isCommand(FormCommand.MULTI)) {
				eventResponse = manageSummaryVO(e);
			}else if (e.getFormCommand().isCommand(FormCommand.DEFAULT)) {
				eventResponse = initCombox();
			}else if(e.getFormCommand().isCommand(FormCommand.SEARCH02)){
				eventResponse = searchLaneCode(e);
			}else if(e.getFormCommand().isCommand(FormCommand.SEARCH03)){
				eventResponse = searchTradeCode(e);
			}
		}
		return eventResponse;
	}
	
	private EventResponse initCombox() throws EventException{
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		MoneyMgmtBC command = new MoneyMgmtBCImpl();

		try{
			List<SummaryVO> partnerCodeList = command.getPartnerCodes();
			StringBuilder partnerCodes=new StringBuilder();
			if(partnerCodeList.size()!=0){
				for(int i=0;i<partnerCodeList.size();i++){
					partnerCodes.append(partnerCodeList.get(i).getJoCrrCd()+"|");
				}
				partnerCodes.deleteCharAt(partnerCodes.length()-1);
			}
			eventResponse.setETCData("partnerCodes",partnerCodes.length()==0?"":partnerCodes.toString());
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	/**
	 * ESM_DOU_0108 : [이벤트]<br>
	 * [비즈니스대상]을 [행위]합니다.<br>
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchSummaryVO(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0108Event event = (EsmDou0108Event)e;
		MoneyMgmtBC command = new MoneyMgmtBCImpl();

		try{
			List<SummaryVO> list = command.searchSummaryVO(event.getSummaryVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	private EventResponse searchDetailVO(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0108Event event = (EsmDou0108Event)e;
		MoneyMgmtBC command = new MoneyMgmtBCImpl();

		try{
			List<DetailVO> list = command.searchDetailVO(event.getDetailVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	private EventResponse searchLaneCode(Event e) throws EventException{
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0108Event event = (EsmDou0108Event)e;
		MoneyMgmtBC command = new MoneyMgmtBCImpl();

		try{
			List<SummaryVO> laneCodeList = command.getLaneCodes(event.getSummaryVO());
			StringBuilder laneCodes=new StringBuilder();
			if(laneCodeList.size()!=0){
				for(int i=0;i<laneCodeList.size();i++){
					laneCodes.append(laneCodeList.get(i).getRlaneCd()+"|");
				}
				laneCodes.deleteCharAt(laneCodes.length()-1);
			}
			eventResponse.setETCData("laneCodes",laneCodes.toString());
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	private EventResponse searchTradeCode(Event e) throws EventException{
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0108Event event = (EsmDou0108Event)e;
		MoneyMgmtBC command = new MoneyMgmtBCImpl();

		try{
			List<SummaryVO> tradeCodeList = command.getTradeCodes(event.getSummaryVO());
			StringBuilder tradeCodes=new StringBuilder();
			if(tradeCodeList.size()!=0){
				for(int i=0;i<tradeCodeList.size();i++){
					tradeCodes.append(tradeCodeList.get(i).getTrdCd()+"|");
				}
				tradeCodes.deleteCharAt(tradeCodes.length()-1);
			}
			eventResponse.setETCData("tradeCodes",tradeCodes.toString());
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	/**
	 * ESM_DOU_0108 : [이벤트]<br>
	 * [비즈니스대상]을 [행위]합니다.<br>
	 *
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse manageSummaryVO(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		EsmDou0108Event event = (EsmDou0108Event)e;
		MoneyMgmtBC command = new MoneyMgmtBCImpl();
		try{
			begin();
			command.manageSummaryVO(event.getSummaryVOS(),account);
			eventResponse.setUserMessage(new ErrorHandler("XXXXXXXXX").getUserMessage());
			commit();
		} catch(EventException ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch(Exception ex) {
			rollback();
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		return eventResponse;
	}
}