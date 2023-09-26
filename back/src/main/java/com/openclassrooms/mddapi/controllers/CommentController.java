package com.openclassrooms.mddapi.controllers;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.payload.request.CommentRequest;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;
import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.services.PostService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentMapper commentMapper;
    private final CommentService commentService;
    private final PostService postService;

    public CommentController(CommentService commentService, CommentMapper commentMapper, PostService postService) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
        this.postService = postService;
    }

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody CommentRequest commentRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        log.info(commentRequest.getPostId());
        log.info(userDetails.getEmail());

        Post post = postService.findById(commentRequest.getPostId());

        if (post == null) {
            return ResponseEntity.notFound().build();
        }

        log.info(post);

        Comment commentData = Comment.builder()
                .content(commentRequest.getContent())
                .post(post)
                .user(User.builder()
                        .id(userDetails.getId())
                        .username(userDetails.getUsername())
                        .password(userDetails.getPassword())
                        .email(userDetails.getEmail())
                        .build())
                .build();

        log.info(commentData);

        Comment comment = this.commentService.create(commentData);

        log.info(comment);

        return ResponseEntity.ok().body(commentMapper.toDto(comment));
    }

}
