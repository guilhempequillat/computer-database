package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

import model.Computer;

/**
 * Servlet implementation class indexServlet
 */
@WebServlet("/indexServlet")
public class IndexServlet extends HttpServlet {
	
	private static Logger logger = (Logger) LoggerFactory.getLogger("DaoFactory");
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Computer computer = new Computer();
		computer.setName("Toshiba satelite");
		request.getSession().setAttribute("computer", computer);
		this.getServletContext().getRequestDispatcher( "/WEB-INF/index.jsp" ).forward( request, response );
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("manage-members");
	}

}
