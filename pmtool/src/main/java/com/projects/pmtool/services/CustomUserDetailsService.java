package com.projects.pmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projects.pmtool.domain.User;
import com.projects.pmtool.repositories.UserRespository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	UserRespository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if(user==null) {
			throw new UsernameNotFoundException("User not found");
		}
		return user;
	}
	
	@Transactional
	public User loadUserById(Long id) {
		User user = userRepo.getById(id);
		if(user==null) {
			throw new UsernameNotFoundException("User not found");
		}
		return user;
	}

}
