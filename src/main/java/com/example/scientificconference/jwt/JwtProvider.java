package com.example.activeuser.jwt;

import com.example.activeuser.dto.SendMailDto;
import com.example.activeuser.dto.SignupDto;
import com.example.activeuser.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.mail.MessagingException;
import jakarta.mail.Transport;
import jakarta.transaction.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.crypto.SecretKey;
import javax.xml.stream.events.Characters;
import java.util.Date;
import java.util.Random;
import java.util.StringJoiner;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final JavaMailSender mailSender;
    @Value("${secret.key}")
    private String secretKey;

    @SneakyThrows
    public String generate(User user)  {
        Integer password = new Random().nextInt(100000, 1000000);
        sendMail(password.toString());
        
        StringJoiner roles = new StringJoiner(",");
        user.getRoles().forEach(role -> roles.add(role.getName().toUpperCase()));
        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 600000 * 1000))
                .claim("roles", roles.toString())
                .claim("password",password.toString())
                .signWith(key())
                .compact();
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public Claims parse(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Async
    public void sendMail(String password) throws MessagingException {
        SendMailDto dto = SendMailDto.builder()
                .to("admin@example.com")
                .content(password)
                .subject("Password").build();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
       mailMessage.setText(dto.getContent());
       mailMessage.setSubject(dto.getSubject());
       mailMessage.setSentDate(new Date());                //for mailtrap
       mailMessage.setTo(dto.getTo());
       mailSender.send(mailMessage);
    }

    public boolean validate(final String token) {
        try {
            Claims claims = parse(token);
            if (claims.getExpiration().after(new Date())) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
