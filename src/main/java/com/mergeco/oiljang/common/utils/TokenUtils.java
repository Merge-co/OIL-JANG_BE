package com.mergeco.oiljang.common.utils;

/*import com.auth0.jwt.JWT;
import com.mergeco.oiljang.common.AuthConstants;*/
import com.mergeco.oiljang.user.entity.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
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
            e.printStackTrace();
            return false;

        } catch (JwtException e) {
            e.printStackTrace();
            return false;

        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecretKey))
                .parseClaimsJws(token).getBody();
    }


    public static String generateJwtToken(User user) {
        Date expireTime = new Date(System.currentTimeMillis() + tokenValidateTime);
        JwtBuilder builder = Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(user))
                .setSubject("oiljang token : " + user.getUserCode())
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


    private static Map<String, Object> createClaims(User user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("userName", user.getName());
        claims.put("Role", user.getRole());
        claims.put("userEmail", user.getEmail());

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
