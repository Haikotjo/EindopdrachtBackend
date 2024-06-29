package nl.novi.eindopdrachtbackend.security;

import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public SecurityConfig(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder encoder, UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder)
                .and()
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService(this.userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .authorizeRequests()

                //                USER entity endpoints

                // Public access for creating new users and for authentication
                .requestMatchers(HttpMethod.POST, "/users/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth").permitAll()

                // Authenticated users can make GET, PUT, DELETE requests to /users
                .requestMatchers(HttpMethod.GET, "/users/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/users/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/users/**").authenticated()

                // ADMIN access only
                .requestMatchers(HttpMethod.GET, "/users/admin/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/users/admin/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/admin/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/search/by-email").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/search/by-role").hasAuthority("ADMIN")

                // USER and ADMIN access
                .requestMatchers(HttpMethod.GET, "/{userId}/address").hasAnyAuthority("ADMIN", "CUSTOMER")

                // OWNER and ADMIN access
                .requestMatchers(HttpMethod.GET, "/{userId}/restaurants").hasAnyAuthority("ADMIN", "OWNER")

//                .requestMatchers("/**").hasAnyAuthority("ADMIN")

                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtRequestFilter(jwtService, userDetailsService()), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
}
