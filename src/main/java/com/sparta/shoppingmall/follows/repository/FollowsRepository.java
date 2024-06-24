package com.sparta.shoppingmall.follows.repository;

import com.sparta.shoppingmall.follows.entity.Follows;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowsRepository extends JpaRepository<Follows, Long> {

    Optional<Follows> findByFollowingIdAndFollowerId(Long followingId, Long id);

    List<Follows> findByFollowerid(Long id);
}
