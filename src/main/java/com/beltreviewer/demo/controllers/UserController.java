package com.beltreviewer.demo.controllers;
import com.beltreviewer.demo.models.State;
import com.beltreviewer.demo.models.User;
import com.beltreviewer.demo.services.AllService;
import com.beltreviewer.demo.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private AllService uService;
    @Autowired
    private UserValidator validator;

    @RequestMapping("/")
    public String Index(@ModelAttribute("user") User user,Model model) {
        model.addAttribute("states", State.States);
        return "registrationloginpage.jsp";
    }
    @RequestMapping(value = "/",method = RequestMethod.POST)
    public String Register(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session,Model model){
        validator.validate(user, result);
        if(result.hasErrors()){
            model.addAttribute("states", State.States);
            return "registrationloginpage.jsp";
        }else {

        User newUser = this.uService.registerUser(user);
        session.setAttribute("userId", newUser.getId());
        return "redirect:/events";
        }
    }

    @RequestMapping("/login")
    public String Login(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, RedirectAttributes redirs) {
        if(this.uService.authenticateUser(email, password)) {
            User user = this.uService.getUserByEmail(email);
            session.setAttribute("userId", user.getId());
            return "redirect:/events";
        }
        redirs.addFlashAttribute("error", "Invalid Email/Password");
        return "redirect:/";
    }

    @RequestMapping("/logout")
    public  String logut(HttpSession session)
    {
        session.invalidate();
        return "redirect:/events";
    }


}
