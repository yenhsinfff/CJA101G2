package com.lutu.forget_password;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {
    Optional<ResetPasswordToken> findByToken(String token);//
}
