package com.zfz.myspringmvc.controller;

import com.zfz.myspringmvc.annotation.Controller;
import com.zfz.myspringmvc.annotation.Qualifier;
import com.zfz.myspringmvc.annotation.RequestMapping;
import com.zfz.myspringmvc.service.UserService;

@Controller("userController")
@RequestMapping("/user")
public class UserController {
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@RequestMapping("/insert")
	public void insert(){
		userService.insert();
	}
}
