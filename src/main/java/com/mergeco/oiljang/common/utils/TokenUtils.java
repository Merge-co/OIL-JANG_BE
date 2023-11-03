/*
package com.mergeco.oiljang.common.utils;

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

*/
/*
 * 토큰을 관리하기 위한 uitls 모음 클래스
 * *//*

@Component
public class TokenUtils {

    private static String jwtSecretKey;
    private static Long tokenValidateTime;


    @Value("${jwt.key}")
    public void setJwtSecretKey(String jwtSecretKey) {
        TokenUtils.jwtSecretKey = jwtSecretKey;
    }

    @Value("${jwt.time}")
    public void setTokenValidateTime(Long tokenValidateTime) {
        TokenUtils.tokenValidateTime = tokenValidateTime;
    }

    */
/* header의 token을 분리하는 메소드 *//*

    public static String splitHeader(String header) {
        if(!header.equals("")) {
            return header.split(" ")[1];
        } else {
            return null;
        }
    }

    */
/* 토큰이 유효한지 확인하는 메소드 *//*

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

    */
/* 토큰을 복호화 하는 메소드 *//*

    public static Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecretKey))
                .parseClaimsJws(token).getBody();
    }

    */
/* 토큰을 생성하는 메소드 *//*

    public static String generateJwtToken(User user) {
        Date expireTime = new Date(System.currentTimeMillis() + tokenValidateTime);
        JwtBuilder builder = Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(user))
                .setSubject("oiljang token : " + user.getUserId())
                .signWith(SignatureAlgorithm.HS256, createSignature())
                .setExpiration(expireTime);
        return builder.compact();
    }

    */
/* 토큰의 header를 설정하는 메소드 *//*

    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("type", "jwt");
        header.put("alg", "HS256");
        header.put("date", System.currentTimeMillis());

        return header;
    }

    */
/* 사용자 정보를 기반으로 클레임을 생성하는 메소드 *//*

    private static Map<String, Object> createClaims(User user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("userName", user.getName());
        claims.put("Role", user.getRole());
        claims.put("userEmail", user.getEmail());

        return claims;
    }

    */
/* JWT 서명을 발급하는 메소드 *//*

    private static Key createSignature() {
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(jwtSecretKey);
        return new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());
    }
}
*/
