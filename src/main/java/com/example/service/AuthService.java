package com.example.service;

import com.example.dto.AuthDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.RegistrationDTO;
import com.example.entity.ProfileEntity;
import com.example.entity.SmsHistoryEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exp.AppBadException;
import com.example.repository.ProfileRepository;
import com.example.repository.SmsHistoryRepository;
import com.example.util.JWTUtil;
import com.example.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;

    @Autowired
    private SmsServerService smsServerService;

    public boolean registration(RegistrationDTO dto) {
        Pattern phone = Pattern.compile("^\\+998(90|91|93|94|95|97|98|99|50|88|77|33|20)\\d{7}$");
        Pattern password = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        if (!phone.matcher(dto.getPhone()).matches()) {
            throw new AppBadException("invalid phone");
        }
        if (!password.matcher(dto.getPassword()).matches()) {
            throw new AppBadException("invalid password");
        }

        if (profileRepository.checkPhoneStatus(dto.getPhone()).isPresent()) {
            throw new AppBadException("you are already registered");
        }
        //================ 1 minutda faqat 1 sms ga ruxsat qilib qo'yildi ================\\
        LocalDateTime from = LocalDateTime.now().minusMinutes(1);
        LocalDateTime to = LocalDateTime.now();
        if (smsHistoryRepository.countSendSms(dto.getPhone(), from, to) >= 1) {
            throw new AppBadException("Please wait 1 minute");
        }

        Optional<ProfileEntity> optional = profileRepository.findByPhone(dto.getPhone());
        if (optional.isPresent()) {
            if (optional.get().getStatus().equals(ProfileStatus.REGISTRATION)) {
                profileRepository.delete(optional.get());
            } else {
                throw new AppBadException("phone.exist");
            }
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setPhone(dto.getPhone());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setStatus(ProfileStatus.REGISTRATION);
        entity.setRole(ProfileRole.ROLE_USER);
        profileRepository.save(entity);

        /**
         * sms kod yuborish
         */
        String code = RandomUtil.getRandomSmsCode();
        smsServerService.send(dto.getPhone(), "metan zaprapka uchun kodingiz: ", code);
        return true;
    }

    public ProfileDTO verification(AuthDTO authDTO) {
        LocalDateTime from = LocalDateTime.now().minusMinutes(1);
        LocalDateTime to = LocalDateTime.now();
        Optional<ProfileEntity> optionalProfile = profileRepository.findByPhone(authDTO.getPhone());
        if (optionalProfile.isEmpty()) {
            throw new AppBadException("profile not found");
        }
        if (!optionalProfile.get().getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("you are unregistered");
        }
        Optional<SmsHistoryEntity> optional = smsHistoryRepository.checkSmsCode(authDTO.getPhone(), authDTO.getCode(), from, to);
        if (optional.isEmpty()){
            throw new AppBadException("the SMS code was entered incorrectly");
        }
        profileRepository.updateStatus(authDTO.getPhone()); // profile statusini active qilib qo'yadi.
        saveHistory(authDTO.getPhone(), authDTO.getCode(), ProfileStatus.ACTIVE);
        ProfileEntity profileEntity = optionalProfile.get();
        ProfileDTO dto = new ProfileDTO();
        dto.setRole(profileEntity.getRole());
        dto.setName(profileEntity.getName());
        dto.setSurname(profileEntity.getSurname());
        dto.setJwt(JWTUtil.encode(profileEntity.getId(), profileEntity.getRole()));
        return dto;
    }

    public void saveHistory(String phone, String code, ProfileStatus profileStatus) {
        SmsHistoryEntity entity = new SmsHistoryEntity();
        entity.setStatus(profileStatus);
        entity.setPhone(phone);
        entity.setMessage(code);
        entity.setCreatedDate(LocalDateTime.now());
        smsHistoryRepository.save(entity);
    }
}
