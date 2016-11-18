package com.cmri.bpt.service.param.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmri.bpt.entity.po.SysParamPO;
import com.cmri.bpt.service.param.interfac.SysParamService;
import com.cmri.bpt.service.param.mapper.SysParamMapper;

@Service
public class SysParamServiceImpl implements SysParamService{
	
	@Autowired
	SysParamMapper sysParamMapper;
		
	@Override
	public void saveOrUpdate(SysParamPO sysParamPO){
		//根据用户查询如果有则更否则新增
		SysParamPO tempPO = new SysParamPO();
		tempPO.setUserId(sysParamPO.getUserId());
		List<SysParamPO> list=sysParamMapper.getListByCondition(tempPO);
		if(list==null||list.size()==0){
			//添加
			sysParamMapper.save(sysParamPO);
		}else{
			//更新
			sysParamPO.setId(list.get(0).getId());
			sysParamMapper.update(sysParamPO);
		}
	}

	@Override
	public SysParamPO getByUserId(Integer userId){
			SysParamPO sysParamPO=new SysParamPO();
			sysParamPO.setUserId(userId);
			List<SysParamPO> list = sysParamMapper.getListByCondition(sysParamPO);
			if(list!=null&&list.size()>0){
				return list.get(0);
			}else{
				return null;
			}
	}

}