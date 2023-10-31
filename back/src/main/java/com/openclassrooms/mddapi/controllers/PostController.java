package com.openclassrooms.mddapi.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.Theme;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;
import com.openclassrooms.mddapi.services.PostService;
import com.openclassrooms.mddapi.services.ThemeService;


@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostMapper postMapper;
    private final PostService postService;
    private final ThemeService themeService;

    public PostController(PostService postService, PostMapper postMapper, ThemeService themeService) {
        this.postService = postService;
        this.postMapper = postMapper;
        this.themeService = themeService;
    }

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody PostDto postDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        Theme theme = themeService.findById(postDto.getThemeId());

        if (theme == null) {
            return ResponseEntity.notFound().build();
        }

        postDto.setUserId(userDetails.getId());

        Post post = this.postService.create(postMapper.toEntity(postDto));

        return ResponseEntity.ok().body(postMapper.toDto(post));
    }

    @GetMapping("/feed")
    public ResponseEntity<?> getSubscribedPosts() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        List<Post> posts = postService.getSubscribedPosts(userDetails.getId());

        return ResponseEntity.ok().body(postMapper.toDto(posts));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getPostById(@PathVariable("id") String id) {
        Post post = postService.findById(Long.valueOf(id));

        if (post == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(postMapper.toDto(post));
    }

}
