package org.cdb.springController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.cdb.model.Company;
import org.cdb.model.Computer;
import org.cdb.security.PasswordVerification;
import org.cdb.service.serviceImplementation.CompanyServiceImplementation;
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
@RequestMapping("/edit-computer")
public class SpringEditComputerController {

	@Autowired
	private ComputerServiceImplementation computerService;
	@Autowired
	private CompanyServiceImplementation companyService;
	@Autowired
	private PasswordVerification passwordVerification;

	private Long computerId;
	private Computer computer;
	private ArrayList<Company> listCompany;
	
	private static Logger logger = (Logger) LoggerFactory.getLogger("EditComputerServlet");
	
	@RequestMapping(method = RequestMethod.GET)
    public String getDashBoard(@RequestParam(value = "numberComputerToShow", required = false) String numberComputerToShow, 
    		@RequestParam(value = "id", required = true) String id,
    		Model model, Locale locale) {
		loadParameter(id,model);
        return "editComputer";
    }
	
	@RequestMapping(method = RequestMethod.POST)
    public String postEdit(@RequestParam(value = "numberComputerToShow", required = false) String numberComputerToShow, 
    		@RequestParam(value = "name", required = false) String name,
    		@RequestParam(value = "introduced", required = false) String introduced,
    		@RequestParam(value = "discontinued", required = false) String discontinued,
    		@RequestParam(value = "companyId", required = false) String companyId,
    		@RequestParam(value = "password", required = false) String password, 
    		Model model, Locale locale) {
		parseComputer(name, introduced, discontinued, companyId);
		if(computer != null) {
			updateDb(password);
		}
        return "redirect:/dashboard";
    }
	
	public void loadCompany(Model model) {
		listCompany = companyService.findAll();
		model.addAttribute("listCompany", listCompany);
	}
	
	public void loadParameter(String id,Model model) {
		computer = computerService.find((int) Integer.parseInt(id.toString()));
		loadCompany(model);
		if(computer != null) {
			model.addAttribute("idComputer", computer.getId());
			model.addAttribute("computer",computer);
			computerId = Long.parseLong(id);
		}else {
			model.addAttribute("idComputer", -1L);
		}
	}
	
	public void updateDb(String password) {
		if (computerId != (-1L) && inputAreValide() && passwordVerification.passwordIsCorrect(password)) {
			if(computer.getName() != null ) {
				computerService.updateName(Integer.parseInt(computer.getId().toString()), computer.getName());
			}
			if(computer.getIntroduced() != null) {
				computerService.updateIntroduced(Integer.parseInt(computer.getId().toString()), computer.getIntroduced());
			}
			if(computer.getDiscontinued() != null) {
				computerService.updateDiscontinued(Integer.parseInt(computer.getId().toString()), computer.getDiscontinued());
			}
			if(computer.getCompany_id() != null) {
				computerService.updateCompany(Integer.parseInt(computer.getId().toString()), Integer.parseInt(computer.getCompany_id().toString()));
			}
		}else if( !passwordVerification.passwordIsCorrect(password)) {
			logger.info("Password invalide");
		}
	}
	

	public boolean inputAreValide() {
		if(computer.getIntroduced() != null && computer.getDiscontinued() != null) {
			if(computer.getIntroduced().compareTo(computer.getDiscontinued()) < 0) {
				return true;
			} else {
				logger.info("The dates are invalide");
				return false;
			}
		}
		return true;
	}
	
	public void parseComputer(String name, String introduced, String discontinued, String companyId) {
		computer = new Computer();
		computer.setId(computerId);
		if( ! computer.getId().equals( (Long) (-1L))) {
			if( name != "" ) {
				computer.setName(name);
				logger.debug(computer.toString());
			}
			if( introduced!= "" ) {
				computer.setIntroduced( LocalDate.parse(introduced));
				logger.debug(computer.toString());
			}
			if( discontinued != "" ) {
				computer.setDiscontinued(LocalDate.parse(discontinued));
				logger.debug(computer.toString());
			}
			if( companyId != "" ) {
				computer.setCompany_id(Long.parseLong(companyId));
				logger.debug(computer.toString());
			}
		}
	}
}
