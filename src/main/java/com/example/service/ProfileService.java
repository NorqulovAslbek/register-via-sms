package com.example.service;

import com.example.dto.CreateProfileDTO;
import com.example.dto.ProfileDTO;
import com.example.entity.ProfileEntity;
import com.example.exp.AppBadException;
import com.example.repository.ProfileRepository;
import com.example.util.JWTUtil;
import com.example.util.MDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDTO create(CreateProfileDTO dto) {
        if (profileRepository.findByEmail(dto.getEmail()).isEmpty()) {
            ProfileEntity save = profileRepository.save(toEntity(dto));
            return toDTO(save);
        }
        log.warn("A user with this email already exists{}", dto);
        throw new AppBadException("A user with this email already exists!");
    }




    private ProfileEntity toEntity(CreateProfileDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassword(MDUtil.encode(dto.getPassword()));
        entity.setRole(dto.getRole());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    private ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setPassword(MDUtil.encode(entity.getPassword()));
        dto.setStatus(entity.getStatus());
        dto.setRole(entity.getRole());
        dto.setJwt(JWTUtil.encode(entity.getEmail(), dto.getRole()));
        return dto;
    }


}
