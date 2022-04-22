package com.employee.managament.empmemt.config;

import java.util.Date;

import com.employee.managament.empmemt.dto.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour

    @Value("${app.jwt.secret}")
    private String SECRET_KEY;

    public String generateAccessToken(Employee employee) {
        return Jwts.builder()
                .setSubject(String.format("%s,%s,%s", employee.getEmpNo(), employee.getEmpName(),employee.getRole() ))
                .setIssuer("Employee")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}