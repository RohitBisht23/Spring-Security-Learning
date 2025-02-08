package com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Repository;

import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
}
