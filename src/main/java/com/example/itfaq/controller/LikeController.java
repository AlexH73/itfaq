package com.example.itfaq.controller;

import com.example.itfaq.model.Like;
import com.example.itfaq.model.User;
import com.example.itfaq.repository.LikeRepository;
import com.example.itfaq.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    @PostMapping("/{objectType}/{objectId}")
    public ResponseEntity<?> addLike(
            @PathVariable String objectType,
            @PathVariable Long objectId,
            @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        boolean alreadyLiked = likeRepository.findByUserAndObjectTypeAndObjectId(user, objectType, objectId).isPresent();
        if (alreadyLiked) {
            return ResponseEntity.badRequest().body("Already liked");
        }

        Like like = Like.builder()
                .user(user)
                .objectType(objectType)
                .objectId(objectId)
                .build();
        likeRepository.save(like);

        long likeCount = likeRepository.countByObjectTypeAndObjectId(objectType, objectId);
        return ResponseEntity.ok(likeCount);
    }

    @DeleteMapping("/{objectType}/{objectId}")
    public ResponseEntity<?> removeLike(
            @PathVariable String objectType,
            @PathVariable Long objectId,
            @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        likeRepository.deleteByUserAndObjectTypeAndObjectId(user, objectType, objectId);

        long likeCount = likeRepository.countByObjectTypeAndObjectId(objectType, objectId);
        return ResponseEntity.ok(likeCount);
    }

    @GetMapping("/{objectType}/{objectId}/count")
    public ResponseEntity<Long> getLikeCount(
            @PathVariable String objectType,
            @PathVariable Long objectId) {
        long count = likeRepository.countByObjectTypeAndObjectId(objectType, objectId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{objectType}/{objectId}/isLiked")
    public ResponseEntity<Boolean> isLiked(
            @PathVariable String objectType,
            @PathVariable Long objectId,
            @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.ok(false);
        }

        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
        if (user == null) {
            return ResponseEntity.ok(false);
        }

        boolean liked = likeRepository.findByUserAndObjectTypeAndObjectId(user, objectType, objectId).isPresent();
        return ResponseEntity.ok(liked);
    }
}
