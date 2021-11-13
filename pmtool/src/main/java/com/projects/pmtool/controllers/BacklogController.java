package com.projects.pmtool.controllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projects.pmtool.domain.ProjectTask;
import com.projects.pmtool.repositories.ProjectTaskRepository;
import com.projects.pmtool.services.MapValidationErrorService;
import com.projects.pmtool.services.ProjectTaskService;

@RestController
@RequestMapping("api/backlog")
@CrossOrigin
public class BacklogController {

	@Autowired
	private ProjectTaskService projectTaskService;
	
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@PostMapping("/{backlog_id}")
	public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask,BindingResult result
			, @PathVariable String backlog_id,Principal principal){
		
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
		if(errorMap!=null) return errorMap;
		
		ProjectTask projectTaskResult = projectTaskService.addProjectTask(backlog_id, projectTask,principal.getName());
		
		return new ResponseEntity<ProjectTask>(projectTaskResult,HttpStatus.CREATED);
	}
	
	@GetMapping("/{backlog_id}")
	public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id,Principal principal){
		return projectTaskService.findBacklogById(backlog_id,principal.getName());
	}
	
	@GetMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id,Principal principal){
		ProjectTask projectTask= projectTaskService.findPTByProjectSequence(backlog_id,pt_id,principal.getName());
		
		return new ResponseEntity<ProjectTask>(projectTask,HttpStatus.OK);
	}
	
	@PatchMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask updatedProjectTask,BindingResult result, @PathVariable String backlog_id, @PathVariable String pt_id,Principal principal){
		
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
		if(errorMap!=null) return errorMap;
		
		ProjectTask updatedTaskResp = projectTaskService.updatePTByProjectSequence(updatedProjectTask, backlog_id, pt_id,principal.getName());
		
		return new ResponseEntity<ProjectTask>(updatedTaskResp,HttpStatus.OK);
	}
	
	@DeleteMapping("/{backlog_id}/{pt_id}")
	public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id,Principal principal){
		
		String deletedTask = projectTaskService.deletePTByProjectSequence(backlog_id, pt_id,principal.getName());
		
		return new ResponseEntity<String>(deletedTask,HttpStatus.OK);
	}
	
}
