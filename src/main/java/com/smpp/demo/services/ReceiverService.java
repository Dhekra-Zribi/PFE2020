package com.smpp.demo.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.smpp.demo.dao.CountTrRepo;
import com.smpp.demo.dao.SmsRepository;
import com.smpp.demo.entities.CountMsgDate;
import com.smpp.demo.entities.CountTr;
import com.smpp.demo.entities.Sms;
@Service
//@EnableMongoRepositories
public class ReceiverService{
	
	@Autowired
	private SmsRepository smsRepository;
	@Autowired
	private CountTrRepo countTrRepo;
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	/*
	 * Parameters used for connecting to SMSC (or SMPPSim)
	 */
	private Session session = null;
	private String ipAddress = "localhost";
	private String systemId = "smppclient1";
	private String password = "password";
	private int port = 2775;
	private static final Logger log = LoggerFactory.getLogger(ReceiverService.class);
	
	
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
			Sms s = new Sms();
			

			PDU pdu = session.receive(1500);

			if (pdu != null) {
				DeliverSM sms = (DeliverSM) pdu;
				
				System.out.println("4");
				
				if ((int)sms.getDataCoding() == 0 ) {
					//message content is English Or Frensh (ASCII)
					System.out.println("5");
					log.info("\n ***** New Message Received ***** \n Content: {} \n From: {} \n To: {}",
							sms.getShortMessage().trim() ,
							sms.getSourceAddr().getAddress(),sms.getDestAddr().getAddress() );
					s.setShortMessage(sms.getShortMessage().trim());
				
				}
				else if ((int)sms.getDataCoding() == 8 ) {
					System.out.println("6");
					//message content is Non-English (Arabe, chinoi..)
					log.info("\n ***** New Message Received ***** \n Content: {} \n From: {} \n To: {}",
							sms.getShortMessage(Data.ENC_UTF16).trim() ,
							sms.getSourceAddr().getAddress(),sms.getDestAddr().getAddress() );
					s.setShortMessage(sms.getShortMessage(Data.ENC_UTF16).trim());
					
				}
				
				s.setDestAddr(sms.getDestAddr().getAddress());
				s.setSourceAddr(sms.getSourceAddr().getAddress());
				s.setType("tr");
				LocalDateTime datetime = LocalDateTime.now();  
			    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");   
			    s.setDateTime(datetime.format(format));
			    DateTimeFormatter format2 = DateTimeFormatter.ofPattern("HH:mm:ss");
			    s.setTime(datetime.format(format2));
				s.setId(sequenceGeneratorService.generateSequence(CountMsgDate.SEQUENCE_NAME));
			   // s.setId(353);
			    log.info("ok");
			    log.info(s.getShortMessage());
			    System.out.println(s);
			    
			    smsRepository.save(s);
				//receiveImpl.save(s);
			    
			    log.info("ok");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return s;
	}
	
	public Sms recu() {
		this.bindToSmscReceiver();
		while(true) {
			//s= this.receiveSms(s);
			this.receiveSms();
			
		}
	}
	
	public List<Sms> getAllTr(){
		String type = "tr";
		return smsRepository.findAllByTypeOrderByIdDesc(type);
				//smsRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
	}
	
	public String deleteAll() {
		String type = "tr";
		smsRepository.deleteSmsByType(type);
		deleteAllNb();
		countTrByDate();
		//Iterable<? extends Sms> sms = this.getAllTr();
		//smsRepository.deleteAll(sms );
		return "All sms received are deleted";
	}
	
	public void delete(long id) {
		Sms s = smsRepository.findById(id).get();
		smsRepository.deleteById(id);
		//countTrRepo.deleteByDate(s.getDateTime());
		deleteAllNb();
		countTrByDate();
	}

	
	public static HashMap<String, Long> trierHashMap(Map<String, Long> hmap){
        List<Map.Entry<String, Long>> list =
           new LinkedList<Map.Entry<String, Long>>( hmap.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<String, Long>>(){
           public int compare
           (Map.Entry<String, Long>o1, Map.Entry<String, Long> o2 )
           {
              //comparer deux clés
              return (o1.getKey()).compareTo( o2.getKey() );
           }
        });
    HashMap<String, Long> hmapTriee = new LinkedHashMap<String, Long>();
    for (Map.Entry<String, Long> entry : list)
    {
        hmapTriee.put( entry.getKey(), entry.getValue() );
    }
    return hmapTriee;
 }
 

 public void countTrByDate() {
		
		List<String> listeDate = new ArrayList<>();
		String ch=null;
		long nb=0;
		HashMap<String, Long> hm = new HashMap<>();
		
		//List<Sms> list = smsRepository.findAll();
		List<Sms> list = smsRepository.findAllByTypeOrderByIdDesc("tr");
		for (int i = 0; i < list.size(); i++) {
			ch = list.get(i).getDateTime();
			
			listeDate.add(list.get(i).getDateTime());		
		}
		
		//for (String a : listeDate) {
		String a="";
		for (int j = 0; j < listeDate.size(); j++) {
			a = listeDate.get(j);
			//System.out.println("a: "+a);
		  if(hm.containsKey(a)) {
		    hm.put(a, hm.get(a)+1);
		  }
		  else{ 
			  hm.put(a, (long) 1); 
			  }
		}
		System.out.println("liste: "+hm);
		System.out.println("tri: "+trierHashMap(hm));
		//trierHashMap(hm);
		
		CountTr countMsgDate = new CountTr();
		System.out.println("hashmap:");
        Iterator iterator = trierHashMap(hm).entrySet().iterator();
        while (iterator.hasNext()) {
          Map.Entry mapentry = (Map.Entry) iterator.next();
          System.out.println("clé: "+mapentry.getKey()
                            + " | valeur: " + mapentry.getValue());
          
          String tempDate = countMsgDate.getDate();
  		if (tempDate != null && !"".equals(tempDate)) {
  			CountTr obj = countTrRepo.findByDate(mapentry.getKey().toString());;
  			if (obj != null) {
  				obj.setNb((long) mapentry.getValue());
  				countTrRepo.save(countMsgDate);
  				break;
  				}
  		}
  		CountTr obj = null;
  		
          countMsgDate.setId(sequenceGeneratorService.generateSequence(Sms.SEQUENCE_NAME));
          countMsgDate.setDate(mapentry.getKey().toString());
          countMsgDate.setNb((long) mapentry.getValue());
          countTrRepo.save(countMsgDate);
          
        } 
		
	}
 
 public void deleteAllNb() {
	 countTrRepo.deleteAll();
		//countByDate();
	}
 
 public List<CountTr> getNb(){
		//countByDate();
		return countTrRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
	}
}
