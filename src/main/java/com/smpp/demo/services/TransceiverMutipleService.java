package com.smpp.demo.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
import org.smpp.pdu.DestinationAddress;
import org.smpp.pdu.PDU;
import org.smpp.pdu.SubmitMultiSM;
import org.smpp.pdu.SubmitMultiSMResp;
import org.smpp.pdu.SubmitSM;
import org.smpp.pdu.SubmitSMResp;
import org.smpp.util.ByteBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smpp.demo.dao.CsvRepository;
import com.smpp.demo.dao.SmsRepository;
import com.smpp.demo.entities.Csv;
import com.smpp.demo.entities.Sms;
import com.smpp.demo.entities.User;

@Service
public class TransceiverMutipleService {
	@Autowired
	CsvReaderService service;
	@Autowired
	CsvRepository repository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@Autowired
	private SmsRepository smsRepository;
	private Session session = null;

	private String ipAddress = "127.0.0.1";

	private String systemId = "smppclient1";
	private String password = "password";
	private int port = 2775;

	private int i = 0;
	private static final Logger log = LoggerFactory.getLogger(TransceiverMutipleService.class);

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
			if (response.getCommandStatus() == Data.ESME_ROK) { // ESME_ROK = new error
				System.out.println("Sms Transciever is connected to SMPPSim.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void transcieveMultSms(MultipartFile file,Sms sms) {

		
		try {

			Address srcAddr = new Address();

//			SubmitMultiSM message to the Message Center for onward delivery to multiple mobiles / short message entities (SME).
			SubmitMultiSM smRequest = new SubmitMultiSM();
			SubmitMultiSMResp resp = null;
			String msg="";
			
			
			
				srcAddr.setTon((byte) 1);
				srcAddr.setNpi((byte) 1);
				srcAddr.setAddress(sms.getSourceAddr());
				smRequest.setDataCoding((byte) 8);
				msg = sms.getShortMessage();
				//sms.get(i).setId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
				
			
			

			
			int nb = 0;
			
			List<Csv>data=service.csvToTutorials(file.getInputStream());
			List<Sms>smsList=new ArrayList<Sms>();
			if (msg.length() <= 160) {

				smRequest.setSourceAddr(srcAddr);

				
				//String[][] csv =service.csvToTutorials();
					for (int i = 0; i < data.size();i++) {
						
						
					Address destAddr = new Address();
					
					destAddr.setTon((byte) 1);
					destAddr.setNpi((byte) 1);
					destAddr.setAddress(data.get(i).getDestAddr());
					smRequest.addDestAddress(new DestinationAddress(destAddr));
					smsList.add(new Sms(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME),sms.getShortMessage(),sms.getSourceAddr(),data.get(i).getDestAddr()));

				}

				smRequest.setShortMessage(msg, "UTF-16");
				
				resp = session.submitMulti(smRequest);
				nb = 1;
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

					for (int i1 = 0; i1 < data.size(); i1++) {
						Address destAddr = new Address();
						

						destAddr.setTon((byte) 1);
						destAddr.setNpi((byte) 1);
						destAddr.setAddress(data.get(i1).getDestAddr());
						smRequest.addDestAddress(new DestinationAddress(destAddr));
						smsList.add(new Sms(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME),sms.getShortMessage(),sms.getSourceAddr(),data.get(i).getDestAddr()));
					}
				}

				resp = session.submitMulti(smRequest);
				nb++;

				if (resp.getCommandStatus() == Data.ESME_ROK) {
					System.out.println("Long Message submitted....");
				}
		
			}

			while (nb != 0) {
				this.recive();
				nb--;

			}
			
			smsRepository.saveAll(smsList);
			
			
			
		//service.save(file);
			
			/*while(!file.isEmpty()) {
				//List<Csv> objectList=new ArrayList<>();
				for (int i = 0; i < data.size(); i++) {
					
					repository.saveAll(data);
				//objectList.add((Csv) data);
				//service.save(file);
			}
				
			} */

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	

	}
	 

	public void recive() {
		try {

			PDU pdu = session.receive(1500);

			if (pdu != null) {
				DeliverSM sms = (DeliverSM) pdu;
				

				if ((int) sms.getDataCoding() == 0) {

					log.info("\n ***** New Message Received ***** \n Content: {} \n From: {} \n To: {}",
							sms.getShortMessage().trim(), sms.getSourceAddr().getAddress(),
							sms.getDestAddr().getAddress());
				} else if ((int) sms.getDataCoding() == 8) {

					log.info("\n ***** New Message Received ***** \n Content: {} \n From: {} \n To: {}",
							sms.getShortMessage(Data.ENC_UTF16).trim(), sms.getSourceAddr().getAddress(),
							sms.getDestAddr().getAddress());
				}
			}
		} catch (Exception e) {
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
	
	

	public void createMult(MultipartFile file, Sms sms) {
		this.bindToSmscTransciever();
		this.transcieveMultSms(file,sms);
	}
	
	
		
		
		
		
	
	
	
	
	
	
}
