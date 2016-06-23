package com.dvoss.services;

import com.dvoss.entities.Meet;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dan on 6/23/16.
 */
public interface MeetRepository extends CrudRepository<Meet, Integer> {
}
