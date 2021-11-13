package com.projects.pmtool.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.projects.pmtool.domain.User;
import com.projects.pmtool.services.CustomUserDetailsService;

import static com.projects.pmtool.security.SecurityConstants.*;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JWTTokenProvider jwtTokenProvider;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String jwt = getJwtFromRequest(request);

			if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
				Long userId = jwtTokenProvider.getUserIdFromJwt(jwt);
				User userDetails = customUserDetailsService.loadUserById(userId);

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, java.util.Collections.emptyList());
				
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			logger.error("can not set user authentication in security context",e);
		}

		filterChain.doFilter(request, response);
	}

	private String getJwtFromRequest(HttpServletRequest req) {
		String bearerToken = req.getHeader(HEADER_STRING);

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
			return bearerToken.substring(7,bearerToken.length());
		}

		return null;
	}

}
