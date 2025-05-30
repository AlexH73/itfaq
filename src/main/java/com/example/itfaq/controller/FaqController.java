package com.example.itfaq.controller;

import com.example.itfaq.service.PrivacyService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FaqController {
    private final PrivacyService privacyService;

    public FaqController(PrivacyService privacyService) {
        this.privacyService = privacyService;
    }

    @GetMapping("/faq")
    public String faq(Model model, Authentication authentication) {
        boolean isPublic = privacyService.isSectionPublic("faq");
        if (!isPublic && authentication == null) {
            return "redirect:/login";
        }
        // ... обычная логика вывода FAQ ...
        return "faq";
    }
}
