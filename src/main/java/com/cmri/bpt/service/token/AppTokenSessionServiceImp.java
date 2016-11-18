package com.cmri.bpt.service.token;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmri.bpt.entity.auth.AppTokenSession;
import com.cmri.bpt.service.token.mapper.AppTokenSessionMapper;

@Service
public class AppTokenSessionServiceImp implements AppTokenSessionService {

	@Autowired
	AppTokenSessionMapper mapper;

	@Override
	public List<AppTokenSession> queryByUserId(Integer userId) {

		return mapper.queryByUserId(userId);
	}

	@Override
	public void updateTagByIds(List<Integer> ids, String tag) {

		mapper.updateTag(tag, (Integer[]) ids.toArray(new Integer[0]));

	}

	@Override
	public List<String> queryTags(Integer userId) {

		return mapper.getTags(userId);

	}

	@Override
	public List<String> queryXppIds(Integer userId, String tag) {

		return mapper.getXppIds(userId, tag);
	}

	@Override
	public List<AppTokenSession> queryAll() {
		return mapper.queryAll();
	}

	@Override
	public List<AppTokenSession> queryByIds(List<Integer> ids) {

		return mapper.queryByIds((Integer[]) ids.toArray(new Integer[0]));
	}

}
