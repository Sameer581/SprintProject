package com.cg.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.dto.AuthRequest;
import com.cg.dto.AuthResponse;
import com.cg.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    

    public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}
    
	@PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        String token = authService.login(request.getEmail(), request.getPassword());
        return new AuthResponse(token);
    }
	
}
