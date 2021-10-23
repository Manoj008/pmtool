package com.projects.pmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.pmtool.domain.Project;
import com.projects.pmtool.repositories.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	ProjectRepository projectRepo;
	
	public Project	 saveOrUppdateProject(Project project) {
		return projectRepo.save(project);
	}
	
}
