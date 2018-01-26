package org.cdb.springConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "org.cdb" })
public class WebMvcConfig extends WebMvcConfigurerAdapter {
 
	   	@Override
		public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		    configurer.enable();
		}
	   
	   @Override
	   public void addResourceHandlers(final ResourceHandlerRegistry registry) {
	       registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/lib/js/");
	       registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/lib/css/");
	   }

	    /**
	     * Provide a view resolver to map views to the correct template files.
	     *
	     * @return
	     */
	    @Bean
	    public ViewResolver getViewResolver() {
	        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	        viewResolver.setPrefix("/WEB-INF/view/");
	        viewResolver.setSuffix(".jsp");
	        return viewResolver;
	}
 
}
