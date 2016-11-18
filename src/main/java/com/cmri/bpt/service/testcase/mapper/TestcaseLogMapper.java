package com.cmri.bpt.service.testcase.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cmri.bpt.entity.testcase.TestcaseLog;
import com.framework.layer.dao.ibatis.IbatisMapper;

@IbatisMapper
public interface TestcaseLogMapper {

	List<TestcaseLog> queryAll();
	
	List<TestcaseLog> query(@Param("array") Integer[] sessionIds);

	boolean exists(Integer appSessionId);

	void create(TestcaseLog s);

	void updateBySessionId(TestcaseLog s);

	void delete(TestcaseLog s);

}
