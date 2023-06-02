package com.bank.Digitale_Banking.security.web;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}
