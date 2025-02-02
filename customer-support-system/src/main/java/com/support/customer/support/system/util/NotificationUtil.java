package com.support.customer.support.system.util;


import com.support.customer.support.system.model.Ticket;
import com.support.customer.support.system.model.User;

public class NotificationUtil {

    // Notify customer about ticket assignment
    public static void notifyCustomerOnAssignment(Ticket ticket) {
        System.out.println("Notification to Customer: Your ticket '" + ticket.getTitle() +
                "' has been assigned to agent '" + ticket.getAssignedTo().getUsername() + "'.");
    }

    // Notify agent about a new ticket assignment
    public static void notifyAgentOnAssignment(Ticket ticket) {
        System.out.println("Notification to Agent: You have been assigned ticket '" +
                ticket.getTitle() + "'.");
    }

    // Notify customer about ticket status change
    public static void notifyCustomerOnStatusChange(Ticket ticket) {
        System.out.println("Notification to Customer: The status of your ticket '" +
                ticket.getTitle() + "' has been updated to '" + ticket.getStatus() + "'.");
    }
}
