package org.cdb.springController;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.cdb.model.Computer;

@RestController
public class SpringRestController {

	@RequestMapping("/test")
	public Computer computer(@PathVariable String player) {
		Computer msg = new Computer();
        return msg;
    }
}
