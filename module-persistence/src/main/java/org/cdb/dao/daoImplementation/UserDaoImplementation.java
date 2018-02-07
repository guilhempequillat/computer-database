package org.cdb.dao.daoImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.cdb.dao.DaoUtilitary;
import org.cdb.exception.DAOException;
import org.cdb.mapper.RowMapperUser;
import org.cdb.model.Role;
import org.cdb.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;

@Transactional
@Repository
public class UserDaoImplementation {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private DataSource dataSource;

	private Logger logger = (Logger) LoggerFactory.getLogger(UserDaoImplementation.class);
	
	private final String SQL_ADD_USER = "INSERT INTO user (username, email, password) VALUES (?,?,?)";
	private final String SQL_FIND_ID_ROLE_BY_NAME= "SELECT id FROM role WHERE name = ?";
	private final String SQL_ADD_JOIN_ROLE= "INSERT INTO roles (user_id , role_id) VALUES (?,?)";
	private final String SQL_FIND_BY_NAME = " SELECT * FROM user LEFT JOIN  roles ON user.id = roles.user_id LEFT JOIN role ON roles.role_id = role.id WHERE user.username=?";
	
	public ArrayList<User> findAll(){
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
		cr.add(Restrictions.eq("username", name));
		List<User> users = cr.list();
		if( !users.isEmpty()) {
			for(User user : users) {
				if(user.getUsername().equals(name)) {
					logger.info(user.getRoles().toString());
					return user;
				}
			}
		}
		return null;
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
	    	 Iterator<Role> it = user.getRoles().iterator();
	    	 while( it.hasNext()) {
	    		 String roleName = it.next().getName();
	    		 preparedStatement = daoUtilitary.initializePreparedRequest( connection, SQL_FIND_ID_ROLE_BY_NAME , false , roleName);	 
	    		 ResultSet rs = preparedStatement.executeQuery();
	    		 rs.first();
	    		 int idRole = rs.getInt("id");
	    		 preparedStatement = daoUtilitary.initializePreparedRequest( connection, SQL_ADD_JOIN_ROLE , false , idUser, idRole);
	    		 preparedStatement.executeUpdate();
	    	 }
	    	connection.commit();
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	    	DaoUtilitary.closeDao(preparedStatement,connection);
	    }
	}

}
