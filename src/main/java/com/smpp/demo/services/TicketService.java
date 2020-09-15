package com.smpp.demo.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.smpp.demo.dao.CommentaireRepository;
import com.smpp.demo.dao.PrioritiesRepository;
import com.smpp.demo.dao.TicketRepository;
import com.smpp.demo.dao.UserRepository;
import com.smpp.demo.entities.Commentaire;
import com.smpp.demo.entities.ERole;
import com.smpp.demo.entities.Epriorities;
import com.smpp.demo.entities.Priorities;
import com.smpp.demo.entities.Role;
import com.smpp.demo.entities.Ticket;
import com.smpp.demo.entities.User;
import com.smpp.demo.payload.request.SignupRequest;
import com.smpp.demo.payload.request.TicketRequest;
import com.smpp.demo.payload.response.MessageResponse;

@Service
public class TicketService {
	
	@Autowired
	private PrioritiesRepository prioritiesRepository;
	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RegistrationService registrationService;
	@Autowired
	private CommentaireRepository commentaireRepository;

	
	public Ticket sendTicket(TicketRequest ticket) {
	
		Ticket t = new Ticket(ticket.getSubject(),
				ticket.getContent(),
				ticket.getCreator());
		
		LocalDateTime datetime = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");   
	    t.setCreated_at(datetime.format(format));
	    t.setUpdated_at(datetime.format(format));
	    t.setCompleted_at(datetime.format(format));
		
		// set user id
		/*
		Set<String> strUsers = ticket.getUsers();
		Set<User> users = new HashSet<>();
		int id=0;
		
			User ticketUsers = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Error: User is not found."));
			users.add(ticketUsers);
		
		t.setUsers(users);
		*/
		//set priorities
		Set<String> strPriorities = ticket.getPriorities();
		Set<Priorities> priorities = new HashSet<>();
		if (strPriorities == null) {
			Priorities ticketPriorities = prioritiesRepository.findByName(Epriorities.Low)
				.orElseThrow(() -> new RuntimeException("Error: Priorities is not found."));
			priorities.add(ticketPriorities);
		} else {
			strPriorities.forEach(p -> {
			switch (p) {
			case "Normal":
				Priorities p1 = prioritiesRepository.findByName(Epriorities.Normal)
						.orElseThrow(() -> new RuntimeException("Error: Priorities is not found."));
				priorities.add(p1);
				break;
			case "High":
				Priorities p2 = prioritiesRepository.findByName(Epriorities.High)
						.orElseThrow(() -> new RuntimeException("Error: Priorities is not found."));
				priorities.add(p2);
				break;
			
			default:
				Priorities p3 = prioritiesRepository.findByName(Epriorities.Low)
				.orElseThrow(() -> new RuntimeException("Error: Priorities is not found."));
		priorities.add(p3);
			}
		});
		}
		
		t.setPriorities(priorities);


		return ticketRepository.save(t);

		
	}
	
	public List<Ticket> getTickets() {
		return ticketRepository.findAll();
	}
	
	public List<Ticket> getTicketsByCreator(String creator) {
		return ticketRepository.findByCreatorOrderByIdDesc(creator);
	}

	public List<Commentaire> getCommentairesByTicket(String ticket){
		return commentaireRepository.findByTicket(ticket);
	}
	public Optional<Ticket> getTicketById(String ticket_id){
		return ticketRepository.findById(ticket_id);
	}
	
    public void updateTicket(String ticket_id, TicketRequest ticketDetails) {
        Ticket ticket = ticketRepository.findById(ticket_id).get();
        ticket.setSubject(ticketDetails.getSubject());
        ticket.setContent(ticketDetails.getContent());
        
        LocalDateTime datetime = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
		ticket.setUpdated_at(datetime.format(format));
		ticket.setCompleted_at(datetime.format(format));
        
        Set<String> strPriorities = ticketDetails.getPriorities();
		Set<Priorities> priorities = new HashSet<>();
		if (strPriorities == null) {
			Priorities ticketPriorities = prioritiesRepository.findByName(Epriorities.Low)
				.orElseThrow(() -> new RuntimeException("Error: Priorities is not found."));
			priorities.add(ticketPriorities);
		} else {
			strPriorities.forEach(p -> {
			switch (p) {
			case "Normal":
				Priorities p1 = prioritiesRepository.findByName(Epriorities.Normal)
						.orElseThrow(() -> new RuntimeException("Error: Priorities is not found."));
				priorities.add(p1);
				break;
			case "High":
				Priorities p2 = prioritiesRepository.findByName(Epriorities.High)
						.orElseThrow(() -> new RuntimeException("Error: Priorities is not found."));
				priorities.add(p2);
				break;
			
			default:
				Priorities p3 = prioritiesRepository.findByName(Epriorities.Low)
				.orElseThrow(() -> new RuntimeException("Error: Priorities is not found."));
		priorities.add(p3);
			}
		});
		}
		
		ticket.setPriorities(priorities);
	   
        
       // final Ticket updatedticket =
        		ticketRepository.save(ticket);
       // return ResponseEntity.ok(updatedticket);
    }

    public void deleteTicket(String ticket_id) {
    	ticketRepository.deleteById(ticket_id);
    }
    
    public void completedTicket(String ticket_id) {
    	 Ticket ticket = ticketRepository.findById(ticket_id).get();
    	 LocalDateTime datetime = LocalDateTime.now();
 		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
 		ticket.setCompleted_at(datetime.format(format));
 		ticketRepository.save(ticket);
    	
    }
    
    /*public ResponseEntity<?> addComment(String ticket_id, Ticket ticket) {
    	 Ticket t = ticketRepository.findById(ticket_id).get();
    
    	t.getComment().addAll(ticket.getComment());
    	
    	//t.getComment().put("ok", ticket.getComment().toString());
    	
        LocalDateTime datetime = LocalDateTime.now();
 		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
 		t.setUpdated_at(datetime.format(format));
 		t.setCompleted_at(datetime.format(format));
 		
 		final Ticket addComment =ticketRepository.save(t);
        return ResponseEntity.ok(addComment);
    }*/
   
    /*public ResponseEntity<?> addComment(String ticket_id, Ticket ticket) {
 	 Ticket t = ticketRepository.findById(ticket_id).get();
 	// Commentaire comment = new Commentaire();
 	// comment.setComment(ticket.getComment().toString());
 	 //commentaireRepository.save(comment);
 	 
 	 //Commentaire newCommnet = new Commentaire(ticketDTO.getComments().toString());
 //	 if (t.getComments() == null) {
 //         t.setComments(new ArrayList<>());
  //    }
 	 
 //	 t.getComments().add(newCommnet);
 	 
 	 LocalDateTime datetime = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
		t.setUpdated_at(datetime.format(format));
		t.setCompleted_at(datetime.format(format));
 	 
 	//List<String> c = new ArrayList<>();
		//t.getComments().addAll(ticket.getComments());
     t.setComments(ticket.getComments());
		
		final Ticket addComment =ticketRepository.save(t);
     return ResponseEntity.ok(addComment);
 }
 
   public void addComment(TicketRequest ticketDTO) {
       Optional<Ticket> post = ticketRepository.findById(ticketDTO.getTicket_id());
       Commentaire newCommnet = new Commentaire(ticketDTO.getNewComment());
       if (post.getComments() == null) {
           post.setComments(new ArrayList<>());
       }
       post.getComments().add(newCommnet);
       post.setModifiedDate(new Date());
       postRepo.save(post);
   }*/
  
    
    
    
    public Commentaire saveComment(Commentaire comment) {
    	Commentaire c = new Commentaire(
    			comment.getComment(),
    			comment.getUser(),
    			comment.getTicket()
    			);
    	LocalDateTime datetime = LocalDateTime.now();
  		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
  		c.setCreationDate(datetime.format(format));
   	 return commentaireRepository.save(c);
   }
      
	//priorities
	public Priorities save(Priorities p) {
		
		return prioritiesRepository.save(p);
	}
	
	public List<Priorities> getPriorities() {
		return prioritiesRepository.findAll();
	}
	
	public void deletePrioritie(String id) {
		prioritiesRepository.deleteById(id);
	}
}
