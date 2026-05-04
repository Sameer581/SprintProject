package com.cg.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.cg.entity.User;
import com.cg.repo.RoleRepo;
import com.cg.repo.UserRepo;

@Service
public class UserDetailsServiceImple implements UserDetailsService {
	@Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userRepo.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> roles = roleRepo.findRolesByUsername(username);

        var authorities = roles.stream()
            .map(SimpleGrantedAuthority::new)
            .toList();

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
           // user.isEnabled(),
          //  true, true, true,
            authorities
        );
    }
}
