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
        return themeRepository.save(theme);
    }

    public void delete(Long id) {
        themeRepository.deleteById(id);
    }

    public List<Theme> findAll() {
        return themeRepository.findAll();
    }

    public Theme findById(Long id) {
        return themeRepository.findById(id).orElse(null);
    }

    public void subscribe(Long id, Long userId) {
        Theme theme = themeRepository.findById(id).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (theme == null || user == null) {
            throw new NotFoundException();
        }

        Boolean alreadySubscribed = user.getThemes().contains(theme);
        if (alreadySubscribed) {
            throw new BadRequestException();
        }

        user.getThemes().add(theme);

        userRepository.save(user);
    }

    public void unsubscribe(Long id, Long userId) {
        Theme theme = themeRepository.findById(id).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (theme == null || user == null) {
            throw new NotFoundException();
        }

        Boolean subscribed = user.getThemes().contains(theme);
        if (!subscribed) {
            throw new BadRequestException();
        }

        user.getThemes().remove(theme);
        userRepository.save(user);
    }
}
