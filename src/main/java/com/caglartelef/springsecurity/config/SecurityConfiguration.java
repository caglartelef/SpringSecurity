package com.caglartelef.springsecurity.config;

import com.caglartelef.springsecurity.properties.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private ApplicationProperties properties;

    /**
     * This method checks the access information of users.
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/admin/login/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/secAdmin/login/**").hasRole("SECADMIN")
                .antMatchers(HttpMethod.GET, "/_monitoring/health/**").permitAll()
                .anyRequest().denyAll()
                .and()
                .csrf().disable()
                .formLogin().disable();
    }

    /**
     * This method provide ignore to swagger configuration.
     * */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    /**
     * This method allows users to login the system.
     * */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> builder = auth.inMemoryAuthentication();
        Map<String, ApplicationProperties.User> users = properties.getUsers();
        users.forEach((username, user) -> {
            builder.withUser(username).password(user.getPassword()).authorities(user.getConfig().getRoles());
        });
    }
}
