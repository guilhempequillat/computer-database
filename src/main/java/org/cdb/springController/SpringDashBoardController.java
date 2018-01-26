package org.cdb.springController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class SpringDashBoardController {
	
	@Autowired
	
	
	@RequestMapping(value="/dashboard",method=RequestMethod.GET)
	public void getDashBoard() {
		System.out.println("Dashboard");
	}
}
