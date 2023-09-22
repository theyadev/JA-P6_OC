package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exception.BadRequestException;
import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {
    private final ThemeRepository themeRepository;
    private final UserRepository userRepository;

    public ThemeService(ThemeRepository themeRepository, UserRepository userRepository) {
        this.themeRepository = themeRepository;
        this.userRepository = userRepository;

    }

    public Theme create(Theme theme) {
        return this.themeRepository.save(theme);
    }

    public void delete(Long id) {
        this.themeRepository.deleteById(id);
    }

    public List<Theme> findAll() {
        return this.themeRepository.findAll();
    }

    public void subscribe(Long id, Long userId) {
        Theme theme = this.themeRepository.findById(id).orElse(null);
        User user = this.userRepository.findById(userId).orElse(null);

        if (theme == null || user == null) {
            throw new NotFoundException();
        }

        Boolean alreadySubscribed = theme.getUsers().stream().anyMatch(o -> o.getId().equals(userId));
        if (alreadySubscribed) {
            throw new BadRequestException();
        }

        theme.getUsers().add(user);

        this.themeRepository.save(theme);
    }

    public void unsubscribe(Long id, Long userId) {
        Theme theme = this.themeRepository.findById(id).orElse(null);
        User user = this.userRepository.findById(userId).orElse(null);

        if (theme == null || user == null) {
            throw new NotFoundException();
        }

        Boolean subscribed = theme.getUsers().contains(user);
        if (!subscribed) {
            throw new BadRequestException();
        }

        theme.getUsers().remove(user);
        this.themeRepository.save(theme);
    }
}
