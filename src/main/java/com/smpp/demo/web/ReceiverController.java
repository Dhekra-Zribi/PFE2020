package com.smpp.demo.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.smpp.demo.dao.SmsRepository;
import com.smpp.demo.entities.CountMsgDate;
import com.smpp.demo.entities.CountTr;
import com.smpp.demo.entities.Sms;
import com.smpp.demo.services.ReceiverService;
import com.smpp.demo.services.SequenceGeneratorService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class ReceiverController {

	@Autowired
	ReceiverService service;

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	@Autowired
	private SmsRepository smsRepository;
	
	//@GetMapping("inject_mo{short_message}{source_addr}{destination_addr}{submit}{data_coding}")
	//public void recu(@Valid @RequestBody Sms sms) {
	//public void recu(@RequestParam String short_message,@RequestParam String source_addr,@RequestParam String destination_addr,@RequestParam String submit, @RequestParam String data_coding) {
	
	/*@PostConstruct
	public void recu() {
		service.recu();
	}*/
	
	@GetMapping("/smsrecu")
	public List<Sms> getAll(){
		return service.getAllTr();
	}
	
	@DeleteMapping("/smsrecu/deleteAll")
	public String deleteSmsRecu() {
		return service.deleteAll();
	}
	
	@DeleteMapping("/smsrecu/smsdelete{id}")
	public String delete(@RequestParam long id) {
		service.delete(id);
		return "Deleted "+id;
	}
	
	@GetMapping("/nb2")
	public List<CountTr> getNb(){
		return service.getNb();
	}
}
