package com.projects.pmtool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projects.pmtool.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long>{

	Project  findByProjectIdentifier(String projectIdentifier);
	
	Iterable<Project> findAllByProjectLeader(String username);
	
}
