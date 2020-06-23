package com.smpp.demo.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smpp.demo.entities.Sms;
import com.smpp.demo.entities.User;
import com.smpp.demo.services.SequenceGeneratorService;
import com.smpp.demo.services.TransceiverService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TransceiverController {

	@Autowired
	TransceiverService service;

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@PostMapping("/sms/create")
	//public String create(@RequestParam String shortMessage, @RequestParam String sourceAddr, @RequestParam String destAddr) {
	public Sms create(@Valid @RequestBody Sms sms) {
		//Sms sms = service.create(shortMessage, sourceAddr, destAddr);
		sms.setId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
		return service.create(sms);
	}
	
	@GetMapping("/sms")
	public List<Sms> getAll(){
		return service.getAll();
	}
	
	@DeleteMapping("/smsdelete{id}")
	public String delete(@RequestParam long id) {
		service.delete(id);
		return "Deleted "+id;
	}
	
	@DeleteMapping ("/sms/deleteAll")
	public String deleteAll() {
		service.deleteAll();
		return "Deleted all records";
	}
	
}