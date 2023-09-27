package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(max = 20)
    private String username;

    private List<Long> themes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
