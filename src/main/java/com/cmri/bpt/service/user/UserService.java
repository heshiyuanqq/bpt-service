package com.cmri.bpt.service.user;

import com.cmri.bpt.common.base.Result;
import com.cmri.bpt.service.user.bo.User;

public interface UserService {
	
	public Result<User> login(String phoneNumber, String password);

	public void logout(User user);

	public void updateSessionId(String phoneNumber, String sessionId);

	public void updatePassword(User user, String oldPassword, String newPassword);
	
	public void updatePassword(User user, String newPassword);
	
	public void resetPassword(User user);

	public void updateInfo(User user);

	public User findUserBySessionId(String phoneNumber, String sessionId);

	public void checkPhoneNumberHaveRegistered(String phoneNumber);
	
	public User selectUserByPhoneNumber(String phoneNumber);

	public void addUser(User user);

	public User proxyAddUser(String phoneNumber, String nickname);
	
	public User selectUserById(Integer id);
	
	public Boolean existUserByPhoneNumber(String phoneNumber);

	public int queryIsUniqueNickName(String nickname);

	public int queryIsUniquePhoneNumber(String phoneNumber);

	public int insertUser(User user) throws Exception;
	
	/**
	 * 检查当前用户密码是否是所传密码
	 * @param user
	 * @param oldPassword
	 */
	public boolean checkPassword(User user, String oldPassword);
}
