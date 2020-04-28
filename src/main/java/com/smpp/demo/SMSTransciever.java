package com.smpp.demo;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smpp.Data;
import org.smpp.Session;
import org.smpp.TCPIPConnection;
import org.smpp.pdu.Address;
import org.smpp.pdu.BindRequest;
import org.smpp.pdu.BindResponse;
import org.smpp.pdu.BindTransciever;
import org.smpp.pdu.DeliverSM;
import org.smpp.pdu.PDU;
import org.smpp.pdu.SubmitSM;
import org.smpp.pdu.SubmitSMResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@ConfigurationProperties("sms")
public class SMSTransciever {

	private Session session = null;
//	@Value("${sms.host}")
	private String ipAddress = "127.0.0.1";

	private String systemId = "smppclient1";
	private String password = "password";
	private int port = 2775;
	private String shortMessage;
	private String sourceAddress;
	private String destinationAddress;
	private int i =0;
	private static final Logger log = LoggerFactory.getLogger(SMSTransciever.class);
	

	public void bindToSmscTransciever() {
		try {
			// setup connection
			TCPIPConnection connection = new TCPIPConnection(ipAddress, port);
			connection.setReceiveTimeout(20 * 1000);
			session = new Session(connection);

			// set request parameters
			BindRequest request = new BindTransciever();
			request.setSystemId(systemId);
			request.setPassword(password);

			// send request to bind
			BindResponse response = session.bind(request);
			if (response.getCommandStatus() == Data.ESME_ROK) { //ESME_ROK = new error
				System.out.println("Sms Transciever is connected to SMPPSim.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void transcieveSms() {
		
		try {
			
			SubmitSM request = new SubmitSM();

			Address srcAddr = new Address();
			Address destAddr = new Address();
			
			Scanner sc = new Scanner(System.in);
			if (i==0) {
				System.out.println("Write a message");
				shortMessage = sc.nextLine();
				System.out.println("Write the source adress");
				sourceAddress = sc.nextLine();
				
				System.out.println("Write the destination adress");
				destinationAddress = sc.nextLine();
				i++;
			}else {
				System.out.println("Replay");
				shortMessage = sc.nextLine();
				String temp;
				temp = destinationAddress;
				destinationAddress = sourceAddress;
				sourceAddress = temp;
			}
			
			// set values
			srcAddr.setTon((byte) 1);
			srcAddr.setNpi((byte) 1);
			srcAddr.setAddress(sourceAddress);

			destAddr.setTon((byte) 1);
			destAddr.setNpi((byte) 1);
			destAddr.setAddress(destinationAddress);

			request.setSourceAddr(srcAddr);
			request.setDestAddr(destAddr);
			request.setDataCoding((byte) 8);
			
			request.setShortMessage(shortMessage, "UTF-16");

			// send the request
			SubmitSMResp resp = session.submit(request);

			if (resp.getCommandStatus() == Data.ESME_ROK) {
				System.out.println("Message submitted....");
			}
			
			PDU pdu = session.receive(1500);

			if (pdu != null) {
				DeliverSM sms = (DeliverSM) pdu;
				
				if ((int)sms.getDataCoding() == 0 ) {
					//message content is English Or Frensh (ASCII)
					/*System.out.println("***** New Message Received *****");
					System.out.println("From: " + sms.getSourceAddr().getAddress());
					System.out.println("To: " + sms.getDestAddr().getAddress());
					System.out.println("Content: " + sms.getShortMessage());*/
					
					log.info("\n ***** New Message Received ***** \n Content: {} \n From: {} \n To: {}", 
							sms.getShortMessage().trim(), sms.getSourceAddr().getAddress(), sms.getDestAddr().getAddress() );
				}
				else if ((int)sms.getDataCoding() == 8 ) {
					//message content is Non-English (Arabe, chinoi..)
					/*System.out.println("***** New Message Received *****");
					System.out.println("From: " + sms.getSourceAddr().getAddress());
					System.out.println("To: " + sms.getDestAddr().getAddress());
					System.out.println("Content: " + sms.getShortMessage(Data.ENC_UTF16));*/
					
					log.info("\n ***** New Message Received ***** \n Content: {} \n From: {} \n To: {}",
							sms.getShortMessage(Data.ENC_UTF16).trim() ,sms.getSourceAddr().getAddress(),sms.getDestAddr().getAddress() );
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}