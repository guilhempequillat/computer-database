package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import controller.DeleteController;
import model.Computer;

/**
 * Servlet implementation class DeleteComputerServlet
 */
@WebServlet("/delete-computer")
public class DeleteComputerServlet extends HttpServlet {
       
	
    private Logger logger = (Logger) LoggerFactory.getLogger("DeleteComputerServlet");
	private DeleteController deleteController;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		deleteController = DeleteController.getInstance();
		deleteController.loadListId(request);
		deleteController.loadPassword(request);
		deleteController.deleteComputerDb();
		this.getServletContext().getRequestDispatcher( "/dashboard" ).forward( request, response );
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
