package com.cg.service;

import org.springframework.stereotype.Service;

import com.cg.config.JwtUtil;
import com.cg.entity.User;
import com.cg.repo.UserRepo;

import jakarta.transaction.Transactional;

@Service

public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepo userRepo, JwtUtil jwtUtil) {
		super();
		this.userRepo = userRepo;
		this.jwtUtil = jwtUtil;
	}

	@Override
	@Transactional
	public String login(String email, String password) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }
        
        String token = jwtUtil.generateToken(email); 
        return token;
    }
}
