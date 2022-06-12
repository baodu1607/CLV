/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ErrMsgMgmtDBDAO.java
*@FileTitle : Error Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.11
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.11 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining.errmsgmgmt.integration;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.esm.clv.doutraining.errmsgmgmt.basic.ErrMsgMgmtBCImpl;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;
import com.clt.apps.opus.esm.clv.doutraining.errmsgmgmt.vo.ErrMsgVO;


/**
 * ALPS ErrMsgMgmtDBDAO <br>
 * - ALPS-DouTraining system Business Logic을 처리하기 위한 JDBC 작업수행.<br>
 * 
 * @author phuoc
 * @see ErrMsgMgmtBCImpl 참조
 * @since J2EE 1.6
 */
public class ErrMsgMgmtDBDAO extends DBDAOSupport {

	/**
	 * [처리대상] 정보를 [행위] 합니다.<br>
	 * 
	 * @param ErrMsgVO errMsgVO
	 * @return List<ErrMsgVO>
	 * @exception DAOException
	 */
	 @SuppressWarnings("unchecked")
	 //Searching action
	public List<ErrMsgVO> searchErrMsgVO(ErrMsgVO errMsgVO) throws DAOException {
		//Store returning value from Database
		DBRowSet dbRowset = null;
		
		//List of VO
		List<ErrMsgVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			//if errMsgVO isn't null
			if(errMsgVO != null){
				Map<String, String> mapVO = errMsgVO .getColumnValues();
				//add all value to param
				param.putAll(mapVO);
				
				//add all value to velParam
				velParam.putAll(mapVO);
			}
			//Query
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new ErrMsgMgmtDBDAOErrMsgVORSQL(), param, velParam);
			
			//Convert dbRowset to list
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, ErrMsgVO .class);
		} catch(SQLException se) {
			//Logging exception
			log.error(se.getMessage(),se);
			
			//throw new Exception
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	
	/**
	 * [처리대상] 정보를 [행위] 합니다.<br>
	 * 
	 * @param ErrMsgVO errMsgVO
	 * @exception DAOException
	 * @exception Exception
	 */
	//Inserting action
	public void addmanageErrMsgVO(ErrMsgVO errMsgVO) throws DAOException,Exception {
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		try {
			Map<String, String> mapVO = errMsgVO .getColumnValues();
			//add all value to param
			param.putAll(mapVO);
			
			//add all value to velParam
			velParam.putAll(mapVO);
			
			//Create SQLExecuter
			SQLExecuter sqlExe = new SQLExecuter("");
			
			//Executing action 
			int result = sqlExe.executeUpdate((ISQLTemplate)new ErrMsgMgmtDBDAOErrMsgVOCSQL(), param, velParam);
			//if it failed
			if(result == Statement.EXECUTE_FAILED)
				//throw DAOException
					throw new DAOException("Fail to insert SQL");
		} catch(SQLException se) {
			//Logging Exception
			log.error(se.getMessage(),se);
			//throw DAOException
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
	}
	
	/**
	 * [처리대상] 정보를 [행위] 합니다.<br>
	 * 
	 * @param ErrMsgVO errMsgVO
	 * @return int
	 * @exception DAOException
	 * @exception Exception
	 */
	
	//Updating action
	public int modifymanageErrMsgVO(ErrMsgVO errMsgVO) throws DAOException,Exception {
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		int result = 0;
		try {
			Map<String, String> mapVO = errMsgVO .getColumnValues();
			//add all value to param
			param.putAll(mapVO);
			
			//add all value to velParam
			velParam.putAll(mapVO);
			
			//Create SQLExecuter
			SQLExecuter sqlExe = new SQLExecuter("");
			//Executing action
			result = sqlExe.executeUpdate((ISQLTemplate)new ErrMsgMgmtDBDAOErrMsgVOUSQL(), param, velParam);
			
			//if it failed
			if(result == Statement.EXECUTE_FAILED)
				//throw DAOException
					throw new DAOException("Fail to insert SQL");
		} catch(SQLException se) {
			//Logging Exception
			log.error(se.getMessage(),se);
			//throw DAOException
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return result;
	}
	
	/**
	 * [처리대상] 정보를 [행위] 합니다.<br>
	 * 
	 * @param ErrMsgVO errMsgVO
	 * @return int
	 * @exception DAOException
	 * @exception Exception
	 */
	
	//Deleting action
	public int removemanageErrMsgVO(ErrMsgVO errMsgVO) throws DAOException,Exception {
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		int result = 0;
		try {
			Map<String, String> mapVO = errMsgVO .getColumnValues();
			//add all value to param
			param.putAll(mapVO);
			
			//add all value to velParam
			velParam.putAll(mapVO);
			
			//Create SQLExecuter
			SQLExecuter sqlExe = new SQLExecuter("");
			//Executing action
			result = sqlExe.executeUpdate((ISQLTemplate)new ErrMsgMgmtDBDAOErrMsgVODSQL(), param, velParam);
			//if it failed
			if(result == Statement.EXECUTE_FAILED)
				//throw DAOException
					throw new DAOException("Fail to insert SQL");
		} catch(SQLException se) {
			//Logging Exception
			log.error(se.getMessage(),se);
			//throw DAOException
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return result;
	}

	/**
	 * [처리대상] 정보를 [행위] 합니다.<br>
	 * 
	 * @param List<ErrMsgVO> errMsgVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	
	//Same as addmanageErrMsgVO but it's used for list of VO
	public int[] addmanageErrMsgVOS(List<ErrMsgVO> errMsgVO) throws DAOException,Exception {
		int insCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(errMsgVO .size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new ErrMsgMgmtDBDAOErrMsgVOCSQL(), errMsgVO,null);
				for(int i = 0; i < insCnt.length; i++){
					if(insCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return insCnt;
	}
	/**
	 * [처리대상] 정보를 [행위] 합니다.<br>
	 * 
	 * @param List<ErrMsgVO> errMsgVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	
	//Same as modifymanageErrMsgVO but it's used for list of VO
	public int[] modifymanageErrMsgVOS(List<ErrMsgVO> errMsgVO) throws DAOException,Exception {
		int updCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(errMsgVO .size() > 0){
				updCnt = sqlExe.executeBatch((ISQLTemplate)new ErrMsgMgmtDBDAOErrMsgVOUSQL(), errMsgVO,null);
				for(int i = 0; i < updCnt.length; i++){
					if(updCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return updCnt;
	}
	
	/**
	 * [처리대상] 정보를 [행위] 합니다.<br>
	 * 
	 * @param List<ErrMsgVO> errMsgVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	//Same as removemanageErrMsgVO but it's used for list of VO
	public int[] removemanageErrMsgVOS(List<ErrMsgVO> errMsgVO) throws DAOException,Exception {
		int delCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(errMsgVO .size() > 0){
				delCnt = sqlExe.executeBatch((ISQLTemplate)new ErrMsgMgmtDBDAOErrMsgVODSQL(), errMsgVO,null);
				for(int i = 0; i < delCnt.length; i++){
					if(delCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return delCnt;
	}
	
}