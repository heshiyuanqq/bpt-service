package com.cmri.bpt.service.param.interfac;

import java.util.List;

import com.cmri.bpt.entity.po.BaseCaseParamPO;
import com.cmri.bpt.entity.po.UserCaseParamPO;
import com.cmri.bpt.entity.vo.paramVO;
import com.framework.layer.dao.ibatis.IbatisMapper;

public interface ICaseParamService {
	
	/**
	 * 保存用户参数
	 * @param userid userid
	 * @param paramvolist 参数列表
	 * @return
	 */
	public String saveParamByUserId(Integer userid,List<paramVO> paramvolist);
	
	/**
	 * 通过参数名称获取参数值
	 * @param userid userid
	 * @param action_name 参数名称
	 * @return
	 */
	public String getParamValueByUserIdAndParamCode(Integer userid,String param_name);
	
	/**
	 * 通过行为获取基础参数
	 * @param action_name
	 * @return
	 */
	public List<BaseCaseParamPO> getBaseParamByAction(String action_name);
	
	//根据用户Id获取自定义参数值
	public List<UserCaseParamPO> selectUserCaseParam(Integer userId);
}
