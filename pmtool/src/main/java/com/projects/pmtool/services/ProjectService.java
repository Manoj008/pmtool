package com.projects.pmtool.services;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projects.pmtool.domain.Backlog;
import com.projects.pmtool.domain.Project;
import com.projects.pmtool.domain.User;
import com.projects.pmtool.exceptions.ProjectIdException;
import com.projects.pmtool.exceptions.ProjectNotFoundException;
import com.projects.pmtool.repositories.BacklogRepository;
import com.projects.pmtool.repositories.ProjectRepository;
import com.projects.pmtool.repositories.UserRespository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepo;
	@Autowired
	private BacklogRepository backlogRepo;
	@Autowired
	private UserRespository userRepo;

	public Project saveOrUppdateProject(Project project,String username) {

		if(project.getId() != null) {
			
			Project existingProject = projectRepo.findByProjectIdentifier(project.getProjectIdentifier());
			
			if(existingProject != null && (!existingProject.getProjectLeader().equals(username))) {
				throw new ProjectNotFoundException("Project no found in your account");
			}
			
			if(existingProject !=null && (!existingProject.getProjectIdentifier().equals(project.getProjectIdentifier()))) {
				throw new ProjectNotFoundException("mismtch!, Project Identifier does not match with poject id");
			}
		}
		
		try {
			
			User user = userRepo.findByUsername(username);
			project.setUser(user);
			project.setProjectLeader(user.getUsername());
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			
			if(project.getId()==null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}
			
			if(project.getId()!=null) {
				project.setBacklog(backlogRepo.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}
			
			if(project.getProjectStatus()==null) {
				project.setProjectStatus("Planned");
			}
			return projectRepo.save(project);

		} catch (Exception ex) {
			throw new ProjectIdException("Project Id: "+project.getProjectIdentifier().toUpperCase()+" already exist.");
		}
	}
	
	public Project findProjectByIdentifier(String projectId,String username) {
		Project response = projectRepo.findByProjectIdentifier(projectId.toUpperCase());
		
		if(response == null) {
			throw new ProjectIdException("Project Id: "+projectId.toUpperCase()+" does not exist.");
		}
		
		if(!response.getProjectLeader().equals(username)) {
			throw new ProjectNotFoundException("project not found in your account");
		}
		
		return response;
	}

	public Iterable<Project> findAllProjects(String username){
		
		return projectRepo.findAllByProjectLeader(username);
	}
	
	public Project dropProjectByIdentifier(String projectId,String username) {
		Project project = findProjectByIdentifier(projectId.toUpperCase(),username);
		if(project==null) {
			throw new ProjectIdException("Can not drop project with ProjectId: "+projectId.toUpperCase()+". This project does not exist.");

		}
		
		project.setProjectStatus("Dropped");
		projectRepo.save(project);
		
		return project;
		
	}
	
}
