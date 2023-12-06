package com.mergeco.oiljang.user.model.service;


import com.mergeco.oiljang.common.UserRole;
import com.mergeco.oiljang.user.entity.EnrollType;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.entity.UserProfile;
import com.mergeco.oiljang.user.repository.UserProfileRepository;
import com.mergeco.oiljang.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class OAuth2Service {

    private String uploadDir = "C:/OIL-JANG_FE/public/images/userProfile";

    private final UserRepository userRepository;

    private final UserProfileRepository userProfileRepository;

    private final PasswordEncoder passwordEncoder;


    public OAuth2Service(UserRepository userRepository, UserProfileRepository userProfileRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User joinFromGoogle(OAuth2User oauth2User) throws IOException {

        String email = oauth2User.getAttribute("email");

        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()){
            return existingUser.get();
        }

        User newUser = buildUserFromOAuth2User(oauth2User);
        return userRepository.save(newUser);
        

    }

    public User joinFromNaver(OAuth2User oauth2User) throws IOException {

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

    private User buildUserFromOAuth2User(OAuth2User oauth2User) throws IOException {

        if (System.getProperty("os.name").indexOf("Windows") != -1 ){
            uploadDir = "C:/OIL-JANG_FE/public/images/userProfile";
        } else if (System.getProperty("os.name").indexOf("Mac") != -1) {
            uploadDir = "/Users/OIL-JANG_FE/public/images/userProfile";
        }

        String userId = oauth2User.getAttribute("sub");
        String familyName = oauth2User.getAttribute("family_name");
        String giveName = oauth2User.getAttribute("given_name");
        String nickname = oauth2User.getAttribute("name");
        String email = oauth2User.getAttribute("email");
        String pwd = generateRandomString(16);

        String defaultImageDir = uploadDir + "/default/";
        String defaultImageFileName = "default.jpg";

        Path defaultImagePath = Paths.get(defaultImageDir, defaultImageFileName);

        String originalFileName = userId + "-original-" + defaultImageFileName;
        String thumbnailFileName = userId + "-thumbnail-" + defaultImageFileName;

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        File uploadDirFile = new File(uploadPath.toString());
        File originDir = new File(uploadPath.resolve("origin").toString());
        File thumbnailDir = new File(uploadPath.resolve("thumbnail").toString());

        String originFolderPath = Paths.get(uploadDir, "origin").toString();
        String thumbnailFolderPath = Paths.get(uploadDir, "thumbnail").toString();

        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }
        if (!originDir.exists()) {
            originDir.mkdirs();
        }
        if (!thumbnailDir.exists()) {
            thumbnailDir.mkdirs();
        }

        String userProfileOriginPath = Paths.get(originFolderPath, originalFileName).toString();
        String userProfileThumbPath = Paths.get(thumbnailFolderPath, thumbnailFileName).toString();

        Files.copy(defaultImagePath, Paths.get(userProfileOriginPath), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(defaultImagePath, Paths.get(userProfileThumbPath), StandardCopyOption.REPLACE_EXISTING);

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
                .role(UserRole.ROLE_USER)
                .birthDate("소셜 로그인")
                .gender("알 수 없음")
                .phone("")
                .verifyStatus("Y")
                .withdrawStatus("N")
                .enrollDate(LocalDateTime.now())
                .enrollType(EnrollType.GOOGLE)
                .build();


        newUser.setUserProfile(newUserProfile);

        log.debug("user : {}",newUser);

        newUser.passwordEncode(passwordEncoder);

        User joinUser = userRepository.save(newUser);

        newUserProfile.setRefUserCode(joinUser);

        userProfileRepository.save(newUserProfile);

        return joinUser;
    }

    private User buildUserFromNaver(OAuth2User oauth2User) throws IOException {

        if (System.getProperty("os.name").indexOf("Windows") != -1 ){
            uploadDir = "C:/OIL-JANG_FE/public/images/userProfile";
        } else if (System.getProperty("os.name").indexOf("Mac") != -1) {
            uploadDir = "/Users/OIL-JANG_FE/public/images/userProfile";
        }

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

        String pwd = generateRandomString(16);

        System.out.println("birthyear : " + birthyear);
        log.debug("birthyear : {}" , birthyear);

        String defaultImageDir = uploadDir + "/default/";
        String defaultImageFileName = "default.jpg";

        Path defaultImagePath = Paths.get(defaultImageDir, defaultImageFileName);

        String originalFileName = userId + "-original-" + defaultImageFileName;
        String thumbnailFileName = userId + "-thumbnail-" + defaultImageFileName;

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        File uploadDirFile = new File(uploadPath.toString());
        File originDir = new File(uploadPath.resolve("origin").toString());
        File thumbnailDir = new File(uploadPath.resolve("thumbnail").toString());

        String originFolderPath = Paths.get(uploadDir, "origin").toString();
        String thumbnailFolderPath = Paths.get(uploadDir, "thumbnail").toString();

        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }
        if (!originDir.exists()) {
            originDir.mkdirs();
        }
        if (!thumbnailDir.exists()) {
            thumbnailDir.mkdirs();
        }

        String userProfileOriginPath = Paths.get(originFolderPath, originalFileName).toString();
        String userProfileThumbPath = Paths.get(thumbnailFolderPath, thumbnailFileName).toString();

        Files.copy(defaultImagePath, Paths.get(userProfileOriginPath), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(defaultImagePath, Paths.get(userProfileThumbPath), StandardCopyOption.REPLACE_EXISTING);

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
                .role(UserRole.ROLE_USER)
                .birthDate(birthyear)
                .gender(gender)
                .phone(phone)
                .verifyStatus("Y")
                .withdrawStatus("N")
                .enrollDate(LocalDateTime.now())
                .enrollType(EnrollType.NAVER)
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
