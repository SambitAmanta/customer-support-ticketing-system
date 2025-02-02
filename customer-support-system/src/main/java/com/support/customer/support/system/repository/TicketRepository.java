package com.support.customer.support.system.repository;

import com.support.customer.support.system.model.Ticket;
import com.support.customer.support.system.model.Ticket.Status;
import com.support.customer.support.system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // Find tickets by status
    long countByStatus(Status status);

    // Find tickets assigned to a specific agent
    List<Ticket> findByAssignedTo(User agent);

    // Find tickets created by a specific customer
    List<Ticket> findByCreatedBy(User customer);

    // Find the average resolution time for an agent's tickets
    @Query("SELECT AVG(DATEDIFF(t.updatedDate, t.createdDate)) FROM Ticket t WHERE t.assignedTo = :agent AND t.status = 'RESOLVED'")
    Double findAverageResolutionTimeByAgent(User agent);
}

