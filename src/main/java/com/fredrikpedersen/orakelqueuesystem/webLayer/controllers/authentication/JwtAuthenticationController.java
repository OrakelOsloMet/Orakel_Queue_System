package com.fredrikpedersen.orakelqueuesystem.webLayer.controllers.authentication;

import com.fredrikpedersen.orakelqueuesystem.serviceLayer.authentication.AuthenticationService;
import com.fredrikpedersen.orakelqueuesystem.utilities.constants.URLs;
import com.fredrikpedersen.orakelqueuesystem.webLayer.payloads.request.LoginRequest;
import com.fredrikpedersen.orakelqueuesystem.webLayer.payloads.response.JwtResponse;
import com.fredrikpedersen.orakelqueuesystem.webLayer.security.jwt.JwtUtilities;
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

    private final AuthenticationService<JwtResponse, LoginRequest> authenticationService;
    private final JwtUtilities jwtUtilities;

    public JwtAuthenticationController(final JwtUtilities jwtUtilities, final AuthenticationService<JwtResponse, LoginRequest> authenticationService) {
        this.authenticationService = authenticationService;
        this.jwtUtilities = jwtUtilities;
    }

    @PostMapping(URLs.AUTHENTICATION_SIGN_IN)
    public ResponseEntity<?> authenticateLoginRequest(@Valid @RequestBody final LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.authenticateLoginRequest(loginRequest));
    }

    @PostMapping(URLs.AUTHENTICATION_TOKEN_VALID)
    public boolean isTokenValid(@RequestBody final String token, final HttpServletRequest request) {
        return jwtUtilities.validateJwt(token, request);
    }
}
