package com.projects.pmtool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projects.pmtool.domain.User;

@Repository
public interface UserRespository extends CrudRepository<User, Long> {
	User findByUsername(String username);
	User getById(Long id);
}
