package com.clt.apps.opus.esm.clv.doutraining.codemgmt.basic;

import java.util.List;

import com.clt.apps.opus.esm.clv.doutraining.codemgmt.vo.CodeDetailVO;
import com.clt.apps.opus.esm.clv.doutraining.codemgmt.vo.CodeVO;
import com.clt.apps.opus.esm.clv.doutraining.errmsgmgmt.vo.ErrMsgVO;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.support.view.signon.SignOnUserAccount;

public interface CodeMgmtBC {
	public List<CodeVO> searchCodeVO(CodeVO codeVO) throws EventException;
	public List<CodeDetailVO> searchCodeDetailVO(CodeDetailVO codeDetailVO) throws EventException;
	public void manageCodelVO(CodeVO[] codeVO,SignOnUserAccount account) throws EventException;
	public void manageCodeDetailVO(CodeDetailVO[] codeDetailVO,SignOnUserAccount account) throws EventException;
}