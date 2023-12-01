package com.mergeco.oiljang.user.model.service;


import com.mergeco.oiljang.common.UserRole;
import com.mergeco.oiljang.user.entity.EnrollType;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.entity.UserProfile;
import com.mergeco.oiljang.user.repository.UserProfileRepository;
import com.mergeco.oiljang.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


import java.nio.file.Paths;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class OAuth2Service {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final UserRepository userRepository;

    private final UserProfileRepository userProfileRepository;

    private final PasswordEncoder passwordEncoder;


    public OAuth2Service(UserRepository userRepository, UserProfileRepository userProfileRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User joinFromGoogle(OAuth2User oauth2User) {

        String email = oauth2User.getAttribute("email");

        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()){
            return existingUser.get();
        }

        User newUser = buildUserFromOAuth2User(oauth2User);
        return userRepository.save(newUser);
        

    }

    public User joinFromNaver(OAuth2User oauth2User) {

        log.debug("oauth2User : {}", oauth2User);

        String id = ((Map<String, Object>) oauth2User.getAttribute("response")).get("id").toString();

        log.debug("id : {}", id);

        Optional<User> existingUser = userRepository.findByOAuth2Id(id);


        if (existingUser.isPresent()){
            log.debug("existingUser.isPresent() : {}",existingUser.isPresent());
            log.debug("existingUser.get() : {}",existingUser.get());
            return existingUser.get();
        }

        User newUser = buildUserFromNaver(oauth2User);
        return userRepository.save(newUser);

    }

    private User buildUserFromOAuth2User(OAuth2User oauth2User) {

        String userId = oauth2User.getAttribute("sub");
        String familyName = oauth2User.getAttribute("family_name");
        String giveName = oauth2User.getAttribute("given_name");
        String nickname = oauth2User.getAttribute("name");
        String email = oauth2User.getAttribute("email");
        String pwd = generateRandomString(16);

        String originalFileName = userId + "-original-" + "basicImage";
        String thumbnailFileName = userId + "-thumbnail-" + "basicImage";

        String userProfileOriginPath = Paths.get(uploadDir, originalFileName).toString();
        String userProfileThumbPath = Paths.get(uploadDir, thumbnailFileName).toString();

        UserProfile newUserProfile = UserProfile.builder()
                .userImageOriginName(originalFileName)
                .userImageName(thumbnailFileName)
                .userImageOriginAddr(userProfileOriginPath)
                .userImageThumbAddr(userProfileThumbPath)
                .build();

        User newUser = User.builder()
                .id(userId)
                .name(familyName+giveName)
                .nickname(nickname)
                .pwd(pwd)
                .email(email)
                .profileImageUrl(userProfileThumbPath)
                .role(UserRole.ROLE_USER)
                .birthDate("소셜 로그인")
                .gender("알 수 없음")
                .phone("")
                .verifyStatus("Y")
                .withdrawStatus("N")
                .enrollDate(LocalDateTime.now())
                .enrollType(EnrollType.GOOGLE)
                .profileImageUrl(userProfileThumbPath)
                .build();


        newUser.setUserProfile(newUserProfile);

        log.debug("user : {}",newUser);

        newUser.passwordEncode(passwordEncoder);

        User joinUser = userRepository.save(newUser);

        newUserProfile.setRefUserCode(joinUser);

        userProfileRepository.save(newUserProfile);

        return joinUser;
    }



    private User buildUserFromNaver(OAuth2User oauth2User) {

        log.debug("buildUserFromNaver start~~~~~~~");
        log.debug("oauth2User : {}", oauth2User);



        String userId = ((Map<String, Object>) oauth2User.getAttribute("response")).get("id").toString();
        log.debug("oauth2User : {}", ((Map<String, Object>) oauth2User.getAttribute("response")).get("id").toString());

        String name = ((Map<String, Object>) oauth2User.getAttribute("response")).get("name").toString();
        log.debug("oauth2User : {}", ((Map<String, Object>) oauth2User.getAttribute("response")).get("name").toString());

        String nickname = ((Map<String, Object>) oauth2User.getAttribute("response")).get("nickname").toString();
        log.debug("oauth2User : {}", ((Map<String, Object>) oauth2User.getAttribute("response")).get("nickname").toString());

        String email = ((Map<String, Object>) oauth2User.getAttribute("response")).get("email").toString();
        log.debug("oauth2User : {}", ((Map<String, Object>) oauth2User.getAttribute("response")).get("email").toString());

        String phone = ((Map<String, Object>) oauth2User.getAttribute("response")).get("mobile").toString();
        log.debug("oauth2User : {}", ((Map<String, Object>) oauth2User.getAttribute("response")).get("mobile").toString());

        String gender = ((Map<String, Object>) oauth2User.getAttribute("response")).get("gender").toString();
        log.debug("oauth2User : {}", ((Map<String, Object>) oauth2User.getAttribute("response")).get("gender").toString());

        String birthyear = ((Map<String, Object>) oauth2User.getAttribute("response")).get("birthyear").toString();
        log.debug("oauth2User : {}", ((Map<String, Object>) oauth2User.getAttribute("response")).get("birthyear").toString());


        //String birthday = ((Map<String, Object>) oauth2User.getAttribute("response")).get("birthday").toString();
        //log.debug("oauth2User : {}", ((Map<String, Object>) oauth2User.getAttribute("response")).get("birthday").toString());

        String pwd = generateRandomString(16);

        System.out.println("birthyear : " + birthyear);
        log.debug("birthyear : {}" , birthyear);

        String originalFileName = userId + "-original-" + "basicImage";
        String thumbnailFileName = userId + "-thumbnail-" + "basicImage";

        String userProfileOriginPath = Paths.get(uploadDir, originalFileName).toString();
        String userProfileThumbPath = Paths.get(uploadDir, thumbnailFileName).toString();

        UserProfile newUserProfile = UserProfile.builder()
                .userImageOriginName(originalFileName)
                .userImageName(thumbnailFileName)
                .userImageOriginAddr(userProfileOriginPath)
                .userImageThumbAddr(userProfileThumbPath)
                .build();

        User newUser = User.builder()
                .id(userId)
                .name(name)
                .nickname(nickname)
                .pwd(pwd)
                .email(email)
                .profileImageUrl(userProfileThumbPath)
                .role(UserRole.ROLE_USER)
                .birthDate(birthyear)
                .gender(gender)
                .phone(phone)
                .verifyStatus("Y")
                .withdrawStatus("N")
                .enrollDate(LocalDateTime.now())
                .enrollType(EnrollType.NAVER)
                .profileImageUrl(userProfileThumbPath)
                .build();


        newUser.setUserProfile(newUserProfile);

        log.debug("user : {}",newUser);

        newUser.passwordEncode(passwordEncoder);

        User joinUser = userRepository.save(newUser);

        newUserProfile.setRefUserCode(joinUser);

        userProfileRepository.save(newUserProfile);

        return joinUser;
    }

    private String generateRandomString(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

}
