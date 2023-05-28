package com.example.demo.utils;

import com.example.demo.exceptions.AuthException;
import com.example.demo.exceptions.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    private int accessExpiredHour = 24 * 7;
    private int refreshExpiredHour = 24 * 30;

    private Map<String, Object> getHeader() {
        Map<String, Object> headers = new HashMap<>();

        headers.put("typ", "JWT");
        headers.put("alg","HS256");

        return headers;
    }

    private Map<String, Object> getPayload(Long userId) {
        Map<String, Object> payloads = new HashMap<String, Object>();
        payloads.put("id", userId);

        return payloads;
    }

    private Date getExpireDate(int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, hour);

        return calendar.getTime();
    }

    public String getAccessToken(Long userId) {

        Claims claims = Jwts.claims()
                .setIssuedAt(new Date())
                .setExpiration(getExpireDate(accessExpiredHour));

        claims.put("id", userId);

        String token = Jwts.builder().
                setHeader(getHeader()).
                setClaims(claims).
                signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes()).compact();

        return token;
    }

    public void validateToken(String token) throws Exception {
        if ( token == null) {
            throw new AuthException(ErrorCode.Token_Is_Null);
        } else if (!token.startsWith("Bearer ")){
            throw new AuthException(ErrorCode.Invalid_Token_Bearer);
        }
        token = token.substring(7); // "Bearer " 제거
        //System.out.println(token);
        try {
            Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e1){
            throw new AuthException(ErrorCode.Expired_Token);
        } catch(Throwable e2){
            throw new AuthException(ErrorCode.Invalid_Token);
        }
    }

    public Claims getClaims(String token) {
        token = token.substring(7); // "Bearer " 제거
        return Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token).getBody();
    }

    public Long getIdFromToken(String token) {
        return getClaims(token).get("id", Long.class);
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
