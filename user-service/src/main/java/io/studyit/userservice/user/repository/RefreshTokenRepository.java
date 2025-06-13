package io.studyit.userservice.user.repository;

import io.studyit.userservice.user.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
}
