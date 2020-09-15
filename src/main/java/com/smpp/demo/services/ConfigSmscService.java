package com.smpp.demo.services;

import java.util.List;

import org.smpp.Data;
import org.smpp.Session;
import org.smpp.TCPIPConnection;
import org.smpp.pdu.BindRequest;
import org.smpp.pdu.BindResponse;
import org.smpp.pdu.BindTransciever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.smpp.demo.dao.ConfigSmsc;
import com.smpp.demo.dao.ConfigSmscRepo;
import com.smpp.demo.payload.request.ResponseMessage;
import com.smpp.demo.payload.request.SmscRequest;
@Service
public class ConfigSmscService {

	@Autowired
	private ConfigSmscRepo smscRepo;
	@Autowired
	private TransceiverService service;
	
	public ConfigSmsc save(SmscRequest smsc) {
		ConfigSmsc s = new ConfigSmsc(
				smsc.getIpAddress(),
				smsc.getSystemId(),
				smsc.getPassword(),
				smsc.getPort()
				);
		return smscRepo.save(s);
	}
	
	public List<ConfigSmsc> getAllSmsc() {
		return smscRepo.findAll();
	}
	
	public ConfigSmsc getSmsc() {
		List<ConfigSmsc> list = this.getAllSmsc();
		ConfigSmsc s = list.get(0);
		return s;
	}
	
	public ResponseEntity<?> bindToSmsc(ConfigSmsc smsc) {
		try {
			String message ="your are connected to SMPPSim.";
			Session session = null;
			// setup connection
			TCPIPConnection connection = new TCPIPConnection(smsc.getIpAddress(), smsc.getPort());
			connection.setReceiveTimeout(20 * 1000);
			session = new Session(connection);

			// set request parameters
			BindRequest request = new BindTransciever();
			request.setSystemId(smsc.getSystemId());
			request.setPassword(smsc.getPassword());

			// send request to bind
			BindResponse response = session.bind(request);
			if (response.getCommandStatus() == Data.ESME_ROK) { //ESME_ROK = new error
				System.out.println("your are connected to SMPPSim.");
				return ResponseEntity.ok(new ResponseMessage("your are connected to SMPPSim."));
			}else {
				System.out.println("Faild to connect: please check your settings!");
				return ResponseEntity
						.badRequest()
						.body(new ResponseMessage("Please check your settings!"));	
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return ResponseEntity.ok(new ResponseMessage("Your are connected to SMPPSim...  "));
	}

}
