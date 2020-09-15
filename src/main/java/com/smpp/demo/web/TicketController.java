package com.smpp.demo.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smpp.demo.dao.CommentaireRepository;
import com.smpp.demo.dao.TicketRepository;
import com.smpp.demo.entities.Commentaire;
import com.smpp.demo.entities.Priorities;
import com.smpp.demo.entities.Ticket;
import com.smpp.demo.entities.User;
import com.smpp.demo.payload.request.TicketRequest;
import com.smpp.demo.services.TicketService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class TicketController {
	
	@Autowired
	private TicketService ticketService;
	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private CommentaireRepository commentRepo;

	@PostMapping("/sendticket")
	public Ticket addTicket(@Valid @RequestBody TicketRequest ticket) {
		return ticketService.sendTicket(ticket);
	}
	
	@GetMapping("/alltickets")
	public List<Ticket> getTickets() {
		return ticketService.getTickets();
	}
	
	@GetMapping("/ticketcreator")
	public List<Ticket> getTicketsByCreator(@Valid @RequestParam String creator) {
		return ticketService.getTicketsByCreator(creator);
	}
	
	@GetMapping("/ticket{id}")
	public Optional<Ticket> getTicketById(@RequestParam String id){
		return ticketService.getTicketById(id);
	}
	
	@PutMapping("/updateticket{id}")
    public void updateTicket(@RequestParam String id, @Valid @RequestBody TicketRequest ticketDetails) {
         ticketService.updateTicket(id, ticketDetails);
    }
	
	@PutMapping("/completedticket{id}")
    public void completedTicket(@RequestParam String id) {
        ticketService.completedTicket(id);
    }
	
	@DeleteMapping("/Ticketdelete{id}")
	public String delete(@RequestParam String id) {
		ticketService.deleteTicket(id);
		List<Commentaire> c = commentRepo.findByTicket(id);
		commentRepo.deleteAll(c);
		return "Deleted "+id;
	}
	
	/*@PutMapping("/addcomment{id}")
    public ResponseEntity<?> addComment(@RequestParam String id, @Valid @RequestBody Ticket ticketDetails) {
        return ticketService.addComment(id, ticketDetails);
    }*/
	
	@PostMapping("/comment")
	public Commentaire saveComment(@Valid @RequestBody Commentaire c) {
		return ticketService.saveComment(c);
	}
	
	@GetMapping("/commentuser")
	public List<Commentaire> getCommentairesByTicket(@Valid @RequestParam String ticket) {
		return ticketService.getCommentairesByTicket(ticket);
	}
	
	@DeleteMapping("deletecomment")
	public void deleteComment(@Valid @RequestBody Commentaire com) {
		//commentRepo.deleteByComment(comment);
		commentRepo.delete(com);
	}
	
	@PostMapping("/priorities")
	public Priorities savePriorities(@Valid @RequestBody Priorities p) {
		return ticketService.save(p);
	}
	
	@GetMapping("/getpriorities")
	public List<Priorities> getPriorities() {
		return ticketService.getPriorities();
	}
}
