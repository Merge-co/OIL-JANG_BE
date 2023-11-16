package com.mergeco.oiljang.auth.handler;

import com.mergeco.oiljang.auth.model.DetailsUser;
import com.mergeco.oiljang.auth.model.dto.TokenDTO;
import com.mergeco.oiljang.auth.model.service.DetailsService;
import com.mergeco.oiljang.common.AuthConstants;
import com.mergeco.oiljang.common.utils.TokenUtils;
import com.mergeco.oiljang.user.entity.User;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenProvider {

    private final DetailsService detailsService;

    private ModelMapper modelMapper;


    public TokenProvider(DetailsService detailsService, ModelMapper modelMapper) {
        this.detailsService = detailsService;
        this.modelMapper = modelMapper;
    }

    public TokenDTO generateTokenDTO(User user){

        log.debug("generateTokenDTO user :{}", user);


        DetailsUser userDTO = modelMapper.map(user, DetailsUser.class);

        log.debug("generateTokenDTO userDTO :{}", userDTO);

        return new TokenDTO(AuthConstants.TOKEN_TYPE, user.getName(), TokenUtils.generateJwtToken(userDTO));
    }


    public String getUserId(String token){

        Claims claims = TokenUtils.getClaimsFromToken(token);
        String userId = claims.getSubject();
        log.debug("Extracted user ID '{}' from token", userId);

        return userId;
    }

    public Authentication getAuthentication(String token, UserDetailsService userDetailsService){
        Claims claims = TokenUtils.getClaimsFromToken(token);

        if (claims.get("Role") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        String userId = this.getUserId(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        log.info("Created Authentication object for user: {}", userId);
        return authentication;
    }

}
