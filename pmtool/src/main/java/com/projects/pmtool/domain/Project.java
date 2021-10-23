package com.projects.pmtool.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Project name is required")
	private String projectName;
	@NotBlank(message = "Project identifier is required")
	@Size(min=3, max=5,message = "Please use 3 to 5 characters")
	@Column(unique = true,updatable = false)
	private String projectIdentifier;
	@NotBlank(message = "Project description is required")
	private String description;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date startDate;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date endDate;
	
	@Column(nullable = false)
	private String projectStatus;
	
	@JsonFormat(pattern = "dd-MM-yyyy, hh:mm")
	private Date createdAt;
	@JsonFormat(pattern = "dd-MM-yyyy, hh:mm")
	private Date updatedAt;
	
	
	
	public Project() {
		super();
	}

	
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getProjectName() {
		return projectName;
	}



	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}



	public String getProjectIdentifier() {
		return projectIdentifier;
	}



	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public Date getStartDate() {
		return startDate;
	}



	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}



	public Date getEndDate() {
		return endDate;
	}



	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}



	public Date getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}



	public Date getUpdatedAt() {
		return updatedAt;
	}



	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}



	public String getProjectStatus() {
		return projectStatus;
	}



	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}



	@PrePersist
	protected void onCreate() {
		createdAt= new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		updatedAt= new Date();
	}
	
	

}
