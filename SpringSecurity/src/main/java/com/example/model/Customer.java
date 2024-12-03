package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "pwd", nullable = false)
    private String pwd;

    @Column(name = "rrole", nullable = false) // Map to the database column "rrole"
    private String role; // Use a consistent name like "role" in the code for clarity

    // Default constructor
    public Customer() {
        super();
    }

    // Parameterized constructor
    public Customer(long id, String email, String pwd, String role) {
        super();
        this.id = id;
        this.email = email;
        this.pwd = pwd;
        this.role = role;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRole() {
        return role; // Getter matches the "role" field
    }

    public void setRole(String role) {
        this.role = role; // Setter matches the "role" field
    }
}
