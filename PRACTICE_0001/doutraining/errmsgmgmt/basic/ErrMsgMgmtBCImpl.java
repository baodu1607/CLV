/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ErrMsgMgmtBCImpl.java
*@FileTitle : Error Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.11
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.11 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining.errmsgmgmt.basic;

import java.util.ArrayList;
import java.util.List;
import com.clt.apps.opus.esm.clv.doutraining.errmsgmgmt.integration.ErrMsgMgmtDBDAO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.doutraining.errmsgmgmt.vo.ErrMsgVO;

/**
 * ALPS-DouTraining Business Logic Command Interface<br>
 * - ALPS-DouTraining에 대한 비지니스 로직에 대한 인터페이스<br>
 *
 * @author phuoc
 * @since J2EE 1.6
 */
public class ErrMsgMgmtBCImpl extends BasicCommandSupport implements ErrMsgMgmtBC {

	// Database Access Object
	//transient: marks a member variable not to be serialized
	private transient ErrMsgMgmtDBDAO dbDao = null;

	/**
	 * ErrMsgMgmtBCImpl 객체 생성<br>
	 * ErrMsgMgmtDBDAO를 생성한다.<br>
	 */
	public ErrMsgMgmtBCImpl() {
		//create DAO object
		dbDao = new ErrMsgMgmtDBDAO();
	}
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param ErrMsgVO errMsgVO
	 * @return List<ErrMsgVO>
	 * @exception EventException
	 */
	
	//SEARCH action
	public List<ErrMsgVO> searchErrMsgVO(ErrMsgVO errMsgVO) throws EventException {
		try {
			return dbDao.searchErrMsgVO(errMsgVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}
	
	/**
	 * [비즈니스대상]을 [행위] 합니다.<br>
	 * 
	 * @param ErrMsgVO[] errMsgVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	
	//MULTI action
	public void manageErrMsgVO(ErrMsgVO[] errMsgVO, SignOnUserAccount account) throws EventException{
		try {
			//List needs to be inserted
			List<ErrMsgVO> insertVoList = new ArrayList<ErrMsgVO>();
			
			//List needs to be updated
			List<ErrMsgVO> updateVoList = new ArrayList<ErrMsgVO>();
			
			//List needs to be deleted
			List<ErrMsgVO> deleteVoList = new ArrayList<ErrMsgVO>();
			
			//Invalid code message
			StringBuilder invalidMsgCds=new StringBuilder();
			
			//loop through errMsgVO and base on IbFlag to perform corresponding action
			for ( int i=0; i<errMsgVO .length; i++ ) {
				
				//Insert
				if ( errMsgVO[i].getIbflag().equals("I")){
					//Condition need to check before inserting
					ErrMsgVO condition = new ErrMsgVO();
					//set message code for condition
					condition.setErrMsgCd(errMsgVO[i].getErrMsgCd());
					
					//if message code don't exist
					if(searchErrMsgVO(condition).size()==0){
						//set CreUsrId is current user id
						errMsgVO[i].setCreUsrId(account.getUsr_id());
						
						//set UpdUsrId is current user id
						errMsgVO[i].setUpdUsrId(account.getUsr_id());
						
						//add to inserting list
						insertVoList.add(errMsgVO[i]);
					}else{
						//if message code already existed
						//append invalid message code to invalidMsgCds variable
						invalidMsgCds.append(errMsgVO[i].getErrMsgCd()+"|");
					}
				} else if ( errMsgVO[i].getIbflag().equals("U")){
					//Update
					//set UpdUsrId is current user id
					errMsgVO[i].setUpdUsrId(account.getUsr_id());
					
					//add to updating list
					updateVoList.add(errMsgVO[i]);
				} else if ( errMsgVO[i].getIbflag().equals("D")){
					//Delete
					//add to deleting list
					deleteVoList.add(errMsgVO[i]);
				}
			}
			
			//if we have invalid data( because message code already existed)
			if(invalidMsgCds.length()!=0){
				//remove lasted "|"
				invalidMsgCds.deleteCharAt(invalidMsgCds.length()-1);
				//throw new EventException 
				throw new EventException(new ErrorHandler("ERR12356", new String[]{invalidMsgCds.toString()}).getMessage());
			}
			
			//if inserting list isn't empty
			if ( insertVoList.size() > 0 ) {
				dbDao.addmanageErrMsgVOS(insertVoList);
			}
			
			//if updating list isn't empty
			if ( updateVoList.size() > 0 ) {
				dbDao.modifymanageErrMsgVOS(updateVoList);
			}
			
			//if deleting list isn't empty
			if ( deleteVoList.size() > 0 ) {
				dbDao.removemanageErrMsgVOS(deleteVoList);
			}
		} catch(DAOException ex) {
			// throw new EventException if we have DAOException
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			//other exception
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
}