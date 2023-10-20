package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
// import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThemeDto {
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    private String description;

    // private List<Long> users;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
