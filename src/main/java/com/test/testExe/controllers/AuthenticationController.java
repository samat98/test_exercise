package com.test.testExe.controllers;

import com.test.testExe.models.User;
import com.test.testExe.security.jwt.JwtUtils;
import com.test.testExe.security.util.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/")
public class AuthenticationController {

    private final JwtUtils jwtUtils;
    private final SecurityHelper securityHelper;

    @Autowired
    public AuthenticationController(JwtUtils jwtUtils, SecurityHelper securityHelper) {
        this.jwtUtils = jwtUtils;
        this.securityHelper = securityHelper;
    }
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("authenticate")
    public ResponseEntity<?> getToken(@RequestBody User auth) {

        Authentication authentication =
                securityHelper.getAuthentication(auth.getUsername(), auth.getPassword());
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);

        return context.getAuthentication().isAuthenticated() ?
                ResponseEntity.ok(jwtUtils.getTokenDto(authentication)) :
                ResponseEntity.badRequest().body("Error: User is not Authenticated");

    }
}
