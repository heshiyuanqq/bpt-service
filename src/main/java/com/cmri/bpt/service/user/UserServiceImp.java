package com.cmri.bpt.service.user;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmri.bpt.common.base.Result;
import com.cmri.bpt.common.base.Result.Type;
import com.cmri.bpt.common.exception.FieldValidationException;
import com.cmri.bpt.common.exception.HandleException;
import com.cmri.bpt.common.util.DigestUtil;
import com.cmri.bpt.common.util.EncodeUtil;
import com.cmri.bpt.service.user.bo.User;
import com.cmri.bpt.service.user.mapper.ProvinceMapper;
import com.cmri.bpt.service.user.mapper.UserMapper;

@Service
public class UserServiceImp implements UserService {

	private static final int SALT_SIZE = 8;

	@Resource(name = "userMapper")
	private UserMapper userMapper;

	@Autowired
	private ProvinceMapper provinceMapper;

	@SuppressWarnings("unchecked")
	@Override
	public Result<User> login(String phoneNumber, String password) {

		Result<User> result = Result.newOne();
		result.type = Type.info;

		User user = userMapper.selectUserByPhoneNumber(phoneNumber);

		if (user == null) {
			result.type = Type.warn;
			result.message = "用户名不存在!";
			return result;
		} else if (!isPasswordSame(password, user.getSalt(), user.getPassword())) {
			result.type = Type.warn;
			result.message = "用户密码错误!";
			return result;
		}

		result.data = user;
		return result;
	}

	@Override
	public void logout(User user) {
		updateSessionId(user.getPhoneNumber(), null);
	}

	@Override
	public void updateSessionId(String phoneNumber, String sessionId) {
		User parameter = new User();
		parameter.setPhoneNumber(phoneNumber);
		parameter.setSessionId(sessionId);

		userMapper.updateSessionId(parameter);
	}

	@Override
	public void updatePassword(User user, String oldPassword, String newPassword) {
		if (isPasswordSame(oldPassword, user.getSalt(), user.getPassword())) {
			user.setPassword(newPassword);
			entryptPassword(user);
			userMapper.updateUser(user);
		} else {
			throw new FieldValidationException("oldPassword", "NotMatch.oldPassword");
		}
	}

	@Override
	public void resetPassword(User user) {
		entryptPassword(user);
		userMapper.updateUser(user);
	}

	@Override
	public void updateInfo(User user) {
		userMapper.updateUser(user);
	}

	@Override
	public User findUserBySessionId(String phoneNumber, String sessionId) {
		User parameter = new User();
		parameter.setPhoneNumber(phoneNumber);
		parameter.setSessionId(sessionId);

		return userMapper.selectUserByPhoneNumberAndSessionId(parameter);
	}

	@Override
	public void addUser(User user) {
		entryptPassword(user);
		userMapper.insertUser(user);
	}

	@Override
	public User proxyAddUser(String phoneNumber, String nickname) {
		checkPhoneNumberHaveRegistered(phoneNumber);
		User user = new User();
		user.setNickname(nickname);
		user.setPhoneNumber(phoneNumber);
		user.setPassword(phoneNumber.substring(5));
		user.setRegisterTime(new Date());

		addUser(user);
		// promotionService.sendProxyRegisterInform(user);
		return user;
	}

	@Override
	public void checkPhoneNumberHaveRegistered(String phoneNumber) {
		User user = userMapper.selectUserByPhoneNumber(phoneNumber);
		if (user != null) {
			throw new HandleException("HaveRegistered.phoneNumber");
		}
	}

	@Override
	public User selectUserByPhoneNumber(String phoneNumber) {
		return userMapper.selectUserByPhoneNumber(phoneNumber);
	}

	private void entryptPassword(User user) {
		byte[] salt = DigestUtil.generateSalt(SALT_SIZE);
		user.setSalt(EncodeUtil.encodeHex(salt));

		user.setPassword(EncodeUtil.encodeHexOneStep(user.getPassword().getBytes(), salt));
	}

	private boolean isPasswordSame(String orignal, String salt, String encrypted) {

		if (StringUtils.isNotBlank(orignal) && StringUtils.isNotBlank(salt) && StringUtils.isNotBlank(encrypted)) {
			byte[] byteSalt = EncodeUtil.decodeHex(salt);
			String encryptedPassword = EncodeUtil.encodeHexOneStep(orignal.getBytes(), byteSalt);
			if (encryptedPassword.equals(encrypted)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public User selectUserById(Integer id) {
		return userMapper.selectById(id);
	}

	public Boolean existUserByPhoneNumber(String phoneNumber) {
		return userMapper.existUserByPhoneNumber(phoneNumber);
	}

	public int queryIsUniqueNickName(String nickname) {
		return userMapper.queryIsUniqueNickName(nickname);
	}

	public int queryIsUniquePhoneNumber(String phoneNumber) {
		return userMapper.queryIsUniquePhoneNumber(phoneNumber);
	}

	public int insertUser(User user) throws Exception {
		if (userMapper.queryIsUniqueNickName(user.getNickname()) > 0) {
			return 1;
		} else if (userMapper.queryIsUniquePhoneNumber(user.getPhoneNumber()) > 0) {
			return 2;
		} else if (user.getPassword() == null || "".equals(user.getPassword().trim())) {
			return 3;
		} else if (user.getCityId() == null) {
			return 4;
		} else {
			if (user.getCityId() != null && provinceMapper.queryIsEmptyCity(user.getCityId()) > 0) {
				entryptPassword(user);
				int i = userMapper.insertRegUser(user);
				if (i > 0) {
					return 6;
				} else {
					return 5;
				}
			} else {
				return 4;
			}
		}

	}

	@Override
	public void updatePassword(User user, String newPassword) {
		user.setPassword(newPassword);
		entryptPassword(user);
		userMapper.updateUser(user);
	}

	@Override
	public boolean checkPassword(User user, String oldPassword) {
		return isPasswordSame(oldPassword, user.getSalt(), user.getPassword());
	}
}
