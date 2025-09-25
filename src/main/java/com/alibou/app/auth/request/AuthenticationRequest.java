package com.alibou.app.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {

    @NotBlank(message = "VALIDATION.AUTHENTICATION.EMAIL.NOT_BLANK")
    @Email(message = "VALIDATION.AUTHENTICATION.EMAIL.FORMAT")
    @Schema(example = "ali@gmail.com")
    private String email;

    @NotBlank(message = "VALIDATION.AUTHENTICATION.PASSWORD.NOT_BLANK")
    @Schema(example = "pAssword1!_")
    private String password;
}

//we'll add internationalisation
