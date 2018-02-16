package org.cdb.springController;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping("/perform-login")
public class SpringPerformLoginController {

	@RequestMapping(method = RequestMethod.POST)
    public String getDashBoard(Model model, Locale locale) {
        System.out.println("Perform-login");
        return "dashboard";
    }
	
}
