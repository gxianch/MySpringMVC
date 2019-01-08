package com.zfz.myspringmvc.dao.impl;

import com.zfz.myspringmvc.annotation.Repository;
import com.zfz.myspringmvc.dao.UserDao;
@Repository(value = "userDaoImpl")
public class UserDaoImpl implements UserDao{

	public void insert() {
		System.out.println("UserDaoImpl execute insert");
	}

}
