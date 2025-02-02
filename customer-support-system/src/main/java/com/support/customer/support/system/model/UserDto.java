package com.support.customer.support.system.model;


import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String password;
    private User.Role role; // Role (ADMIN, AGENT, CUSTOMER)
}
