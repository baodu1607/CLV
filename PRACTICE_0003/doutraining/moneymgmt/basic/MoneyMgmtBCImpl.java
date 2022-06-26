/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : MoneyMgmtBCImpl.java
*@FileTitle : Money Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.22
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.22 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining.moneymgmt.basic;

import java.util.ArrayList;
import java.util.List;

import com.clt.apps.opus.esm.clv.doutraining.moneymgmt.intergration.MoneyMgmtDBDAO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.doutraining.moneymgmt.vo.DetailVO;
import com.clt.apps.opus.esm.clv.doutraining.moneymgmt.vo.SummaryVO;

/**
 * ALPS-moneymgmt Business Logic Command Interface<br>
 * - ALPS-moneymgmt에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author phuoc
 * @since J2EE 1.6
 */
public class MoneyMgmtBCImpl extends BasicCommandSupport implements MoneyMgmtBC {

	// Database Access Object
	private transient MoneyMgmtDBDAO dbDao = null;

	/**
	 * MoneyMgmtBCImpl 객체 생성<br>
	 * MoneyMgmtDBDAO를 생성한다.<br>
	 */
	public MoneyMgmtBCImpl() {
		dbDao = new MoneyMgmtDBDAO();
	}
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param SummaryVO summaryVO
	 * @return List<SummaryVO>
	 * @exception EventException
	 */
	public List<SummaryVO> searchSummaryVO(SummaryVO summaryVO) throws EventException {
		try {
			return dbDao.searchSummaryVO(summaryVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	public List<DetailVO> searchDetailVO(DetailVO detailVO) throws EventException {
		try {
			return dbDao.searchDetailVO(detailVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param SummaryVO[] summaryVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void manageSummaryVO(SummaryVO[] summaryVO, SignOnUserAccount account) throws EventException{
		try {
			List<SummaryVO> insertVoList = new ArrayList<SummaryVO>();
			List<SummaryVO> updateVoList = new ArrayList<SummaryVO>();
			List<SummaryVO> deleteVoList = new ArrayList<SummaryVO>();
			for ( int i=0; i<summaryVO .length; i++ ) {
				if ( summaryVO[i].getIbflag().equals("I")){
//					summaryVO[i].setCreUsrId(account.getUsr_id());
					insertVoList.add(summaryVO[i]);
				} else if ( summaryVO[i].getIbflag().equals("U")){
//					summaryVO[i].setUpdUsrId(account.getUsr_id());
					updateVoList.add(summaryVO[i]);
				} else if ( summaryVO[i].getIbflag().equals("D")){
					deleteVoList.add(summaryVO[i]);
				}
			}
			
			if ( insertVoList.size() > 0 ) {
				dbDao.addmanageSummaryVOS(insertVoList);
			}
			
			if ( updateVoList.size() > 0 ) {
				dbDao.modifymanageSummaryVOS(updateVoList);
			}
			
			if ( deleteVoList.size() > 0 ) {
				dbDao.removemanageSummaryVOS(deleteVoList);
			}
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	@Override
	public List<SummaryVO> getPartnerCodes() throws EventException {
		try {
			return dbDao.getPartnerCodes();
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}
	@Override
	public List<SummaryVO> getLaneCodes(SummaryVO summaryVO) throws EventException {
		try {
			return dbDao.getLaneCodes(summaryVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	@Override
	public List<SummaryVO> getTradeCodes(SummaryVO summaryVO) throws EventException {
		try {
			return dbDao.getTradeCodes(summaryVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
}