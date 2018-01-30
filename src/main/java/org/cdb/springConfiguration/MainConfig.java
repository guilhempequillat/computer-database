package org.cdb.springConfiguration;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@ComponentScan(basePackages = { "org.cdb" })
public class MainConfig implements WebApplicationInitializer {
 
    @Override
    public void onStartup(ServletContext servletContext) {
    	AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
    	context.setConfigLocation("org.cdb");
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dashboard", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
         
    }
}
