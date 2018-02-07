package org.cdb.springController;

import java.util.Locale;

import org.cdb.model.User;

import org.cdb.service.serviceImplementation.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SpringIndexController {
	
	@Autowired
	UserServiceImplementation userService; 

	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin(Model model, Locale locale) {
        return "index";
    }
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
    public String getRegister(@RequestParam(value = "username", required = true) String username, 
        	@RequestParam(value = "password", required = true) String password, 
        	@RequestParam(value = "email", required = true) String email, 
    	Model model, Locale locale) {
		BCryptPasswordEncoder bcp = new BCryptPasswordEncoder();
		userService.addUser(new User(username,email,bcp.encode(password)));
        return "index";
    }
}

