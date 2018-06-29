package com.digitzones.service;

import java.util.List;

import com.digitzones.model.User;
/**
 * 用户管理service
 * @author zdq
 * 2018年6月11日
 */
public interface IUserService extends ICommonService<User> {
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	public User login(String username,String password);
	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	public User queryUserByUsername(String username);
	/**
	 * 查询非当前用户
	 * @return
	 */
	public List<User> queryNotCurrentUsers(Long currentUserId);
	/**
	 * 查询所有用户
	 * @return
	 */
	public List<User> queryAllUsers();
}
