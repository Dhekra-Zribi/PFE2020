package com.smpp.demo.web;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smpp.demo.dao.DriveRepository;
import com.smpp.demo.entities.Drive;
import com.smpp.demo.payload.request.ResponseDrive;
import com.smpp.demo.payload.request.ResponseMessage;
import com.smpp.demo.payload.response.MessageResponse;
import com.smpp.demo.services.DriveService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class DriveController {

	@Autowired
	  private DriveService storageService;
	 @Autowired
	  private DriveRepository driveRepository;


	  @PostMapping("/upload")
	  public ResponseEntity<?> uploadFile(@Valid @RequestParam String user, @RequestParam("file") List<MultipartFile> file) throws IOException {

		   String message = "";
		   String tempFileName="";
		  for (int i = 0; i < file.size(); i++) {
		  tempFileName = file.get(i).getOriginalFilename();
			boolean fileobj = driveRepository.existsByNameAndUser(tempFileName, user);
			if (fileobj) {
					return ResponseEntity
							.badRequest()
							.body(new ResponseMessage("File already exist!"));	
			}
			
			 message = "Uploaded the file successfully: " + tempFileName;
			 storageService.store(file, user);
			 return ResponseEntity.ok(new ResponseMessage(message));
		  }
		  
		  message = "Could not upload the file: " + tempFileName + "!";
		  return ResponseEntity
					.badRequest()
					.body(new ResponseMessage(message));
	     
	  }

	  @GetMapping("/files")
	  public ResponseEntity<List<ResponseDrive>> getListFiles(@Valid @RequestParam String user) {
	    List<ResponseDrive> files = storageService.getAllFiles(user).stream().map(dbFile -> {
	      String fileDownloadUri = ServletUriComponentsBuilder
	          .fromCurrentContextPath()
	          .path("/api/auth/files/")
	          .path(dbFile.getId())
	          .toUriString();

	      return new ResponseDrive(
	          dbFile.getName(),
	          fileDownloadUri,
	          dbFile.getType(),
	          dbFile.getData().length,
	          dbFile.getUploaded_at());
	    }).collect(Collectors.toList());
	    
		// return storageService.getAllFiles(user);
	    return ResponseEntity.status(HttpStatus.OK).body(files);
	  }

	  @GetMapping("/file{name}")
	  public ResponseEntity<?> getFile(@RequestParam String name, @RequestParam String user) {
		 Optional<Drive> fileDB= storageService.getFile(name, user);

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.get().getName() + "\"")
	        .body(fileDB.get().getData());
	  }
	  
	  @DeleteMapping("/delete{name}")
	  public void delete(@RequestParam String name , @RequestParam String user) {
		  driveRepository.deleteByNameAndUser(name, user);
	  }
	  
	 
	   /* @GetMapping("/file{id}")
		public void show(@RequestParam String id, HttpServletResponse response) {

	    	Optional<Drive> drive = storageService.getFile(id);
		      if (drive.get().getName().indexOf(".doc")>-1) response.setContentType("application/msword");
		      if (drive.get().getName().indexOf(".docx")>-1) response.setContentType("application/msword");
		      if (drive.get().getName().indexOf(".xls")>-1) response.setContentType("application/vnd.ms-excel");
		      if (drive.get().getName().indexOf(".csv")>-1) response.setContentType("application/vnd.ms-excel");
		      if (drive.get().getName().indexOf(".ppt")>-1) response.setContentType("application/ppt");
		      if (drive.get().getName().indexOf(".pdf")>-1) response.setContentType("application/pdf");
		      if (drive.get().getName().indexOf(".zip")>-1) response.setContentType("application/zip");
		      response.setHeader("Content-Disposition", "attachment; filename=" +drive.get().getName());
		      response.setHeader("Content-Transfer-Encoding", "binary");
		      try {
		    	  BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
		    	  FileInputStream fis = new FileInputStream(drive.get().getName());
		    	  int len;
		    	  byte[] buf = new byte[1024];
		    	  while((len = fis.read(buf)) > 0) {
		    		  bos.write(buf,0,len);
		    	  }
		    	  bos.close();
		    	  response.flushBuffer();
		      }
		      catch(IOException e) {
		    	  e.printStackTrace();
		    	  
		      }
		}*/
	  
	  /*@GetMapping("/file{id}")
	  public @ResponseBody
	  void download(@RequestParam String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
		  
		 Optional<Drive> file = storageService.getFile(id);
	      if (file != null) {
	          try {
	              response.setContentType(file.get().getType());
	              response.setHeader("content-Disposition", "attachment; filename=" + file.get().getName());// "attachment;filename=test.xls"
	              // copy it to response's OutputStream$
	              IOUtils.copyLarge(file.get().getStream(), response.getOutputStream());
	          } catch (IOException ex) {
	              System.out.println("'Error writing file to output stream. Filename was '" + id + "'");
	              throw new RuntimeException("Error writing file to output stream");
	          }
	      }
	  }*/

}
