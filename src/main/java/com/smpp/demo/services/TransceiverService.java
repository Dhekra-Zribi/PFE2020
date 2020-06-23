package com.smpp.demo.services;
import java.util.List;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
import org.smpp.pdu.Outbind;
import org.smpp.pdu.PDU;
import org.smpp.pdu.ShortMessage;
import org.smpp.pdu.SubmitSM;
import org.smpp.pdu.SubmitSMResp;
import org.smpp.util.ByteBuffer;
import org.smpp.util.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.smpp.demo.dao.SmsRepository;
import com.smpp.demo.entities.Sms;

import lombok.extern.java.Log;


//@ConfigurationProperties(prefix = "sms")
@Service
public class TransceiverService {
	@Autowired
	private SmsRepository smsRepository;
	private Session session = null;
//	@Value("${sms.smpp.host}")
	private String ipAddress = "127.0.0.1";

	private String systemId = "smppclient1";
	private String password = "password";
	private int port = 2775;
	/*private String shortMessage=null;
	private String sourceAddress=null;
	private String destinationAddress=null;*/
	private int i =0;
	private static final Logger log = LoggerFactory.getLogger(TransceiverService.class);
	

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
	
public Sms transcieveSms(Sms sms) {
		
		try {
			
			
			Address srcAddr = new Address();
			Address destAddr = new Address();

			SubmitSM smRequest = new SubmitSM();
			SubmitSMResp resp = null;

		/*	Scanner sc = new Scanner(System.in);
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
			}*/
			
			srcAddr.setTon((byte) 1);
			srcAddr.setNpi((byte) 1);
			srcAddr.setAddress(sms.getSourceAddr());

			destAddr.setTon((byte) 1);
			destAddr.setNpi((byte) 1);
			destAddr.setAddress(sms.getDestAddr());
			smRequest.setDataCoding((byte) 8);
			String msg = sms.getShortMessage();
			int nb = 0;
			if (msg.length() <= 160) {

				smRequest.setSourceAddr(srcAddr);
				smRequest.setDestAddr(destAddr);
				smRequest.setShortMessage(msg, "UTF-16");
				resp = session.submit(smRequest);
				nb=1;
				if (resp.getCommandStatus() == Data.ESME_ROK) {
					System.out.println("Message submitted....");
				}
			} else {
				// SMS length > 160 Char

				smRequest.setEsmClass((byte) Data.SM_UDH_GSM); // Set UDHI Flag Data.SM_UDH_GSM=0x40

				String[] splittedMsg = this.SplitByWidth(msg, 153);

				int totalSegments = splittedMsg.length;

				for (int i = 0; i < totalSegments; i++) {

					ByteBuffer ed = new ByteBuffer();
					ed.appendString(splittedMsg[i]);// , Data.ENC_ASCII

					smRequest.setShortMessageData(ed);

					smRequest.setSourceAddr(srcAddr);
					smRequest.setDestAddr(destAddr);

					resp = session.submit(smRequest);
					nb++;
				}

				if (resp.getCommandStatus() == Data.ESME_ROK) {
					System.out.println("Long Message submitted....");
				}
			}
			
			while (nb!=0) {
				this.recive();
				nb--;
				
			}
			
			LocalDateTime datetime = LocalDateTime.now();  
		    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");   
		    sms.setDateTime(datetime.format(format));
			sms = smsRepository.save(sms);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sms;
	}
	
	public void recive() {
		try {
			
		PDU pdu = session.receive(1500);

		if (pdu != null) {
			DeliverSM sms = (DeliverSM) pdu;
			
			if ((int)sms.getDataCoding() == 0 ) {
				
				log.info("\n ***** New Message Received ***** \n Content: {} \n From: {} \n To: {}",
						sms.getShortMessage().trim() ,sms.getSourceAddr().getAddress(),sms.getDestAddr().getAddress() );
			}
			else if ((int)sms.getDataCoding() == 8 ) {
				
				log.info("\n ***** New Message Received ***** \n Content: {} \n From: {} \n To: {} ",
						sms.getShortMessage(Data.ENC_UTF16).trim() ,sms.getSourceAddr().getAddress(),sms.getDestAddr().getAddress());
			}
		}
	}
		 catch (Exception e) {
			 e.printStackTrace();
			}
	}
	
	
	public String[] SplitByWidth(String s, int width) throws Exception {
		try {

			if (width == 0) {
				String[] ret = new String[1];
				ret[0] = s;
				return ret;
			} else {

				if (s.isEmpty())
					return new String[0];
				else {

					if (s.length() <= width) {
						String[] ret = new String[1];
						ret[0] = s;
						return ret;
					} else {
						int NumSeg = s.length() / width + 1;
						String[] ret = new String[NumSeg];
						int startPos = 0;

						for (int i = 0; i < (NumSeg - 1); i++) {
							ret[i] = s.substring(startPos, ((width * (i + 1))));
							startPos = (i + 1) * width;
							Log(ret[i]);

						}
						ret[NumSeg - 1] = s.substring(startPos, s.length());
						return ret;
					}
				}
			}

		} catch (Exception e) {
			Log(String.valueOf(e.fillInStackTrace()));
			return new String[0];
		}
	}
	private void Log(String valueOf) {
		// TODO Auto-generated method stub

	}

	
	//public Sms create(String shortMessage,String sourceAddr, String destAddr) {
	public Sms create(Sms sms) {
		this.bindToSmscTransciever();
		return transcieveSms(sms);
	}

	public List<Sms> getAll(){
		return smsRepository.findAll(Sort.by(Sort.Direction.DESC, "dateTime"));
	}
	
	public void deleteAll() {
		smsRepository.deleteAll();
	}
	
	public void delete(long id) {
		smsRepository.deleteById(id);
	}
	
}