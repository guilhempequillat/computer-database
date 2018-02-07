package org.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.cdb.model.Computer;
import org.cdb.model.Role;
import org.cdb.model.User;
import org.springframework.jdbc.core.RowMapper;

public class RowMapperUser implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int arg1) throws SQLException {
		String name = rs.getString("username");
		String password = rs.getString("password");
		String email = rs.getString("email");
		Set<Role> roles = new HashSet<>();
		while(rs.next()) {
			roles.add(new Role(rs.getString("name")));
		}
		return new User(name,email,password,roles);
	}

}
