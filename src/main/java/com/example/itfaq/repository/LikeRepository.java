package com.example.itfaq.repository;

import com.example.itfaq.model.Like;
import com.example.itfaq.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndObjectTypeAndObjectId(User user, String objectType, Long objectId);
    long countByObjectTypeAndObjectId(String objectType, Long objectId);
    List<Like> findAllByObjectTypeAndObjectId(String objectType, Long objectId);
    void deleteByUserAndObjectTypeAndObjectId(User user, String objectType, Long objectId);
}