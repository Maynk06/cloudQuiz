package com.campus.quiz.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name="app_users")
public class UserAccount {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true) private String username;
    @Column(nullable=false) private String password;
    @Column(nullable=false) private String displayName;
    @Column(nullable=false) private String role;
    @Enumerated(EnumType.STRING) private Subject subject;
    private LocalDateTime lastLoginAt;
    protected UserAccount() {}
    public UserAccount(String username,String password,String displayName,String role,Subject subject){this.username=username;this.password=password;this.displayName=displayName;this.role=role;this.subject=subject;}
    public Long getId(){return id;} public String getUsername(){return username;} public String getPassword(){return password;}
    public String getDisplayName(){return displayName;} public String getRole(){return role;} public Subject getSubject(){return subject;} public LocalDateTime getLastLoginAt(){return lastLoginAt;} public void setLastLoginAt(LocalDateTime value){lastLoginAt=value;} public void setPassword(String value){password=value;} public void setDisplayName(String value){displayName=value;}
}
