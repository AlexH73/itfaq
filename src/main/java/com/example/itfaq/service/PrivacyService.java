package com.example.itfaq.service;

import com.example.itfaq.repository.PrivacySettingRepository;
import org.springframework.stereotype.Service;

@Service
public class PrivacyService {
    private final PrivacySettingRepository privacySettingRepository;

    public PrivacyService(PrivacySettingRepository repo) {
        this.privacySettingRepository = repo;
    }

    public boolean isSectionPublic(String section) {
        return privacySettingRepository.findById(section)
                .map(setting -> "public".equals(setting.getMode()))
                .orElse(true); // если нет настройки — считаем раздел публичным
    }
}