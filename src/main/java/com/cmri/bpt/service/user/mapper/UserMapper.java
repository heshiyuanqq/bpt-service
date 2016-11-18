package com.cmri.bpt.service.user.mapper;

import com.cmri.bpt.service.user.bo.User;
import com.framework.layer.dao.ibatis.IbatisMapper;

@IbatisMapper
public interface UserMapper {

	public User selectUserByPhoneNumberAndSessionId(User user);

	public void updateSessionId(User user);

	public void updateUser(User user);

	public User selectUserByPhoneNumber(String phoneNumber);

	public void insertUser(User user);
	
	public User selectById(Integer id);
	
	public Boolean existUserByPhoneNumber(String phoneNumber);

	public int queryIsUniqueNickName(String nickname);

	public int queryIsUniquePhoneNumber(String phoneNumber);

	public int insertRegUser(User user);
}
