package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;

    @NotNull
    private Long user_id;

    @NotNull
    private Long post_id;

    @NotNull
    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
