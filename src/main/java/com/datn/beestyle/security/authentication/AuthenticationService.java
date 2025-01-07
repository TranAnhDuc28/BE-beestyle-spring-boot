package com.datn.beestyle.security.authentication;

import com.demo.dto.request.ResetPasswordDTO;
import com.demo.dto.request.SignInRequest;
import com.demo.dto.response.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    TokenResponse accessToken(SignInRequest request);
    TokenResponse refreshToken(HttpServletRequest request);
    String removeToken(HttpServletRequest request);
    String forgotPassword(String email);
    String resetPassword(String secretKey);
    String changePassword(ResetPasswordDTO request);
}
