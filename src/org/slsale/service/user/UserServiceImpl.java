package org.slsale.service.user;

import java.util.List;

import javax.annotation.Resource;

import org.slsale.dao.user.UserMapper;
import org.slsale.pojo.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;
	
	public User getLoginUser(User user) throws Exception {
		return userMapper.getLoginUser(user);
	}

	public int loginCodeIsExists(User user) throws Exception {
		return userMapper.loginCodeIsExists(user);
	}

	public int modifyUser(User user) throws Exception {
		return userMapper.modifyUser(user);
	}

	public List<User> getUserList(User user) throws Exception {
		return userMapper.getUserList(user);
	}

	public int count(User user) throws Exception {
		return userMapper.count(user);
	}

	@Override
	public int addUser(User user) throws Exception {
		return userMapper.addUser(user);
	}

	@Override
	public int delUser(User user) throws Exception {
		return userMapper.delUser(user);
	}

	@Override
	public User getUserById(User user) throws Exception {
		return userMapper.getUserById(user);
	}

}
