package com.fredrikpedersen.orakelqueuesystem.webLayer.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Unauthorized error: {}", authException.getMessage());
        final String jwtExceptionMEssage = (String)request.getAttribute("jwtExceptionMessage");
        final String message = (jwtExceptionMEssage != null) ? jwtExceptionMEssage : "Unauthorized";

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);

    }

}
