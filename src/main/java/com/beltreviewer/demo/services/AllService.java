package com.beltreviewer.demo.services;

import com.beltreviewer.demo.models.Event;
import com.beltreviewer.demo.models.User;
import com.beltreviewer.demo.models.UserEvent;
import com.beltreviewer.demo.repositories.EventRepository;
import com.beltreviewer.demo.repositories.UserEventRepository;
import com.beltreviewer.demo.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllService {
    @Autowired
    private final UserRepository uRepo;

    @Autowired
    private final  EventRepository eRepo;
    @Autowired
    private final UserEventRepository ueRepo;

    public AllService(UserRepository uRepo, EventRepository eRepo, UserEventRepository ueRepo) {
        this.uRepo = uRepo;
        this.eRepo = eRepo;
        this.ueRepo = ueRepo;
    }


    public User findUserById(Long id) {

        return this.uRepo.findById(id).orElse(null);
    }
    public User registerUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        return this.uRepo.save(user);
    }
    public User getUserByEmail(String email) {
        return this.uRepo.findByEmail(email);
    }
    public boolean authenticateUser(String email, String password) {
        User user = this.uRepo.findByEmail(email);
        if(user == null)
            return false;

        return BCrypt.checkpw(password, user.getPassword());
    }




    public List<Event> allEventsWithState(String state) {
        return this.eRepo.findByState(state);
    }

    public List<Event> allEventsNotState(String state) {
        return this.eRepo.findByStateIsNot(state);
    }
    public Event findEventById(Long id) {
        return this.eRepo.findById(id).orElse(null);
    }
    public Event create(Event event) {
        return this.eRepo.save(event);
    }


    public Event update(Event event) {
        return this.eRepo.save(event);
    }


//    public void comment(User user, Event event, String comment) {
//        this.mRepo.save(new Message(user, event, comment));
//    }
    public void delete(Long id) {
        this.eRepo.deleteById(id);
    }



    public UserEvent createAssociation(UserEvent userEvent)
    {
      return   this.ueRepo.save(userEvent);
    }


    public void manageAttendees(Event event, User user, boolean isJoining) {
        if(isJoining) {
            event.getAttendees().add(user);
        } else {
            event.getAttendees().remove(user);
        }
        this.eRepo.save(event);
    }

}
