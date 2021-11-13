package com.projects.pmtool.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.projects.pmtool.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import static com.projects.pmtool.security.SecurityConstants.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTTokenProvider {

	//create token
	public String generateToken(Authentication authentication) {
		User user = (User)authentication.getPrincipal();
		Date now = new Date(System.currentTimeMillis());
		
		Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
		String userId= Long.toString(user.getId());
		
		Map<String,Object> claims= new HashMap<>();
		claims.put("id", Long.toString(user.getId()));
		claims.put("username", user.getUsername());
		claims.put("fullname", user.getFullname());
		
		return Jwts.builder()
				.setSubject(userId)
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS256, SECRET)
				.compact();
	}
	
	//validate token
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
			return true;
		}
		catch(SignatureException ex) {
			System.out.println("Invalid JWT signature");
		}
		catch (MalformedJwtException ex) {
			System.out.println("Invalid JWT token");
		}
		catch(ExpiredJwtException ex) {
			System.out.println("JWT Token expired");
		}
		catch (UnsupportedJwtException ex) {
			System.out.println("jwt claims string is empty");
		}
		return false;
	}
	
	
	//get userId from token
	
	public Long getUserIdFromJwt(String token) {
		Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
		String id = (String)claims.get("id");
		
		return Long.parseLong(id);
	}
}
