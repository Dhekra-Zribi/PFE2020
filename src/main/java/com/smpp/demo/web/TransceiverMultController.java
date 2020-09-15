package com.smpp.demo.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smpp.demo.entities.Csv;
import com.smpp.demo.entities.Sms;
import com.smpp.demo.entities.User;
import com.smpp.demo.services.CsvReaderService;
import com.smpp.demo.services.SequenceGeneratorService;
import com.smpp.demo.services.TransceiverMutipleService;
import com.smpp.demo.services.TransceiverService;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class TransceiverMultController {

	
	
	
	@Autowired
	TransceiverMutipleService service;
	CsvReaderService csvservice;

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	@Autowired
	TransceiverService service2;
	
	 
	
	@RequestMapping(value="/sms/createMult",method=RequestMethod.POST,consumes = "multipart/form-data")
	
	public void createMult(@Valid  @RequestParam("file") MultipartFile file ,@RequestParam("sms")String sms) throws JsonMappingException, IOException  {
	//	ObjectMapper objectMapper=new ObjectMapper();
		
    
  Sms sms1=new ObjectMapper().readValue(sms,Sms.class);
        
		 service.createMult(file,sms1);
		 service2.deleteAllNb();
		service2.countByDate();
		// csvservice.save(file);
	
	}
}

