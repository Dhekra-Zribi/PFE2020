package com.smpp.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smpp.demo.dao.RoleRepository;
import com.smpp.demo.entities.Role;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepo;
	
	public Role save(Role role) {
		return roleRepo.save(role);
	}
	
	public List<Role> getRole() {
		return roleRepo.findAll();
	}
}
