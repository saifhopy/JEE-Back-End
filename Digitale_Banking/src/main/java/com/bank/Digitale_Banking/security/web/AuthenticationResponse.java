package com.bank.Digitale_Banking.security.web;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class AuthenticationResponse {
    private Collection<String> roles;
    private String username;
    private String token;
}
