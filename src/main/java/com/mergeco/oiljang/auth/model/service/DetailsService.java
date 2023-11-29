package com.mergeco.oiljang.auth.model.service;

import com.mergeco.oiljang.auth.model.DetailsUser;
import com.mergeco.oiljang.user.entity.EnrollType;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DetailsService implements UserDetailsService {

    private final UserRepository repository;

    private final OAuth2DetailsService oAuth2DetailsService;

    private final ModelMapper modelMapper;

    public DetailsService(UserRepository repository, OAuth2DetailsService oAuth2DetailsService, ModelMapper modelMapper) {
        this.repository = repository;
        this.oAuth2DetailsService = oAuth2DetailsService;
        this.modelMapper = modelMapper;
    }


    @Transactional
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        log.debug("loadUserByUsername start ~~~~");

        try {
            log.debug("loadUserByUsername check start ~~~~");


            EnrollType enrollType = oAuth2DetailsService.getEnrollType(userId);

            if (enrollType != EnrollType.NORMAL) {
                return oAuth2DetailsService.loadUserByOAuth2Info(userId);
            }

            // OAuth2로 등록되지 않은 일반 사용자인 경우
            User user = repository.findByUserId(userId);

            System.out.println("User Info: " + user);

            if (user == null) {
                throw new UsernameNotFoundException("User not found with ID: " + userId);
            }

            DetailsUser userDTO = modelMapper.map(user, DetailsUser.class);

            System.out.println("User DTO: " + userDTO);

            // 권한 정보 매핑
            List<GrantedAuthority> authorities = user.getRoleList().stream()
                    .map(role -> new SimpleGrantedAuthority(role))
                    .collect(Collectors.toList());
            userDTO.setAuthorities(authorities);

            System.out.println("Final User DTO: " + userDTO);


            return userDTO;
        }catch (NumberFormatException e){
            throw new UsernameNotFoundException("Invalid userCode format: " + userId);
        }
    }

}
