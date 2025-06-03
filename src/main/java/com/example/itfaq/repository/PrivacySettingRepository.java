package com.example.itfaq.repository;

import com.example.itfaq.model.PrivacySetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrivacySettingRepository extends JpaRepository<PrivacySetting, String> {
    Optional<PrivacySetting> findBySection(String section);
}