package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.models.Comment;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.PostService;
import com.openclassrooms.mddapi.services.UserService;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = { UserService.class }, imports = { Arrays.class, Collectors.class, Post.class,
        User.class, Collections.class, Optional.class })
public abstract class CommentMapper implements EntityMapper<CommentDto, Comment> {
    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Mappings({
            @Mapping(target = "post", expression = "java(commentDto.getPostId() != null ? this.postService.findById(commentDto.getPostId()) : null)"),
            @Mapping(target = "user", expression = "java(commentDto.getUserId() != null ? this.userService.findById(commentDto.getUserId()) : null)"),
    })
    public abstract Comment toEntity(CommentDto commentDto);

    @Mappings({
            @Mapping(source = "comment.post.id", target = "postId"),
            @Mapping(source = "comment.user.id", target = "userId"),
    })
    public abstract CommentDto toDto(Comment comment);

}
