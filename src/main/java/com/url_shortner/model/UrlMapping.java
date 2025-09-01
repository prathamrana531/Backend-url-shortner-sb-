package com.url_shortner.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "UrlMapping")
@Data
@Entity
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String originalUrl;
    private String shortUrl;
    private int clickCount;
    private LocalDateTime createdDate;
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @OneToMany
    @JoinColumn(name = "urlMapping")
    private List<EventClick> eventClicks;
}
