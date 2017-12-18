package com.rkremers.rest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rkremers.rest.model.Topic;

@Service
public class TopicService {
	
	@Autowired
	private TopicRepository topicRepository;

	public TopicService() {
		// TODO Auto-generated constructor stub
	}

	public List<Topic> getAllTopics() {
		List<Topic> topics = new ArrayList<>();
		topicRepository.findAll().forEach(topics::add);
		return topics;
	}
	
	public Topic getTopic(long id ) {
		return topicRepository.findOne(id);		
	}

	
	public void addTopic(Topic topic) {
		topicRepository.save(topic);
	}
	
	public void updateTopic(Topic topic) {
		topicRepository.save(topic);
	}
	
	public void deleteTopic(long id) {
		topicRepository.delete(id);
	}
}
