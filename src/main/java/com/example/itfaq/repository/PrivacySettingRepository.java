package com.example.itfaq.repository;

import com.example.itfaq.model.PrivacySetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivacySettingRepository extends JpaRepository<PrivacySetting, String> {
}