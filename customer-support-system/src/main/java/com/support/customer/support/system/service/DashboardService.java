package com.support.customer.support.system.service;

import com.support.customer.support.system.model.Ticket;
import com.support.customer.support.system.model.User;
import com.support.customer.support.system.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private final TicketRepository ticketRepository;

    public DashboardService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    // Admin Dashboard
    public Map<String, Object> getAdminDashboard() {
        Map<String, Object> dashboard = new HashMap<>();

        // Tickets by status
        dashboard.put("openTickets", ticketRepository.countByStatus(Ticket.Status.OPEN));
        dashboard.put("inProgressTickets", ticketRepository.countByStatus(Ticket.Status.IN_PROGRESS));
        dashboard.put("resolvedTickets", ticketRepository.countByStatus(Ticket.Status.RESOLVED));
        dashboard.put("closedTickets", ticketRepository.countByStatus(Ticket.Status.CLOSED));

        return dashboard;
    }

    // Agent Dashboard
    public Map<String, Object> getAgentDashboard(User agent) {
        Map<String, Object> dashboard = new HashMap<>();

        // Tickets assigned to the agent
        List<Ticket> assignedTickets = ticketRepository.findByAssignedTo(agent);
        dashboard.put("assignedTickets", assignedTickets);

        // Average resolution time
        Double avgResolutionTime = ticketRepository.findAverageResolutionTimeByAgent(agent);
        dashboard.put("averageResolutionTime", avgResolutionTime != null ? avgResolutionTime : 0);

        return dashboard;
    }

    // Customer Dashboard
    public List<Ticket> getCustomerDashboard(User customer) {
        return ticketRepository.findByCreatedBy(customer);
    }
}

