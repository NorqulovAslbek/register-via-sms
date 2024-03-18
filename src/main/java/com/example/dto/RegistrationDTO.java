package com.example.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationDTO {
    @NotNull
    String name;
    @NotNull
    String surname;
    @NotNull
    String phone;
    @NotNull
    String password;
}
