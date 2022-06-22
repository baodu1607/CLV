package com.clt.apps.opus.esm.clv.doutraining.carriermgmt.integration;



import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.clt.apps.opus.esm.clv.doutraining.carriermgmt.vo.CarrierVO;
import com.clt.apps.opus.esm.clv.doutraining.codemgmt.integration.CodeMgmtDAOCodeVOCSQL;
import com.clt.apps.opus.esm.clv.doutraining.codemgmt.integration.CodeMgmtDAOCodeVORSQL;
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

public class CarrierMgmtDAO  extends DBDAOSupport{
	public List<CarrierVO> getCrrCds() throws DAOException {
		//Store returning value from Database
		DBRowSet dbRowset = null;
		
		//List of VO
		List<CarrierVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			//Query
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CarrierMgmtDAOSearchCrrCdRSQL(), param, velParam);
			
			//Convert dbRowset to list
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, CarrierVO .class);
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
	
	public List<CarrierVO> getLnCds() throws DAOException {
		//Store returning value from Database
		DBRowSet dbRowset = null;
		
		//List of VO
		List<CarrierVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			//Query
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CarrierMgmtDAOSearchLnCdRSQL(), param, velParam);
			
			//Convert dbRowset to list
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, CarrierVO .class);
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
	
	public List<CarrierVO> searchCarrierVO(CarrierVO carrierVO) throws DAOException {
		//Store returning value from Database
		DBRowSet dbRowset = null;
		
		//List of VO
		List<CarrierVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		List<String> crrCds=new ArrayList<String>();
		
		try{
			//if errMsgVO isn't null
			if(carrierVO != null){
				Map<String, String> mapVO = carrierVO .getColumnValues();
				if(!(carrierVO.getJoCrrCd().isEmpty()&&"All".equalsIgnoreCase(carrierVO.getJoCrrCd()))){
					crrCds=Arrays.asList(carrierVO.getJoCrrCd().split(","));
				}

				//add all value to param
				param.put("crrCds",crrCds);
				param.putAll(mapVO);
				
				//add all value to velParam
				velParam.put("crrCds",crrCds);
				velParam.putAll(mapVO);
			}
			//Query
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CarrierMgmtDAOCarrierVORSQL(), param, velParam);
			
			//Convert dbRowset to list
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, CarrierVO .class);
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
	
	public List<CarrierVO> searchCust(CarrierVO carrierVO) throws DAOException {
		//Store returning value from Database
		DBRowSet dbRowset = null;
		
		//List of VO
		List<CarrierVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			//if codeVO isn't null
			if(carrierVO != null){
				Map<String, String> mapVO = carrierVO .getColumnValues();
				//add all value to param
				param.putAll(mapVO);
				
				//add all value to velParam
				velParam.putAll(mapVO);
			}
			//Query
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CarrierMgmtDAOSearchCustRSQL(), param, velParam);
			
			//Convert dbRowset to list
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, CarrierVO .class);
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
	
	public void addCarrierVO(CarrierVO carrierVO) throws DAOException,Exception {
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		try {
			Map<String, String> mapVO = carrierVO .getColumnValues();
			//add all value to param
			param.putAll(mapVO);
			
			//add all value to velParam
			velParam.putAll(mapVO);
			
			//Create SQLExecuter
			SQLExecuter sqlExe = new SQLExecuter("");
			
			//Executing action 
			int result = sqlExe.executeUpdate((ISQLTemplate)new CarrierMgmtDAOCarrierVOCSQL(), param, velParam);
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
	
	public void updateCarrierVO(CarrierVO carrierVO) throws DAOException,Exception {
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		try {
			Map<String, String> mapVO = carrierVO .getColumnValues();
			//add all value to param
			param.putAll(mapVO);
			
			//add all value to velParam
			velParam.putAll(mapVO);
			
			//Create SQLExecuter
			SQLExecuter sqlExe = new SQLExecuter("");
			
			//Executing action 
			int result = sqlExe.executeUpdate((ISQLTemplate)new CarrierMgmtDAOCarrierVOUSQL(), param, velParam);
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
	
	public void removeCarrierVO(CarrierVO carrierVO) throws DAOException,Exception {
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		try {
			Map<String, String> mapVO = carrierVO .getColumnValues();
			//add all value to param
			param.putAll(mapVO);
			
			//add all value to velParam
			velParam.putAll(mapVO);
			
			//Create SQLExecuter
			SQLExecuter sqlExe = new SQLExecuter("");
			
			//Executing action 
			int result = sqlExe.executeUpdate((ISQLTemplate)new CarrierMgmtDAOCarrierVODSQL(), param, velParam);
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
	
	public int[] addCarrierVOs(List<CarrierVO> carrierVO) throws DAOException,Exception {
		int insCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(carrierVO.size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new CarrierMgmtDAOCarrierVOCSQL(), carrierVO,null);
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
	
	public int[] updateCarrierVOs(List<CarrierVO> carrierVO) throws DAOException,Exception {
		int insCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(carrierVO.size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new CarrierMgmtDAOCarrierVOUSQL(), carrierVO,null);
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
	
	public int[] removeCarrierVOs(List<CarrierVO> carrierVO) throws DAOException,Exception {
		int insCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(carrierVO.size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new CarrierMgmtDAOCarrierVODSQL(), carrierVO,null);
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