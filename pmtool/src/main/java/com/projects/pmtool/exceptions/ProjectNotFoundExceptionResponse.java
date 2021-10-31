package com.projects.pmtool.exceptions;

public class ProjectNotFoundExceptionResponse {

	private String projectNotFound;

	public ProjectNotFoundExceptionResponse(String projectNotFound) {
		this.projectNotFound = projectNotFound;
	}


	public String getProjectNotFound() {
		return this.projectNotFound;
	}

	public void setProjectNotFound(String projectNotFound) {
		this.projectNotFound = projectNotFound;
	}
	
	
	
}
