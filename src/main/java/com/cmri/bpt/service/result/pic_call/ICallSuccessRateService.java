package com.cmri.bpt.service.result.pic_call;

import java.sql.SQLException;
import java.util.List;

import com.cmri.bpt.common.entity.CallSuccessRatePO;


public interface ICallSuccessRateService {
	public void insertCallsuccessRate(CallSuccessRatePO  callsuccessRatePO ) throws SQLException;
	public List<CallSuccessRatePO> getCallsuccessRate(String case_name) throws SQLException;

}
