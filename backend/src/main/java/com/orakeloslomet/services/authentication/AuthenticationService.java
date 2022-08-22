package com.orakeloslomet.services.authentication;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:41
 */

public interface AuthenticationService<Token, Request> {

    Token authenticateLoginRequest(Request request);
}
