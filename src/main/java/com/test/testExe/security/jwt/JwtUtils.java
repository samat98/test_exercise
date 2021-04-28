package com.test.testExe.security.jwt;

import com.test.testExe.models.User;
import com.test.testExe.security.TokenDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwtSecretKey}")
    private String jwtSecretKey;

    @Value("${jwtExpirationsMs}")
    private long jwtExpirationMs;

    @Value("${jwtType}")
    private String jwtType;

    public String generateJwtToken(Authentication authentication) {
        User userPrincipal = (User)authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }

    public String generateJwtTokenOAuth(String id) {

        return Jwts.builder()
                .setSubject(id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) throws Exception {
        try {
            Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(authToken);
            return true;
        }catch (Exception e) {
            throw new Exception("Invalid token " + e.getMessage());
        }
    }

    public TokenDto getTokenDto (Authentication authentication) {
        TokenDto tokenDto = new TokenDto();
        tokenDto.setJwtToken(generateJwtToken(authentication));
        tokenDto.setJwtType(jwtType);

        return tokenDto;
    }

    public String generateRegJwtToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }
}
