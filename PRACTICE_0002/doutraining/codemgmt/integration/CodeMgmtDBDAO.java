package com.clt.apps.opus.esm.clv.doutraining.codemgmt.integration;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.esm.clv.doutraining.codemgmt.vo.CodeDetailVO;
import com.clt.apps.opus.esm.clv.doutraining.codemgmt.vo.CodeVO;
import com.clt.apps.opus.esm.clv.doutraining.errmsgmgmt.integration.ErrMsgMgmtDBDAOErrMsgVOCSQL;
import com.clt.apps.opus.esm.clv.doutraining.errmsgmgmt.vo.ErrMsgVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;

public class CodeMgmtDBDAO  extends DBDAOSupport{
	public List<CodeVO> searchCodeVO(CodeVO codeVO) throws DAOException {
		//Store returning value from Database
		DBRowSet dbRowset = null;
		
		//List of VO
		List<CodeVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			//if errMsgVO isn't null
			if(codeVO != null){
				Map<String, String> mapVO = codeVO .getColumnValues();
				//add all value to param
				param.putAll(mapVO);
				
				//add all value to velParam
				velParam.putAll(mapVO);
			}
			//Query
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CodeMgmtDAOCodeVORSQL(), param, velParam);
			
			//Convert dbRowset to list
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, CodeVO .class);
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
	
	public List<CodeDetailVO> searchCodeDetailVO(CodeDetailVO codeDetailVO) throws DAOException {
		//Store returning value from Database
		DBRowSet dbRowset = null;
		
		//List of VO
		List<CodeDetailVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			//if errMsgVO isn't null
			if(codeDetailVO != null){
				Map<String, String> mapVO = codeDetailVO.getColumnValues();
				//add all value to param
				param.putAll(mapVO);
				
				//add all value to velParam
				velParam.putAll(mapVO);
			}
			//Query
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CodeMgmtDAOCodeDetailRSQL(), param, velParam);
			
			//Convert dbRowset to list
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, CodeDetailVO .class);
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
	
	public int[] addCodeVOs(List<CodeVO> codeVO) throws DAOException,Exception {
		int insCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(codeVO .size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDAOCodeVOCSQL(), codeVO,null);
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
	
	public int[] addCodeDetailVOs(List<CodeDetailVO> codeDetailVOs) throws DAOException,Exception {
		int insCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(codeDetailVOs .size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDAOCodeDetailCSQL(), codeDetailVOs,null);
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
	
	public int[] updateCodeVOs(List<CodeVO> codeVO) throws DAOException,Exception {
		int insCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(codeVO .size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDAOCodeVOUSQL(), codeVO,null);
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
	
	public int[] updateCodeDetailVOs(List<CodeDetailVO> codeDetailVO) throws DAOException,Exception {
		int insCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(codeDetailVO .size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDAOCodeDetailUSQL(), codeDetailVO,null);
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
	
	public int[] removeCodeDetailVOs(List<CodeDetailVO> codeDetailVO) throws DAOException,Exception {
		int insCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(codeDetailVO .size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDAOCodeDetailDSQL(), codeDetailVO,null);
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
	
	public int[] removeCodeVOs(List<CodeVO> codeVO) throws DAOException,Exception {
		int insCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(codeVO .size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new CodeMgmtDAOCodeVODSQL(), codeVO,null);
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
}