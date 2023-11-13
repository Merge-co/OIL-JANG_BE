package com.mergeco.oiljang.user.model.service;

import com.mergeco.oiljang.auth.handler.CustomAuthenticationProvider;
import com.mergeco.oiljang.auth.handler.TokenProvider;
import com.mergeco.oiljang.auth.model.dto.*;
import com.mergeco.oiljang.common.UserRole;
import com.mergeco.oiljang.user.entity.EnrollType;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.entity.UserProfile;
import com.mergeco.oiljang.user.repository.UserProfileRepository;
import com.mergeco.oiljang.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
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


    private final UserRepository userRepository;

    private final CustomAuthenticationProvider authenticationProvider;

    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    public UserService(UserRepository userRepository, CustomAuthenticationProvider authenticationProvider, UserProfileRepository userProfileRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.authenticationProvider = authenticationProvider;
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public User join(JoinDTO joinDTO,MultipartFile imageFile) throws IOException {

/*        //중복 유저 존재 여부 체크
        Optional<User> existingUser = userRepository.findById(joinDTO.getId());
        if (existingUser.isPresent()) {
            // 중복 사용자 처리
            throw new UserException(UserErrorResult.DUPLICATED_MEMBER_REGISTER);
        }

        Optional<User> existingUserByNickname = userRepository.findByNickname(joinDTO.getNickname());
        if (existingUserByNickname.isPresent()) {
            // 중복 닉네임 처리
            throw new UserException(UserErrorResult.DUPLICATED_NICKNAME_REGISTER);
        }*/


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
                .role(UserRole.USER)
                .phone(joinDTO.getPhone())
                .verifyStatus("Y")
                .withdrawStatus("N")
                .email(joinDTO.getEmail())
                .enrollDate(LocalDateTime.now())
                .profileImageUrl(userProfileThumbPath)
                .build();

        user.setUserProfile(userProfile);
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

        if (userId != null){
            return false;
        }else
            return true;
    }


    public boolean checkUserNicknameExist(String nickname) {
        String userNickname = userRepository.checkUserNicknameExist(nickname);

        log.info(userNickname);
        System.out.println(userNickname);

        if (userNickname != null){
            return false;
        }else
            return true;
    }

    @Transactional
    public TokenDTO login(String id, String pwd) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(id, pwd);

        Authentication authentication
                = authenticationProvider.authenticate(authenticationToken);


        TokenDTO token = tokenProvider.generateTokenDTO((User) authentication.getPrincipal());

        return token;
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
