package org.cdb.springController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.cdb.model.Company;
import org.cdb.model.Computer;
import org.cdb.model.User;
import org.cdb.service.serviceImplementation.CompanyServiceImplementation;
import org.cdb.service.serviceImplementation.ComputerServiceImplementation;
import org.cdb.service.serviceImplementation.UserServiceImplementation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Logger;

@RestController
public class SpringRestController {
	
	@Autowired
	private ComputerServiceImplementation computerService;
	
	@Autowired
	private CompanyServiceImplementation companyService;
	
	@Autowired
	UserServiceImplementation userService; 
	
	private Logger logger = (Logger) LoggerFactory.getLogger(SpringRestController.class);

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("find-a-computer/{id}")
	public String findAComputer(@PathVariable String id) {
		ObjectMapper objectMapper = new ObjectMapper();
		Computer computer  = computerService.find(Integer.parseInt(id));
		String jsonInString="";
		try {
			jsonInString = objectMapper.writeValueAsString(computer);
		
		} catch (JsonProcessingException e) {
			logger.error("To JSON format failure : "+e);
		}
		return jsonInString;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("find-all-companies")
	public String findAllCompanies() {
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayList<Company> listCompany = new ArrayList<>();
		String jsonString = "";
		listCompany = companyService.findAll();
		try {
			jsonString = objectMapper.writeValueAsString(listCompany);
		} catch (JsonProcessingException e) {
			logger.error("To JSON format failure : "+e);
		}
		return jsonString;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("find-computer-pagination")
	public String findComputerPagination(
			@RequestParam(value = "order", required = true) String order,
			@RequestParam(value = "orderType", required = true) String orderType,
			@RequestParam(value = "beginComputerDisplay", required = true) String beginComputerDisplay,
			@RequestParam(value = "numberComputerToShow", required = true) String numberComputerToShow) {
		ObjectMapper objectMapper = new ObjectMapper();

		ArrayList<Computer> listComputer= new ArrayList<>();
		String jsonString = "";
		if(order.equals("ASC")) {
			listComputer = computerService.findPaginationAsc(orderType, Integer.parseInt(beginComputerDisplay), Integer.parseInt(numberComputerToShow));
		}else if(order.equals("DESC") ) {
			listComputer = computerService.findPaginationDesc(orderType, Integer.parseInt(beginComputerDisplay), Integer.parseInt(numberComputerToShow));
		}
		try {
			jsonString = objectMapper.writeValueAsString(listComputer);
		} catch (JsonProcessingException e) {
			logger.error("To JSON format failure : "+e);
		}
		return jsonString;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("find-computer-pagination-filter")
	public String findComputerPaginationFilter(
		@RequestParam(value = "order", required = true) String order,
		@RequestParam(value = "orderType", required = true) String orderType,
		@RequestParam(value = "beginComputerDisplay", required = true) String beginComputerDisplay,
		@RequestParam(value = "numberComputerToShow", required = true) String numberComputerToShow,
		@RequestParam(value = "filterName", required = true) String filterName,
		@RequestParam(value = "filterIntroduced", required = true) String filterIntroduced,
		@RequestParam(value = "filterDiscontinued", required = true) String filterDiscontinued,
		@RequestParam(value = "filterCompany", required = true) String filterCompany) {
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayList<Computer> listComputer= new ArrayList<>();
		String jsonString = "";
		
		if(order.equals("ASC")) {
			listComputer = computerService.findPaginationAscFilter(orderType, Integer.parseInt(beginComputerDisplay), 
					Integer.parseInt(numberComputerToShow), filterName, filterIntroduced, filterDiscontinued, filterCompany);
		}else if(order.equals("DESC") ) {
			listComputer = computerService.findPaginationDescFilter(orderType, Integer.parseInt(beginComputerDisplay),
					Integer.parseInt(numberComputerToShow), filterName, filterIntroduced, filterDiscontinued, filterCompany);
		}
		try {
			jsonString = objectMapper.writeValueAsString(listComputer);
		} catch (JsonProcessingException e) {
			logger.error("To JSON format failure : "+e);
		}
		return jsonString;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("count-computer")
	public String countComputer() {
		Map<String, String> result = new HashMap<>();
		result.put("count", computerService.count()+"");
		String jsonString = "";
		try {
			jsonString = new ObjectMapper().writeValueAsString(result);
		} catch (JsonProcessingException e) {
			logger.error("To JSON format failure : "+e);
		}
		return jsonString;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("count-computer-filter")
	public String countFilter(
			@RequestParam(value = "filterName", required = true) String filterName,
			@RequestParam(value = "filterIntroduced", required = true) String filterIntroduced,
			@RequestParam(value = "filterDiscontinued", required = true) String filterDiscontinued,
			@RequestParam(value = "filterCompany", required = true) String filterCompany) {
		return computerService.countFilter(
				filterName, 
				filterIntroduced,
				filterDiscontinued,
				filterCompany)+"";
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("update-name")
	public String updateName(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "name", required = true) String name) {
		computerService.updateName(Integer.parseInt(id),name);
		return resultRequestDone();
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("update-introduced")
	public String updateIntroduced(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "introduced", required = true) String introduced) {
		LocalDate introducedld = null;
		try {
			introducedld = LocalDate.parse(introduced);
		} catch (DateTimeParseException e) {
			introducedld = null;
		}
		computerService.updateIntroduced(Integer.parseInt(id), introducedld);
		return resultRequestDone();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("update-discontinued")
	public String updateDiscontinued(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "discontinued", required = true) String discontinued) {
		LocalDate discontinuedld = null;
		try {
			discontinuedld = LocalDate.parse(discontinued);
		} catch (DateTimeParseException e) {
			discontinuedld = null;
		}
		computerService.updateDiscontinued(Integer.parseInt(id), discontinuedld);
		return resultRequestDone();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("update-company")
	public String updateCompany(@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "idCompany", required = true) String idCompany) {
		computerService.updateCompany(Integer.parseInt(id), Integer.parseInt(idCompany));
		return resultRequestDone();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("create-computer")
	public String createComputer(@RequestParam(value="name",required = true) String name,
			@RequestParam(value="introduced",required = true) String introduced,
			@RequestParam(value="discontinued",required = true) String discontinued,
			@RequestParam(value="idCompany",required = true) String idCompany) {
		Computer computer = new Computer();
		computer.setName(name);
		try {
			computer.setIntroduced(LocalDate.parse(introduced));
		}catch(DateTimeParseException e) {
			computer.setIntroduced(null);
		}
		try {
			computer.setDiscontinued(LocalDate.parse(discontinued));
		}catch(DateTimeParseException e) {
			computer.setDiscontinued(null);
		}
		try {
			computer.setCompany_id(Long.parseLong(idCompany));
		} catch (NumberFormatException e) {
			computer.setCompany_id(null);
		}
		computerService.create(computer);
		return resultRequestDone();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("create-company")
	public String createCompany(@RequestParam(value="name", required = true) String name) {
		Company company = new Company();
		company.setName(name);
		companyService.create(company);
		return resultRequestDone();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("delete-computer")
	public String deleteComputer(@RequestParam(value="id", required = true) String id) {
		computerService.delete(Integer.parseInt(id));
		return resultRequestDone();
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("delete-company")
	public String deleteCompany(@RequestParam(value="id", required = true) String id) {
		companyService.delete(Long.parseLong(id));
		return resultRequestDone();
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("register-user")
	public String registerUser(@RequestParam(value = "username", required = true) String username, 
        	@RequestParam(value = "password", required = true) String password, 
        	@RequestParam(value = "email", required = true) String email) {
		BCryptPasswordEncoder bcp = new BCryptPasswordEncoder();
		userService.addUser(new User(username,email,bcp.encode(password)));
        return "index";
	}
	
	public String resultRequestDone() {
		Map<String, String> result = new HashMap<>();
		result.put("state", "done");
		String jsonString = "";
		try {
			jsonString = new ObjectMapper().writeValueAsString(result);
		} catch (JsonProcessingException e) {
			logger.error("To JSON format failure : "+e);
		}
		return jsonString;
	}
}
