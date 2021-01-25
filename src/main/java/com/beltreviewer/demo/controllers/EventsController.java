package com.beltreviewer.demo.controllers;

import com.beltreviewer.demo.models.Event;
import com.beltreviewer.demo.models.State;
import com.beltreviewer.demo.models.User;
import com.beltreviewer.demo.services.AllService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class EventsController {
    private final AllService allService;

    public EventsController(AllService allService) {
        this.allService = allService;
    }

    private String now() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    @RequestMapping("/events")
    public String events(HttpSession session, Model model, @ModelAttribute("event")Event event) {
        if (session.getAttribute("userId") != null){
            Long id=(Long)session.getAttribute("userId");
            User user=allService.findUserById(id);
            List<Event> userStateEvents=allService.allEventsWithState(user.getState());
            List<Event> notUserStateEvents=allService.allEventsNotState(user.getState());
            model.addAttribute("userState",userStateEvents );
            model.addAttribute("notUserState", notUserStateEvents);
            model.addAttribute("user", user);
            model.addAttribute("now", now());
            model.addAttribute("states", State.States);
            System.out.println("before index******************");
            return "index.jsp";
        }

        else {
            return "redirect:/";
        }
    }



    @RequestMapping(value = "/events",method = RequestMethod.POST)
    public String createEvent(@ModelAttribute("event")Event event){

    }


}
