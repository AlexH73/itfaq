package com.example.itfaq.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrivacySetting {
    @Id
    private String section;
    private String mode; // 'public' или 'authenticated'
}
