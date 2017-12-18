package com.rkremers.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rkremers.rest.model.Course;
import com.rkremers.rest.model.Topic;
import com.rkremers.rest.service.CourseService;
import com.rkremers.rest.service.TopicService;

@RestController
public class CourseController {

	public CourseController() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private CourseService courseService;
	
	@Autowired
	private TopicService topicService;
	
	
	@RequestMapping(method=RequestMethod.GET, value="/topics/{topicId}/courses")
	public List<Course> getAllCourses(@PathVariable long topicId) {
		return courseService.getAllCourses(topicId);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/topics/{topicId}/courses/{courseId}")
	public Course getCourse(@PathVariable long topicId, @PathVariable long courseId) {
		return courseService.getCourse(courseId);
	}
	
	/**
	 * Function: Add a course with a specific topic, identified by the topic id.
	 * 
	 * Note:
	 * The topic should actually already exist. In this setup no actual exception exists.
	 * @param course
	 * @param topicName
	 */
	@RequestMapping(method=RequestMethod.POST, value="/topics/{topicId}/courses")
	public void addCourse( @Valid @RequestBody Course course, @PathVariable long topicId) {
		Topic courseTopic = topicService.getTopic(topicId);
		course.setTopic( courseTopic );

		courseService.addCourse(course);
	}
	
	/**
	 * Note:
	 * The body can probably be improved but for the moment it works.
	 * @param course
	 * @param topicId
	 * @param courseId
	 */
	@RequestMapping(method=RequestMethod.PUT, value="/topics/{topicId}/courses/{courseId}")
	public void updateCourse(@Valid @RequestBody Course course, @PathVariable long topicId, @PathVariable long courseId) {
		Topic courseTopic = topicService.getTopic(topicId);
	    Course updateCourse = courseService.getCourse(courseId);
	    updateCourse.setTopic( courseTopic );
	    updateCourse.setName(course.getName());
	    updateCourse.setDescription(course.getDescription());
		courseService.updateCourse( updateCourse);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/topics/{topicId}/courses/{courseId}")
	public void deleteCourse(@PathVariable long courseId) {
		courseService.deleteCourse(courseId);
	}
}
