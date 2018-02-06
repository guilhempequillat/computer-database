package org.cdb.service.serviceImplementation;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.cdb.dao.daoImplementation.UserDaoImplementation;
import org.cdb.dao.daoInterface.ComputerDao;
import org.cdb.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserDetailsService{

	UserDaoImplementation userDao;

	public UserServiceImplementation(UserDaoImplementation userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		User user = userDao.findByName(name);
		return new org.springframework.security.core.userdetails.User(user.getUsername(),
				user.getPassword(),
				user.getRoles().stream()
				.map(x -> new SimpleGrantedAuthority(x.getName()))
				.collect(Collectors.toSet())
			);
	}

	public void addUser(User user) {
		userDao.addUser(user);
	}
}
