package com.cmri.bpt.service.param.interfac;
import com.cmri.bpt.entity.po.SysParamPO;

public interface SysParamService {
	
	void saveOrUpdate(SysParamPO sysParamPO);

	SysParamPO getByUserId(Integer userId);

}
