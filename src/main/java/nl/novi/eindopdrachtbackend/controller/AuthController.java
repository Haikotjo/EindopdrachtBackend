package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.dto.AuthDTO;
import nl.novi.eindopdrachtbackend.security.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController is responsible for handling authentication requests.
 * It manages user login and token generation.
 */
@RestController
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    /**
     * Constructs an AuthController with the given AuthenticationManager and JwtService.
     *
     * @param man the AuthenticationManager to use for authentication
     * @param service the JwtService to use for token generation
     */
    public AuthController(AuthenticationManager man, JwtService service) {
        this.authManager = man;
        this.jwtService = service;
    }

    /**
     * Handles the sign-in request.
     * Authenticates the user with the provided credentials and generates a JWT token if successful.
     *
     * @param authDTO the authentication data transfer object containing the user's email and password
     * @return a ResponseEntity containing the JWT token if authentication is successful, or an error message if not
     */
    @PostMapping("/auth")
    public ResponseEntity<Object> signIn(@RequestBody AuthDTO authDTO) {
        UsernamePasswordAuthenticationToken up =
                new UsernamePasswordAuthenticationToken(authDTO.email, authDTO.password);

        try {
            Authentication auth = authManager.authenticate(up);

            UserDetails ud = (UserDetails) auth.getPrincipal();
            String token = jwtService.generateToken(ud);

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body("Token generated");
        }
        catch (AuthenticationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
