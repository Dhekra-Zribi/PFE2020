package com.smpp.demo;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.smpp.TimeoutException;
import org.smpp.WrongSessionStateException;
import org.smpp.pdu.PDUException;
import org.smpp.pdu.ValueNotSetException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.smpp.demo.dao.SmsRepository;
import com.smpp.demo.entities.Sms;
import com.smpp.demo.services.TransceiverService;
import com.smpp.demo.services.TransmitterService;
import com.smpp.demo.web.TransceiverController;
import com.smpp.demo.web.TransmitterController;

import reactor.core.publisher.Mono;



@SpringBootApplication
public class Application  {

	
	public static void main(String[] args)  {

		
		SpringApplication.run(Application.class, args);
		
	}
	
	
                

   
	
	}


