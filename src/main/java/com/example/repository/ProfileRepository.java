package com.example.repository;

import com.example.entity.ProfileEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {
    Optional<ProfileEntity> findByEmail(String username);

    Optional<ProfileEntity> findByPhone(String phone);

    @Query(value = "select * from profile where status='ACTIVE';", nativeQuery = true)
    Optional<ProfileEntity> checkPhoneStatus();
    /////////////////
}
