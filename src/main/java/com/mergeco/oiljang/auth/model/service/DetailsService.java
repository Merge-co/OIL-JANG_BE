package com.mergeco.oiljang.auth.model.service;

import com.mergeco.oiljang.auth.model.DetailsUser;
import com.mergeco.oiljang.common.UserRole;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DetailsService implements UserDetailsService {

    private final UserRepository repository;

    private final ModelMapper modelMapper;

    public DetailsService(UserRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }


    @Transactional
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        try {

            User user = repository.findByUserId(userId);

            System.out.println("User Info: " + user);

            if (user == null) {
                throw new UsernameNotFoundException("User not found with ID: " + userId);
            }

            DetailsUser userDTO = modelMapper.map(user, DetailsUser.class);

            System.out.println("User DTO: " + userDTO);


            List<GrantedAuthority> authorities = user.getRoleList().stream()
                    .map(role -> new SimpleGrantedAuthority(role))
                    .collect(Collectors.toList());
            userDTO.setAuthorities(authorities);

       /* if(userId == null || userId.equals("")){
            throw new AuthenticationServiceException(userId + "is EMPTY!");
        } else {
            return repository.findById(userId)
                    .map(data -> new DetailsUser(Optional.of(data)))
                    .orElseThrow(() -> new AuthenticationServiceException(userId));
        }*/

            userDTO.setAuthorities(authorities);

            System.out.println("Final User DTO: " + userDTO);


            return userDTO;
        }catch (NumberFormatException e){
            throw new UsernameNotFoundException("Invalid userCode format: " + userId);
        }
    }


}
