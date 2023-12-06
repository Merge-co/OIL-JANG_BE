package com.mergeco.oiljang.user.model.service;

import com.mergeco.oiljang.auth.handler.CustomAuthenticationProvider;
import com.mergeco.oiljang.auth.handler.TokenProvider;
import com.mergeco.oiljang.auth.model.dto.*;
import com.mergeco.oiljang.common.UserRole;
import com.mergeco.oiljang.common.exception.DuplicatedException;
import com.mergeco.oiljang.common.exception.InvalidPasswordException;
import com.mergeco.oiljang.common.exception.UserNotFoundException;
import com.mergeco.oiljang.user.entity.EnrollType;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.entity.UserProfile;
import com.mergeco.oiljang.user.model.dto.UpdateUserDTO;
import com.mergeco.oiljang.user.model.dto.UserDTO;
import com.mergeco.oiljang.user.repository.UserProfileRepository;
import com.mergeco.oiljang.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
@Slf4j
public class UserService {

   /* @Value("${spring.google.client_id}")
    String clientId;

    @Value("${spring.google.client_secret}")
    String clientSecret;*/

    private String uploadDir = "C:/OIL-JANG_FE";


    @PersistenceContext
    private final EntityManager entityManager;
    private final UserRepository userRepository;

    private final CustomAuthenticationProvider authenticationProvider;

    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    public UserService(EntityManager entityManager, UserRepository userRepository, CustomAuthenticationProvider authenticationProvider, UserProfileRepository userProfileRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
        this.authenticationProvider = authenticationProvider;
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public User join(JoinDTO joinDTO, MultipartFile imageFile) throws IOException {

        if (System.getProperty("os.name").indexOf("Windows") != -1 ){
            uploadDir = "C:/OIL-JANG_FE/public/images/userProfile";
        } else if (System.getProperty("os.name").indexOf("Mac") != -1) {
            uploadDir = "/Users/OIL-JANG_FE/public/images/userProfile";
        }

        log.debug("joinDTO : {}",joinDTO.getId());
        log.debug("joinDTO : {}",joinDTO.getBirthDate());
        log.debug("joinDTO : {}",joinDTO.getName());
        log.debug("joinDTO : {}",joinDTO.getNickname());
        log.debug("joinDTO : {}",joinDTO.getPwd());
        log.debug("joinDTO : {}",joinDTO.getGender());
        log.debug("imageFile : {}",imageFile);

        log.debug("join start-------------");

        if (!checkUserNicknameExist(joinDTO.getNickname())) {
            throw new DuplicatedException("이미 사용 중인 닉네임입니다.");
        }

        log.debug("checkUserNicknameExist end-------------");

        // 중복 아이디 체크
        if (!checkUserIdExist(joinDTO.getId())) {
            throw new DuplicatedException("이미 사용 중인 아이디입니다.");
        }

        log.debug("checkUserIdExist end-------------");

        try {
            log.debug("join method started at {}", LocalDateTime.now());




            // 파일 업로드 및 경로 저장
            String originalFileName = joinDTO.getId() + "-original-" + imageFile.getOriginalFilename();
            String thumbnailFileName = joinDTO.getId() + "-thumbnail-" + imageFile.getOriginalFilename();

            log.debug("originalFileName, thumbnailFileName", originalFileName,thumbnailFileName);

            saveProfileImage(imageFile, originalFileName, thumbnailFileName);

            String thumbnailFolderName = "thumbnail";
            String originFolderName = "origin";

            // UserProfile 경로 설정
            String userProfileOriginPath = Paths.get(uploadDir,originFolderName ,originalFileName).toString();
            String userProfileThumbPath = Paths.get(uploadDir,thumbnailFolderName ,thumbnailFileName).toString();

            log.debug("userProfileOriginPath, userProfileThumbPath", userProfileOriginPath,userProfileThumbPath);

            //UserProfile 생성
            UserProfile userProfile = UserProfile.builder()
                    .userImageOriginName(originalFileName)
                    .userImageName(thumbnailFileName)
                    .userImageOriginAddr(userProfileOriginPath)
                    .userImageThumbAddr(userProfileThumbPath)
                    .build();

            //User 생성
            User user = User.builder()
                    .nickname(joinDTO.getNickname())
                    .name(joinDTO.getName())
                    .id(joinDTO.getId())
                    .pwd(joinDTO.getPwd())
                    .birthDate(joinDTO.getBirthDate())
                    .gender(joinDTO.getGender())
                    .enrollType(EnrollType.NORMAL)
                    .role(UserRole.ROLE_USER)
                    .phone(joinDTO.getPhone())
                    .verifyStatus("Y")
                    .withdrawStatus("N")
                    .email(joinDTO.getEmail())
                    .enrollDate(LocalDateTime.now())
                    .build();

            user.setUserProfile(userProfile);

            log.debug("user : {}",user);

            user.passwordEncode(passwordEncoder);

            User joinUser = userRepository.save(user);

            log.debug("joinUser",joinUser);

            userProfile.setRefUserCode(joinUser); // userProfile에서 refUserCode 설정

            userProfileRepository.save(userProfile);

            return joinUser;
        }catch (Exception e){
            log.error("Exception in join method: {}", e.getMessage(), e);
            throw e; // 예외를 상위로 전파
        }

    }

    private void saveProfileImage(MultipartFile file, String originalFilename, String thumbnailFilename) throws IOException {

        if (System.getProperty("os.name").indexOf("Windows") != -1 ){
            uploadDir = "C:/OIL-JANG_FE/public/images/userProfile";
        } else if (System.getProperty("os.name").indexOf("Mac") != -1) {
            uploadDir = "/Users/OIL-JANG_FE/public/images/userProfile";
        }

        log.debug("saveProfileImage start-------------");


        // 파일 업로드 경로 설정
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        File uploadDir = new File(uploadPath.toString());
        File originDir = new File(uploadPath.resolve("origin").toString());
        File thumbnailDir = new File(uploadPath.resolve("thumbnail").toString());

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        if (!originDir.exists()) {
            originDir.mkdirs();
        }
        if (!thumbnailDir.exists()) {
            thumbnailDir.mkdirs();
        }

        // 파일 저장 경로 설정
        Path originalFilePath = originDir.toPath().resolve(originalFilename).normalize();
        Path thumbnailFilePath = thumbnailDir.toPath().resolve(thumbnailFilename).normalize();

        file.transferTo(originalFilePath.toFile());


        // Thumbnails 라이브러리를 사용하여 썸네일 생성 및 저장
        Thumbnails.of(originalFilePath.toFile())
                .size(100, 100)
                .toFile(thumbnailFilePath.toFile());
    }

    public boolean checkUserIdExist(String id) {
        String userId = userRepository.checkUserIdExist(id);

        log.info(userId);
        System.out.println(userId);

        if (userId != null) {
            return false;
        } else {
            return true;
        }
    }


    public boolean checkUserNicknameExist(String nickname) {
        String userNickname = userRepository.checkUserNicknameExist(nickname);

        log.info(userNickname);
        System.out.println(userNickname);

        if (userNickname != null) {
            return false;
        } else
            return true;
    }

    public User findUserByCode(int userCode) {

        return userRepository.findById(userCode).orElse(null);

    }

    public UserDTO getUserInfo(int userCode) {

        User user = findUserByCode(userCode);


        if (user != null) {
            UserDTO userDTO = UserDTO.fromEntity(user);

            UserProfile userProfile = user.getUserProfile();
            if (userProfile != null) {
                userDTO.setUserImageOriginName(userProfile.getUserImageOriginName());
                userDTO.setUserImageName(userProfile.getUserImageName());
                userDTO.setUserImageOriginAddr(userProfile.getUserImageOriginAddr());
                userDTO.setUserImageThumbAddr(userProfile.getUserImageThumbAddr());
            }

            return userDTO;
        }

        return null;

    }

    @Transactional
    public UserDTO updateUser(int userCode, UpdateUserDTO updateUserDTO, MultipartFile profileImage) throws IOException {

        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new UserNotFoundException("해당 사용자를 찾을 수 없습니다."));

        UserProfile profile = userProfileRepository.findByUser(user);

        System.out.println(user);
        System.out.println(profile);

        log.debug("profileImage: {}",profileImage );

        User updatedUser = findUserByCode(userCode);

        UserProfile updatedUserProfile = userProfileRepository.findByUser(user);

        if (updateUserDTO.getNickname() != null && !("".equals(updateUserDTO.getNickname()))) {

            log.debug("Nickname Change Start================ " );

            validateNickname(userCode, updateUserDTO.getNickname());
            updatedUser = User.builder()
                    .userCode(user.getUserCode())
                    .nickname(updateUserDTO.getNickname())
                    .name(user.getName())
                    .id(user.getId())
                    .pwd(user.getPwd())
                    .birthDate(user.getBirthDate())
                    .gender(user.getGender())
                    .enrollType(user.getEnrollType())
                    .role(user.getRole())
                    .phone(user.getPhone())
                    .verifyStatus(user.getVerifyStatus())
                    .withdrawStatus(user.getWithdrawStatus())
                    .email(user.getEmail())
                    .enrollDate(user.getEnrollDate())
                    .userProfile(user.getUserProfile())
                    .build();

            userRepository.save(updatedUser);

            log.debug("Nickname Change End================ " );
        }


        if (profileImage != null ) {


            if (updatedUser.getUserProfile() != null) {
                updateProfileImage(updatedUser,updatedUser.getUserProfile(), profileImage);
            } else {

                String originalFilename = user.getId() + "-original-" + profileImage.getOriginalFilename();
                String thumbnailFilename = user.getId() + "-thumbnail-" + profileImage.getOriginalFilename();

                saveProfileImage(profileImage, originalFilename, thumbnailFilename);

                String userProfileOriginPath = Paths.get(uploadDir, originalFilename).toString();
                String userProfileThumbPath = Paths.get(uploadDir, thumbnailFilename).toString();

                System.out.println(userProfileOriginPath);
                System.out.println(userProfileThumbPath);

                updatedUserProfile = UserProfile.builder()
                        .profileCode(updatedUserProfile.getProfileCode())
                        .userImageOriginName(originalFilename)
                        .userImageName(thumbnailFilename)
                        .userImageOriginAddr(userProfileOriginPath)
                        .userImageThumbAddr(userProfileThumbPath)
                        .build();

                updatedUser.setUserProfile(updatedUserProfile);
                userProfileRepository.save(updatedUserProfile);
            }
        }

        if (updateUserDTO.getNewPassword() != null && !("".equals(updateUserDTO.getNewPassword()))) {
            log.debug("NewPassword Change Start================ " );
            validatePasswordUpdate(updateUserDTO.getNewPassword(), updateUserDTO.getNewPasswordConfirm());
            updatedUser = User.builder()
                    .userCode(user.getUserCode())
                    .nickname(user.getNickname())
                    .name(user.getName())
                    .id(user.getId())
                    .pwd(updateUserDTO.getNewPassword())
                    .birthDate(user.getBirthDate())
                    .gender(user.getGender())
                    .enrollType(user.getEnrollType())
                    .role(user.getRole())
                    .phone(user.getPhone())
                    .verifyStatus(user.getVerifyStatus())
                    .withdrawStatus(user.getWithdrawStatus())
                    .email(user.getEmail())
                    .enrollDate(user.getEnrollDate())
                    .userProfile(user.getUserProfile())
                    .build();
            updatePasswordAndSave(updatedUser);

            userRepository.save(updatedUser);

            log.debug("NewPassword Change End================ " );

        }else {
            userRepository.save(updatedUser);
        }

        log.debug("updatedUser : {}",updatedUser);

        return UserDTO.fromEntity(updatedUser);
    }

    private void updateProfileImage(User updatedUser, UserProfile userProfile, MultipartFile profileImage) throws IOException {

        if (System.getProperty("os.name").indexOf("Windows") != -1 ){
            uploadDir = "C:/OIL-JANG_FE/public/images/userProfile";
        } else if (System.getProperty("os.name").indexOf("Mac") != -1) {
            uploadDir = "/Users/OIL-JANG_FE/public/images/userProfile";
        }

        deleteProfileImage(userProfile.getUserImageOriginAddr());
        deleteProfileImage(userProfile.getUserImageThumbAddr());

        String originalFilename = userProfile.getRefUserCode().getId() + "-original-" + profileImage.getOriginalFilename();
        String thumbnailFilename = userProfile.getRefUserCode().getId() + "-thumbnail-" + profileImage.getOriginalFilename();


        String thumbnailFolderName = "thumbnail";
        String originFolderName = "origin";

        saveProfileImage(profileImage, originalFilename, thumbnailFilename);

        UserProfile updatedUserProfile = userProfile.builder()
                .profileCode(userProfile.getProfileCode())
                .userImageOriginName(originalFilename)
                .userImageName(thumbnailFilename)
                .userImageOriginAddr(Paths.get(uploadDir,originFolderName, originalFilename).toString())
                .userImageThumbAddr(Paths.get(uploadDir, thumbnailFolderName, thumbnailFilename).toString())
                .build();

        updatedUserProfile.setRefUserCode(updatedUser);

        userProfileRepository.save(updatedUserProfile);
        System.out.println(Paths.get(uploadDir, thumbnailFilename).toString());
        System.out.println(updatedUser.getUserCode());
    }

    private void deleteProfileImage(String userImageOriginAddr) {

        try {
            Files.deleteIfExists(Paths.get(userImageOriginAddr));
            log.info("파일 삭제 성공 : " + userImageOriginAddr);
        } catch (IOException e) {
            log.error("파일 삭제 실패 : " + userImageOriginAddr, e);
        }

    }

    private void validateNickname(int userCode, String newNickname) {

        log.debug("validateNickname Change Start================ " );

        log.debug("validateNickname userCode : {} ", userCode );
        log.debug("validateNickname newNickname : {} ", newNickname );

        User existingUser = userRepository.findByNickname(newNickname);
        if (existingUser != null && existingUser.getUserCode() != userCode) {
            throw new DuplicatedException("이미 사용 중인 닉네임입니다.");
        }
    }

    // 비밀번호 변경 유효성 검사 메소드
    private void validatePasswordUpdate(String newPassword, String newPasswordConfirm) {

        log.debug("updatePasswordAndSave Change Start================ " );

        log.debug("validatePasswordUpdate newPassword : {} ", newPassword );
        log.debug("validatePasswordUpdate newPasswordConfirm : {} ", newPasswordConfirm );

        if (!newPassword.equals(newPasswordConfirm)) {
            throw new InvalidPasswordException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }
    }


    private void updatePasswordAndSave(User user) {

        log.debug("updatePasswordAndSave Change Start================ " );

        log.debug("updatePasswordAndSave User : {} ", user );

        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
    }

    @Transactional
    public void withdrawUser(int userCode) {

        User user = userRepository.findById(userCode).orElseThrow(() -> new UserNotFoundException("해당 사용자를 찾을 수 없습니다."));

        user.withdraw();

        userRepository.save(user);

    }


    public String findId(UserDTO userDTO) {

        System.out.println("UserDTO : "+ userDTO);

        String name = userDTO.getName();
        String gender = userDTO.getGender();
        String birthDate = userDTO.getBirthDate();

        System.out.println("name : " + name);
        System.out.println("gender : " + gender);
        System.out.println("birthDate : " + birthDate);

        String id = userRepository.findId(name,gender,birthDate);

        System.out.println("id : " + id);

        return id;

    }

    @Transactional
    public void changePwd(UpdateUserDTO updateUserDTO) {

        String id = updateUserDTO.getId();
        System.out.println("id : " + id);
        String newPwd = updateUserDTO.getNewPassword();
        System.out.println("newPwd : " + newPwd);
        String newPwdConfirm = updateUserDTO.getNewPasswordConfirm();
        System.out.println("newPwdConfirm : " + newPwdConfirm);

        User user = userRepository.findByUserId(id);

        if (newPwd != null && !("".equals(newPwd))) {
            validatePasswordUpdate(newPwd, newPwdConfirm);

            User updatedUser = User.builder()
                    .userCode(user.getUserCode())
                    .nickname(user.getNickname())
                    .name(user.getName())
                    .id(user.getId())
                    .pwd(newPwd)
                    .birthDate(user.getBirthDate())
                    .gender(user.getGender())
                    .enrollType(user.getEnrollType())
                    .role(user.getRole())
                    .phone(user.getPhone())
                    .verifyStatus(user.getVerifyStatus())
                    .withdrawStatus(user.getWithdrawStatus())
                    .email(user.getEmail())
                    .enrollDate(user.getEnrollDate())
                    .userProfile(user.getUserProfile())
                    .build();

            updatePasswordAndSave(updatedUser);

        }

    }

}
