package com.projects.pmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projects.pmtool.domain.Project;
import com.projects.pmtool.exceptions.ProjectIdException;
import com.projects.pmtool.repositories.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	ProjectRepository projectRepo;

	public Project saveOrUppdateProject(Project project) {

		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			if(project.getProjectStatus()==null) {
				project.setProjectStatus("Planned");
			}
			return projectRepo.save(project);

		} catch (Exception ex) {
			throw new ProjectIdException("Project Id: "+project.getProjectIdentifier().toUpperCase()+" already exist.");
		}
	}
	
	public Project findProjectByIdentifier(String projectId) {
		Project response = projectRepo.findByProjectIdentifier(projectId.toUpperCase());
		
		if(response == null) {
			throw new ProjectIdException("Project Id: "+projectId.toUpperCase()+" does not exist.");
		}
		
		return response;
	}

	public Iterable<Project> findAllProjects(){
		
		return projectRepo.findAll();
	}
	
	public Project dropProjectByIdentifier(String projectId) {
		Project project = projectRepo.findByProjectIdentifier(projectId.toUpperCase());
		if(project==null) {
			throw new ProjectIdException("Can not drop project with ProjectId: "+projectId.toUpperCase()+". This project does not exist.");

		}
		
		project.setProjectStatus("Dropped");
		projectRepo.save(project);
		
		return project;
		
	}
	
}
