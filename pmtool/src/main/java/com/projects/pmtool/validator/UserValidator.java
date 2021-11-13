package com.projects.pmtool.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.projects.pmtool.domain.User;

@Component
public class UserValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		User user = (User) object;
		if(user.getPassword().length()<6) {
			errors.rejectValue("password", "length","password must be at least 6 characters");
		}
		
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "length","passwords must macth");
		}
	}
}
