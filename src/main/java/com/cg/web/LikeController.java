package com.cg.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cg.dto.LikeDto;
import com.cg.service.LikeService;

@RestController
@RequestMapping("/likes")
@CrossOrigin("*")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/toggle")
    public LikeDto toggleLike(@RequestBody LikeDto likeDto) {
        return likeService.toggleLike(likeDto);
    }

    @GetMapping("/count/{postId}")
    public int countLikesByPost(@PathVariable Long postId) {
        return likeService.countLikesByPost(postId);
    }

    @GetMapping("/check")
    public boolean hasUserLiked(@RequestParam Long userId, @RequestParam Long postId) {
        return likeService.hasUserLiked(userId, postId);
    }
}