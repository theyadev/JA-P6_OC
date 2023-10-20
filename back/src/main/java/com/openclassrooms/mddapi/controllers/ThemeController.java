package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        Theme theme = themeService.findById(Long.valueOf(id));

        return ResponseEntity.ok().body(themeMapper.toDto(theme));
    }

    @PostMapping("/{id}/subscribe")
    public ResponseEntity<?> subscribe(@PathVariable("id") String id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        themeService.subscribe(Long.valueOf(id), userDetails.getId());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/unsubscribe")
    public ResponseEntity<?> unsubscribe(@PathVariable("id") String id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        themeService.unsubscribe(Long.valueOf(id), userDetails.getId());

        return ResponseEntity.ok().build();
    }

}
