package org.cdb.springConfiguration;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.Import;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Import({ SecurityConfig.class })
public class MainConfig implements WebApplicationInitializer {
 
    @Override
    public void onStartup(ServletContext servletContext) {
    	AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
    	context.register(WebMvcConfig.class);
    	context.setServletContext(servletContext);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dashboard", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/dashboard");
        dispatcher.addMapping("/");
    }
}
