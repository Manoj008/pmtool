package com.projects.pmtool.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projects.pmtool.domain.Project;
import com.projects.pmtool.services.MapValidationErrorService;
import com.projects.pmtool.services.ProjectService;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

	@Autowired
	ProjectService projectService;
	
	@Autowired
	MapValidationErrorService mapValidationErrorService;
	
	
	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project,BindingResult result,Principal principal){
		ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
		
		if(errorMap != null) {
			return errorMap;
		}
		
		Project response = projectService.saveOrUppdateProject(project,principal.getName());
		return new ResponseEntity<Project>(response,HttpStatus.CREATED);
	}
	
	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectId,Principal principal){
		Project response = projectService.findProjectByIdentifier(projectId,principal.getName());
		
		return new ResponseEntity<Project>(response,HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public Iterable<Project> getAllProjetcs(Principal principal){
		return projectService.findAllProjects(principal.getName());
	}
	
	
	@DeleteMapping("/{projectId}")
	public ResponseEntity<?> dropProjectById(@PathVariable String projectId,Principal principal){
		Project response = projectService.dropProjectByIdentifier(projectId,principal.getName());
		
		return new ResponseEntity<String>("Project with projectId: "+response.getProjectIdentifier().toUpperCase()+" is dropped.",HttpStatus.OK);
	}
}
