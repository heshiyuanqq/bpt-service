package com.cmri.bpt.service.action.mapper;

import java.sql.SQLException;
import java.util.List;

import com.cmri.bpt.entity.po.BaseActionPO;
import com.framework.layer.dao.ibatis.IbatisMapper;

/**
 * Action表操作
 *
 */
@IbatisMapper
public interface BaseActionMapper {
	
	/**
	 * 查询全部行为数据
	 * @return
	 * @throws SQLException
	 */
	public List<BaseActionPO> selectAllAction() throws SQLException;
	
}
