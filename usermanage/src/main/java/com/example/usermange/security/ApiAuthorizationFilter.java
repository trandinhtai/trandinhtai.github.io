package com.example.usermange.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Security;

@Component
public class ApiAuthorizationFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // Lấy thông tin session
        String userEmail = (String) request.getSession().getAttribute("VIETTEL_SESSION");

        // Tạo object Authentication
        UsernamePasswordAuthenticationToken authentication = getAuthentication(userEmail);
        if (authentication == null) {
            chain.doFilter(request, response);
        }
        // Xác thực thành công, lưu object Authentication vào SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
    private UsernamePasswordAuthenticationToken getAuthentication(String userEmail) {
        if (userEmail == null) {
            return null;
        }
        UserDetails user = userDetailsService.loadUserByUsername(userEmail);
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}
