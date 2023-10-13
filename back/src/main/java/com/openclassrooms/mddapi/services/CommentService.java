package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment findById(Long id) {
        return this.commentRepository.findById(id).orElse(null);
    }

    public Comment create(Comment comment) {
        return this.commentRepository.save(comment);
    }

    public void delete(Long id) {
        this.commentRepository.deleteById(id);
    }

    public List<Comment> getByPostId(Long postId) {
        return this.commentRepository.findByPost_Id(postId);
    }

}
