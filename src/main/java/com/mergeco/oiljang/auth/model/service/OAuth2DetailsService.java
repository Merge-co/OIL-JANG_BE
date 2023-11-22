package com.mergeco.oiljang.auth.model.service;

import com.mergeco.oiljang.auth.model.DetailsUser;
import com.mergeco.oiljang.user.entity.EnrollType;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.model.service.OAuth2Service;
import com.mergeco.oiljang.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
@Slf4j
public class OAuth2DetailsService extends DefaultOAuth2UserService {


    private final UserRepository userRepository;

    private final OAuth2Service oAuth2Service;

    public OAuth2DetailsService(UserRepository userRepository, OAuth2Service oAuth2Service) {
        this.userRepository = userRepository;
        this.oAuth2Service = oAuth2Service;
    }

    public UserDetails loadUserByOAuth2Info(String userId) {

        log.debug("loadUserByOAuth2Info sub : {}",userId);

        User user = userRepository.findByUserId(userId);

        log.debug("OAuth2Info: {}",user);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with ID: " + userId);
        }

        return new DetailsUser(user);
    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.debug("OAuth2Info: {}",userRequest);
        log.debug("OAuth2Info ClientRegistration: {}",userRequest.getClientRegistration());
        log.debug("OAuth2Info UserNameAttributeName: {}",userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName());


        try {
            OAuth2User user = super.loadUser(userRequest);
            log.debug("OAuth2User user: {}", user);
            log.debug("Exiting loadUser method");


            if("google".equals(userRequest.getClientRegistration().getRegistrationId())){
                oAuth2Service.joinFromGoogle(user);
            } else if ("naver".equals(userRequest.getClientRegistration().getRegistrationId())) {
                oAuth2Service.joinFromNaver(user);
            }else {
                log.debug("해당되는 소셜 회원가입이 아닙니다.");
            }

            log.debug("OAuth2User details: {}", user.getAttributes());

            return user;
        }catch (OAuth2AuthenticationException e){
            log.debug("OAuth2AuthenticationException : {} ",e);
            throw new OAuth2AuthenticationException("OAuth2AuthenticationException");
        }

    }

    public EnrollType getEnrollType(String userId) {
        if (userId.startsWith("oauth2_google")) {
            return EnrollType.GOOGLE;
        } else if (userId.startsWith("oauth2_naver")) {
            return EnrollType.NAVER;
        } else {
            return EnrollType.NORMAL;
        }
    }



}


