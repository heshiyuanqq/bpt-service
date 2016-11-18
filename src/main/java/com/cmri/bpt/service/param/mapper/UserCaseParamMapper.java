package com.cmri.bpt.service.param.mapper;

import java.sql.SQLException;
import java.util.List;

import com.cmri.bpt.entity.po.UserCaseParamPO;
import com.framework.layer.dao.ibatis.IbatisMapper;

@IbatisMapper
public interface UserCaseParamMapper {
	
	/**
	 * 根据用户ID和其他条件删除记录
	 * @throws SQLException
	 */
	public void deleteUserCaseParamByUserIdWithCondition(UserCaseParamPO po) throws SQLException;
	
	/**
	 * 插入记录
	 * @param po
	 * @return
	 * @throws SQLException
	 */
	public Integer insertUserCaseParam(UserCaseParamPO po) throws SQLException;
	
	/**
	 * 查询
	 * @param po
	 * @return
	 * @throws SQLException
	 */
	public List<UserCaseParamPO> selectUserCaseParamByUserIdWithCondition(UserCaseParamPO po) throws SQLException;
}
