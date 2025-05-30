package com.example.itfaq.controller;

import com.example.itfaq.model.PrivacySetting;
import com.example.itfaq.repository.PrivacySettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/privacy")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminPrivacyController {
    private final PrivacySettingRepository privacySettingRepository;

    @GetMapping
    public String privacySettings(Model model, @RequestParam(value = "success", required = false) String success) {
        List<PrivacySetting> settings = privacySettingRepository.findAll();
        PrivacySettingForm form = new PrivacySettingForm();
        form.setSettings(settings.stream()
                .map(s -> {
                    PrivacySettingForm.Setting ss = new PrivacySettingForm.Setting();
                    ss.setSection(s.getSection());
                    ss.setMode(s.getMode());
                    return ss;
                }).toList());
        model.addAttribute("settings", settings);
        model.addAttribute("settingsForm", form);
        model.addAttribute("success", success != null);
        return "admin/privacy-settings";
    }

    @PostMapping
    public String updatePrivacySettings(@ModelAttribute("settingsForm") PrivacySettingForm form) {
        if (form.getSettings() == null) {
            form.setSettings(new ArrayList<>());
        }
        if (form.getSettings() != null) {
            for (PrivacySettingForm.Setting s : form.getSettings()) {
                privacySettingRepository.save(new PrivacySetting(s.getSection(), s.getMode()));
            }
        }
        return "redirect:/admin/privacy?success";
    }

    // Класс-форма для передачи списка настроек
    @lombok.Data
    public static class PrivacySettingForm {
        private List<Setting> settings;

        public PrivacySettingForm() {
            this.settings = new ArrayList<>();
        }

        @lombok.Data
        public static class Setting {
            private String section;
            private String mode;
        }
    }
}