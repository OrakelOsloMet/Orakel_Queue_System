package com.fredrikpedersen.orakelqueuesystem.serviceLayer.authentication;

public interface AuthenticationService<Token, Request> {

    Token authenticateLoginRequest(Request request);
}
