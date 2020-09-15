package com.smpp.demo.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.expression.Lists;

import com.smpp.demo.dao.DriveRepository;
import com.smpp.demo.entities.Drive;
import com.smpp.demo.entities.User;

@Service
public class DriveService {

	  @Autowired
	  private DriveRepository driveRepository;

	  public List<Drive> store(List<MultipartFile> file, String user) throws IOException {
			
		  List<Drive> listeFile = new ArrayList<>();
		  LocalDateTime datetime = LocalDateTime.now();
		  DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
		  for (int i = 0; i < file.size(); i++) {
			  
			 /* String tempFileName = file.get(i).getName();
					Optional<Drive> fileobj = driveRepository.findByName(tempFileName);
					if (fileobj != null) {
						throw new IOException("File with "+ tempFileName+" is already exist!")	;	
						}*/
			  
			  String fileName = StringUtils.cleanPath(file.get(i).getOriginalFilename());
			   Drive FileDB = new Drive(fileName, file.get(i).getContentType(), file.get(i).getBytes());
			   FileDB.setUser(user);
			   FileDB.setUploaded_at(datetime.format(format));
			   listeFile.add(FileDB);
		}
	    
	    return driveRepository.saveAll(listeFile);
	  }
	  
	 
		 

	  
	  public Optional<Drive> getFile(String name, String user) {
	    return driveRepository.findByNameAndUser(name, user);
	  }
	  
	  public List<Drive> getAllFiles(String user) {
		  List<Drive> liste = driveRepository.findByUserOrderByIdDesc(user);
		  //Collections.sort(liste, Collections.reverseOrder());
		  return liste;
	  //  return driveRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream();
	  }
}
