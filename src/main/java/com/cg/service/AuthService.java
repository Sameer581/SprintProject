package com.cg.service;

public interface AuthService {

    public String login(String email, String password);
    
    public String signUp(String name, String username, String email, String password);
}