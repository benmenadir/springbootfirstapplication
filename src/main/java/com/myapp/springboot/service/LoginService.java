package com.myapp.springboot.service;

import org.springframework.stereotype.Service;

@Service
public class LoginService {
	
	public boolean validateUser(String userid, String password) {
		return userid.equalsIgnoreCase("benno") && password.equalsIgnoreCase("toto");
	}

}
