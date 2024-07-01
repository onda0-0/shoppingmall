package com.sparta.shoppingmall.domain.follows.repository;

import com.sparta.shoppingmall.domain.follows.entity.Follows;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowsRepository extends JpaRepository<Follows, Long> {

    Optional<Follows> findByFollowingIdAndFollowerId(Long followingId, Long id);

}
