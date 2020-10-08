package com.fredrikpedersen.orakelqueuesystem.webLayer.controllers.authentication;

import com.fredrikpedersen.orakelqueuesystem.serviceLayer.authentication.AuthenticationService;
import com.fredrikpedersen.orakelqueuesystem.utilities.constants.URLs;
import com.fredrikpedersen.orakelqueuesystem.webLayer.payloads.request.LoginRequest;
import com.fredrikpedersen.orakelqueuesystem.webLayer.payloads.response.JwtResponse;
import com.fredrikpedersen.orakelqueuesystem.webLayer.security.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(URLs.AUTHENTICATION_BASE_URL)
public class JwtAuthenticationController {

    private AuthenticationService<JwtResponse, LoginRequest> authenticationService;
    private JwtUtils jwtUtils;

    public JwtAuthenticationController(final JwtUtils jwtUtils, final AuthenticationService<JwtResponse, LoginRequest> authenticationService) {
        this.authenticationService = authenticationService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping(URLs.AUTHENTICATION_SIGN_IN)
    public ResponseEntity<?> authenticateLoginRequest(@Valid @RequestBody final LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.authenticateLoginRequest(loginRequest));
    }

    @PostMapping(URLs.AUTHENTICATION_TOKEN_VALID)
    public boolean isTokenValid(@RequestBody final String token, final HttpServletRequest request) {
        return jwtUtils.validateJwtToken(token, request);
    }
}
