package com.cmri.bpt.service.result.pic_call;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmri.bpt.common.entity.CallUserNumberPO;
import com.cmri.bpt.service.result.pic_call.mapper.CallUserNumberMapper;
@Service(value="callUserNumberServiceImpl")
public class CallUserNumberServiceImpl implements ICallUserNumberService{
	@Autowired
	private CallUserNumberMapper callMapper;
	@Override
	public void insertCallUserNumber(CallUserNumberPO callUserNumberPO)
			throws SQLException {
		callMapper.saveCallUserNumber(callUserNumberPO);
		
	}

	@Override
	public List<CallUserNumberPO> getCallUserNumber(String case_name)
			throws SQLException {
		// TODO Auto-generated method stub
		return callMapper.selectCallUserNumber(case_name);
	}

}
