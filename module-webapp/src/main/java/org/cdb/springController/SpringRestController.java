package org.cdb.springController;

import org.cdb.model.Company;
import org.cdb.model.Computer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class SpringRestController {

	@RequestMapping("/test/{test}")
	public Company computer(@PathVariable String test) {
		Computer msg = new Computer();
		System.out.println("Rest Controller");
        return new Company("test");	
    }
	@RequestMapping("/test2")
	public String test() {
		
		return "coucou";
    }
	
	@RequestMapping("/json/{string}")
	public String json(@PathVariable String s) {
		ObjectMapper objectMapper = new ObjectMapper();
		Company company  = new Company("Json");
		String jsonInString="";
		try {
			jsonInString = objectMapper.writeValueAsString(company);
		
			jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(company);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonInString;
	}
}
