package com.mergeco.oiljang.auth.model.service;

import com.mergeco.oiljang.auth.model.DetailsUser;
import com.mergeco.oiljang.common.UserRole;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.repository.UserRepository;
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

@Service
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

        User user = repository.findByUserId(userId);

        DetailsUser userDTO = modelMapper.map(user, DetailsUser.class);

        List<GrantedAuthority> authorities = new ArrayList<>();

       /* if(userId == null || userId.equals("")){
            throw new AuthenticationServiceException(userId + "is EMPTY!");
        } else {
            return repository.findById(userId)
                    .map(data -> new DetailsUser(Optional.of(data)))
                    .orElseThrow(() -> new AuthenticationServiceException(userId));
        }*/

        userDTO.setAuthorities(authorities);

        return userDTO;
    }


}
