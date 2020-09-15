package com.smpp.demo.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smpp.demo.dao.ConfigSmsc;
import com.smpp.demo.entities.Ticket;
import com.smpp.demo.payload.request.SmscRequest;
import com.smpp.demo.payload.request.TicketRequest;
import com.smpp.demo.services.ConfigSmscService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class ConfigSmscController {
	
	 @Autowired
	 private ConfigSmscService smscService;
	 
	@PostMapping("/savesmsc")
	public ConfigSmsc save(@Valid @RequestBody SmscRequest smsc) {
		return smscService.save(smsc);
	}
	
	/*@GetMapping("/allsmsc")
	public List<ConfigSmsc> get() {
		return ;
	}
*/
	@GetMapping("/smsc")
	public ConfigSmsc getSmsc() {
		return smscService.getSmsc();
	}
	 
	@PostMapping("/bind")
	public ResponseEntity<?> bind(@Valid @RequestBody ConfigSmsc smsc) {
		 return smscService.bindToSmsc(smsc);
	}
}
