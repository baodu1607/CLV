/*=========================================================
*Copyright(c) 2022 CarrierMgmtBCImpl
*@FileName : ErrMsgMgmtBCImpl.java
*@FileTitle :Carrier Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.28
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.28 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining.carriermgmt.basic;

import java.util.ArrayList;
import java.util.List;

import com.clt.apps.opus.esm.clv.doutraining.carriermgmt.integration.CarrierMgmtDAO;
import com.clt.apps.opus.esm.clv.doutraining.carriermgmt.vo.CarrierVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;

public class CarrierMgmtBCImpl extends BasicCommandSupport implements CarrierMgmtBC {
	
	private transient CarrierMgmtDAO dbDao = null;
	
	public CarrierMgmtBCImpl() {
		//create DAO object
		dbDao = new CarrierMgmtDAO();
	}
	
	@Override
	public List<CarrierVO> getCrrCds() throws EventException {
		try {
			return dbDao.getCrrCds();
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	@Override
	public List<CarrierVO> getLnCds() throws EventException {
		try {
			return dbDao.getLnCds();
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}

	@Override
	public List<CarrierVO> searchCarrierVO(CarrierVO carrierVO) throws EventException {
		try {
			return dbDao.searchCarrierVO(carrierVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	@Override
	public List<CarrierVO> searchCust(CarrierVO carrierVO) throws EventException {
		try {
			return dbDao.searchCust(carrierVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}

	@Override
	public void manageCarrierVO(CarrierVO[] carrierVO, SignOnUserAccount account) throws EventException {
		try {
			List<CarrierVO> insertVoList = new ArrayList<CarrierVO>();
			
			List<CarrierVO> updateVoList = new ArrayList<CarrierVO>();
			
			List<CarrierVO> deleteVoList = new ArrayList<CarrierVO>();
			
			StringBuilder invalidData=new StringBuilder();
			
			for ( int i=0; i<carrierVO.length; i++ ) {
				
				if ( carrierVO[i].getIbflag().equals("I")){
					CarrierVO condition = new CarrierVO();
					condition.setJoCrrCd(carrierVO[i].getJoCrrCd());
					condition.setRlaneCd(carrierVO[i].getRlaneCd());
					if(searchCarrierVO(condition).size()==0){
						carrierVO[i].setCreUsrId(account.getUsr_id());
						
						carrierVO[i].setUpdUsrId(account.getUsr_id());
						
						insertVoList.add(carrierVO[i]);
					}else{
						invalidData.append(carrierVO[i].getJoCrrCd()+"-"+carrierVO[i].getRlaneCd()+"|");
					}
				} else if (carrierVO[i].getIbflag().equals("U")){
					carrierVO[i].setUpdUsrId(account.getUsr_id());
					
					updateVoList.add(carrierVO[i]);
				} else if ( carrierVO[i].getIbflag().equals("D")){
					deleteVoList.add(carrierVO[i]);
				}
			}
			
			if(invalidData.length()!=0){
				invalidData.deleteCharAt(invalidData.length()-1);
				throw new EventException(new ErrorHandler("ERR12356", new String[]{invalidData.toString()}).getMessage());
			}
			
			if ( insertVoList.size() > 0 ) {
				dbDao.addCarrierVOs(insertVoList);
			}
			
			if ( updateVoList.size() > 0 ) {
				dbDao.updateCarrierVOs(updateVoList);
			}
			if ( deleteVoList.size() > 0 ) {
				dbDao.removeCarrierVOs(deleteVoList);
			}
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}


}