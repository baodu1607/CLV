/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName       : CodeMgmtBC.java
*@FileTitle      : Interface Code Management Business Component
*Open Issues     :
*Change history  :
*@LastModifyDate : 2022.06.21
*@LastModifier   : 
*@LastVersion    : 1.0
* 2022.06.11 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining.codemgmt.basic;

import java.util.List;

import com.clt.apps.opus.esm.clv.doutraining.codemgmt.vo.CodeDetailVO;
import com.clt.apps.opus.esm.clv.doutraining.codemgmt.vo.CodeVO;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.support.view.signon.SignOnUserAccount;

public interface CodeMgmtBC {
	/**
	 * This method is used to search
	 * @param codeVO
	 * @return List of results
	 * @throws EventException
	 */
	public List<CodeVO> searchCodeVO(CodeVO codeVO) throws EventException;
	
	/**
	 * This method is used to search data
	 * @param codeDetailVO
	 * @return List of results
	 * @throws EventException
	 */
	public List<CodeDetailVO> searchCodeDetailVO(CodeDetailVO codeDetailVO) throws EventException;
	
	/**
	 * This method is used to save data
	 * @param codeVO
	 * @param account
	 * @throws EventException
	 */
	public void manageCodelVO(CodeVO[] codeVO,SignOnUserAccount account) throws EventException;
	
	/**
	 * This method is used to save data
	 * @param codeDetailVO
	 * @param account
	 * @throws EventException
	 */
	public void manageCodeDetailVO(CodeDetailVO[] codeDetailVO,SignOnUserAccount account) throws EventException;
}