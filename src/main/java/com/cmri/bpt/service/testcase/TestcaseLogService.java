package com.cmri.bpt.service.testcase;

import java.util.List;

import com.cmri.bpt.entity.testcase.TestcaseLog;

public interface TestcaseLogService {

	List<TestcaseLog> queryAll();
	
	List<TestcaseLog> query(List<Integer> sessionId);	
	
	void persist(TestcaseLog log);	
	
}
