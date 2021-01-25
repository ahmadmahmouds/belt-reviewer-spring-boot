package com.beltreviewer.demo.repositories;

import com.beltreviewer.demo.models.UserEvent;
import org.springframework.data.repository.CrudRepository;

public interface UserEventRepository extends CrudRepository<UserEvent,Long> {

}
