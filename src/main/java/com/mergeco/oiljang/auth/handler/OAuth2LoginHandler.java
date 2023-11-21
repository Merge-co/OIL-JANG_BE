package com.mergeco.oiljang.auth.handler;

import com.mergeco.oiljang.auth.model.DetailsUser;
import com.mergeco.oiljang.auth.model.dto.OAuth2LoginDTO;
import com.mergeco.oiljang.auth.model.dto.OAuth2LoginResponseDTO;
import com.mergeco.oiljang.auth.model.service.DetailsService;
import com.mergeco.oiljang.user.entity.EnrollType;
import com.mergeco.oiljang.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
@Component
@Slf4j
public class OAuth2LoginHandler {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private DetailsService detailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public OAuth2LoginResponseDTO authenticateOAuth2User(OAuth2LoginDTO oAuth2LoginDTO){

        DetailsUser detailsUser = (DetailsUser) buildUserDetailsFromOAuth2Info(oAuth2LoginDTO);

        String token = tokenProvider.generateTokenDTO(detailsUser.getUser()).getAccessToken();

        EnrollType enrollType = detailsUser.getUser().getEnrollType();

        switch (enrollType) {
            case NORMAL:
                log.info("일반 가입 사용자");
                break;
            case GOOGLE:
                log.info("Google 가입 사용자");
                break;
            case NAVER:
                log.info("Naver 가입 사용자");
                break;
            default:
                log.warn("알 수 없는 가입 종류");
        }

        return OAuth2LoginResponseDTO.builder()
                .userDetails(detailsUser)
                .accessToken(token)
                .build();
    }

    private UserDetails buildUserDetailsFromOAuth2Info(OAuth2LoginDTO oAuth2LoginDTO) {

        User user = (User) detailsService.loadUserByUsername(oAuth2LoginDTO.getUserId());

        return new DetailsUser(user);

    }

}
