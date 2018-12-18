package com.myapp.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.myapp.springboot.service.LoginService;




@Controller
@SessionAttributes("name")
public class LoginController {
	
	@Autowired
	LoginService service;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String showLoginPage(ModelMap model) {
		
		//model.addAttribute("name", name);
		return "login";
	}
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String showWelcomPage(Model model,@RequestParam("name") String name,@RequestParam("password") String password) {
		boolean isValidUser = service.validateUser(name, password);
		if (!isValidUser) {
			model.addAttribute("errorMessage", "Invalid credentials");
			return "login";
		}
		model.addAttribute("name", name);
		model.addAttribute("password", password);
		return "welcome";
	}
}
