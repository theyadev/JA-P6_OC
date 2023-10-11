package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.models.Post;
import com.openclassrooms.mddapi.models.User;
import com.openclassrooms.mddapi.services.ThemeService;
import com.openclassrooms.mddapi.services.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring", uses = { UserService.class }, imports = { Arrays.class, Collectors.class, Post.class,
        User.class, Collections.class, Optional.class })
public abstract class PostMapper implements EntityMapper<PostDto, Post> {
    @Autowired
    ThemeService themeService;
    @Autowired
    UserService userService;

    @Mappings({
            @Mapping(target = "theme", expression = "java(postDto.getThemeId() != null ? this.themeService.findById(postDto.getThemeId()) : null)"),
            @Mapping(target = "user", expression = "java(postDto.getUserId() != null ? this.userService.findById(postDto.getUserId()) : null)"),
    })
    public abstract Post toEntity(PostDto postDto);

    @Mappings({
            @Mapping(source = "post.theme.id", target = "themeId"),
            @Mapping(source = "post.user.id", target = "userId"),
    })
    public abstract PostDto toDto(Post post);
}
