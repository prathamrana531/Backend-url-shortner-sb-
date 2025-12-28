package com.url.shortner.model;



import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "Userss")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String username;
    private String password;
    private String role="ROLE_USER";
}
