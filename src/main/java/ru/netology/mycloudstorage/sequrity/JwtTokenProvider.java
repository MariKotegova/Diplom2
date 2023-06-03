package ru.netology.mycloudstorage.sequrity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.netology.mycloudstorage.exeption.JwtAutentifExeption;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

//создаем токен
@Component
public class JwtTokenProvider {
    private final UserDetailsService userDetailsService;

    @Value("${jwt.token.secret}")// берем из проперти
    private String secret;

    @Value("${jwt.token.expired}")
    private long validitySeconds;

    @Value("${jwt.header}")// берем из проперти
    private String authorizationHeader;

    public JwtTokenProvider(@Qualifier("userDatailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    //зашифровываем ключ для безопасности
    @PostConstruct
    protected void init(){
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String username, String role){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);
        Date now = new Date();
        Date validiti = new Date(now.getTime() + validitySeconds + 1000);//переводим в секунды

return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validiti)
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();
    }

    //определяем хороший токен или плохой
    public boolean validateToken(String token){
       try {
           Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
           //верни что срок токена не истек
           return !claimsJws.getBody().getExpiration().before(new Date());
       }catch (JwtException | IllegalArgumentException e){
           throw new JwtAutentifExeption("токен не подходит ", HttpStatus.UNAUTHORIZED);
        }
    }

    //получить юзера из токена
    public String getUsername(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    // получить аутентификациюиз токена
    public Authentication getAuthentication(String token){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    //для контроллера
    public String resolveToken(HttpServletRequest request){
       return request.getHeader(authorizationHeader);
    }
}
