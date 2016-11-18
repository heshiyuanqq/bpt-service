package com.cmri.bpt.service.token.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.cmri.bpt.entity.auth.AppTokenSession;
import com.framework.layer.dao.ibatis.IbatisMapper;

@IbatisMapper
public interface AppTokenSessionMapper {

	public void updateSession(AppTokenSession appSession);

	public void insertSession(AppTokenSession appSession);

	public void deleteSession(AppTokenSession appSession);

	public AppTokenSession selectByToken(String token);

	public List<AppTokenSession> queryAll();

	public List<AppTokenSession> queryByUserId(Integer userId);
	
	public List<AppTokenSession> queryByIds(@Param(value = "array") Integer[] ids);
	
	public Integer getMaxSysId(Integer userId);

	public void updateTag(@Param(value = "tag") String tag, @Param(value = "array") Integer[] ids);
	
	public void updateBox(@Param(value = "box") String box, @Param(value = "array") Integer[] ids);

	public void updateOnline(@Param(value = "online") Boolean online, @Param(value = "array") Integer[] ids);

	public List<String> getTags(Integer userId);

	public List<String> getXppIds(@Param(value = "userId") Integer userId, @Param(value = "tag") String tag);

}
