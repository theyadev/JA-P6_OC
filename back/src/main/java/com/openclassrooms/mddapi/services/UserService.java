package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.request.UpdateRequest;
import com.openclassrooms.mddapi.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User update(User user, UpdateRequest updateRequest) {
        String username = updateRequest.getUsername().orElse(null);
        String email = updateRequest.getEmail().orElse(null);

        if (username == null && email == null) {
            return user;
        }

        if (username != null) {
            user.setUsername(username);
        }

        if (email != null) {
            user.setEmail(email);
        }

        return this.userRepository.save(user);
    }

}
