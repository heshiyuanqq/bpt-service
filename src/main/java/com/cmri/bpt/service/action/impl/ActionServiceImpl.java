package com.cmri.bpt.service.action.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmri.bpt.entity.po.BaseActionPO;
import com.cmri.bpt.service.action.interfac.IActionService;
import com.cmri.bpt.service.action.mapper.BaseActionMapper;

@Service(value="actionService")
public class ActionServiceImpl implements IActionService {
	
	@Autowired
	private BaseActionMapper actionMapper;

	@Override
	public List<BaseActionPO> getAllAction() throws SQLException {
		return actionMapper.selectAllAction();
	}

}
