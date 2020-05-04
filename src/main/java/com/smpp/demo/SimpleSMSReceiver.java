package com.smpp.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smpp.Data;
import org.smpp.Session;
import org.smpp.TCPIPConnection;
import org.smpp.pdu.BindReceiver;
import org.smpp.pdu.BindRequest;
import org.smpp.pdu.BindResponse;
import org.smpp.pdu.DeliverSM;
import org.smpp.pdu.PDU;
import org.springframework.stereotype.Component;


@Component
public class SimpleSMSReceiver{
	
	/*
	 * Parameters used for connecting to SMSC (or SMPPSim)
	 */
	private Session session = null;
	private String ipAddress = "localhost";
	private String systemId = "smppclient1";
	private String password = "password";
	private int port = 2775;
	private static final Logger log = LoggerFactory.getLogger(SimpleSMSReceiver.class);
	
	
	public void bindToSmscReceiver() {
		try {
			// setup connection
			TCPIPConnection connection = new TCPIPConnection(ipAddress, port);
			connection.setReceiveTimeout(20 * 1000);
			session = new Session(connection);

			// set request parameters
			BindRequest request = new BindReceiver();
			request.setSystemId(systemId);
			request.setPassword(password);

			// send request to bind
			BindResponse response = session.bind(request);
			if (response.getCommandStatus() == Data.ESME_ROK) { //ESME_ROK = new error
				System.out.println("Sms receiver is connected to SMPPSim.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void receiveSms() {
		try {

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
							sms.getShortMessage().trim() ,
							sms.getSourceAddr().getAddress(),sms.getDestAddr().getAddress() );
				
				}
				else if ((int)sms.getDataCoding() == 8 ) {
					//message content is Non-English (Arabe, chinoi..)
					log.info("\n ***** New Message Received ***** \n Content: {} \n From: {} \n To: {}",
							sms.getShortMessage(Data.ENC_UTF16).trim() ,
							sms.getSourceAddr().getAddress(),sms.getDestAddr().getAddress() );
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
