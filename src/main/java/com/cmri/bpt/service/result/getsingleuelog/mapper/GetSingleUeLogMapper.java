package com.cmri.bpt.service.result.getsingleuelog.mapper;

import com.cmri.bpt.common.entity.CaseUeVO;
import com.cmri.bpt.common.entity.SingleUeLogPO;
import com.framework.layer.dao.ibatis.IbatisMapper;

@IbatisMapper
public interface GetSingleUeLogMapper {
	public void insertSingleUeLog(SingleUeLogPO singleuelog);
    public SingleUeLogPO selectsingleuedata(CaseUeVO caseuevo);
    public void updateSingleUeLog(SingleUeLogPO singleuelog);
    public int selectsingleuecount(SingleUeLogPO singleuelog);
}
