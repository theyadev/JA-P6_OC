package com.openclassrooms.mddapi.payload.request;

import java.util.Optional;

import javax.validation.constraints.*;

import org.springframework.lang.Nullable;

import lombok.Data;

@Data
public class UpdateRequest {

  @Nullable
  @Size(max = 50)
  @Email
  private Optional<String> email;

  @Nullable
  @Size(min = 3, max = 20)
  private Optional<String> username;
}
