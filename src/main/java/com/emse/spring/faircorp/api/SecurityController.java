package com.emse.spring.faircorp.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@CrossOrigin
@RestController // (1)
@RequestMapping("/api/security") // (2)
@Transactional // (3)
public class SecurityController {

    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/{id}")
    public String findUserName(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails.getUsername();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public ResponseEntity<String> findAll(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userDetails.getUsername());
    }

}
