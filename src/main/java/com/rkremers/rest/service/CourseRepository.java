package com.rkremers.rest.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import com.rkremers.rest.model.Course;

@PreAuthorize("hasRole('ROLE_USER')")
public interface CourseRepository extends CrudRepository<Course, Long> {

	/**
	 * Functionality
	 * This means that Spring will look for an instance of class Topic with a parameter id.
	 * I DO NOT HAVE TO IMPLEMENT THIS MYSELF.
	 * This is advanced functionality of the CrudRepository.
	 * 
	 * @param topicId
	 * @return
	 */
	public List<Course> findByTopicId(long topicId);
	
	/**
	 * Functionality:
	 * This means that Spring will look for an instance of class Topic with a parameter name.
	 * Again: I do not need to implement this myself; I effectively define a query based on the naming convention.
	 * @param topicName
	 * @return
	 */
	public List<Course> findByTopicName(String topicName);
	

}
