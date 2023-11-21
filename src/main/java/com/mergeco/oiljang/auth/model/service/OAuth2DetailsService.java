package com.mergeco.oiljang.auth.model.service;

import com.mergeco.oiljang.auth.model.DetailsUser;
import com.mergeco.oiljang.common.UserRole;
import com.mergeco.oiljang.user.entity.EnrollType;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.entity.UserProfile;
import com.mergeco.oiljang.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class OAuth2DetailsService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public OAuth2DetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByOAuth2Info(String sub) {
        User user = userRepository.findByUserId(sub);

        log.debug("OAuth2Info: {}",user);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with ID: " + sub);
        }

        return new DetailsUser(user);
    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        return super.loadUser(userRequest);
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


