package com.example.profile.repository;

import com.example.profile.model.entity.User;
import com.example.profile.model.projection.UserRating;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmailOrUsername(String email, String username);
    Collection<UserRating> findAllByOrderByPointsDesc(PageRequest pageRequest);
}
