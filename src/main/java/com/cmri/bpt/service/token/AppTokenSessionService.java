package com.cmri.bpt.service.token;

import java.util.List;

import com.cmri.bpt.entity.auth.AppTokenSession;

public interface AppTokenSessionService {

	List<AppTokenSession> queryByUserId(Integer userId);
	
	List<AppTokenSession> queryAll();

	void updateTagByIds(List<Integer> ids, String tag);
	
	
	List<AppTokenSession> queryByIds(List<Integer> ids);
	
	List<String> queryTags(Integer userId);
	
	
	List<String> queryXppIds(Integer userId, String tag);

}
