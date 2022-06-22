package com.clt.apps.opus.esm.clv.doutraining.carriermgmt.basic;

import java.util.List;

import com.clt.apps.opus.esm.clv.doutraining.carriermgmt.vo.CarrierVO;
import com.clt.apps.opus.esm.clv.doutraining.codemgmt.vo.CodeVO;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.support.view.signon.SignOnUserAccount;
public interface CarrierMgmtBC {
	public List<CarrierVO> getCrrCds() throws EventException;
	public List<CarrierVO> getLnCds() throws EventException;
	public List<CarrierVO> searchCarrierVO(CarrierVO carrierVO) throws EventException;
	public List<CarrierVO> searchCust(CarrierVO carrierVO) throws EventException;
	public void manageCarrierVO(CarrierVO[] carrierVO,SignOnUserAccount account) throws EventException;
}