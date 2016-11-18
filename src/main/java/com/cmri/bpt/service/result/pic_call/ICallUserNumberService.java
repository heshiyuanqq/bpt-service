package com.cmri.bpt.service.result.pic_call;

import java.sql.SQLException;
import java.util.List;

import com.cmri.bpt.common.entity.CallUserNumberPO;

public interface ICallUserNumberService {
	public void insertCallUserNumber(CallUserNumberPO  callUserNumberPO ) throws SQLException;
	public List<CallUserNumberPO> getCallUserNumber(String case_name) throws SQLException;
}
