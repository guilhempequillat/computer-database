package org.cdb.dao.daoImplementation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.cdb.dao.DaoUtilitary;
import org.cdb.exception.DAOException;
import org.cdb.model.Company;
import org.cdb.model.Role;
import org.cdb.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class UserDaoImplementation {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private DataSource dataSource;

	private final String SQL_ADD_USER = "INSERT INTO user (username, email, password) VALUES (?,?,?)";
	private final String SQL_FIND_ID_ROLE_BY_NAME= "SELECT id FROM role WHERE name = ?";
	private final String SQL_ADD_JOIN_ROLE= "INSERT INTO roles (user_id , role_id) VALUES (?,?)";
	
	
	public ArrayList<User> findAll(){
		ArrayList<User> users = new ArrayList<User>();
		Session session = sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(User.class);
		return (ArrayList<User>) cr.list();
	}
	
	public User find(int id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(User.class);
		cr.add(Restrictions.eq("id", (Long) Long.parseLong(id+"")));
		return (User) cr.uniqueResult();
	}
	
	public void delete(int id) {
		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.createCriteria(User.class)
				.add(Restrictions.eq("id", (Long) Long.parseLong(id+""))).uniqueResult();
		session.delete(user);
	}
	
	public User findByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Criteria cr = session.createCriteria(User.class);
		cr.add(Restrictions.eq("username",name));
		return (User) cr.uniqueResult();
	}
	
	public void addUser(User user) {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    DaoUtilitary daoUtilitary = DaoUtilitary.getInstance();
	    try {
	    	connection = (Connection) dataSource.getConnection();
	    	connection.setAutoCommit(false);
	    	Object[] objects = { user.getUsername() , user.getEmail(), user.getPassword()};
	    	 preparedStatement = daoUtilitary.initializePreparedRequest(connection, SQL_ADD_USER , true , objects);
	    	 preparedStatement.executeUpdate();
	    	 ResultSet rskey = preparedStatement.getGeneratedKeys();
	    	 rskey.first();
	    	 int idUser = rskey.getInt(1);
	    	 System.out.println("User !!!!!!!!! "+idUser);
	    	 Iterator<Role> it = user.getRoles().iterator();
	    	 while( it.hasNext()) {
	    		 String roleName = it.next().getName();
	    		 preparedStatement = daoUtilitary.initializePreparedRequest( connection, SQL_FIND_ID_ROLE_BY_NAME , false , roleName);	 
	    		 ResultSet rs = preparedStatement.executeQuery();
	    		 rs.first();
	    		 int idRole = rs.getInt("id");
	    		 System.out.println("User !!!!!!!!! "+roleName);
	    		 preparedStatement = daoUtilitary.initializePreparedRequest( connection, SQL_ADD_JOIN_ROLE , false , idUser, idRole);
	    		 preparedStatement.executeUpdate();
	    	 }
	    	connection.commit();
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	DaoUtilitary.closeDao(preparedStatement,connection);
	    	DaoUtilitary.closeDao(preparedStatement,connection);
	    }
	}

}
