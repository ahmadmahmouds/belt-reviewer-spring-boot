package com.beltreviewer.demo.repositories;

import com.beltreviewer.demo.models.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<Event,Long> {
    List<Event> findByState(String state);
    List<Event> findByStateIsNot(String state);
}
