package com.smpp.demo.web;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.xml.ws.soap.Addressing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smpp.demo.dao.RoleRepository;
import com.smpp.demo.dao.UserRepository;
import com.smpp.demo.entities.ERole;
import com.smpp.demo.entities.Role;
import com.smpp.demo.entities.User;
import com.smpp.demo.payload.request.LoginRequest;
import com.smpp.demo.payload.request.SignupRequest;
import com.smpp.demo.payload.response.JwtResponse;
import com.smpp.demo.payload.response.MessageResponse;
import com.smpp.demo.security.jwt.JwtUtils;
import com.smpp.demo.security.services.UserDetailsImpl;
import com.smpp.demo.services.RegistrationService;
import com.smpp.demo.services.SequenceGeneratorService;
//@CrossOrigin(origins = "*", maxAge = 3600)
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	private RegistrationService service;
	
	
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(),
												 userDetails.getMobile(),
												 userDetails.getPhone(),
												 roles));
	}
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUserName(signUpRequest.getUserName())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmailId(signUpRequest.getEmailId())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUserName(), 
							 signUpRequest.getEmailId(),
							 signUpRequest.getMobile(), 
							 signUpRequest.getPhone(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.User)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "Administrator":
					Role adminRole = roleRepository.findByName(ERole.Administrator)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				
				default:
					Role userRole = roleRepository.findByName(ERole.User)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		user.setId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	@Autowired
	private UserRepository repo;
	@PutMapping("/profile{id}")
    public void updateProfile(@RequestParam int id, @Valid @RequestBody User userDetails) {
        User user = repo.findById(id).get();
        user.setEmailId(userDetails.getEmailId());
        user.setUserName(userDetails.getUserName());
        user.setMobile(userDetails.getMobile());
        user.setPhone(userDetails.getPhone());
        user.setPassword( encoder.encode(userDetails.getPassword()));
        
        repo.save(user);
        
       // final User updateduser = repo.save(user);
        //return ResponseEntity.ok(updateduser);
    }
	
	@PutMapping("/update{id}")
    public void updateUser(@RequestParam int id, @Valid @RequestBody User userDetails) {
        User user = repo.findById(id).get();
        user.setEmailId(userDetails.getEmailId());
        user.setUserName(userDetails.getUserName());
        user.setMobile(userDetails.getMobile());
        user.setPhone(userDetails.getPhone());
        user.setRoles(userDetails.getRoles());
        
        repo.save(user);
        
       // final User updateduser = repo.save(user);
        //return ResponseEntity.ok(updateduser);
    }
	
	@GetMapping("/user{id}")
	public Optional<User> getUser(@RequestParam int id){
		return service.getUser(id);
	}
	
	
}


