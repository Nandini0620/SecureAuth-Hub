package com.example.securitydemo.security;

import com.example.securitydemo.service.UserLoginService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthFilter extends OncePerRequestFilter
{
    private JwtUtil jwtUtil;
    private UserLoginService userLoginService;

    public JwtAuthFilter(JwtUtil jwtUtil, UserLoginService userLoginService) {
        this.jwtUtil = jwtUtil;
        this.userLoginService = userLoginService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException
    {
        String token=request.getHeader("Authorization");
        if(token != null && !token.equals(""))
        {
            token=token.substring(7);
            if(token != null && !token.equals(""))
            {
                String username=jwtUtil.extractToken(token);

                if(username != null && !username.equals(""))
                {
                    UserDetails userDetails = userLoginService.loadUserByUsername(username);
                    if(userDetails!=null)
                    {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                                            new UsernamePasswordAuthenticationToken(
                                                    userDetails.getUsername(),userDetails.getPassword(),
                                                    userDetails.getAuthorities()
                                            );
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
