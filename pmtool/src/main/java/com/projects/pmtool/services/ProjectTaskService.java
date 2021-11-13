package com.projects.pmtool.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.pmtool.domain.Backlog;
import com.projects.pmtool.domain.Project;
import com.projects.pmtool.domain.ProjectTask;
import com.projects.pmtool.exceptions.ProjectNotFoundException;
import com.projects.pmtool.repositories.BacklogRepository;
import com.projects.pmtool.repositories.ProjectRepository;
import com.projects.pmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepo;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepo;
	
	@Autowired
	private ProjectRepository projectRepo;
	@Autowired
	private ProjectService projectService;
	
	public ProjectTask addProjectTask(String projectIdentifier,ProjectTask projectTask,String username) {
		

			Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();//backlogRepo.findByProjectIdentifier(projectIdentifier.toUpperCase());
			projectTask.setBacklog(backlog);
			
			Integer backlogSeq = backlog.getPTSequence();
			backlogSeq++;
			backlog.setPTSequence(backlogSeq);
			projectTask.setProjectSequence(backlog.getProjectIdentifier()+"-"+backlogSeq);
			projectTask.setProjectIdentifier(projectIdentifier.toUpperCase());

			
			if(projectTask.getPriority() ==null || projectTask.getPriority()==0){
				projectTask.setPriority(3);
			}
			
			if(projectTask.getStatus()=="" || projectTask.getStatus()==null) {
				projectTask.setStatus("TO_DO");
			}
			return projectTaskRepo.save(projectTask);
		
		
	}

	public Iterable<ProjectTask> findBacklogById(String id,String username) {
		Project project = projectService.findProjectByIdentifier(id.toUpperCase(),username);
		if(project == null) {
			throw new ProjectNotFoundException("Project with ID: '"+id.toUpperCase()+"' does not exist" );
		}
		return projectTaskRepo.findByProjectIdentifierOrderByPriority(id.toUpperCase());
	}
	
	public ProjectTask findPTByProjectSequence(String backlog_id,String taskSequence,String username) {
		
		Backlog backlog = projectService.findProjectByIdentifier(backlog_id.toUpperCase(),username).getBacklog();
		if(backlog==null) {
			throw new ProjectNotFoundException("Project with ID: '"+backlog_id.toUpperCase()+"' does not exist" );
		}
		
		ProjectTask projectTask = projectTaskRepo.findByProjectSequence(taskSequence.toUpperCase());
		if(projectTask == null) {
			throw new ProjectNotFoundException("Project Task '"+taskSequence.toUpperCase()+"' not found" );			
		}
		
		if(!projectTask.getProjectIdentifier().equals(backlog_id.toUpperCase())) {
			throw new ProjectNotFoundException("Project Task '"+taskSequence.toUpperCase()+"' does not exist in Project: '"+backlog_id.toUpperCase()+"'.");			

		}
		
		return projectTask;
	}

	public ProjectTask updatePTByProjectSequence(ProjectTask updatedTask,String backlog_id,String taskSequence,String username) {
		
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, taskSequence,username);
				
		projectTask=updatedTask;

		return projectTaskRepo.save(projectTask);
	}
	
public String deletePTByProjectSequence(String backlog_id,String taskSequence,String username) {
		
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, taskSequence,username);
		 
		//projectTaskRepo.delete(projectTask);
		return "Project Task with ID: '"+taskSequence.toUpperCase()+"' was deleted successfully" ;
	}
}
