package com.fredrikpedersen.orakelqueuesystem.serviceLayer.authentication;

import com.fredrikpedersen.orakelqueuesystem.webLayer.payloads.request.LoginRequest;
import com.fredrikpedersen.orakelqueuesystem.webLayer.payloads.response.JwtResponse;
import com.fredrikpedersen.orakelqueuesystem.webLayer.security.jwt.JwtUtilities;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtAuthenticationServiceImpl implements AuthenticationService<JwtResponse, LoginRequest> {

    private final AuthenticationManager authenticationManager;
    private final JwtUtilities jwtUtilities;

    public JwtAuthenticationServiceImpl(final AuthenticationManager authenticationManager, final JwtUtilities jwtUtilities) {
        this.authenticationManager = authenticationManager;
        this.jwtUtilities = jwtUtilities;
    }

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
