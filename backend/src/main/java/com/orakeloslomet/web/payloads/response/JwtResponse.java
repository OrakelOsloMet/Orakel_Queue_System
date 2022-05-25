package com.orakeloslomet.web.payloads.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:41
 */

@Getter
@Setter
public class JwtResponse {

    private final String type = "Bearer";

    private Long id;
    private String token;
    private String username;
    private String email;
    private List<String> roles;

    public JwtResponse(final Long id, final String accessToken, final String username, final String email,  final List<String> roles) {
        this.id = id;
        this.token = accessToken;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
