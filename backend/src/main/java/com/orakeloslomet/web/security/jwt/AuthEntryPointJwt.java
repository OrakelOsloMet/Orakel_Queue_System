package com.orakeloslomet.web.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:41
 */

@Slf4j
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(final HttpServletRequest request,
                         final HttpServletResponse response,
                         final AuthenticationException authException) throws IOException {
        log.error("Unauthorized error: {}", authException.getMessage());
        final String jwtExceptionMessage = (String) request.getAttribute("jwtExceptionMessage");
        final String message = (jwtExceptionMessage != null) ? jwtExceptionMessage : "Unauthorized";

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
    }

}
