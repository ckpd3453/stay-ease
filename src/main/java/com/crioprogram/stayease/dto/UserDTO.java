package com.crioprogram.stayease.dto;

import com.crioprogram.stayease.model.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UserDTO {

    @Column(nullable = true)
    private String firstName;

    @Column(nullable = true)
    private String lastName;
    @Column(nullable = false)
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String emailId;

    @Column(nullable = false)
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long, contain one uppercase letter, one lowercase letter, " +
                    "one digit, and one special character"
    )
    private String password;

    @Column(nullable = false)
    @Pattern(
            regexp = "^(CUSTOMER|ADMIN|HOTEL_MANAGER)$",
            message = "Role must be one of: CUSTOMER, ADMIN, HOTEL_MANAGER"
    )
    private String role = "CUSTOMER";

    public UserDTO() {

    }

    public UserDTO(User user){
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.emailId = user.getEmailId();
        this.role = user.getRole();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
