package com.alibou.app.auth.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {

    //TODO: Validations

    private String email;
    private String password;
}
