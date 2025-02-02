package com.support.customer.support.system.service;

import com.support.customer.support.system.model.Ticket;
import com.support.customer.support.system.model.User;
import com.support.customer.support.system.repository.TicketRepository;
import com.support.customer.support.system.util.EmailUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EmailUtil emailUtil;

    public TicketService(TicketRepository ticketRepository, EmailUtil emailUtil) {
        this.ticketRepository = ticketRepository;
        this.emailUtil = emailUtil;
    }

    // Create a ticket
    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    // Get all tickets (Admin)
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    // Get tickets by creator (Customer)
    public List<Ticket> getTicketsByCreator(User user) {
        return ticketRepository.findByCreatedBy(user);
    }

    // Get tickets by assigned agent (Agent)
    public List<Ticket> getTicketsByAgent(User user) {
        return ticketRepository.findByAssignedTo(user);
    }

    // Get a ticket by ID
    public Optional<Ticket> getTicketById(Long id) {
        return ticketRepository.findById(id);
    }

    // Update a ticket
    public Ticket updateTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    // Delete a ticket
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }

    // Assign a ticket to an agent
    // Assign a ticket to an agent (with email notifications)
    public Ticket assignTicket(Long ticketId, User agent) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            ticket.setAssignedTo(agent);
            Ticket updatedTicket = ticketRepository.save(ticket);

            // Notify customer and agent via email
            if (ticket.getCreatedBy() != null) {
                emailUtil.sendEmail(
                        ticket.getCreatedBy().getUsername(),
                        "Ticket Assigned",
                        "Your ticket '" + ticket.getTitle() + "' has been assigned to agent: " + agent.getUsername()
                );
            }
            emailUtil.sendEmail(
                    agent.getUsername(),
                    "New Ticket Assigned",
                    "You have been assigned ticket: '" + ticket.getTitle() + "'"
            );

            return updatedTicket;
        }
        throw new IllegalArgumentException("Ticket not found!");
    }

    // Update a ticket status (with email notifications)
    public Ticket updateTicketStatus(Long ticketId, Ticket.Status status) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            ticket.setStatus(status);
            Ticket updatedTicket = ticketRepository.save(ticket);

            // Notify the customer about status change
            if (ticket.getCreatedBy() != null) {
                emailUtil.sendEmail(
                        ticket.getCreatedBy().getUsername(),
                        "Ticket Status Updated",
                        "The status of your ticket '" + ticket.getTitle() + "' has been updated to: " + status
                );
            }
            return updatedTicket;
        }
        throw new IllegalArgumentException("Ticket not found!");
    }


    // Search tickets by title, description, or ID
    public List<Ticket> searchTickets(String query) {
        return ticketRepository.findAll().stream()
                .filter(ticket -> ticket.getTitle().toLowerCase().contains(query.toLowerCase())
                        || ticket.getDescription().toLowerCase().contains(query.toLowerCase())
                        || ticket.getId().toString().equals(query))
                .toList();
    }

    // Filter tickets by status, priority, or assigned agent
    public List<Ticket> filterTickets(String status, String priority, User agent) {
        return ticketRepository.findAll().stream()
                .filter(ticket -> (status == null || ticket.getStatus().name().equalsIgnoreCase(status))
                        && (priority == null || ticket.getPriority().name().equalsIgnoreCase(priority))
                        && (agent == null || ticket.getAssignedTo() != null && ticket.getAssignedTo().equals(agent)))
                .toList();
    }
}

