package com.library.config;

import com.library.security.CustomAuthenticationFilter;
import com.library.security.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //All Roles
    private static final String[] ALLOW_ALL_URLS = {
            "/api/login/**",
            "/api/token/refresh",
            "/api/download/{fileId}",
            "/api/menus/search?keyword=**",
            "/api/user/username/{username}",
            "/api/user/email/{email}",

            //Category
            "/api/categories",
            "/api/category/{id}",

            //Book
            "/api/books",
            "/api/book/{id}",
    };

    private static final String[] ALLOW_POST_ALL_URLS = {
            "api/user/resetPassword/**",
            "api/user/savePassword/**",
            "/api/user/save/**",
    };

    private static final String[] ALLOW_POST_USER_URLS = {

    };

    private static final String[] ALLOW_GET_USER_URLS = {
            "/api/user/**",

    };

    private static final String[] ALLOW_GET_MEMBER_URLS = {
            "/api/user/{id}",
            "/api/user/profile"
    };

    private static final String[] ALLOW_POST_MEMBER_URLS = {
            "/api/user/changePassword",
    };

    private static final String[] ALLOW_GET_ADMIN_URLS = {
            "/api/users",
            "/api/user/{id}",
            "/api/users/export-to-excel",
    };

    private static final String[] ALLOW_POST_ADMIN_URLS = {
            "api/role/addtouser/**",
            "api/book/save/**",
            "/api/upload",

            //Category
            "/api/categories/add/**",

            //Book
            "/api/books/add/**",
    };

    private static final String[] ALLOW_PUT_ADMIN_URLS = {
            "/api/books/save/{id}/**",
            "/api/categories/save/{id}/**",
    };

    private static final String[] ALLOW_DELETE_ADMIN_URLS = {
            "/api/books/delete/{id}/**",
            "/api/categories/delete/{id}/**"
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000/"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.cors().and();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers( ALLOW_ALL_URLS ).permitAll();
        http.authorizeRequests().antMatchers(POST, ALLOW_POST_ALL_URLS ).permitAll();
        http.authorizeRequests().antMatchers(POST, ALLOW_POST_USER_URLS ).hasAnyAuthority("USER");
        http.authorizeRequests().antMatchers(GET, ALLOW_GET_USER_URLS ).hasAnyAuthority("USER");
        http.authorizeRequests().antMatchers(POST, ALLOW_POST_MEMBER_URLS ).hasAnyAuthority("MEMBER");
        http.authorizeRequests().antMatchers(GET, ALLOW_GET_MEMBER_URLS ).hasAnyAuthority("MEMBER");
        http.authorizeRequests().antMatchers(GET, ALLOW_GET_ADMIN_URLS ).hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(POST, ALLOW_POST_ADMIN_URLS ).hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(PUT, ALLOW_PUT_ADMIN_URLS ).hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(DELETE, ALLOW_DELETE_ADMIN_URLS ).hasAnyAuthority("ADMIN");

        //http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

