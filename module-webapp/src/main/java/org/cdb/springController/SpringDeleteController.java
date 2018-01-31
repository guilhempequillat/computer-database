package org.cdb.springController;

import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.cdb.security.PasswordVerification;
import org.cdb.service.serviceImplementation.ComputerServiceImplementation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/delete-computer")
public class SpringDeleteController {
	
	@Autowired
	private ComputerServiceImplementation computerService;
	@Autowired
	private PasswordVerification passwordVerification;

	private ArrayList<Integer> listIdComputer = new ArrayList<>();
	
    private Logger logger = (Logger) LoggerFactory.getLogger("SpringDeleteController");
	
	@RequestMapping(method = RequestMethod.POST)
    public String getDeleteComputer(@RequestParam(value = "selection", required = false) String listId,
    		@RequestParam(value = "password", required = false) String password, 
    		Model model, Locale locale) {
		loadListId(listId);
		deleteComputerDb(password);
        return "redirect:/dashboard";
    }
	
	public void loadListId(String listId) {
		String[] listComputerIdString = listId.split(",");
		for(String computerIdString : listComputerIdString ) {
			listIdComputer.add(Integer.parseInt(computerIdString));
		}
	}
	
	public void deleteComputerDb(String password) {
		if( passwordVerification.passwordIsCorrect(password)) {
			for( Integer id : listIdComputer ) {
				computerService.delete(id);
				logger.debug(id.toString());
			}
		} else {
			logger.info("The password is not correct");
		}	
	}
}
