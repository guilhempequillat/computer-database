package org.cdb.springController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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
@RequestMapping("/add-computer")
public class SpringAddComputerController {

	@Autowired
	private ComputerServiceImplementation computerService;
	@Autowired
	private CompanyServiceImplementation companyService;

	@Autowired
	private PasswordVerification passwordVerification;
	

	private static Logger logger = (Logger) LoggerFactory.getLogger("SpringAddComputerController");
	
	@RequestMapping(method = RequestMethod.GET)
    public String getAddComputer(
    		Model model, Locale locale) {
		loadCompany(model);
        return "addComputer";
    }
	
	@RequestMapping(method = RequestMethod.POST)
    public String postAddComputer(@RequestParam(value = "numberComputerToShow", required = false) String numberComputerToShow, 
    		@RequestParam(value = "computerName", required = false) String name,
    		@RequestParam(value = "introduced", required = false) String introduced,
    		@RequestParam(value = "discontinued", required = false) String discontinued,
    		@RequestParam(value = "companyId", required = false) String companyId,
    		@RequestParam(value = "password", required = false) String password, 
    		Model model, Locale locale) {
		boolean isAdded = addComputerDb(name, introduced, discontinued, companyId, password);
		if(isAdded) {
			return "redirect:/dashboard";
		}
		return "addComputer";
    }
	
	public void loadCompany(Model model) {
		ArrayList<Company> listCompany = companyService.findAll();
		model.addAttribute("listCompany", listCompany);
	}
	
	public Optional<Computer> parseComputer(String name, String introduced, String discontinued, String companyId) {
		Computer computer = new Computer();
		if( name != "" ) {
			computer.setName(name);
			if( introduced != "" ) {
				computer.setIntroduced( LocalDate.parse(introduced));
			}
			if( discontinued != "" ) {
				computer.setDiscontinued(LocalDate.parse(discontinued));
			}
			if( companyId != "" ) {
				computer.setCompany_id(Long.parseLong(companyId));
			} 
			return Optional.of(computer);
		}
		return Optional.empty();
	}
	
	public boolean allFieldsAreCompleted(Computer computer) {
		if(computer != null) {
			return (computer.getName()!=null && computer.getIntroduced()!=null && computer.getDiscontinued()!=null && computer.getCompany_id() !=null);
		}else {
			return false;
		}		
	}
	public boolean addComputerDb(String name, String introduced, String discontinued, String companyId, String password) {
		Optional<Computer> computer = parseComputer( name, introduced,  discontinued,  companyId);
		if(computer.isPresent() && passwordVerification.passwordIsCorrect(password)) {
			if(allFieldsAreCompleted(computer.get()) ) {
				computerService.create(computer.get());
				return true;
			}
		}else if( !passwordVerification.passwordIsCorrect(password)) {
			logger.info("Password invalide");
		}
		return false;
	}
}
