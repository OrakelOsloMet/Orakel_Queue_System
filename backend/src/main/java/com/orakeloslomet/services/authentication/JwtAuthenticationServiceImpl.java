package com.orakeloslomet.services.authentication;

import com.orakeloslomet.web.payloads.request.LoginRequest;
import com.orakeloslomet.web.payloads.response.JwtResponse;
import com.orakeloslomet.web.security.jwt.JwtUtilities;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:41
 */

@Service
@RequiredArgsConstructor
public class JwtAuthenticationServiceImpl implements AuthenticationService<JwtResponse, LoginRequest> {

    private final AuthenticationManager authenticationManager;
    private final JwtUtilities jwtUtilities;

    @Override
    public JwtResponse authenticateLoginRequest(final LoginRequest loginRequest) {

        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        final String jwtToken = jwtUtilities.generateJwt(authentication);
        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        final List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new JwtResponse(
                userDetails.getId(),
                jwtToken,
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }
}
