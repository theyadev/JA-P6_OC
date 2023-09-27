package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.mapper.ThemeMapper;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;
import com.openclassrooms.mddapi.services.ThemeService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/theme")
public class ThemeController {
    private final ThemeMapper themeMapper;
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService, ThemeMapper themeMapper) {
        this.themeService = themeService;
        this.themeMapper = themeMapper;
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        List<Theme> themes = themeService.findAll();

        return ResponseEntity.ok().body(themeMapper.toDto(themes));
    }

    @PostMapping("/subscribe/{id}")
    public ResponseEntity<?> subscribe(Long id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        themeService.subscribe(id, userDetails.getId());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/unsubscribe/{id}")
    public ResponseEntity<?> unsubscribe(Long id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        themeService.unsubscribe(id, userDetails.getId());

        return ResponseEntity.ok().build();
    }

}
