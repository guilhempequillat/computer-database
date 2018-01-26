package org.cdb.springController;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.cdb.model.Computer;
import org.cdb.service.serviceImplementation.ComputerServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/edit-computer")
public class SpringEditComputerController {
	
	@Autowired
	private ComputerServiceImplementation computerService;
	
	private Long computerId;
	private Computer computer;
	
	@RequestMapping(method = RequestMethod.GET)
    public String getDashBoard(@RequestParam(value = "numberComputerToShow", required = false) String numberComputerToShow, 
    		@RequestParam(value = "id", required = false) String id, 
    		@RequestParam(value = "beginComputerDisplay", required = false) String beginComputerDisplay, 
    		Model model, Locale locale) {
		loadParameter(id,model);
        return "editComputer";
    }
	
	public void loadParameter(String id,Model model) {
		computer = computerService.find((int) Integer.parseInt(id.toString()));
		if(computer != null) {
			model.addAttribute("idComputer", computer.getId());
		}else {
			model.addAttribute("idComputer", -1L);
		}
	}
}
