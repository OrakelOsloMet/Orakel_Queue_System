package com.orakeloslomet.web.controllers.authentication;

import com.orakeloslomet.services.authentication.AuthenticationService;
import com.orakeloslomet.utilities.constants.URLs;
import com.orakeloslomet.web.payloads.request.LoginRequest;
import com.orakeloslomet.web.payloads.response.JwtResponse;
import com.orakeloslomet.web.security.jwt.JwtUtilities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:41
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(URLs.AUTHENTICATION_BASE_URL)
public class JwtAuthenticationController {

    private final AuthenticationService<JwtResponse, LoginRequest> authenticationService;
    private final JwtUtilities jwtUtilities;

    @PostMapping(URLs.AUTHENTICATION_SIGN_IN_URL)
    public ResponseEntity<?> authenticateLoginRequest(@Valid @RequestBody final LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.authenticateLoginRequest(loginRequest));
    }

    @PostMapping(URLs.AUTHENTICATION_TOKEN_VALID_URL)
    public boolean isTokenValid(@RequestBody final String token, final HttpServletRequest request) {
        return jwtUtilities.validateJwt(token, request);
    }
}
