package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.models.Theme;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring", uses = { UserService.class }, imports = { Arrays.class, Collectors.class, User.class,
                Collections.class, Optional.class, Theme.class })
public abstract class UserMapper implements EntityMapper<UserDto, User> {
        @Autowired
        ThemeService themeService;
        @Autowired
        UserService userService;

        @Mappings({
                @Mapping(target = "themes", expression = "java(mapThemes(userDto.getThemes()))")
        })
        public abstract User toEntity(UserDto userDto);

        @Mappings({
                @Mapping(target = "themes", expression = "java(mapThemeIds(user.getThemes()))")
        })
        public abstract UserDto toDto(User user);

        protected List<Theme> mapThemes(List<Long> themeIds) {
                if (themeIds == null) {
                        return Collections.emptyList();
                }
                return themeIds.stream()
                .map((Long id) -> themeService.findById(id))
                .filter(theme -> theme != null)
                .collect(Collectors.toList());
        }

        protected List<Long> mapThemeIds(List<Theme> themes) {
                if (themes == null) {
                        return Collections.emptyList();
                }
                return themes.stream()
                .map(Theme::getId)
                .collect(Collectors.toList());
        }
}
