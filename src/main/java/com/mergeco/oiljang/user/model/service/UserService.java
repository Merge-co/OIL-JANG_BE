package com.mergeco.oiljang.user.model.service;

import com.mergeco.oiljang.auth.handler.CustomAuthenticationProvider;
import com.mergeco.oiljang.auth.handler.TokenProvider;
import com.mergeco.oiljang.auth.model.dto.*;
import com.mergeco.oiljang.common.UserRole;
import com.mergeco.oiljang.common.exception.DuplicateNicknameException;
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
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${file.upload-dir}")
    private String uploadDir;


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

        if (!checkUserNicknameExist(joinDTO.getNickname())) {
            throw new DuplicateNicknameException("이미 사용 중인 닉네임입니다.");
        }

        // 중복 아이디 체크
        if (!checkUserIdExist(joinDTO.getId())) {
            throw new DuplicateNicknameException("이미 사용 중인 아이디입니다.");
        }


        // 파일 업로드 및 경로 저장
        String originalFileName = joinDTO.getId() + "-original-" + imageFile.getOriginalFilename();
        String thumbnailFileName = joinDTO.getId() + "-thumbnail-" + imageFile.getOriginalFilename();

        saveProfileImage(imageFile, originalFileName, thumbnailFileName);

        // UserProfile 경로 설정
        String userProfileOriginPath = Paths.get(uploadDir, originalFileName).toString();
        String userProfileThumbPath = Paths.get(uploadDir, thumbnailFileName).toString();


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
                .profileImageUrl(userProfileThumbPath)
                .build();

        user.setUserProfile(userProfile);

        log.debug("user : {}",user);

        user.passwordEncode(passwordEncoder);

        User joinUser = userRepository.save(user);

        userProfile.setRefUserCode(joinUser); // userProfile에서 refUserCode 설정

        userProfileRepository.save(userProfile);

        return joinUser;
    }

    private void saveProfileImage(MultipartFile file, String originalFilename, String thumbnailFilename) throws IOException {
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
        } else
            return true;
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
    public UserDTO updateUser(int userCode, UpdateUserDTO updateUserDTO) throws IOException {

        User user = userRepository.findById(userCode)
                .orElseThrow(() -> new UserNotFoundException("해당 사용자를 찾을 수 없습니다."));

        UserProfile profile = userProfileRepository.findByUser(user);

        System.out.println(user);
        System.out.println(profile);

        User updatedUser = null;

        UserProfile updatedUserProfile = null;

        if (updateUserDTO.getNickname() != null) {
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
                    .profileImageUrl(user.getProfileImageUrl())
                    .userProfile(user.getUserProfile())
                    .build();
        }


        if (updateUserDTO.getProfileImage() != null) {
            MultipartFile profileImage = updateUserDTO.getProfileImage();


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

        if (updateUserDTO.getNewPassword() != null) {
            validatePasswordUpdate(updatedUser, updateUserDTO.getNewPassword(), updateUserDTO.getNewPasswordConfirm());
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
                    .profileImageUrl(user.getProfileImageUrl())
                    .userProfile(user.getUserProfile())
                    .build();
            updatePasswordAndSave(updatedUser);
        }else {
            userRepository.save(updatedUser);
        }

        User finalUpdatedUser = userRepository.save(updatedUser != null ? updatedUser : user);

        return UserDTO.fromEntity(finalUpdatedUser);
    }

    private void updateProfileImage(User updatedUser, UserProfile userProfile, MultipartFile profileImage) throws IOException {
        deleteProfileImage(userProfile.getUserImageOriginAddr());
        deleteProfileImage(userProfile.getUserImageThumbAddr());

        String originalFilename = userProfile.getRefUserCode().getId() + "-original-" + profileImage.getOriginalFilename();
        String thumbnailFilename = userProfile.getRefUserCode().getId() + "-thumbnail-" + profileImage.getOriginalFilename();

        saveProfileImage(profileImage, originalFilename, thumbnailFilename);

        UserProfile updatedUserProfile = userProfile.builder()
                .profileCode(userProfile.getProfileCode())
                .userImageOriginName(originalFilename)
                .userImageName(thumbnailFilename)
                .userImageOriginAddr(Paths.get(uploadDir, originalFilename).toString())
                .userImageThumbAddr(Paths.get(uploadDir, thumbnailFilename).toString())
                .build();

        updatedUserProfile.setRefUserCode(updatedUser);

        userProfileRepository.save(updatedUserProfile);
        System.out.println(Paths.get(uploadDir, thumbnailFilename).toString());
        System.out.println(updatedUser.getUserCode());
        userRepository.editProfileUrl(Paths.get(uploadDir, thumbnailFilename).toString(), updatedUser.getUserCode());
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
        User existingUser = userRepository.findByNickname(newNickname);
        if (existingUser != null && existingUser.getUserCode() != userCode) {
            throw new DuplicateNicknameException("이미 사용 중인 닉네임입니다.");
        }
    }

    // 비밀번호 변경 유효성 검사 메소드
    private void validatePasswordUpdate(User user, String newPassword, String newPasswordConfirm) {
        if (!newPassword.equals(newPasswordConfirm)) {
            throw new InvalidPasswordException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }
    }


    private void updatePasswordAndSave(User user) {
        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
    }

    @Transactional
    public void withdrawUser(int userCode) {

        User user = userRepository.findById(userCode).orElseThrow(() -> new UserNotFoundException("해당 사용자를 찾을 수 없습니다."));

        user.withdraw();

        userRepository.save(user);

    }













    /*@Transactional
    @RequestMapping(value="/api/v1/oauth2/google", method = RequestMethod.GET)
    public String getGoogleInfo(@RequestParam(value = "code") String authCode){
        RestTemplate restTemplate = new RestTemplate();
        GoogleRequest googleOAuthRequestParam = GoogleRequest
                .builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .code(authCode)
                .redirectUri("Google Console 사용자 정보에 등록한 URI")
                .grantType("authorization_code").build();
        ResponseEntity<GoogleResponse> response = restTemplate.postForEntity("https://oauth2.googleapis.com/token",
                googleOAuthRequestParam, GoogleResponse.class);
        String jwtToken=response.getBody().getId_token();
        Map<String, String> map=new HashMap<>();
        map.put("id_token",jwtToken);
        ResponseEntity<GoogleInfoResponse> infoResponse = restTemplate.postForEntity("https://oauth2.googleapis.com/tokeninfo",
                map, GoogleInfoResponse.class);
        String email=infoResponse.getBody().getEmail();

        log.info("이메일 "+ email);
        return email;
    }*/


}
