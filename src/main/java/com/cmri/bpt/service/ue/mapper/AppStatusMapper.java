package com.cmri.bpt.service.ue.mapper;

import java.util.List;

import com.cmri.bpt.service.ue.bo.AppStatus;
import com.framework.layer.dao.ibatis.IbatisMapper;

@IbatisMapper
public interface AppStatusMapper {

	List<AppStatus> query(Integer userId);
	
	List<AppStatus> queryAll();

	boolean exists(Integer sessionId);

	void create(AppStatus s);

	void updateBySessionId(AppStatus s);

	void delete(AppStatus s);
	
	void deleteBySessionId(Integer sessionId);
	
	Integer count(Integer sessionId);

}
