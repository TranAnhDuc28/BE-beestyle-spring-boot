package com.datn.beestyle.security.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignInRequest { // đăng nhập
    @NotBlank(message = "Username must be not null")
    private String username;

    @NotBlank(message = "Password must be not null")
    private String password;
}
