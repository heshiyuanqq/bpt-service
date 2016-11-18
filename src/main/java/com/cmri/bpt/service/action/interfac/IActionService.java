package com.cmri.bpt.service.action.interfac;

import java.sql.SQLException;
import java.util.List;

import com.cmri.bpt.entity.po.BaseActionPO;

public interface IActionService {
	
	/**
	 * 获取所有action
	 * @return
	 * @throws SQLException
	 */
	public List<BaseActionPO> getAllAction() throws SQLException;
}
