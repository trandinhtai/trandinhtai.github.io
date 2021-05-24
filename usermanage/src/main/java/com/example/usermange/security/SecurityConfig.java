/*
package com.example.usermange.security;

import com.example.usermange.service.CustomUserDetailService;
import com.example.usermange.service.MyAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private ApiAuthorizationFilter apiAuthorizationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/register", "/authenticate").permitAll()
        */
/*        .antMatchers("/hello").permitAll()
                .antMatchers("/profile").hasRole("ADMIN")*//*

                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                */
/*.formLogin()
                .loginProcessingUrl("/login")
                .permitAll()
                .and()*//*

                .logout()
                .logoutUrl("/logout-test")
                .logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)))
                */
/*.logoutSuccessUrl("/login.html")*//*

                .and()
             */
/*   .exceptionHandling()
                .and()
                .httpBasic()
                .authenticationEntryPoint(myAuthenticationEntryPoint);*//*

                */
/*.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .and()
                .httpBasic()
                .authenticationEntryPoint(myAuthenticationEntryPoint);*//*

                .addFilterBefore(apiAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
*/
