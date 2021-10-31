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
	
	public ProjectTask addProjectTask(String projectIdentifier,ProjectTask projectTask) {
		
		try {
			
			Backlog backlog = backlogRepo.findByProjectIdentifier(projectIdentifier.toUpperCase());
			projectTask.setBacklog(backlog);
			
			Integer backlogSeq = backlog.getPTSequence();
			backlogSeq++;
			backlog.setPTSequence(backlogSeq);
			projectTask.setProjectSequence(backlog.getProjectIdentifier()+"-"+backlogSeq);
			projectTask.setProjectIdentifier(projectIdentifier.toUpperCase());

			
			if(projectTask.getPriority() ==null){
				projectTask.setPriority(3);
			}
			
			if(projectTask.getStatus()=="" || projectTask.getStatus()==null) {
				projectTask.setStatus("TO_DO");
			}
			return projectTaskRepo.save(projectTask);
		} catch (Exception e) {
			throw new ProjectNotFoundException("Project Not Found");
		}
		
	}

	public Iterable<ProjectTask> findBacklogById(String id) {
		Project project = projectRepo.findByProjectIdentifier(id.toUpperCase());
		if(project == null) {
			throw new ProjectNotFoundException("Project with ID: '"+id.toUpperCase()+"' does not exist" );
		}
		return projectTaskRepo.findByProjectIdentifierOrderByPriority(id.toUpperCase());
	}
	
	public ProjectTask findPTByProjectSequence(String backlog_id,String taskSequence) {
		
		Backlog backlog = backlogRepo.findByProjectIdentifier(backlog_id.toUpperCase());
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

	public ProjectTask updatePTByProjectSequence(ProjectTask updatedTask,String backlog_id,String taskSequence) {
		
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, taskSequence);
				
		projectTask=updatedTask;

		return projectTaskRepo.save(projectTask);
	}
	
public String deletePTByProjectSequence(String backlog_id,String taskSequence) {
		
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, taskSequence);
		 
		projectTaskRepo.delete(projectTask);
		return "Project Task with ID: '"+taskSequence.toUpperCase()+"' was deleted successfully" ;
	}
}
