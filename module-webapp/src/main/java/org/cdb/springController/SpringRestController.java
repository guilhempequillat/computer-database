package org.cdb.springController;

import org.cdb.model.Company;
import org.cdb.model.Computer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
