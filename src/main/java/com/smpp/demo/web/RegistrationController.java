package com.smpp.demo.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smpp.demo.dao.UserRepository;
import com.smpp.demo.entities.Sms;
import com.smpp.demo.entities.User;
import com.smpp.demo.services.RegistrationService;
import com.smpp.demo.services.SequenceGeneratorService;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
@RestController
public class RegistrationController {

	@Autowired
	private RegistrationService service;
	@Autowired
	private UserRepository repo;
	
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
	
	@GetMapping("/profil")
	private User profilUser(@Valid @RequestParam String emailId) {
		return service.fetchUserByEmailId(emailId);
	}
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return service.getAllUsers();
	}
	
	
	@DeleteMapping("/userdelete{id}")
	public String delete(@RequestParam int id) {
		service.delete(id);
		return "Deleted "+id;
	}
	
	
	@PutMapping("/users{id}")
    public ResponseEntity<User> updateUser(@RequestParam int id,
         @Valid @RequestBody User userDetails) {
        User user = repo.findById(id).get();

        user.setEmailId(userDetails.getEmailId());
        user.setUserName(userDetails.getUserName());
        user.setMobile(userDetails.getMobile());
        user.setPhone(userDetails.getPhone());
        user.setPassword(userDetails.getPassword());
        user.setRoles(userDetails.getRoles());
        final User updateduser = repo.save(user);
        return ResponseEntity.ok(updateduser);
    }
	
	/*@PutMapping("/profile{id}")
    public ResponseEntity<User> updateProfile(@RequestParam int id,
         @Valid @RequestBody User userDetails) {
        User user = repo.findById(id).get();
        user.setEmailId(userDetails.getEmailId());
        user.setUserName(userDetails.getUserName());
        user.setMobile(userDetails.getMobile());
        user.setPhone(userDetails.getPhone());
        user.setPassword(userDetails.getPassword());
        final User updateduser = repo.save(user);
        return ResponseEntity.ok(updateduser);
    }*/
}
