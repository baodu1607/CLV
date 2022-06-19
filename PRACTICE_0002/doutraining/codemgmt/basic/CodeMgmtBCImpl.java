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
package com.clt.apps.opus.esm.clv.doutraining.codemgmt.basic;

import java.util.ArrayList;
import java.util.List;

import com.clt.apps.opus.esm.clv.doutraining.codemgmt.integration.CodeMgmtDBDAO;
import com.clt.apps.opus.esm.clv.doutraining.codemgmt.vo.CodeDetailVO;
import com.clt.apps.opus.esm.clv.doutraining.codemgmt.vo.CodeVO;
import com.clt.apps.opus.esm.clv.doutraining.errmsgmgmt.integration.ErrMsgMgmtDBDAO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.doutraining.errmsgmgmt.vo.ErrMsgVO;

public class CodeMgmtBCImpl extends BasicCommandSupport implements CodeMgmtBC {
	
	private transient CodeMgmtDBDAO dbDao = null;
	
	public CodeMgmtBCImpl() {
		//create DAO object
		dbDao = new CodeMgmtDBDAO();
	}

	@Override
	public List<CodeVO> searchCodeVO(CodeVO codeVO) throws EventException {
		try {
			return dbDao.searchCodeVO(codeVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}

	@Override
	public List<CodeDetailVO> searchCodeDetailVO(CodeDetailVO codeDetailVO) throws EventException {
		try {
			return dbDao.searchCodeDetailVO(codeDetailVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}

	@Override
	public void manageCodelVO(CodeVO[] codeVOs, SignOnUserAccount account) throws EventException {
		try {
			//List needs to be inserted
			List<CodeVO> insertVoList = new ArrayList<CodeVO>();
			
			//List needs to be updated
			List<CodeVO> updateVoList = new ArrayList<CodeVO>();
			
			//List needs to be deleted
			List<CodeVO> deleteVoList = new ArrayList<CodeVO>();
			
			List<CodeDetailVO> deleteCodeDetailList = new ArrayList<CodeDetailVO>();
			//Invalid code message
			StringBuilder invalidMsgCds=new StringBuilder();
			
			//loop through errMsgVO and base on IbFlag to perform corresponding action
			for ( int i=0; i<codeVOs.length; i++ ) {
				
				//Insert
				if ( codeVOs[i].getIbflag().equals("I")){
					//Condition need to check before inserting
					CodeVO condition = new CodeVO();
					//set message code for condition
					condition.setIntgCdId(codeVOs[i].getIntgCdId());
					
					//if message code don't exist
					if(searchCodeVO(condition).size()==0){
						//set CreUsrId is current user id
						codeVOs[i].setCreUsrId(account.getUsr_id());
						
						//set UpdUsrId is current user id
						codeVOs[i].setUpdUsrId(account.getUsr_id());
						
						//add to inserting list
						insertVoList.add(codeVOs[i]);
					}else{
						//if message code already existed
						//append invalid message code to invalidMsgCds variable
						invalidMsgCds.append(codeVOs[i].getIntgCdId()+"|");
					}
				} else if (codeVOs[i].getIbflag().equals("U")){
					//Update
					//set UpdUsrId is current user id
					codeVOs[i].setUpdUsrId(account.getUsr_id());
					
					//add to updating list
					updateVoList.add(codeVOs[i]);
				} else if ( codeVOs[i].getIbflag().equals("D")){
					CodeDetailVO codeDetailVO = new CodeDetailVO();
					codeDetailVO.setIntgCdId(codeVOs[i].getIntgCdId());
					deleteCodeDetailList.addAll(dbDao.searchCodeDetailVO(codeDetailVO));
					deleteVoList.add(codeVOs[i]);
				}
			}
			
			//if we have invalid data( because message code already existed)
			if(invalidMsgCds.length()!=0){
				//remove "|" at the end
				invalidMsgCds.deleteCharAt(invalidMsgCds.length()-1);
				//throw new EventException 
				throw new EventException(new ErrorHandler("ERR12356", new String[]{invalidMsgCds.toString()}).getMessage());
			}
			
			//if inserting list isn't empty
			if ( insertVoList.size() > 0 ) {
				dbDao.addCodeVOs(insertVoList);
			}
			
			//if updating list isn't empty
			if ( updateVoList.size() > 0 ) {
				dbDao.updateCodeVOs(updateVoList);
			}
//			
//			//if deleting list isn't empty
			if ( deleteVoList.size() > 0 ) {
				if(deleteCodeDetailList.size()>0){					
					dbDao.removeCodeDetailVOs(deleteCodeDetailList);
				}
				dbDao.removeCodeVOs(deleteVoList);
			}
		} catch(DAOException ex) {
			// throw new EventException if we have DAOException
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			//other exception
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
		
	}

	@Override
	public void manageCodeDetailVO(CodeDetailVO[] codeDetailVOs, SignOnUserAccount account) throws EventException {
		try {
			//List needs to be inserted
			List<CodeDetailVO> insertVoList = new ArrayList<CodeDetailVO>();
			
			//List needs to be updated
			List<CodeDetailVO> updateVoList = new ArrayList<CodeDetailVO>();
			
			//List needs to be deleted
			List<CodeDetailVO> deleteVoList = new ArrayList<CodeDetailVO>();
			
			//Invalid code message
			StringBuilder invalidMsgCds=new StringBuilder();
			
			//loop through errMsgVO and base on IbFlag to perform corresponding action
			for ( int i=0; i<codeDetailVOs.length; i++ ) {
				
				//Insert
				if ( codeDetailVOs[i].getIbflag().equals("I")){
					//Condition need to check before inserting
					CodeDetailVO condition = new CodeDetailVO();
					//set message code for condition
					condition.setIntgCdId(codeDetailVOs[i].getIntgCdId());
					condition.setIntgCdValCtnt(codeDetailVOs[i].getIntgCdValCtnt());
					
//					if message code don't exist
					if(searchCodeDetailVO(condition).size()==0){
						//set CreUsrId is current user id
						codeDetailVOs[i].setCreUsrId(account.getUsr_id());
						
						//set UpdUsrId is current user id
						codeDetailVOs[i].setUpdUsrId(account.getUsr_id());
						
						//add to inserting list
						insertVoList.add(codeDetailVOs[i]);
					}else{
						//if message code already existed
						//append invalid message code to invalidMsgCds variable
						invalidMsgCds.append(codeDetailVOs[i].getIntgCdValCtnt()+"|");
					}
				} else if ( codeDetailVOs[i].getIbflag().equals("U")){
					//Update
					//set UpdUsrId is current user id
					codeDetailVOs[i].setUpdUsrId(account.getUsr_id());
					
					//add to updating list
					updateVoList.add(codeDetailVOs[i]);
				} else if ( codeDetailVOs[i].getIbflag().equals("D")){
					deleteVoList.add(codeDetailVOs[i]);
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
				dbDao.addCodeDetailVOs(insertVoList);
			}
			
//			if updating list isn't empty
			if ( updateVoList.size() > 0 ) {
				dbDao.updateCodeDetailVOs(updateVoList);
			}
////			
////			//if deleting list isn't empty
			if ( deleteVoList.size() > 0 ) {
				dbDao.removeCodeDetailVOs(deleteVoList);
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