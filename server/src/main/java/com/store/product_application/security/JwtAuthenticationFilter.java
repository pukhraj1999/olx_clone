package com.store.product_application.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.store.product_application.service.JwtService;

import lombok.RequiredArgsConstructor;

/*
 * @author Pukhraj Singh
 * @version 1.0
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest req,
            @NotNull HttpServletResponse res,
            @NotNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = req.getHeader("authorization");
        final String jwt;
        final String email;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(req, res); // passing it to the next filter
            return;
        }

        // fetching jwt from header
        jwt = authHeader.substring(7); // because token is after 7 characters
        email = jwtService.extractEmail(jwt); // extract it from jwt token

        // checking user is already authenticated or not
        if (email != null
                && SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(req));
                // updating security context holder
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(req, res);
    }

}
