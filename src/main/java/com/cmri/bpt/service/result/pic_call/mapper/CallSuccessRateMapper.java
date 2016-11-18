package com.cmri.bpt.service.result.pic_call.mapper;

import java.sql.SQLException;
import java.util.List;

import com.cmri.bpt.common.entity.CallSuccessRatePO;import com.framework.layer.dao.ibatis.IbatisMapper;
@IbatisMapper
public interface CallSuccessRateMapper {
	//保存语音成功率的信息
public void saveCallsuccessRate(CallSuccessRatePO callsuccessRatePO) throws SQLException;
    //根据case_name查找相应的所有的语音成功率的节点
public List<CallSuccessRatePO> selectCallsuccessRate(String case_name) throws SQLException;
	


}
