package com.example.repository;

import com.example.entity.ProfileEntity;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {
    Optional<ProfileEntity> findByEmail(String username);
    @Query(value = "FROM ProfileEntity WHERE phone=?1 AND password=?2 AND visible=true")
    Optional<ProfileEntity> findByPhoneAndPassword(String phone, String password);
    @Query(value = "select * from profile where phone=:phone", nativeQuery = true)
    Optional<ProfileEntity> findByPhone(@Param("phone") String phone);

    @Query(value = "select * from profile where phone=:phone and status='ACTIVE';", nativeQuery = true)
    Optional<ProfileEntity> checkPhoneStatus(@NotNull @Param("phone") String phone);


    @Modifying
    @Transactional
    @Query(value = "update profile set status='ACTIVE' where phone=:phone", nativeQuery = true)
    void updateStatus(@Param("phone") String phone);


}
