package com.cmri.bpt.service.result.pic_call;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmri.bpt.common.entity.CallSuccessRatePO;
import com.cmri.bpt.service.result.pic_call.mapper.CallSuccessRateMapper;

@Service(value="callsuccessRateServiceImpl")
public class CallsuccessRateServiceImpl implements ICallSuccessRateService{
	@Autowired
	private CallSuccessRateMapper callsuccessRateMapper;

	@Transactional
	@Override
	public void insertCallsuccessRate(CallSuccessRatePO callsuccessRatePO)
			throws SQLException {
		callsuccessRateMapper.saveCallsuccessRate(callsuccessRatePO);
		
	}

	@Override
	public List<CallSuccessRatePO> getCallsuccessRate(String case_name)
			throws SQLException {
		return callsuccessRateMapper.selectCallsuccessRate(case_name);
	}

}
