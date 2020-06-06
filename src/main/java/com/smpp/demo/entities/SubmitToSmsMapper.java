package com.smpp.demo.entities;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.smpp.pdu.SubmitSM;

@Mapper(componentModel = "spring")

public interface SubmitToSmsMapper {
	

	      @Mapping(target="shortMessage", source="")
	      
	    
	Sms submitToSms(SubmitSM submit);
	
	      SubmitSM sm=new SubmitSM();
	      
         
}
