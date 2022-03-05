package com.fromsys;

import java.io.Serializable;
import java.util.UUID;


public class Employee implements Serializable {
    // Properties
    private UUID id;
    private String name;
    private String contact;
    private String address;
    private String status;
    // Generator
    public Employee() {}
    // Getters
    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getContact() { return contact; }
    public String getAddress() { return address; }
    public String getStatus() { return status; }
    // Setters
    public void setId(UUID tId) { this.id = tId; }
    public void setName(String tName) { this.name = tName; }
    public void setContact(String tContact) { this.contact = tContact; }
    public void setAddress(String tAddress) { this.address = tAddress; }
    public void setStatus(String tStatus) { this.status = tStatus; }

} //public class Employee implements Serializable
