package com.mergeco.oiljang.common.utils;

/*import com.auth0.jwt.JWT;
import com.mergeco.oiljang.common.AuthConstants;*/
import com.mergeco.oiljang.auth.model.DetailsUser;
import com.mergeco.oiljang.common.exception.TokenException;
import com.mergeco.oiljang.user.entity.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.*;

@Component
@Slf4j
public class TokenUtils {

    private static String jwtSecretKey;
    private static Long tokenValidateTime;

   /* @Value("${jwt.access.header}")
    private static String accessHeader;

    @Value("${jwt.refresh.expiration}")
    private static String refreshHeader;
*/

    @Value("${jwt.secretKey}")
    public void setJwtSecretKey(String jwtSecretKey) {
        TokenUtils.jwtSecretKey = jwtSecretKey;
    }

    @Value("${jwt.access.expiration}")
    public void setTokenValidateTime(Long tokenValidateTime) {
        TokenUtils.tokenValidateTime = tokenValidateTime;
    }



    public static String splitHeader(String header) {
        if(!header.equals("")) {
            return header.split(" ")[1];
        } else {
            return null;
        }
    }


    public static boolean isValidToken(String token) {

        try {
            Claims claims = getClaimsFromToken(token);
            return true;

        } catch(ExpiredJwtException e) {
            log.info("[TokenProvider] 만료된 JWT 토큰입니다.");
            throw new TokenException("만료된 JWT 토큰입니다.");

        } catch (UnsupportedJwtException e) {
            log.info("[TokenProvider] 지원되지 않는 JWT 토큰입니다.");
            throw new TokenException("지원되지 않는 JWT 토큰입니다.");

        } catch (IllegalArgumentException e) {
            log.info("[TokenProvider] JWT 토큰이 잘못되었습니다.");
            throw new TokenException("JWT 토큰이 잘못되었습니다.");
        } catch (SecurityException e){
            log.info("[TokenProvider] 잘못된 JWT 서명입니다.");
            throw new TokenException("잘못된 JWT 서명입니다.");
        }
    }


    public static Claims getClaimsFromToken(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecretKey))
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e){
            return e.getClaims(); // 토큰이 만료되어 예외가 발생하더라도 클레임 값들을 뽑을 수 있다.
        }
    }


    public static String generateJwtToken(DetailsUser user) {
        Date expireTime = new Date(System.currentTimeMillis() + tokenValidateTime);
        JwtBuilder builder = Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(user))
                .setSubject("oiljang token : " + user.getUser().getUserCode())
                .signWith(SignatureAlgorithm.HS256, createSignature())
                .setExpiration(expireTime);
        return builder.compact();
    }


    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("type", "jwt");
        header.put("alg", "HS256");
        header.put("date", System.currentTimeMillis());

        return header;
    }


    private static Map<String, Object> createClaims(DetailsUser user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("userName", user.getUser().getName());
        claims.put("Role", user.getUser().getRole());

        return claims;
    }


    private static Key createSignature() {
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey);
        return new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

/*    public String createRefreshToken() {
        Date now = new Date();
        return JWT.create()
                .withSubject(AuthConstants.REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .sign(Algorithm.HMAC512(secretKey));
    }*/

}
