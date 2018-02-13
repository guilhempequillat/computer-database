package org.cdb.springController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.cdb.model.Company;
import org.cdb.model.Computer;
import org.cdb.service.serviceImplementation.CompanyServiceImplementation;
import org.cdb.service.serviceImplementation.ComputerServiceImplementation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
	
	private Logger logger = (Logger) LoggerFactory.getLogger(SpringRestController.class);

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

	@GetMapping("find-computer-pagination")
	public String findComputerPagination(@RequestParam(value = "params", required = true) String params) {
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayList<Computer> listComputer= new ArrayList<>();
		String jsonString = "";
		HashMap<String,String> mapParams = convertJsonToHashMap(params);
		
		if(mapParams.get("order").equals("ASC")) {
			listComputer = computerService.findPaginationAsc(mapParams.get("orderType"), Integer.parseInt(mapParams.get("beginComputerDisplay")), Integer.parseInt(mapParams.get("numberComputerToShow")));
		}else if(mapParams.get("order").equals("DESC") ) {
			listComputer = computerService.findPaginationDesc(mapParams.get("orderType"), Integer.parseInt(mapParams.get("beginComputerDisplay")), Integer.parseInt(mapParams.get("numberComputerToShow")));
		}
		try {
			jsonString = objectMapper.writeValueAsString(listComputer);
		} catch (JsonProcessingException e) {
			logger.error("To JSON format failure : "+e);
		}
		return jsonString;
	}
	
	@GetMapping("find-computer-pagination-filter")
	public String findComputerPaginationFilter(@RequestParam(value = "params", required = true) String params) {
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayList<Computer> listComputer= new ArrayList<>();
		String jsonString = "";
		
		HashMap<String,String> mapParams = convertJsonToHashMap(params);
		
		if(mapParams.get("order").equals("ASC")) {
			listComputer = computerService.findPaginationAscFilter(mapParams.get("orderType"), Integer.parseInt(mapParams.get("beginComputerDisplay")), Integer.parseInt(mapParams.get("numberComputerToShow")), mapParams.get("filterName"), mapParams.get("filterIntroduced"), mapParams.get("filterDiscontinued"), mapParams.get("filterCompany"));
		}else if(mapParams.get("order").equals("DESC") ) {
			listComputer = computerService.findPaginationDescFilter(mapParams.get("orderType"), Integer.parseInt(mapParams.get("beginComputerDisplay")), Integer.parseInt(mapParams.get("numberComputerToShow")), mapParams.get("filterName"), mapParams.get("filterIntroduced"), mapParams.get("filterDiscontinued"), mapParams.get("filterCompany"));
		}
		try {
			jsonString = objectMapper.writeValueAsString(listComputer);
		} catch (JsonProcessingException e) {
			logger.error("To JSON format failure : "+e);
		}
		return jsonString;
	}
	
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
	
	@GetMapping("count-computer-filter")
	public String countFilter(@RequestParam(value = "params", required = true) String params) {
		HashMap<String, String> jsonMap = convertJsonToHashMap(params);
		return computerService.countFilter(
				jsonMap.get("filterName"), 
				jsonMap.get("filterIntroduced"), 
				jsonMap.get("filterDiscontinued"), 
				jsonMap.get("filterCompany"))+"";
	}

	@PutMapping("update-name")
	public String updateName(@RequestParam(value = "params", required = true) String params) {
		HashMap<String, String> jsonMap = convertJsonToHashMap(params);
		
		computerService.updateName(Integer.parseInt(jsonMap.get("id")), jsonMap.get("name"));

		return resultRequestDone();
	}

	@PutMapping("update-introduced")
	public String updateIntroduced(@RequestParam(value = "params", required = true) String params) {
		HashMap<String, String> jsonMap = convertJsonToHashMap(params);
		LocalDate introduced = LocalDate.parse(jsonMap.get("introduced"));
		computerService.updateIntroduced(Integer.parseInt(jsonMap.get("id")), introduced);
		return resultRequestDone();
	}

	@PutMapping("update-discontinued")
	public String updateDiscontinued(@RequestParam(value = "params", required = true) String params) {
		HashMap<String, String> jsonMap = convertJsonToHashMap(params);
		LocalDate discontinued = LocalDate.parse(jsonMap.get("discontinued"));
		computerService.updateDiscontinued(Integer.parseInt(jsonMap.get("id")), discontinued);
		return resultRequestDone();
	}
	
	@PutMapping("update-company")
	public String updateCompany(@RequestParam(value = "params", required = true) String params) {
		HashMap<String, String> jsonMap = convertJsonToHashMap(params);
		computerService.updateCompany(Integer.parseInt(jsonMap.get("id")), Integer.parseInt(jsonMap.get("idCompany")));
		
		return resultRequestDone();
	}
	
	@PostMapping("create-computer")
	public String createComputer(@RequestParam(value="params",required = true) String params) {
		HashMap<String, String> jsonMap = convertJsonToHashMap(params);
		Computer computer = new Computer();
		computer.setName(jsonMap.get("name"));
		computer.setIntroduced(LocalDate.parse(jsonMap.get("introduced")));
		computer.setDiscontinued(LocalDate.parse(jsonMap.get("discontinued")));
		computer.setCompany_id(Long.parseLong(jsonMap.get("idCompany")));
		computerService.create(computer);
		return resultRequestDone();
	}
	
	@PostMapping("create-company")
	public String createCompany(@RequestParam(value="params", required = true) String params) {
		HashMap<String, String> jsonMap = convertJsonToHashMap(params);
		Company company = new Company();
		company.setName(jsonMap.get("name"));
		companyService.create(company);
		return resultRequestDone();
	}
	
	@DeleteMapping("delete-computer")
	public String deleteComputer(@RequestParam(value="params", required = true) String params) {
		HashMap<String, String> jsonMap = convertJsonToHashMap(params);
		computerService.delete(Integer.parseInt(jsonMap.get("id")));
		return resultRequestDone();
	}
	
	@DeleteMapping("delete-company")
	public String deleteCompany(@RequestParam(value="params", required = true) String params) {
		HashMap<String, String> jsonMap =  convertJsonToHashMap(params);
		companyService.delete(Long.parseLong("id"));
		return resultRequestDone();
	}
	
	public HashMap<String,String> convertJsonToHashMap(String jsonString){
		byte[] dataJson = jsonString.getBytes();
		HashMap<String,String> jsonMap = new HashMap<String, String>();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			jsonMap = objectMapper.readValue(dataJson, HashMap.class);
		}catch(ClassCastException e) {
			logger.error("The json input isn't only String composed : "+e);
		} catch (JsonParseException e) {
			logger.error("Json String to hashMap failed : "+e);
		} catch (JsonMappingException e) {
			logger.error("Json String to hashMap failed : "+e);
		} catch (IOException e) {
			logger.error("Json String to hashMap failed : "+e);
		}
		return jsonMap;
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
