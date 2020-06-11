package com.smpp.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smpp.demo.entities.Sms;
import com.smpp.demo.services.TransceiverService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class TransceiverController {

	@Autowired
	TransceiverService service;
	@RequestMapping("/sms/create")
	public String create(@RequestParam String shortMessage, @RequestParam String sourceAddr, @RequestParam String destAddr) {
		Sms sms = service.create(shortMessage, sourceAddr, destAddr);
		return sms.toString();
	}
	
	@RequestMapping("/sms")
	public List<Sms> getAll(){
		return service.getAll();
	}
	
	@RequestMapping("/sms/delete")
	public String delete(@RequestParam String id) {
		service.delete(id);
		return "Deleted "+id;
	}
	
	@RequestMapping ("/sms/deleteAll")
	public String deleteAll() {
		service.deleteAll();
		return "Deleted all records";
	}
}