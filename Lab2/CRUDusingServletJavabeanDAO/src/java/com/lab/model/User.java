
package com.lab.model;

/**
 *
 * @author Ikhwan
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String roles;
    
    // Constructor Kosong (Wajib untuk JavaBean)
   public User() {
    }
   
    // Constructor dengan ID (Untuk Update & Delete)
    public User(int id, String username, String password, String roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
    
    // Constructor tanpa ID (Untuk Insert)
    public User(String username, String password, String roles) {
    this.username = username;
    this.password = password;
    this.roles = roles;
    }
    
    // Getter dan Setter
    public int getId() {
    return id;
    }
    
    public void setId(int id) {
    this.id = id;
    }
    public String getUsername() {
    return username;
    }
    
    public void setUsername(String username) {
    this.username = username;
    }
    
    public String getPassword() {
    return password;
    }
    
    public void setPassword(String password) {
    this.password = password;
    }
    
    public String getRoles() {
    return roles;
    }
    
    public void setRoles(String roles) {
    this.roles = roles;
    }
}

