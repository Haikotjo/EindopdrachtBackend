package nl.novi.eindopdrachtbackend.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthDTO {
    @NotBlank(message = "Email is mandatory")
    public String email;

    @NotBlank(message = "Password is mandatory")
    public String password;
}
