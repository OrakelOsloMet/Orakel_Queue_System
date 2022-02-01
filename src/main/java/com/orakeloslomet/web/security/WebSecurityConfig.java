package com.orakeloslomet.web.security;

import com.orakeloslomet.services.authentication.UserDetailsServiceImpl;
import com.orakeloslomet.utilities.ProfileManager;
import com.orakeloslomet.web.security.jwt.AuthEntryPointJwt;
import com.orakeloslomet.web.security.jwt.AuthTokenFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:41
 */

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntryPointJwt unauthorizedHandler;
    private final ProfileManager profileManager;

    public WebSecurityConfig(final UserDetailsServiceImpl userDetailsService, final AuthEntryPointJwt unauthorizedHandler,
                             final ProfileManager profileManager) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.profileManager = profileManager;
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    public void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {

        if (profileManager.isProduction()) {
            httpSecurity.requiresChannel()
                    .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                    .requiresSecure();
        }

        httpSecurity.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/queue/**").permitAll()
                .antMatchers("/api/queue/**/**").permitAll()
                .antMatchers("/api/subjects/**").permitAll()
                .antMatchers("/api/resources/**/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated();

        httpSecurity.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.headers().frameOptions().sameOrigin();
    }
}