package com.example.SoftwareProject.security;


import com.example.SoftwareProject.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    private static final String SECRETY_KEY = "NMQ4NSl604sgyHJj1qwEkR3ycUeR4uUAt7WJraD7EN3O9DVM4yyYuHxMEbSF4XXyYJkal13eqgB0F7A8JPctFuna59f5";

    public String create(UserEntity userEntity){   //create는 JWT라이브러리를 이용하여 JWT토큰을 생성한다



        //기한은 지금부터 1일로 설정
        Date expiryDate = Date.from(
                Instant.now().plus(1, ChronoUnit.DAYS));

        //Jwt Token 생성
        return Jwts.builder()
                //Header 에 들어갈 내용 및 서명을 하기 위한 Secretkey
                .signWith(SignatureAlgorithm.HS512, SECRETY_KEY)
                //payload에 들어갈 내용
                .setSubject(userEntity.getId())  //sub
                .setIssuer("Software project app")  //iss
                .setIssuedAt(new Date())  //iat
                .setExpiration(expiryDate)   //exp
                .compact();
        /*
        byte[] keyBytes = SECRETY_KEY.getBytes();
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                // header에 들어갈 내용 및 서명을 하기 위한 SECRET_KEY
                .signWith(key, SignatureAlgorithm.HS512)
                // payload에 들어갈 내용
                .setSubject(userEntity.getId()) // sub
                .setIssuer("demo app") // iss
                .setIssuedAt(new Date()) // iat
                .setExpiration(expiryDate) // exp
                .compact();

         */
    }

    public String validateAndGetUserId(String token){       //토큰을 디코딩 및 파싱하고 토큰의 위조여부를 확인한다 이후 우리가 원하는
                                                            //subject, 즉 사용자의 아이디를 리턴한다
        //paraseClaimsJws 메서드가 Base64로 디코딩 및 파싱
        //헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해 서명한 후 token의 서명과 비교
        //위조되지 않았다면 페이로드(Claims) 리턴, 위조라면 예외를 날림
        //그중 우리는 userId가 필요하므로 getBody를 부른다

        Claims claims = Jwts.parser()
                .setSigningKey(SECRETY_KEY)
                .parseClaimsJws(token)
                .getBody();   //Body부분을 가져오고

        return claims.getSubject(); //그중 subject를 가져온다 (사용자의 아이디)
    }



}
