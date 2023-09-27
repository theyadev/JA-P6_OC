package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.UserDto;
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
@Mapper(componentModel = "spring", uses = { UserService.class }, imports = { Arrays.class, Collectors.class, User.class,
                User.class, Collections.class, Optional.class })
public abstract class UserMapper implements EntityMapper<UserDto, User> {
        @Autowired
        ThemeService themeService;
        @Autowired
        UserService userService;

        @Mappings({
                        @Mapping(target = "themes", expression = "java(Optional.ofNullable(sessionDto.getThemes()).orElseGet(Collections::emptyList).stream().map(themeId -> { Theme theme = this.themeService.findById(themeId); if (theme != null) { return theme; } return null; }).collect(Collectors.toList()))"),
        })
        public abstract User toEntity(UserDto userDto);

        @Mappings({
                        @Mapping(target = "themes", expression = "java(Optional.ofNullable(session.getThemes()).orElseGet(Collections::emptyList).stream().map(u -> u.getId()).collect(Collectors.toList()))"),
        })
        public abstract UserDto toDto(User user);
}
