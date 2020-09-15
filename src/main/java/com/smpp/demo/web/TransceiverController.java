package com.smpp.demo.web;

import java.util.HashMap;
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

import com.smpp.demo.entities.CountMsgDate;
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
		sms.setId(sequenceGeneratorService.generateSequence(CountMsgDate.SEQUENCE_NAME));
		Sms s = new Sms();
		s = service.create(sms);
		//nb msg per day
		service.deleteAllNb();
		service.countByDate();
		 return s;
	}
	
	@GetMapping("/Allsms")
	public List<Sms> getAll(){
		return service.getAll();
	}
	
	@GetMapping("/sms")
	public List<Sms> getAllTrx(){
		return service.getAllTrx();
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
	
	@GetMapping("/count")
	//public long count(@RequestParam String dateTime) {
	public long count() {
		return service.count();
	}
	
	@GetMapping("/count2")
	//public long count(@RequestParam String dateTime) {
	public long count2() {
		return service.count2();
	}
	
	@GetMapping("/count3")
	//public long count(@RequestParam String dateTime) {
	public long count3() {
		return service.count3();
	}
	
	
	@GetMapping("/nb")
	public List<CountMsgDate> getNb(){
		return service.getNb();
	}
	
	
	
}