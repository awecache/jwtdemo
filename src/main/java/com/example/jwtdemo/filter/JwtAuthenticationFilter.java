package com.example.jwtdemo.filter;

import com.example.jwtdemo.service.CustomUserDetailsService;
import com.example.jwtdemo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");
        String username = null;
        String token = null;

        //check if token exist
        if (Objects.isNull(bearerToken)) {
            System.out.println("Auth token is null");
            filterChain.doFilter(request, response);
            return;
        }

        //check if token is valid
        if (bearerToken.startsWith("Bearer")) {
            //extract jwt token
            token = bearerToken.substring(7);

            try {
                username = jwtUtil.extractUsername(token);

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                //security check
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);

                } else {
                    System.out.println("Invalid token");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid Bearer Token Format");
        }

        // if all go well, forward the filter request to the requested endpoint
        filterChain.doFilter(request, response);

    }

}
