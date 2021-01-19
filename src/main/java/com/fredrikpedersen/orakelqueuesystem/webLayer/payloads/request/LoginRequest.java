package com.fredrikpedersen.orakelqueuesystem.webLayer.payloads.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:41
 */

@Setter
@Getter
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
