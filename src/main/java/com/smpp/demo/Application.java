package com.smpp.demo;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;





//@EnableScheduling
@SpringBootApplication
//@EnableConfigurationProperties(ApplicationProperties.class)
public class Application {

/*	@Autowired
	private ApplicationProperties properties ;
	
	private void test (ApplicationProperties properties) {
		System.out.println(properties.getSmpp().getPort());
	}*/
	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

		//SMSTransciever.configuration();
			
		
		
		System.out.println("****Menu****");
		System.out.println("**** 1 For transmitter ****");
		System.out.println("**** 2 For receiver ****");
		System.out.println("**** 3 For transciver ****");
		System.out.println("**** 4 For exit ****");
		Scanner sc = new Scanner(System.in);
		int choix = sc.nextInt();
		while (true) {

			switch (choix) {
			case 1:

				SimpleSMSTransmitter objSimpleSMSTransmitter = new SimpleSMSTransmitter();
				objSimpleSMSTransmitter.bindToSmscTransmitter();
				//objSimpleSMSTransmitter.sendSingleSMS();
				objSimpleSMSTransmitter.sendSMS();
				break;
				
			case 2:
				SimpleSMSReceiver objSimpleSMSReceiver = new SimpleSMSReceiver();
				objSimpleSMSReceiver.bindToSmscReceiver();
				while (true) {
					objSimpleSMSReceiver.receiveSms();
				}

			case 3:
				SMSTransciever objSMSTransciever = new SMSTransciever();
				objSMSTransciever.bindToSmscTransciever();
				while (true) {
					objSMSTransciever.transcieveSms();
					/*while (true){
						objSMSTransciever.recive();
						
					}*/
				}
				
			case 4:
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
		}
		
		

		/*
		 * SMSTransciever objSMSTransciever = new SMSTransciever();
		 * objSMSTransciever.bindToSmscTransciever(); 
		 * while(true) {
		 * objSMSTransciever.transcieveSms(); }
		 */

		/*
		 * SimpleSMSTransmitter objSimpleSMSTransmitter = new SimpleSMSTransmitter();
		 * objSimpleSMSTransmitter.bindToSmscTransmitter();
		 * 
		 * SimpleSMSReceiver objSimpleSMSReceiver = new SimpleSMSReceiver();
		 * objSimpleSMSReceiver.bindToSmscReceiver();
		 * 
		 * objSimpleSMSTransmitter.sendSingleSMS();
		 * 
		 * while (true) { objSimpleSMSReceiver.receiveSms();
		 * 
		 * }
		 */

	}

}
