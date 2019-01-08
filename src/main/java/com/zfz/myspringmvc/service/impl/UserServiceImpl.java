package com.zfz.myspringmvc.service.impl;

import com.zfz.myspringmvc.annotation.Qualifier;
import com.zfz.myspringmvc.annotation.Service;
import com.zfz.myspringmvc.dao.UserDao;
import com.zfz.myspringmvc.service.UserService;
@Service(value = "userServiceImpl")
public class UserServiceImpl implements UserService{
	@Qualifier("userDaoImpl")
	private UserDao userDao;
	
	public void insert() {
		System.out.println("UserServiceImpl start");
		userDao.insert();
		System.out.println("UserServiceImpl end");
	}

}
