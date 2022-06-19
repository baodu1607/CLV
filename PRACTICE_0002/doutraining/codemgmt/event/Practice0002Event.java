/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : Practice0001Event.java
*@FileTitle : Error Message Management
*Open Issues :
*Change history :
*@LastModifyDate : 2022.06.11
*@LastModifier : 
*@LastVersion : 1.0
* 2022.06.11 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.doutraining.codemgmt.event;

import com.clt.framework.support.layer.event.EventSupport;
import com.clt.apps.opus.esm.clv.doutraining.codemgmt.vo.CodeDetailVO;
import com.clt.apps.opus.esm.clv.doutraining.codemgmt.vo.CodeVO;

public class Practice0002Event extends EventSupport {
	//The serialVersionUID attribute is an identifier that is used to serialize/deserialize an object
	private static final long serialVersionUID = 1L;
	
	/** Table Value Object 조회 조건 및 단건 처리  */
	//VO for single data
	CodeVO codeVO = null;
	CodeDetailVO codeDetailVO = null;
	
	/** Table Value Object Multi Data 처리 */
	//VO for many data
	CodeVO[] codeVOs = null;
	CodeDetailVO[] codeDetailVOs = null;

	public Practice0002Event(){}

	public CodeVO getCodeVO() {
		return codeVO;
	}

	public void setCodeVO(CodeVO codeVO) {
		this.codeVO = codeVO;
	}

	public CodeDetailVO getCodeDetailVO() {
		return codeDetailVO;
	}

	public void setCodeDetailVO(CodeDetailVO codeDetailVO) {
		this.codeDetailVO = codeDetailVO;
	}

	public CodeVO[] getCodeVOs() {
		return codeVOs;
	}

	public void setCodeVOs(CodeVO[] codeVOs) {
		this.codeVOs = codeVOs;
	}

	public CodeDetailVO[] getCodeDetailVOs() {
		return codeDetailVOs;
	}

	public void setCodeDetailVOs(CodeDetailVO[] codeDetailVOs) {
		this.codeDetailVOs = codeDetailVOs;
	}


}