package com.projects.pmtool.exceptions;

public class ProjectIdExceptionResponse {
	
	private String projectIdentifier;
	

	public ProjectIdExceptionResponse(String msg) {
		this.projectIdentifier = msg;
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}
	
	

}
