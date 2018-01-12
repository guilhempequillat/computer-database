package servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import model.Computer;

@RunWith(MockitoJUnitRunner.class)
public class EditComputerServletTest {
	
	@Mock
	private EditComputerServlet editComputerServlet;
	
	@Test
	public void getComputerEditedTest() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		Mockito.when(editComputerServlet.getComputerEdited(request,1L)).thenCallRealMethod();
		
		Computer computer = new Computer();
		computer.setId(1L);
		ArrayList<Computer> listComputer = new ArrayList<>();
		listComputer.add(computer);
		Mockito.when(editComputerServlet.getListComputer(request)).thenReturn( listComputer );
		assertEquals(editComputerServlet.getComputerEdited(request,1L),computer );
		
		Mockito.when(editComputerServlet.getComputerEdited(request,2L)).thenCallRealMethod();
		assertEquals(editComputerServlet.getComputerEdited(request,2L),null );
		
		Mockito.when(editComputerServlet.getListComputer(request)).thenReturn( null );
		Mockito.when(editComputerServlet.getComputerEdited(request,-1L)).thenCallRealMethod();
		assertEquals(editComputerServlet.getComputerEdited(request,-1L),null );
		
		Mockito.when(editComputerServlet.getListComputer(request)).thenReturn( null );
		Mockito.when(editComputerServlet.getComputerEdited(request,1L)).thenCallRealMethod();
		assertEquals(editComputerServlet.getComputerEdited(request,1L),null );
	}
	
	@Test
	public void getListComputerTest() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpSession session = mock(HttpSession.class);

		Computer computer1 = new Computer();
		computer1.setId(1L);
		Computer computer2 = new Computer();
		computer2.setId(2L);
		
		ArrayList<Computer> listComputer = new ArrayList<>();
		listComputer.add(computer1);
		listComputer.add(computer2);

		Mockito.when(session.getAttribute("listComputer")).thenReturn(listComputer);
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(editComputerServlet.getListComputer(request)).thenCallRealMethod();
		assertEquals(editComputerServlet.getListComputer(request),listComputer );
		
		Mockito.when(session.getAttribute("listComputer")).thenReturn(null);
		Mockito.when(request.getSession()).thenReturn(session);
		assertEquals(editComputerServlet.getListComputer(request),null );
	}
	
	@Test
	public void loadComputerIdTest() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		
		Mockito.when(request.getParameter("id")).thenReturn("1");
		Mockito.when(editComputerServlet.loadComputerId(request)).thenCallRealMethod();
		assertEquals(editComputerServlet.loadComputerId(request),(Long) 1L );
		
		Mockito.when(request.getParameter("id")).thenReturn("1L");
		assertNotEquals(editComputerServlet.loadComputerId(request),(Long) 1L );
		
		Mockito.when(request.getParameter("id")).thenReturn("test");
		assertEquals(editComputerServlet.loadComputerId(request),(Long) (-1L) );

		Mockito.when(request.getParameter("id")).thenReturn("");
		assertEquals(editComputerServlet.loadComputerId(request),(Long) (-1L) );
	}
	
	@Test
	public void parseComputerTest() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		
		
	}
}
