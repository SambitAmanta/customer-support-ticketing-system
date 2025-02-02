package com.support.customer.support.system.controller;

import com.support.customer.support.system.model.Ticket;
import com.support.customer.support.system.model.User;
import com.support.customer.support.system.service.TicketService;
import com.support.customer.support.system.util.NotificationUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // Create a new ticket (Customer)
    @PostMapping
    public ResponseEntity<?> createTicket(@RequestBody Ticket ticket, @AuthenticationPrincipal User user) {
        ticket.setCreatedBy(user);
        Ticket savedTicket = ticketService.createTicket(ticket);
        return ResponseEntity.ok(savedTicket);
    }

    // Get all tickets (Admin)
    @GetMapping
    public ResponseEntity<?> getAllTickets(@AuthenticationPrincipal User user) {
        if (!user.getRole().equals(User.Role.ADMIN)) {
            return ResponseEntity.status(403).body("Access denied!");
        }
        List<Ticket> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }

    // Get tickets created by customer
    @GetMapping("/my-tickets")
    public ResponseEntity<?> getMyTickets(@AuthenticationPrincipal User user) {
        List<Ticket> tickets = ticketService.getTicketsByCreator(user);
        return ResponseEntity.ok(tickets);
    }

    // Get tickets assigned to an agent
    @GetMapping("/assigned-tickets")
    public ResponseEntity<?> getAssignedTickets(@AuthenticationPrincipal User user) {
        if (!user.getRole().equals(User.Role.AGENT)) {
            return ResponseEntity.status(403).body("Access denied!");
        }
        List<Ticket> tickets = ticketService.getTicketsByAgent(user);
        return ResponseEntity.ok(tickets);
    }

    // Update a ticket
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        Optional<Ticket> existingTicket = ticketService.getTicketById(id);
        if (existingTicket.isPresent()) {
            Ticket updatedTicket = ticketService.updateTicket(ticket);
            return ResponseEntity.ok(updatedTicket);
        }
        return ResponseEntity.status(404).body("Ticket not found!");
    }

    // Delete a ticket
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.ok("Ticket deleted successfully!");
    }


    // Assign a ticket to an agent (Admin only)
    @PostMapping("/{id}/assign")
    public ResponseEntity<?> assignTicket(@PathVariable Long id, @RequestBody User agent,
                                          @AuthenticationPrincipal User currentUser) {
        if (!currentUser.getRole().equals(User.Role.ADMIN)) {
            return ResponseEntity.status(403).body("Access denied!");
        }

        Ticket assignedTicket = ticketService.assignTicket(id, agent);

        // Notify customer and agent
        NotificationUtil.notifyCustomerOnAssignment(assignedTicket);
        NotificationUtil.notifyAgentOnAssignment(assignedTicket);

        return ResponseEntity.ok(assignedTicket);
    }

    // Update ticket status (Admin/Agent)
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateTicketStatus(@PathVariable Long id, @RequestParam Ticket.Status status,
                                                @AuthenticationPrincipal User user) {
        if (user.getRole().equals(User.Role.ADMIN) || user.getRole().equals(User.Role.AGENT)) {
            Ticket updatedTicket = ticketService.updateTicketStatus(id, status);
            return ResponseEntity.ok(updatedTicket);
        }
        return ResponseEntity.status(403).body("Access denied!");
    }

    // Search tickets by title, description, or ID
    @GetMapping("/search")
    public ResponseEntity<?> searchTickets(@RequestParam String query) {
        List<Ticket> tickets = ticketService.searchTickets(query);
        return ResponseEntity.ok(tickets);
    }

    // Filter tickets by status, priority, or assigned agent
    @GetMapping("/filter")
    public ResponseEntity<?> filterTickets(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) Long agentId) {
        User agent = agentId != null ? new User() : null;
        if (agent != null) agent.setId(agentId); // Mock agent user object

        List<Ticket> tickets = ticketService.filterTickets(status, priority, agent);
        return ResponseEntity.ok(tickets);
    }
}

