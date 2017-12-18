package com.rkremers.rest.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import com.rkremers.rest.model.Topic;

/**
 * The entity Topic will be handled, it's id is String Id, hence: T = Topic, ID = String
 * @author LTAdmin
 *
 */
@PreAuthorize("hasRole('ROLE_USER')")
public interface TopicRepository extends CrudRepository<Topic, Long> {

}
