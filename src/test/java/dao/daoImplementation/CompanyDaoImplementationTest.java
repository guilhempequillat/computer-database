package dao.daoImplementation;

import static org.mockito.Mockito.mock;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import dao.DaoFactory;
import dao.DaoUtilitary;
import dao.ScriptRunner;
import mapper.CompanyMapper;

@RunWith(MockitoJUnitRunner.class)
public class CompanyDaoImplementationTest {

	private final String SCRIPT_SQL_PATH_SCHEMA = "/home/excilys/Documents/computer-database/git-computer-database/config/db/1-SCHEMA.sql";
	private final String SCRIPT_SQL_PATH_PRIVILEGES = "/home/excilys/Documents/computer-database/git-computer-database/config/db/2-PRIVILEGES.sql";
	private final String SCRIPT_SQL_PATH_ENTRIES = "/home/excilys/Documents/computer-database/git-computer-database/config/db/3-ENTRIES.sql";
	private final String SQL_SELECT = "SELECT * FROM company";
	private CompanyMapper companyMapper = CompanyMapper.getCompanyMapper();
	private Logger logger = (Logger) LoggerFactory.getLogger("CompanyDaoImplementationTest");
	
	@Mock
	private CompanyDaoImplementation companyDaoImplementation;
	

	private static Connection connection;
	
	@BeforeClass 
	public static void onlyOnce() {
		try {
			connection = (Connection) DriverManager.getConnection("jdbc:hsqldb:mem:computer-database", "test",  "test");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFindAll() {
		DaoFactory daoFactory = mock(DaoFactory.class);
		DaoUtilitary daoUtilitary = mock(DaoUtilitary.class);
		Mockito.when(companyDaoImplementation.findAll()).thenCallRealMethod();
		try {
			createTable(connection);
			Mockito.when(companyDaoImplementation.getDaoFactoryConnection()).thenReturn(connection);
			Mockito.when(companyDaoImplementation.getPreparedStatement(connection)).thenReturn( (PreparedStatement) connection.prepareStatement(SQL_SELECT));
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(SQL_SELECT);
			ResultSet resultSet = preparedStatement.executeQuery();
			System.out.println(resultSet.next());
			System.out.println();
			System.out.println(companyDaoImplementation.findAll());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void createTable(Connection connection) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, FileNotFoundException, IOException {
		Class.forName("org.hsqldb.jdbcDriver").newInstance();
		connection = (Connection) DriverManager.getConnection("jdbc:hsqldb:mem:computer-database", "test",  "test");
		ScriptRunner scriptRunner = new ScriptRunner(connection, true, true);
		scriptRunner.runScript(new BufferedReader(new FileReader(SCRIPT_SQL_PATH_SCHEMA)));
		scriptRunner.runScript(new BufferedReader(new FileReader(SCRIPT_SQL_PATH_ENTRIES)));
	}
}
