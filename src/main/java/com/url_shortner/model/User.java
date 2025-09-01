package com.url_shortner.model;



import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String emaail;
    private String password;
    private String username;
    private String role="ROLE_USER";
}
