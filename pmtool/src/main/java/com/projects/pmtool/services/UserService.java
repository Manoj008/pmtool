package com.projects.pmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.projects.pmtool.domain.User;
import com.projects.pmtool.exceptions.UsernameAlreadyExistException;
import com.projects.pmtool.repositories.UserRespository;

@Service
public class UserService {

	@Autowired
	private UserRespository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User saveUser(User newUser) {
		 	try {
				newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
				newUser.setUsername(newUser.getUsername());
				newUser.setConfirmPassword("");
				return userRepo.save(newUser);
			} catch (Exception e) {
				throw new UsernameAlreadyExistException("username '"+newUser.getUsername()+"' already exist");
			}
	}
}
