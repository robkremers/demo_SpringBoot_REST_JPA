package com.rkremers.rest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rkremers.rest.model.Course;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository courseRepository;

	public CourseService() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Functionality:
	 * Courses have a many-to-one relation with topics.
	 * Now courses will be found by the topic id.
	 * 
	 * @param topicId
	 * @return
	 */
	public List<Course> getAllCourses(long topicId) {
		List<Course> courses = new ArrayList<>();
		
		courseRepository.findByTopicId(topicId).forEach(courses::add);
		return courses;
	}
	
	public Course getCourse(long id ) {
		return courseRepository.findOne(id);		
	}

	
	public void addCourse(Course course) {
		courseRepository.save(course);
	}
	
	public void updateCourse(Course course) {
		courseRepository.save(course);
		System.out.println("Updated course.id = " + course.getId() + " with description = " + course.getDescription() );
	}
	
	public void deleteCourse(long id) {
		courseRepository.delete(id);
	}

}
