package com.rkremers.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rkremers.rest.model.Topic;
import com.rkremers.rest.service.TopicService;

/**
 * Note that the use of @RestController assumes @ResponseBody semantics by default. 
 * So no need to worry (e.g. in case of GET).
 * 
 */
@RestController	
@RequestMapping("/topics")
public class TopicController {

	public TopicController() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private TopicService topicService;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Topic> getAllTopics() {
		return topicService.getAllTopics();
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{id}")
	public Topic getTopic(@PathVariable long id) {
		return topicService.getTopic(id);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public void addTopic(@Valid @RequestBody Topic topic) {
		topicService.addTopic(topic);
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	public void updateTopic(@Valid @RequestBody Topic topic) {
		topicService.updateTopic( topic);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public void deleteTopic(@PathVariable long id) {
		topicService.deleteTopic(id);
	}
}
