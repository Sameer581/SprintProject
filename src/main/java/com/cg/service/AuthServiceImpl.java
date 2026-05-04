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
    public String signUp(String name, String username, String email, String password) {

        // Optional: check if user already exists
        if (userRepo.findByEmail(email).isPresent()) {
            return "User already exists with this email";
        }

        if (userRepo.findByUsername(username).isPresent()) {
            return "Username already taken";
        }

        // Create new user
        User user = new User();
        user.setUsername(name);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        // Save user
        userRepo.save(user);

        return "User registered successfully";
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
