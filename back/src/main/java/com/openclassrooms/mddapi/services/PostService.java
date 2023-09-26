package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post create(Post post) {
        return this.postRepository.save(post);
    }

    public void delete(Long id) {
        this.postRepository.deleteById(id);
    }

    public List<Post> findAll() {
        return this.postRepository.findAll();
    }

    public Post findById(Long id) {
        return this.postRepository.findById(id).orElse(null);
    }
 
    public List<Post> getSubscribedPosts(Long userId) {
        User user = this.userRepository.findById(userId).orElse(null);

        if (user == null) {
            throw new NotFoundException();
        }

        List<Long> themeIds = user.getThemes().stream()
                .map(Theme::getId)
                .collect(Collectors.toList());

        return this.postRepository.findByUserIdOrThemeIdIn(user.getId(), themeIds);
    }

}
