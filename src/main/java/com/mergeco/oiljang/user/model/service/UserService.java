package com.mergeco.oiljang.user.model.service;

import com.mergeco.oiljang.auth.model.dto.GoogleInfoResponse;
import com.mergeco.oiljang.auth.model.dto.GoogleRequest;
import com.mergeco.oiljang.auth.model.dto.GoogleResponse;
import com.mergeco.oiljang.auth.model.dto.JoinDTO;
import com.mergeco.oiljang.common.UserRole;
import com.mergeco.oiljang.user.entity.EnrollType;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

   /* @Value("${spring.google.client_id}")
    String clientId;

    @Value("${spring.google.client_secret}")
    String clientSecret;*/

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

    public JoinDTO join(JoinDTO joinDTO) throws Exception{
        User user = User.builder()
                .nickname(joinDTO.getNickname())
                .name(joinDTO.getName())
                .id(joinDTO.getId())
                .pwd(joinDTO.getPwd())
                .birthDate(joinDTO.getBirthDate())
                .gender(joinDTO.getGender())
                .enrollType(EnrollType.NORMAL) // 임의로 설정
                .role(UserRole.USER)
                .phone(joinDTO.getPhone())
                .verifyStatus("Y")
                .withdrawStatus("N")
                .build();

        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
        return joinDTO;
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
