package com.smpp.demo;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.smpp.TimeoutException;
import org.smpp.WrongSessionStateException;
import org.smpp.pdu.PDUException;
import org.smpp.pdu.ValueNotSetException;
import org.springframework.beans.factory.annotation.Autowired;
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



@SpringBootApplication()

@EnableReactiveMongoRepositories(basePackages = "com.smpp.demo.dao")

public class Application  {

	
	public static void main(String[] args)  {

		
		SpringApplication.run(Application.class, args);
		
		
					/*	
						TransmitterService transmitter1=new TransmitterService();
						transmitter1.bindToSmscTransmitter();
						
						TransmitterController transmitter=new TransmitterController();
						transmitter.createMessage("hello","123","456");
						
		*/
		
		
		
		
		
	}
	
	/*public void init() {
		TransceiverService  bind=new TransceiverService();
		bind.bindToSmscTransciever();
		
		TransceiverController transceiver=new TransceiverController();
		transceiver.sendMessage("hello", "123","456");
	}*/
	
	
	
		
	/*@PostConstruct
    public void  init()
    		{
      
            Sms sms=new Sms();
            sms.setShortMessage("message");
            sms.setDestAddr("123");
          Mono<Sms> sms1=smsRepository.save(sms);
           System.out.println(sms1);
           
            
                
	}*/
                

        
		
	/*System.out.println("****Menu****");
		System.out.println("**** 1 For transmitter ****");
		System.out.println("**** 2 For receiver ****");
		System.out.println("**** 3 For transciver ****");
		System.out.println("**** 4 For exit ****");
		Scanner sc = new Scanner(System.in);
		int choix = sc.nextInt();
		while (true) {

			switch (choix) {
			case 1:

				TransmitterService objSimpleSMSTransmitter = new TransmitterService();
				objSimpleSMSTransmitter.bindToSmscTransmitter();
				//objSimpleSMSTransmitter.sendSingleSMS();
				objSimpleSMSTransmitter.sendSMS();
				break;
				
			case 2:
				ReceiverService objSimpleSMSReceiver = new ReceiverService();
				objSimpleSMSReceiver.bindToSmscReceiver();
				while (true) {
					objSimpleSMSReceiver.receiveSms();
				}

			case 3:
				TranscieverService objSMSTransciever = new TranscieverService();
				objSMSTransciever.bindToSmscTransciever();
				while (true) {
					objSMSTransciever.transcieveSms();
					//desactiver
					/*while (true){
						objSMSTransciever.recive();
						
					}*/
				//}
				
		/*	case 4:
				System.out.println("Thank you :)");
				System.exit(0);
				break;

			default:
				System.out.println("**** 1 For transmitter ****");
				System.out.println("**** 2 For receiver ****");
				System.out.println("**** 3 For transciver ****");
				System.out.println("**** 4 For exit ****");
				choix = sc.nextInt();
			
		}
        
	
}*/
		
		


	
	}


