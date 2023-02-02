package com.kodzotech.fileupload.config;

import com.kodzotech.security.jwt.JwtAuthentificationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/content/**").permitAll()
                .and()
                .cors().and().csrf().disable();
        http.addFilterBefore(new JwtAuthentificationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}