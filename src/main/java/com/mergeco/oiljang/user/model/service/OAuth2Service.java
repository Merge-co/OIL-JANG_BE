package com.mergeco.oiljang.user.model.service;

import com.mergeco.oiljang.auth.model.DetailsUser;
import com.mergeco.oiljang.common.UserRole;
import com.mergeco.oiljang.user.entity.EnrollType;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.entity.UserProfile;
import com.mergeco.oiljang.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OAuth2Service {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public OAuth2Service(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
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

        String email = oauth2User.getAttribute("email");

        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()){
            return existingUser.get();
        }

        User newUser = buildUserFromOAuth2User(oauth2User);
        return userRepository.save(newUser);

    }

    private User buildUserFromOAuth2User(OAuth2User oauth2User) {

        String userId = oauth2User.getAttribute("sub");
        String name = oauth2User.getAttribute("name");
        String email = oauth2User.getAttribute("email");

        User newUser = User.builder()
                .id(userId)
                .name(name)
                .nickname(userId)
                .email(email)
                .profileImageUrl("C:\\Users\\User\\Desktop\\dir\\upload\\image.jpg")
                .userProfile(UserProfile.builder().userImageName("기본").userImageOriginName("기본").userImageOriginAddr("C:\\Users\\User\\Desktop\\dir\\upload\\image.jpg").userImageThumbAddr("C:\\Users\\User\\Desktop\\dir\\upload\\image.jpg").build())
                .role(UserRole.ROLE_USER)
                .birthDate("소셜 로그인")
                .gender("알 수 없음")
                .phone("")
                .verifyStatus("Y")
                .withdrawStatus("N")
                .enrollDate(LocalDateTime.now())
                .enrollType(EnrollType.GOOGLE)
                .build();

        return newUser;
    }
}
