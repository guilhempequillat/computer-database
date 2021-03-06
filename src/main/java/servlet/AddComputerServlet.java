package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.AddComputerController;

@WebServlet("/add-computer")
public class AddComputerServlet extends HttpServlet {
	
	private static AddComputerController pageWebAddComputer;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher( "/WEB-INF/view/addComputer.jsp" ).forward( request, response );
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		pageWebAddComputer = AddComputerController.getInstance(request);
		boolean isAdded = pageWebAddComputer.addComputerDb(request);
		if(isAdded) {
			this.getServletContext().getRequestDispatcher( "/dashboard" ).forward( request, response );
		} else {
			doGet(request,response);
		}
	}
}
