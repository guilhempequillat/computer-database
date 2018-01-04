package persistance;

import java.sql.DriverManager;

import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Save {

	
	public void saveCompany() {
		String url = "jdbc:mysql://localhost:3306/computer-database-db";
		String login = "admincdb";
		String password = "qwerty1234";
		Connection connection = null;
		Statement statement = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = (Connection) DriverManager.getConnection(url , login , password);
			statement = (Statement) connection.createStatement();
			String companyName = "Test";
			String sql = "INSERT INTO `company` (`name`) VALUES ('" + companyName + "') ";
			statement.executeUpdate(sql);
			
		} catch( ClassNotFoundException e) {
			System.out.println(e);
		} catch(SQLException e) {
			System.out.println(e);
		} finally {
			if(connection != null) {
				try {
					System.out.println("Fermeture db");
					connection.close();
				}catch( SQLException e ) {
					System.out.println(e);
				}
			}
		}
	}
}
