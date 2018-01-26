package org.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cdb.controller.DeleteController;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ch.qos.logback.classic.Logger;

/**
 * Servlet implementation class DeleteComputerServlet
 */
@Controller
@WebServlet("/delete-computer")
public class DeleteComputerServlet extends HttpServlet {
       
	
    private Logger logger = (Logger) LoggerFactory.getLogger("DeleteComputerServlet");
    
    @Autowired
	private DeleteController deleteController;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		deleteController.loadListId(request);
		deleteController.loadPassword(request);
		deleteController.deleteComputerDb();
		this.getServletContext().getRequestDispatcher( "/dashboard" ).forward( request, response );
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext servletContext = config.getServletContext();
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	    AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
	    autowireCapableBeanFactory.autowireBean(this);
	}
}
