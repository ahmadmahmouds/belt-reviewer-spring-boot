package com.beltreviewer.demo.validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import com.beltreviewer.demo.models.User;
import com.beltreviewer.demo.repositories.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class UserValidator implements Validator {
    @Autowired
    private  UserRepository uRepo;
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if(this.uRepo.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "Unique");
        }

        if (!user.getPasswordConfirmation().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirmation", "Match");
        }
    }
}
