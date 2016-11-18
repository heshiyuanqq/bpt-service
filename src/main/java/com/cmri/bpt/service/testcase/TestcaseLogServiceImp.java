package com.cmri.bpt.service.testcase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmri.bpt.entity.testcase.TestcaseLog;
import com.cmri.bpt.service.testcase.mapper.TestcaseLogMapper;

@Service
public class TestcaseLogServiceImp implements TestcaseLogService {

	@Autowired
	TestcaseLogMapper mapper;

	@Override
	public void persist(TestcaseLog log) {

		if (mapper.exists(log.getAppSessionId()))
			mapper.updateBySessionId(log);
		else

			mapper.create(log);

	}

	@Override
	public List<TestcaseLog> queryAll() {
		return mapper.queryAll();
	}

	@Override
	public List<TestcaseLog> query(List<Integer> sessionId) {

		return mapper.query((Integer[]) sessionId.toArray(new Integer[0]));
	}

}
