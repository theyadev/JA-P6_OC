package com.openclassrooms.mddapi.controllers;

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

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;
import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.services.PostService;

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

    @GetMapping("/{id}")
    public  ResponseEntity<?> findById(@PathVariable Long id) {
        Comment comment = this.commentService.findById(id);

        if (comment == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(commentMapper.toDto(comment));
    }

    @GetMapping("/post/{id}")
    public  ResponseEntity<?> findByPostId(@PathVariable Long id) {
        Post post = this.postService.findById(id);

        if (post == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(commentMapper.toDto(post.getComments()));
    }

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody CommentDto commentDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        Post post = postService.findById(commentDto.getPostId());

        if (post == null) {
            return ResponseEntity.notFound().build();
        }

        commentDto.setUserId(userDetails.getId());

        Comment comment = this.commentService.create(commentMapper.toEntity(commentDto));

        return ResponseEntity.ok().body(commentMapper.toDto(comment));
    }

}
