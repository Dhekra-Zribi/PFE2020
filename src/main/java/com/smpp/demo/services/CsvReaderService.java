package com.smpp.demo.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smpp.demo.dao.CsvRepository;
import com.smpp.demo.entities.Csv;
import com.smpp.demo.entities.Sms;

@Service
public class CsvReaderService {
	public static String TYPE = "text/csv";
	@Autowired
	CsvRepository repository;
	 /*public static List<String> readCSV(String fileName) throws FileNotFoundException, IOException {
	        List<String> data = new ArrayList<>();
	        BufferedReader br = new BufferedReader(new FileReader("fileName"));

	        String line = br.readLine(); // Reading header, Ignoring

	        while ((line = br.readLine()) != null && !line.isEmpty()) {
	           
	        	String[] fields = line.split(",");
	            String destAddr=fields[0];
	        //    String destAddr = fields[0];
	          Sms sms=new Sms();
	          sms.setDestAddr(fields[0]);
	           Sms dest = new Sms(destAddr);
	            data.add(sms);
	        }
	        br.close();
	        return data;
	    }*/
	 
	/* public static List<Sms> parseCSV(String fileName) throws IOException{
		 
	 List<String[]>data=new ArrayList<>();
		 File csvFile = new File(fileName);
		 List<Sms> dest = new ArrayList<>();
		
		 if (csvFile.isFile()) {
			 
			 BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
			 String row = csvReader.readLine();
			 
			 while ((row = csvReader.readLine()) != null) {
			     String[] datas = row.split(",");
			     // do something with the data
			     
			     for (String[] oneData : data) {
			    	 String destAddr = oneData[0];
			    	 Csv sms = new Csv(destAddr);
			    	 dest.add(sms);
				     
			 }
			    
			 csvReader.close();
			 return dest;
			
		 }
		 
		 
	 }
		
		
	 }*/
	 
	 
	/*public void save(MultipartFile file) {
	    try {
	      List<Sms> content = this.csvToTutorials(file.getInputStream());
	      repository.saveAll(content);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store csv data: " + e.getMessage());
	    }
	  }*/
	 
	 
	 public  List<Csv> csvToTutorials(InputStream is) {
		    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		        CSVParser csvParser = new CSVParser(fileReader,
		            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

		      List<Csv> csvdata = new ArrayList<Csv>();

		      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

		      for (CSVRecord csvRecord : csvRecords) {
		        Csv tutorial = new Csv(
		              
		              csvRecord.get("destAddr"));
		             
		            

		        csvdata.add(tutorial);
		       // repository.saveAll(csvdata);
		       
		      }
		     

		      return csvdata;
		    } catch (IOException e) {
		      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		    }
		  }
	 

	 public static boolean hasCSVFormat(MultipartFile file) {

		    if (!TYPE.equals(file.getContentType())) {
		      return false;
		    }

		    return true;
		  }
	 
	 
	 

	/* private static final String SEPARATOR = ";";

	    private Reader source;

	 public void   CsvReader(Reader source) {
	        this.source = source;
	    }
	    List<String> readHeader() {
	        try (BufferedReader reader = new BufferedReader(source)) {
	            return reader.lines()
	                    .findFirst()
	                    .map(line -> Arrays.asList(line.split(SEPARATOR)))
	                    .get();
	        } catch (IOException e) {
	            throw new UncheckedIOException(e);
	        }
	    }  */
	    
	    
	  /*  List<List<String>> readRecords() {
	        try (BufferedReader reader = new BufferedReader(source)) {
	            return reader.lines()
	                    .substream(1)
	                    .map(line -> Arrays.asList(line.split(separator)))
	                    .collect(Collectors.toList());
	        } catch (IOException e) {
	            throw new UncheckedIOException(e);
	        }
	  
	    
	    } */ 
	
	
	
	/*
	 public  List<String>parseCsv() throws FileNotFoundException {

		List<List<String>> records = new ArrayList<>();
		try (Scanner scanner = new Scanner(new File("destinataires.csv"));) {
		    while (scanner.hasNextLine()) {
		        records.add(getRecordFromLine(scanner.nextLine()));
		    }
		}
		return null;

}
	private List<String> getRecordFromLine(String line) {
	    List<String> values = new ArrayList<String>();
	    try (Scanner rowScanner = new Scanner(line)) {
	       
	        while (rowScanner.hasNext()) {
	            values.add(rowScanner.next());
	        }
	    }
	    return values;
	}
	    
	  */  
	    
	   
}
