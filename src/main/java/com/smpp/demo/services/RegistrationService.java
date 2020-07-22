package com.smpp.demo.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.smpp.demo.dao.RoleRepository;
import com.smpp.demo.dao.UserRepository;
import com.smpp.demo.entities.Role;
import com.smpp.demo.entities.Sms;
import com.smpp.demo.entities.User;

@Service
public class RegistrationService {

	@Autowired
	private UserRepository repo;
	@Autowired
	private RoleRepository roleRepo;
	
	public User saveUser(User user) {
		//Role role = new Role();
		//role.setUsers(Arrays.asList(new User[] { user }));
		//roleRepo.save(role);
		return repo.save(user);
	}
	
	public User fetchUserByEmailId(String email) {
		return repo.findByEmailId(email);
	}
	public User fetchUserByEmailIdAndPassword(String email, String password) {
		return repo.findByEmailIdAndPassword(email, password);
	}
	
	public List<User> getAllUsers(){
		return repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
	}
	public void delete(int id) {
		repo.deleteById(id);
	}
	
	public Optional<User> getUser(int id) {
		return repo.findById(id);
	}
}
