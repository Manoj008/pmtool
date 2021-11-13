package com.projects.pmtool.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projects.pmtool.domain.User;
import com.projects.pmtool.payload.JWTLoginSuccessResponse;
import com.projects.pmtool.payload.LoginRequest;
import com.projects.pmtool.security.JWTTokenProvider;
import static com.projects.pmtool.security.SecurityConstants.*;
import com.projects.pmtool.services.MapValidationErrorService;
import com.projects.pmtool.services.UserService;
import com.projects.pmtool.validator.UserValidator;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private MapValidationErrorService errorService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private JWTTokenProvider jwtTokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
		ResponseEntity<?> errorMap = errorService.mapValidationService(result);
		if(errorMap != null) {
			return errorMap;
		}
		
		Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
		);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = TOKEN_PREFIX+jwtTokenProvider.generateToken(authentication);
		
		return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> resgisterUser(@Valid @RequestBody User user,BindingResult result){
		
		userValidator.validate(user, result);
		
		ResponseEntity<?> errorMap = errorService.mapValidationService(result);
		if(errorMap != null) return errorMap;
		
		User newUser = userService.saveUser(user);
		return new ResponseEntity<User>(newUser,HttpStatus.CREATED);
	}

}
