package com.cmri.bpt.service.param.mapper;

import java.sql.SQLException;
import java.util.List;

import com.cmri.bpt.entity.po.BaseCaseParamPO;
import com.framework.layer.dao.ibatis.IbatisMapper;

@IbatisMapper
public interface CaseParamMapper {
	/**
	 * 查询
	 * @return
	 * @throws SQLException
	 */
	public List<BaseCaseParamPO> selectParamByCond(BaseCaseParamPO po) throws SQLException;
}
