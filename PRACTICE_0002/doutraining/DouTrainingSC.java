/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : DouTrainingSC.java
*@FileTitle : Error Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.11
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.11 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining;

import java.util.List;

import com.clt.apps.opus.dou.sinhvien.sinhvien.vo.SinhVienVO;
import com.clt.apps.opus.esm.clv.doutraining.carriermgmt.basic.CarrierMgmtBC;
import com.clt.apps.opus.esm.clv.doutraining.carriermgmt.basic.CarrierMgmtBCImpl;
import com.clt.apps.opus.esm.clv.doutraining.carriermgmt.event.Practice0004Event;
import com.clt.apps.opus.esm.clv.doutraining.carriermgmt.vo.CarrierVO;
import com.clt.apps.opus.esm.clv.doutraining.codemgmt.basic.CodeMgmtBC;
import com.clt.apps.opus.esm.clv.doutraining.codemgmt.basic.CodeMgmtBCImpl;
import com.clt.apps.opus.esm.clv.doutraining.codemgmt.event.Practice0002Event;
import com.clt.apps.opus.esm.clv.doutraining.codemgmt.vo.CodeDetailVO;
import com.clt.apps.opus.esm.clv.doutraining.codemgmt.vo.CodeVO;
import com.clt.apps.opus.esm.clv.doutraining.errmsgmgmt.basic.ErrMsgMgmtBC;
import com.clt.apps.opus.esm.clv.doutraining.errmsgmgmt.basic.ErrMsgMgmtBCImpl;
import com.clt.apps.opus.esm.clv.doutraining.errmsgmgmt.event.Practice0001Event;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.GeneralEventResponse;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.framework.support.layer.service.ServiceCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.doutraining.errmsgmgmt.vo.ErrMsgVO;


/**
 * ALPS-DouTraining Business Logic ServiceCommand - ALPS-DouTraining 대한 비지니스 트랜잭션을 처리한다.
 * 
 * @author phuoc
 * @see ErrMsgMgmtDBDAO
 * @since J2EE 1.6
 */
public class DouTrainingSC extends ServiceCommandSupport {
	// Login User Information
	private SignOnUserAccount account = null;

	/**
	 * DouTraining system 업무 시나리오 선행작업<br>
	 * 업무 시나리오 호출시 관련 내부객체 생성<br>
	 */
	public void doStart() {
		log.debug("DouTrainingSC 시작");
		try {
			//Authenticate
			account = getSignOnUserAccount();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	/**
	 * DouTraining system 업무 시나리오 마감작업<br>
	 * 업무 시나리오 종료시 관련 내부객체 해제<br>
	 */
	public void doEnd() {
		log.debug("DouTrainingSC 종료");
	}

	/**
	 * 각 이벤트에 해당하는 업무 시나리오 수행<br>
	 * ALPS-DouTraining system 업무에서 발생하는 모든 이벤트의 분기처리<br>
	 * 
	 * @param e Event
	 * @return EventResponse
	 * @exception EventException
	 */
	public EventResponse perform(Event e) throws EventException {
		// RDTO(Data Transfer Object including Parameters)
		EventResponse eventResponse = null;

		// SC가 여러 이벤트를 처리하는 경우 사용해야 할 부분
		if (e.getEventName().equalsIgnoreCase("Practice0001Event")) {
			if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				eventResponse = searchErrMsgVO(e);
			}
			else if (e.getFormCommand().isCommand(FormCommand.MULTI)) {
				eventResponse = manageErrMsgVO(e);
			}
		}else if (e.getEventName().equalsIgnoreCase("Practice0002Event")) {
			if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				eventResponse = searchCodeVO(e);
			}
			else if (e.getFormCommand().isCommand(FormCommand.MULTI)) {
				eventResponse = manageCodeVO(e);
			}
			else if (e.getFormCommand().isCommand(FormCommand.SEARCH01)) {
				eventResponse = searchCodeDetailVO(e);
			}
			else if (e.getFormCommand().isCommand(FormCommand.MULTI01)) {
				eventResponse = manageCodeDetailVO(e);
			}
		}else if(e.getEventName().equalsIgnoreCase("Practice0004Event")){
			if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				eventResponse = searchCarrierVO(e);
			}
			else if (e.getFormCommand().isCommand(FormCommand.DEFAULT)) {
				eventResponse = initCombox();
			}
		}
		return eventResponse;
	}

	private EventResponse initCombox() throws EventException{
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		CarrierMgmtBC command = new CarrierMgmtBCImpl();

		try{
			List<CarrierVO> crrCdList = command.getCrrCds();
			List<CarrierVO> lnCdList = command.getLnCds();
			StringBuilder crrCds=new StringBuilder();
			StringBuilder lnCds=new StringBuilder();
			if(crrCdList.size()!=0){
				for(int i=0;i<crrCdList.size();i++){
					crrCds.append(crrCdList.get(i).getJoCrrCd()+"|");
				}
				crrCds.deleteCharAt(crrCds.length()-1);
			}
			if(lnCdList.size()!=0){
				for(int i=0;i<lnCdList.size();i++){
					lnCds.append(lnCdList.get(i).getJoCrrCd()+"|");
				}
				lnCds.deleteCharAt(lnCds.length()-1);
			}
			eventResponse.setETCData("crrCds",crrCds.toString());
			eventResponse.setETCData("lnCds",lnCds.toString());
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}

	private EventResponse manageCodeVO(Event e) throws EventException {
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Practice0002Event event = (Practice0002Event)e;
		CodeMgmtBC command = new CodeMgmtBCImpl();
		try{
			begin();
			command.manageCodelVO(event.getCodeVOs(),account);
			eventResponse.setUserMessage(new ErrorHandler("XXXXX").getUserMessage());
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
	
	private EventResponse manageCodeDetailVO(Event e) throws EventException {
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Practice0002Event event = (Practice0002Event)e;
		CodeMgmtBC command = new CodeMgmtBCImpl();
		try{
			begin();
			command.manageCodeDetailVO(event.getCodeDetailVOs(),account);
			eventResponse.setUserMessage(new ErrorHandler("DOU00001").getUserMessage());
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

	/**
	 * PRACTICE_0001 : [이벤트]<br>
	 * [비즈니스대상]을 [행위]합니다.<br>
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchErrMsgVO(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Practice0001Event event = (Practice0001Event)e;
		ErrMsgMgmtBC command = new ErrMsgMgmtBCImpl();

		try{
			List<ErrMsgVO> list = command.searchErrMsgVO(event.getErrMsgVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	private EventResponse searchCarrierVO(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Practice0004Event event = (Practice0004Event)e;
		CarrierMgmtBC command = new CarrierMgmtBCImpl();

		try{
			List<CarrierVO> list = command.searchCarrierVO(event.getCarrierVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	/**
	 * PRACTICE_0001 : [이벤트]<br>
	 * [비즈니스대상]을 [행위]합니다.<br>
	 *
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse manageErrMsgVO(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Practice0001Event event = (Practice0001Event)e;
		ErrMsgMgmtBC command = new ErrMsgMgmtBCImpl();
		try{
			begin();
			command.manageErrMsgVO(event.getErrMsgVOS(),account);
			eventResponse.setUserMessage(new ErrorHandler("DOU00001").getUserMessage());
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
	
	private EventResponse searchCodeVO(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Practice0002Event event = (Practice0002Event)e;
		CodeMgmtBC command = new CodeMgmtBCImpl();

		try{
			List<CodeVO> list = command.searchCodeVO(event.getCodeVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	private EventResponse searchCodeDetailVO(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		Practice0002Event event = (Practice0002Event)e;
		CodeMgmtBC command = new CodeMgmtBCImpl();

		try{
			List<CodeDetailVO> list = command.searchCodeDetailVO(event.getCodeDetailVO());
			eventResponse.setRsVoList(list);
			eventResponse.setETCData("hasValue", list.size()==0?"false":"true");
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
}