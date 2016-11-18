package com.cmri.bpt.service.ue;

import java.util.List;

import com.cmri.bpt.service.ue.bo.AppStatus;

public interface AppStatusService {

	void pesist(AppStatus s);
	List<AppStatus> query(Integer userId);
	
	List<AppStatus> queryAll();

}
