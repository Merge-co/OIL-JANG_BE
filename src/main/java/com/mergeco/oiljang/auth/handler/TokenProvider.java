package com.mergeco.oiljang.auth.handler;

import com.mergeco.oiljang.auth.model.DetailsUser;
import com.mergeco.oiljang.auth.model.dto.TokenDTO;
import com.mergeco.oiljang.auth.model.service.DetailsService;
import com.mergeco.oiljang.common.AuthConstants;
import com.mergeco.oiljang.common.utils.TokenUtils;
import com.mergeco.oiljang.user.entity.User;
import com.mergeco.oiljang.user.model.dto.UserDTO;
import io.jsonwebtoken.Claims;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    private final DetailsService detailsService;

    private ModelMapper modelMapper;


    public TokenProvider(DetailsService detailsService, ModelMapper modelMapper) {
        this.detailsService = detailsService;
        this.modelMapper = modelMapper;
    }

    public TokenDTO generateTokenDTO(User user){
        DetailsUser userDTO = modelMapper.map(user, DetailsUser.class);

        return new TokenDTO(AuthConstants.TOKEN_TYPE,
                user.getName(),
                TokenUtils.generateJwtToken(userDTO));
    }


    public String getUserId(String token){return TokenUtils.getClaimsFromToken(token).getSubject();}

    public Authentication getAuthentication(String token){
        Claims claims = TokenUtils.getClaimsFromToken(token);

        if(claims.get("users") == null){
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        UserDetails userDetails = detailsService.loadUserByUsername(this.getUserId(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
