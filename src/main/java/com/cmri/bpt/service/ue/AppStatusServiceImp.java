package com.cmri.bpt.service.ue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmri.bpt.service.ue.bo.AppStatus;
import com.cmri.bpt.service.ue.mapper.AppStatusMapper;

@Service
public class AppStatusServiceImp implements AppStatusService {

	@Autowired
	AppStatusMapper mapper;

	@Override
	public void pesist(AppStatus s) {

		if (s.getAppSessionId() == null)

			return;

		Integer cnt = mapper.count(s.getAppSessionId());
		if (cnt == 1)
			mapper.updateBySessionId(s);
		else if (cnt >= 2) {
			mapper.deleteBySessionId(s.getAppSessionId());
			mapper.create(s);
		} else
			mapper.create(s);

	}

	@Override
	public List<AppStatus> query(Integer userId) {
		return mapper.query(userId);
	}

	@Override
	public List<AppStatus> queryAll() {

		return mapper.queryAll();
	}

}
