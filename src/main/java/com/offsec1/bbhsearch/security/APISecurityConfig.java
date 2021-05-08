package com.offsec1.bbhsearch.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class APISecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${app.http.api-key-header-name}")
    private String principalRequestHeader;

    @Value("${app.http.api-key}")
    private String principalRequestValue;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        APIKeyAuthFilter filter = new APIKeyAuthFilter(principalRequestHeader);
        filter.setAuthenticationManager(authentication -> {
            String principal = (String) authentication.getPrincipal();
            if (!principalRequestValue.equals(principal)) {
                throw new BadCredentialsException("API key not found or invalid.");
            }
            authentication.setAuthenticated(true);
            return authentication;
        });

        http
                .csrf().disable().cors().and()
                .antMatcher("/**")
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilter(filter)
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }

}