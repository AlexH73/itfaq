package com.example.itfaq.controller;

import com.example.itfaq.model.Like;
import com.example.itfaq.model.User;
import com.example.itfaq.model.PrivacySetting;
import com.example.itfaq.repository.LikeRepository;
import com.example.itfaq.repository.UserRepository;
import com.example.itfaq.repository.PrivacySettingRepository;
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
    private final PrivacySettingRepository privacySettingRepository;

    private boolean onlyAuthenticatedAllowed(String section) {
        PrivacySetting setting = privacySettingRepository.findBySection(section).orElse(null);
        return setting != null && "authenticated".equals(setting.getMode());
    }

    @PostMapping("/{objectType}/{objectId}")
    public ResponseEntity<?> addLike(
            @PathVariable String objectType,
            @PathVariable Long objectId,
            @AuthenticationPrincipal UserDetails userDetails) {

        boolean onlyAuthLike = onlyAuthenticatedAllowed("like");
        if (onlyAuthLike && userDetails == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        User user = null;
        if (userDetails != null) {
            user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
            if (user == null) {
                return ResponseEntity.status(404).body("User not found");
            }
        }

        boolean alreadyLiked = false;
        if (user != null) {
            alreadyLiked = likeRepository.findByUserAndObjectTypeAndObjectId(user, objectType, objectId).isPresent();
        }
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

        boolean onlyAuthDislike = onlyAuthenticatedAllowed("dislike");
        if (onlyAuthDislike && userDetails == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        User user = null;
        if (userDetails != null) {
            user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
            if (user == null) {
                return ResponseEntity.status(404).body("User not found");
            }
        }

        if (user != null) {
            likeRepository.deleteByUserAndObjectTypeAndObjectId(user, objectType, objectId);
        }

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