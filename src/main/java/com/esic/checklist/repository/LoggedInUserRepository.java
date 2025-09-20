package com.esic.checklist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.esic.checklist.model.LoggedInUser;

public interface LoggedInUserRepository extends JpaRepository<LoggedInUser, Long> {
    List<LoggedInUser> findByUserId(String userId);
    
    boolean existsBySessionIdAndIsValid(String sessionId, boolean isValid);

    @Transactional
    @Modifying
    @Query("update LoggedInUser l set l.isValid = false where l.sessionId = :sessionId")
    void invalidateSession(@Param("sessionId") String sessionId);
}

