package com.openclassrooms.mddapi.payload.request;

import javax.validation.constraints.*;

import lombok.Data;

@Data
public class CommentRequest {
  @NotBlank
  private String content;

  @NotNull
  private Long postId;

}
