package com.mergeco.oiljang.user.model.service;

import com.mergeco.oiljang.auth.model.dto.*;
import com.mergeco.oiljang.common.UserRole;
import com.mergeco.oiljang.common.exception.UserErrorResult;
import com.mergeco.oiljang.common.exception.UserException;
import com.mergeco.oiljang.user.entity.EnrollType;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.entity.UserProfile;
import com.mergeco.oiljang.user.model.dto.UserProfileDTO;
import com.mergeco.oiljang.user.repository.UserRepository;
/*import lombok.RequiredArgsConstructor;*/
import lombok.extern.slf4j.Slf4j;
/*import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;*/
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
/*import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;*/
import org.springframework.web.multipart.MultipartFile;

/*import java.util.HashMap;
import java.util.Map;*/
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

   /* @Value("${spring.google.client_id}")
    String clientId;

    @Value("${spring.google.client_secret}")
    String clientSecret;*/

    @Value("${file.upload-dir}")
    private String uploadDir;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findUser(String id) {
        Optional<User> user = userRepository.findById(id);

        return user;
    }

    public User join(JoinDTO joinDTO, UserProfileDTO profileDTO, MultipartFile file) throws IOException {

        //중복 유저 존재 여부 체크
        Optional<User> existingUser = userRepository.findById(joinDTO.getId());
        if (existingUser.isPresent()) {
            // 중복 사용자 처리
            throw new UserException(UserErrorResult.DUPLICATED_MEMBER_REGISTER);
        }

        Optional<User> existingUserByNickname = userRepository.findByNickname(joinDTO.getNickname());
        if (existingUserByNickname.isPresent()) {
            // 중복 닉네임 처리
            throw new UserException(UserErrorResult.DUPLICATED_NICKNAME_REGISTER);
        }


        // 파일 업로드 처리
        String originalFileName = null;
        String thumbnailFileName = null;

        if (file != null && !file.isEmpty()) {
            originalFileName = joinDTO.getId() + "-original-" + file.getOriginalFilename();
            thumbnailFileName = joinDTO.getId() + "-thumbnail-" + file.getOriginalFilename();
            saveProfileImage(file, originalFileName, thumbnailFileName);
        }

        //UserProfile 생성
        UserProfile userProfile = UserProfile.builder()
                .userImageOriginName(profileDTO.getUserImageOriginName())
                .userImageName(profileDTO.getUserImageOriginName())
                .userImageOriginAddr(profileDTO.getUserImageOriginName())
                .userImageThumbAddr(profileDTO.getUserImageOriginName())
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
                .role(UserRole.USER)
                .phone(joinDTO.getPhone())
                .profileImageUrl(profileDTO.getUserImageOriginName())
                .verifyStatus("Y")
                .withdrawStatus("N")
                .enrollDate(LocalDateTime.now())
                .build();

        userProfile.setRefUserCode(user); // userProfile에서 refUserCode 설정
        user.setUserProfile(userProfile); // user에서 userProfile 설정

        user.passwordEncode(passwordEncoder);

        User joinUser = userRepository.save(user);
        return joinUser;
    }

    private void saveProfileImage(MultipartFile file, String originalFilename, String thumbnailFilename) throws IOException {
        // 파일 업로드 경로 설정
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        File uploadDir = new File(uploadPath.toString());
        if(!uploadDir.exists()){
            uploadDir.mkdirs();
        }

        // 파일 저장 경로 설정
        Path originalFilePath = uploadPath.resolve(originalFilename).normalize();
        file.transferTo(originalFilePath.toFile());

        File thumbnailFile = new File(uploadPath.resolve(thumbnailFilename).toUri());

        // Thumbnails 라이브러리를 사용하여 썸네일 생성 및 저장
        Thumbnails.of(originalFilePath.toFile())
                .size(100, 100)
                .toFile(thumbnailFilename);
    }

    public User login(LoginDTO loginDTO) {
        return null;
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
