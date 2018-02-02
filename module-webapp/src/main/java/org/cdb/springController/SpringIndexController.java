package org.cdb.springController;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/index")
public class SpringIndexController {

	@RequestMapping(method = RequestMethod.GET)
    public String getDashBoard(@RequestParam(value = "numberComputerToShow", required = false) String numberComputerToShow, 
    		@RequestParam(value = "order", required = false) String order, 
    		@RequestParam(value = "beginComputerDisplay", required = false) String beginComputerDisplay, 
    		Model model, Locale locale) {
        return "index";
    }

}
