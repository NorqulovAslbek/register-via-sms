package com.example.dto;

import com.example.enums.ProfileRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtDTO {
    Integer id;
    ProfileRole role;
    String phone;

    public JwtDTO(Integer id) {
        this.id = id;
    }

    public JwtDTO(Integer id, ProfileRole role) {
        this.id = id;
        this.role = role;
    }

    public JwtDTO(String phone, ProfileRole role) {
        this.role = role;
        this.phone = phone;
    }
}
