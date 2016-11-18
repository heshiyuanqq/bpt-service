package com.cmri.bpt.service.param.mapper;
import java.util.List;
import com.cmri.bpt.entity.po.SysParamPO;
import com.framework.layer.dao.ibatis.IbatisMapper;


@IbatisMapper
public interface SysParamMapper {
	public List<SysParamPO> getListByCondition(SysParamPO sysParamPO);

	public void save(SysParamPO sysParamPO);

	public void update(SysParamPO sysParamPO);

}
