package com.smpp.demo;

import java.util.Random;
import java.util.Scanner;

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
import org.smpp.pdu.tlv.TLV;
import org.smpp.pdu.tlv.TLVException;
import org.smpp.util.ByteBuffer;
import org.springframework.stereotype.Component;

import com.cloudhopper.commons.gsm.GsmUtil;

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

	public void sendSingleSMS() {
		try {
			SubmitSM request = new SubmitSM();

			Address srcAddr = new Address();
			Address destAddr = new Address();

			Scanner sc = new Scanner(System.in);

			System.out.println("Write a message \n");
			shortMessage = sc.nextLine();
			System.out.println("Write the source adress \n");
			sourceAddress = sc.nextLine();
			System.out.println("Write the destination adress \n");
			destinationAddress = sc.nextLine();

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

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to submit message....");
		}
	}

	public void sendLongSMS() {
		try {
			SubmitSM request = new SubmitSM();

			Random rand = new Random();
			int refNum = rand.nextInt(255);
			byte[][] smsParts = GsmUtil.createConcatenatedBinaryShortMessages(shortMessage.getBytes(), (byte) refNum);

			for (int i = 0; i < smsParts.length; i++) {
				ByteBuffer sms = new ByteBuffer();
				sms.setBuffer(smsParts[i]);

				Address srcAddr = new Address();
				Address destAddr = new Address();

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

				//request.setEsmClass((byte) 64); // sms has UDH

				request.setShortMessage(shortMessage, "UTF-16");

				/*
				 * ByteBuffer msg = new ByteBuffer(); msg.setBuffer(shortMessage.getBytes());
				 * request.setExtraOptional((short) 0x0424, msg);
				 */

				// send the request
				SubmitSMResp resp = session.submit(request);

				if (resp.getCommandStatus() == Data.ESME_ROK) {
					System.out.println("Long Message submitted....");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to submit long message....");
		}
	}
}
