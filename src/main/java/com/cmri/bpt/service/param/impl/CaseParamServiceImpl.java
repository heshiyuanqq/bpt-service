package com.cmri.bpt.service.param.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmri.bpt.entity.po.BaseCaseParamPO;
import com.cmri.bpt.entity.po.UserCaseParamPO;
import com.cmri.bpt.entity.vo.paramVO;
import com.cmri.bpt.service.param.interfac.ICaseParamService;
import com.cmri.bpt.service.param.mapper.CaseParamMapper;
import com.cmri.bpt.service.param.mapper.UserCaseParamMapper;

@Service(value = "paramService")
public class CaseParamServiceImpl implements ICaseParamService {

	@Autowired
	private CaseParamMapper paramMapper;

	@Autowired
	private UserCaseParamMapper userparamMapper;

	@Override
	public String saveParamByUserId(Integer userid, List<paramVO> paramvolist) {
		String reStr = "FAILED";
		if (userid != null && paramvolist != null && !paramvolist.isEmpty()) {
			for (paramVO voitem : paramvolist) {
				UserCaseParamPO ucpo = new UserCaseParamPO();
				switch (voitem.getCode()) {
				case "WeiXin_DestID":
				case "FTP_DestIP":
					ucpo.setUserid(userid);
					ucpo.setStrParamId(voitem.getCode());
					try {
						userparamMapper.deleteUserCaseParamByUserIdWithCondition(ucpo);
						BaseCaseParamPO bcpo = new BaseCaseParamPO();
						bcpo.setParamid(voitem.getCode());
						List<BaseCaseParamPO> poList = paramMapper.selectParamByCond(bcpo);
						for (BaseCaseParamPO poitem : poList) {
							ucpo = new UserCaseParamPO();
							ucpo.setUserid(userid);
							ucpo.setStrParamId(voitem.getCode());
							ucpo.setStrActionName(poitem.getActionname());
							ucpo.setStrBusinessSonName(poitem.getBusinesssonname());
							ucpo.setStrParamAllName(poitem.getParamallname());
							ucpo.setStrValue(voitem.getValue());
							userparamMapper.insertUserCaseParam(ucpo);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					reStr = "SUCCESS";
					break;
				default:
					ucpo = new UserCaseParamPO();
					ucpo.setUserid(userid);
					ucpo.setStrParamAllName(voitem.getCode());
					try {
						userparamMapper.deleteUserCaseParamByUserIdWithCondition(ucpo);
						BaseCaseParamPO bcpo = new BaseCaseParamPO();
						bcpo.setParamallname(voitem.getCode());
						List<BaseCaseParamPO> poList = paramMapper.selectParamByCond(bcpo);
						for (BaseCaseParamPO poitem : poList) {
							ucpo = new UserCaseParamPO();
							ucpo.setUserid(userid);
							ucpo.setStrParamId(poitem.getParamid());
							ucpo.setStrActionName(poitem.getActionname());
							ucpo.setStrBusinessSonName(poitem.getBusinesssonname());
							ucpo.setStrParamAllName(poitem.getParamallname());
							ucpo.setStrValue(voitem.getValue());
							userparamMapper.insertUserCaseParam(ucpo);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					reStr = "SUCCESS";
					break;
				}
			}
		}
		return reStr;
	}

	@Override
	public String getParamValueByUserIdAndParamCode(Integer userid, String param_name) {
		String paramValue = null;
		UserCaseParamPO ucpo = new UserCaseParamPO();
		ucpo.setUserid(userid);
		ucpo.setStrParamAllName(param_name);
		try {
			List<UserCaseParamPO> userList = userparamMapper.selectUserCaseParamByUserIdWithCondition(ucpo);
			if (userList != null && !userList.isEmpty()) {
				paramValue = userList.get(0).getStrValue();
			} else {
				BaseCaseParamPO bcpo = new BaseCaseParamPO();
				bcpo.setParamallname(param_name);
				List<BaseCaseParamPO> baseList = paramMapper.selectParamByCond(bcpo);
				if (baseList != null && !baseList.isEmpty()) {
					paramValue = baseList.get(0).getDefaultvalue();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (paramValue != null && paramValue.equals("Group")) {
			paramValue = getTargetDest(param_name, userid, paramValue);
		}
		return paramValue;
	}

	/**
	 * 获取发送目标，功能后期添加
	 * 
	 * @param param_name
	 * @param userid
	 * @param paramValue
	 * @return
	 */
	private String getTargetDest(String param_name, Integer userid, String paramValue) {
		String reStr = "";
		if (param_name.contains("WeiXin")) {
			reStr = "Ma";
		} else if (param_name.contains("Call")) {
			reStr = "10086";
		}
		return reStr;
	}

	@Override
	public List<BaseCaseParamPO> getBaseParamByAction(String action_name) {
		List<BaseCaseParamPO> reList = null;
		if (action_name != null) {
			BaseCaseParamPO po = new BaseCaseParamPO();
			po.setActionname(action_name);
			try {
				reList = paramMapper.selectParamByCond(po);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return reList;
	}

	@Override
	public List<UserCaseParamPO> selectUserCaseParam(Integer userId) {
		
		UserCaseParamPO po =new UserCaseParamPO();
		
		po.setUserid(userId);
		
		try {
			return userparamMapper.selectUserCaseParamByUserIdWithCondition(po);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return null;
	}

}
