package com.smpp.demo;

import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Function;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smpp.Connection;
import org.smpp.Data;
import org.smpp.Session;
import org.smpp.TCPIPConnection;
import org.smpp.pdu.Address;
import org.smpp.pdu.BindRequest;
import org.smpp.pdu.BindTransmitter;
import org.smpp.pdu.BindTransmitterResp;
import org.smpp.pdu.SubmitSM;
import org.smpp.pdu.SubmitSMResp;
import org.smpp.pdu.ValueNotSetException;
import org.smpp.pdu.WrongLengthOfStringException;
import org.smpp.pdu.tlv.TLV;
import org.smpp.pdu.tlv.TLVException;
import org.smpp.util.ByteBuffer;
import org.springframework.stereotype.Component;

import com.cloudhopper.commons.gsm.GsmUtil;

import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class SimpleSMSTransmitter {

	/**
	 * @param args
	 */

	private Session session = null;
	private String ipAddress = "127.0.0.1";
	private String systemId = "smppclient1";
	private String password = "password";
	private int port = 2775;
	private String shortMessage;
	private String sourceAddress;
	private String destinationAddress;

	public void bindToSmscTransmitter() {
		try {
			Connection conn = new TCPIPConnection(ipAddress, port);
			session = new Session(conn);

			BindRequest breq = new BindTransmitter();

			breq.setSystemId(systemId);
			breq.setPassword(password);
			BindTransmitterResp bresp = (BindTransmitterResp) session.bind(breq);

			if (bresp.getCommandStatus() == Data.ESME_ROK) {
				System.out.println("Connected to SMSC");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sendSMS() {
		try {
			Address srcAddr = new Address();
			Address destAddr = new Address();

			SubmitSM smRequest = new SubmitSM();
			SubmitSMResp resp = null;

			Scanner sc = new Scanner(System.in);

			System.out.println("Write a message \n");
			shortMessage = sc.nextLine();
			System.out.println("Write the source address \n");
			sourceAddress = sc.nextLine();
			System.out.println("Write the destination address \n");
			destinationAddress = sc.nextLine();
			
			srcAddr.setTon((byte) 1);
			srcAddr.setNpi((byte) 1);
			srcAddr.setAddress(sourceAddress);

			destAddr.setTon((byte) 1);
			destAddr.setNpi((byte) 1);
			destAddr.setAddress(destinationAddress);

			if (shortMessage.length() <= 160) {

				smRequest.setSourceAddr(srcAddr);
				smRequest.setDestAddr(destAddr);
				smRequest.setDataCoding((byte) 8);
				smRequest.setShortMessage(shortMessage, "UTF-16");
				resp = session.submit(smRequest);
				if (resp.getCommandStatus() == Data.ESME_ROK) {
					System.out.println("Message submitted....");
				}
			} else {
				// SMS length > 160 Char

				smRequest.setEsmClass((byte) Data.SM_UDH_GSM); // Set UDHI Flag Data.SM_UDH_GSM=0x40

				String[] splittedMsg = this.SplitByWidth(shortMessage, 153);

				int totalSegments = splittedMsg.length;

				for (int i = 0; i < totalSegments; i++) {

					ByteBuffer ed = new ByteBuffer();
					ed.appendString(splittedMsg[i]);// , Data.ENC_ASCII

					smRequest.setShortMessageData(ed);

					smRequest.setSourceAddr(srcAddr);
					smRequest.setDestAddr(destAddr);

					resp = session.submit(smRequest);
				}

				if (resp.getCommandStatus() == Data.ESME_ROK) {
					System.out.println("Long Message submitted....");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to submit message....");
		}
	}

	public void sendSingleSMS() {
		try {
			SubmitSM request = new SubmitSM();

			Address srcAddr = new Address();
			Address destAddr = new Address();

			Scanner sc = new Scanner(System.in);

			System.out.println("Write a message \n");
			shortMessage = sc.nextLine();
			System.out.println("Write the source address \n");
			sourceAddress = sc.nextLine();
			System.out.println("Write the destination address \n");
			destinationAddress = sc.nextLine();

			// Mono<Address> srcAddr = Mono.just(new Address());
			// Mono<Address> destAddr = Mono.just(new Address());
			// srcAddr.subscribe(s1 -> System.out.println("Write the source address \n" +
			// s1));
			// System.out.println(srcAddr); //MonoJust
			/*
			 * srcAddr.subscribe(n -> n.setTon((byte) 1)); srcAddr.subscribe(n ->
			 * n.setNpi((byte) 1)); srcAddr.subscribe(n -> { try {
			 * n.setAddress(sourceAddress); } catch (WrongLengthOfStringException e) { //
			 * TODO Auto-generated catch block e.printStackTrace(); } });
			 */

			/*
			 * Flux<Address> srcAddr; Flux<Address> destAddr; srcAddr.subscribe(n ->
			 * n.setTon((byte) 1)); srcAddr.subscribe(n -> n.setNpi((byte) 1));
			 * srcAddr.subscribe(n -> n.setAddress(sourceAddress));
			 * 
			 * 
			 * destAddr.subscribe(n -> n.setTon((byte) 1)); destAddr.subscribe(n ->
			 * n.setNpi((byte) 1)); destAddr.subscribe(n -> n.setAddress(sourceAddress));
			 */

			// set values
			srcAddr.setTon((byte) 1);
			srcAddr.setNpi((byte) 1);
			srcAddr.setAddress(sourceAddress);

			destAddr.setTon((byte) 1);
			destAddr.setNpi((byte) 1);
			destAddr.setAddress(destinationAddress);

			// Mono.just(srcAddr);
			// System.out.println("1"+srcAddr);
			// Mono.just(destAddr);
			// System.out.println("2"+destAddr);

			request.setSourceAddr(srcAddr);
			request.setDestAddr(destAddr);
			request.setDataCoding((byte) 8);
			request.setShortMessage(shortMessage, "UTF-16");

			// Mono<SubmitSM> data = (Mono<SubmitSM>) Mono.just(request).subscribe();

			// send the request
			SubmitSMResp resp = session.submit(request);
			// Mono<SubmitSMResp> r = (Mono<SubmitSMResp>) Mono.just(resp);

			if (resp.getCommandStatus() == Data.ESME_ROK) {
				System.out.println("Message submitted....");
			}

////////////////////////////////////////////////
			/*
			 * return WebClient.builder() .build() .get() .uri(keys.getProfileUrl())
			 * .header(HttpHeaders.AUTHORIZATION, OAuth2AccessToken.BEARER_TYPE + " " +
			 * accessToken) .exchange() .flatMap(resp -> resp.bodyToMono(Map.class))
			 * .flatMap(body -> { if(Integer.valueOf(401).equals(body.get("status"))){
			 * return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED,
			 * body.get("message").toString())); } else { return Mono.just(body); } })
			 * .map(values -> new TokenService.UserSSO(values, keys)) .map(userSSO -> { User
			 * user = new User(); user.setIdSSO(ssoProvider.toString() + "#" + userSSO.id);
			 * user.setFirstName(userSSO.firstName); user.setLastName(userSSO.lastName);
			 * return user; });
			 */

/////////////////////////////////////
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Failed to submit message....");
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

}
