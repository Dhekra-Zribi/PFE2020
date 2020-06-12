package com.smpp.demo.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smpp.demo.entities.User;
import com.smpp.demo.services.RegistrationService;
import com.smpp.demo.services.SequenceGeneratorService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class RegistrationController {

	@Autowired
	private RegistrationService service;
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@PostMapping("/registerUser")
	public User registerUser(@Valid @RequestBody User user) throws Exception {

		user.setId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
		
		String tempEmailId = user.getEmailId();
		if (tempEmailId != null && !"".equals(tempEmailId)) {
			User userobj = service.fetchUserByEmailId(tempEmailId);
			if (userobj != null) {
				throw new Exception("User with "+ tempEmailId+" is already exist!")	;	
				}
		}
		User userObj = null;
		userObj = service.saveUser(user);
		return userObj;
	}
	
	@PostMapping("/login")
	private User loginUser(@RequestBody User user) throws Exception {
		String tempEmailId = user.getEmailId();
		String tempPass = user.getPassword();
		User userObj = null;
		if(tempEmailId != null && tempPass != null) {
			userObj = service.fetchUserByEmailIdAndPassword(tempEmailId, tempPass);
		}
		if (userObj == null) {
			throw new Exception("Bad credentials");
		}
		return userObj;
	}
}