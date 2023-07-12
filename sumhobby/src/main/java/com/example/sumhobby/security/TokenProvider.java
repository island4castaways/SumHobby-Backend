package com.example.sumhobby.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.sumhobby.entity.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenProvider {
	private static final String SECRET_KEY = "V8dx4gVYH9uXUa5y0LUdYlgkAt4CKEhmOkbPHetcriuYnNZXyfrncy6wgxEPxYoEsvhTrx8SeTkZWnCIv1L4JcrziXb9GY6M2cajlree7q6kYRL3dHQTGRU7EFsvWxmioByiNxBBIHdPSuZSYvkFFu0QgxvXh3VwLxaHmrBSedGGzeCTiCxFfsjtxrtSXWaHztmpyM6DdXzpB3sLFXMvECSem46BFUeNpdqG8ctyP7Zu9Iqon7fqdc3TSoPV2k8T5LfXDGCZIbTvWXkn20jS7lKYaVre7MPUifF27keq8AD3IzHc6vAWRAJCmE2idjER9MI8etq0wDBtXRM47ZH1U3gO4lNw4Yk0cgpD75nS3cleJIWSJeFCPjlelbOb3VpTnXYAgIMmn3Eh7ctSfSwgYwqzOARrfxopDnLgS55Sdc9NnKFtZnnSdKA0ALJSUxw0PSroMfsQiNvwLFtgMQ3rHFaw84NyaCbCe9NBXOcjYns4jrOoO5MTSJGZ2B2JrrM3\r\n";

	public String create(UserEntity userEntity) {
		// 기한 지금으로부터 1일
		Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

		// JWT Token 생성

		return Jwts.builder()
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.setSubject(userEntity.getUserTk())
				.setIssuer("sumhobby")
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)
				.compact();
	}

	public String validateAndGetUserTk(String token) {
		Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
}
