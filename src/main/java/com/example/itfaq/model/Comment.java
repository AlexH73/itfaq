package com.example.itfaq.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {
    @Id @GeneratedValue
    private Long id;
    @ManyToOne
    private News news;
    @ManyToOne
    private User author;
    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    private Comment parentComment; // если нужны вложенные комментарии

    // getters, setters
}