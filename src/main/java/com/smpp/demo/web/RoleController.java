package com.smpp.demo.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smpp.demo.entities.Role;
import com.smpp.demo.services.RoleService;

@RestController
public class RoleController {

	@Autowired
	private RoleService service;
	
	@PostMapping("/addRole")
	public Role save(@Valid @RequestBody Role role) {
		return service.save(role);
	}
	
	@GetMapping("/roles")
	public List<Role> getRole() {
		return service.getRole();
	}
}
