package com.cmri.bpt.service.result.pic_call.mapper;

import java.sql.SQLException;
import java.util.List;

import com.cmri.bpt.common.entity.CallUserNumberPO;
import com.framework.layer.dao.ibatis.IbatisMapper;
@IbatisMapper
public interface CallUserNumberMapper  {
	//保存语音通话用户数统计的信息
public void saveCallUserNumber(CallUserNumberPO callUserNumberPO) throws SQLException;
    //根据case_name查找相应的语音通话用户数统计的坐标
public List<CallUserNumberPO> selectCallUserNumber(String case_name) throws SQLException;
	

}
