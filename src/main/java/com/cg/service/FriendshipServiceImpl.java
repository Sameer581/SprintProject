package com.cg.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.dto.FriendshipDto;
import com.cg.entity.Friendship;
import com.cg.entity.User;
import com.cg.enums.FriendshipStatus;
import com.cg.repo.FriendshipRepo;
import com.cg.repo.UserRepo; // Assuming you have a User repository

@Service
public class FriendshipServiceImpl implements FriendshipService {

    @Autowired
    private FriendshipRepo friendshipRepo;

    @Autowired
    private UserRepo userRepo;

    
}