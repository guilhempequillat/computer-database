package org.cdb.cli;

import org.cdb.cli.Page;
import org.cdb.springConfiguration.ApplicationConfig;
import org.cdb.springConfiguration.MainConfig;
import org.cdb.springConfiguration.WebMvcConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Main {

	public static void main(String[] args) {
		ApplicationContext app = new AnnotationConfigApplicationContext(WebMvcConfig.class);
		Page page = Page.class.cast(app.getBean("Page"));
		page.commandLineInterfaceWorking();
	}

}
