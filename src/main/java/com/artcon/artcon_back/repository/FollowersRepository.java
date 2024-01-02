package com.artcon.artcon_back.repository;

import com.artcon.artcon_back.model.Followers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowersRepository extends JpaRepository<Followers, Integer> {
    void deleteByFollowerIdAndFollowingId(Integer followerId, Integer followingId);
    boolean existsByFollowerIdAndFollowingId(Integer followerId, Integer followingId);

}
