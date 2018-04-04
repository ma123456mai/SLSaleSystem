package org.slsale.service.user;

import java.util.List;

import org.slsale.pojo.User;

public interface UserService {
	/**
	 * 用户登录
	 * */
	public User getLoginUser(User user) throws Exception;

	/**
	 * 验证登录名
	 * */
	public int loginCodeIsExists(User user) throws Exception;

	/**
	 * 修改用户信息
	 * */
	public int modifyUser(User user) throws Exception;

	/**
	 * 查询所有用户信息
	 * */
	public List<User> getUserList(User user) throws Exception;

	/**
	 * 查询总的记录条数
	 * */
	public int count(User user) throws Exception;

	/**
	 * 增加用户
	 * */
	public int addUser(User user) throws Exception;

	/**
	 * 删除用户
	 * */
	public int delUser(User user) throws Exception;

	/**
	 * 查询某一个用户详情（id）
	 * */
	public User getUserById(User user) throws Exception;
}
