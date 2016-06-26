package com.dvoss.services;

import com.dvoss.entities.Meet;
import com.dvoss.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Dan on 6/23/16.
 */
public interface MeetRepository extends CrudRepository<Meet, Integer> {
    public Iterable<Meet> findByUser(User user);
    List<Meet> findByOrderByIdDesc();
}
