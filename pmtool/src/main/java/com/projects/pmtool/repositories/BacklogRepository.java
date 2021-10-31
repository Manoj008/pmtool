package com.projects.pmtool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projects.pmtool.domain.Backlog;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog,Long> {
	Backlog findByProjectIdentifier(String identifier);
}
