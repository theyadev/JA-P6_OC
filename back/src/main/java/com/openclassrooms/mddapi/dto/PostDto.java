package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    private String content;

    @NotNull
    private Long themeId;

    private Long userId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
