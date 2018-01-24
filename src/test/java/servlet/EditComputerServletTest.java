package servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.cdb.model.Computer;
import org.cdb.servlet.EditComputerServlet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

@RunWith(MockitoJUnitRunner.class)
public class EditComputerServletTest {
	
	private Logger logger = (Logger) LoggerFactory.getLogger("EditComputerServletTest");
	
	@Mock
	private EditComputerServlet editComputerServlet;
	
	@Test
	public void getComputerEditedTest() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		Mockito.when(editComputerServlet.getComputerEdited(request,1L)).thenCallRealMethod();
		
		Computer computer = new Computer();
		computer.setId(1L);
		Optional<Computer> computerOptional;
		computerOptional = Optional.of(computer);
		
		ArrayList<Computer> listComputer = new ArrayList<>();
		listComputer.add(computer);
		Optional<ArrayList<Computer>> listComputerOptional;
		listComputerOptional = Optional.of(listComputer);
		
		
		Mockito.when(editComputerServlet.getListComputer(request)).thenReturn( listComputerOptional );
		assertEquals(editComputerServlet.getComputerEdited(request,1L),computerOptional );
		
		Mockito.when(editComputerServlet.getComputerEdited(request,2L)).thenCallRealMethod();
		assertEquals(editComputerServlet.getComputerEdited(request,2L),Optional.empty() );
		
		Mockito.when(editComputerServlet.getListComputer(request)).thenReturn( Optional.empty()  );
		Mockito.when(editComputerServlet.getComputerEdited(request,-1L)).thenCallRealMethod();
		assertEquals(editComputerServlet.getComputerEdited(request,-1L),Optional.empty()  );
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
		
		Mockito.when(session.getAttribute("listComputer")).thenReturn(null);
		Mockito.when(request.getSession()).thenReturn(session);
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
		
		Mockito.when(request.getParameter("id")).thenReturn("1");
		Mockito.when(request.getParameter("computerName")).thenReturn("TestName");		
		Mockito.when(request.getParameter("introduced")).thenReturn("2000-10-30");		
		Mockito.when(request.getParameter("discontinued")).thenReturn("2000-11-30");		
		Mockito.when(request.getParameter("companyId")).thenReturn("12");
		Mockito.when(editComputerServlet.parseComputer(request)).thenCallRealMethod();
		Mockito.when(editComputerServlet.getComputerId()).thenReturn((Long) 1L);
		
		Computer computer = new Computer();
		computer.setId(1L);
		computer.setName("TestName");
		computer.setIntroduced(LocalDate.parse("2000-10-30"));
		computer.setDiscontinued(LocalDate.parse("2000-11-30"));
		computer.setCompany_id(12L);
	
		//assertTrue(editComputerServlet.parseComputer(request).equals(computer));
		
		Mockito.when(editComputerServlet.getComputerId()).thenReturn((Long) (-1L));
		Mockito.when(editComputerServlet.parseComputer(request)).thenCallRealMethod();
	
		//assertEquals(editComputerServlet.parseComputer(request),null);
	}
}
