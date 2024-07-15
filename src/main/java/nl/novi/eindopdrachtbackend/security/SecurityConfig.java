package nl.novi.eindopdrachtbackend.security;

import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public SecurityConfig(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
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
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz

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


                                //                Notification entity endpoints

                                .requestMatchers(HttpMethod.GET, "/notifications/**").hasAnyAuthority("ADMIN", "OWNER")
                                .requestMatchers(HttpMethod.POST, "/notifications/**").hasAnyAuthority("ADMIN", "OWNER")
                                .requestMatchers(HttpMethod.PUT, "/notifications/**").hasAnyAuthority("ADMIN", "OWNER")
                                .requestMatchers(HttpMethod.DELETE, "/notifications/**").hasAnyAuthority("ADMIN", "OWNER")

                                .requestMatchers(HttpMethod.GET, "/notifications/admin/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/notifications/owner/**").hasAuthority("OWNER")

                                //                  Ingredient entity endpoints
                                .requestMatchers(HttpMethod.GET, "/ingredients/admin/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/ingredients/owner/**").hasAnyAuthority("ADMIN", "OWNER")
                                .requestMatchers(HttpMethod.POST, "/ingredients/admin/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/ingredients/owner/**").hasAnyAuthority("ADMIN", "OWNER")
                                .requestMatchers(HttpMethod.PUT, "/ingredients/admin/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/ingredients/owner/**").hasAnyAuthority("ADMIN", "OWNER")
                                .requestMatchers(HttpMethod.DELETE, "/ingredients/admin/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/ingredients/owner/**").hasAnyAuthority("ADMIN", "OWNER")

                                //                  MenuItem entity endpoints
                                .requestMatchers(HttpMethod.GET, "/menu-items/admin/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/menu-items/owner/**").hasAnyAuthority("ADMIN", "OWNER")
                                .requestMatchers(HttpMethod.GET, "/menu-items/restaurant/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/menu-items/{id}").permitAll()

                                .requestMatchers(HttpMethod.POST, "/menu-items/admin/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/menu-items/owner/**").hasAnyAuthority("OWNER", "ADMIN")

                                .requestMatchers(HttpMethod.PUT, "/menu-items/owner/**").hasAnyAuthority("OWNER", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/menu-items/admin/**").hasAuthority("ADMIN")

                                .requestMatchers(HttpMethod.DELETE, "/menu-items/owner/**").hasAnyAuthority("OWNER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/menu-items/admin/**").hasAuthority("ADMIN")


//                .requestMatchers("/**").hasAnyAuthority("ADMIN")

                                .anyRequest().authenticated()
                )

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtRequestFilter(jwtService, userDetailsService()), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
