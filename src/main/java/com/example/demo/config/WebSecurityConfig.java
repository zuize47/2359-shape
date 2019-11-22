package com.example.demo.config;

import com.example.demo.security.jwt.JwtAuthenticationConfigurer;
import com.example.demo.security.jwt.JwtAuthenticationFilter;
import com.example.demo.security.jwt.JwtAuthenticationTokenProvider;
import com.example.demo.security.UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig {


    final private UserDetailsService userDetailsService;
    final private JwtAuthenticationTokenProvider tokenProvider;

    public WebSecurityConfig(UserDetailsService userDetailsService,
                             JwtAuthenticationTokenProvider tokenProvider) {
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*"); // e.g. http://domain1.com
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }

    @Configuration
    class JwtSecurity extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(AuthenticationManagerBuilder auth)
                throws Exception {
            auth.userDetailsService(userDetailsService)
                    .passwordEncoder(passwordEncoder());
        }


        @Bean
        public JwtAuthenticationFilter jwtAuthenticationFilter() {
            return new JwtAuthenticationFilter(tokenProvider);
        }

        @Override
        public void configure(WebSecurity web) {
            web.ignoring()
                    .antMatchers(HttpMethod.OPTIONS, "/**")

                    // allow anonymous resource requests
                    .antMatchers(
                            "/",
                            "/swagger-ui.html",
                            "/api/user/register",
                            "/js/templates/**",
                            "/templates/**",
                            "/components/**",
                            "/*.html",
                            "/favicon.ico",
                            "/**/*.html",
                            "/**/*.css",
                            "/**/*.js",
                            "/h2-console/**"
                    );
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http
                    // we don't need CSRF because our token is invulnerable
                    .csrf().disable()

//                    .addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)


                    // enable h2-console
//                    .and()
                    .headers()
                    .frameOptions()
                    .sameOrigin()

                    // create no session
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()

                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/api/auth/login")
                    .permitAll()

//                    .antMatchers("/",
//                            "/css/**",
//                            "/js/**")
//                    .permitAll()

                    .antMatchers("/api/admin").hasAuthority("ROLE_ADMIN")
                    .antMatchers("/api/shape").hasAuthority("ROLE_USER")
                    .antMatchers("/api/**").authenticated()
                    .and()
                    .apply(securityConfigurerAdapter())
                    .and()
                    .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        }
    }

    private JwtAuthenticationConfigurer securityConfigurerAdapter() {
        return new JwtAuthenticationConfigurer(tokenProvider);
    }
}