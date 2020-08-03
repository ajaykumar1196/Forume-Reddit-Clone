package com.example.forume.security;

import com.example.forume.config.JWTConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JWTConfig jwtConfig;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JWTConfig jwtConfig, @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.jwtConfig = jwtConfig;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = getJwtFromRequest(request);

        try {

           if(StringUtils.hasText(jwt)){

               Jws<Claims> claimsJws = Jwts.parser()
                       .setSigningKey(jwtConfig.secretKey())
                       .parseClaimsJws(jwt);

               Claims body = claimsJws.getBody();

               String username = body.getSubject();

               UserDetails userDetails = userDetailsService.loadUserByUsername(username);

               UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                       null,
                       userDetails.getAuthorities());

               authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authentication);
           }

        } catch (JwtException e) {
            throw new IllegalStateException(String.format("Token %s cannot be trusted", jwt));
        }

        filterChain.doFilter(request, response);

    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtConfig.getAuthorizationHeader());

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return bearerToken;
    }
}
