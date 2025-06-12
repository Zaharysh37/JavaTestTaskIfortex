package com.example.java_ifortex_test_task.repository;

import com.example.java_ifortex_test_task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value =
        "SELECT u.* FROM users u " +
        "WHERE u.id = ( " +
        "SELECT s.user_id FROM sessions s " +
        "GROUP BY s.user_id  " +
        "ORDER BY COUNT(*) DESC, s.user_id DESC " +
        "LIMIT 1 " +
        ")", nativeQuery = true)
    User getUserWithMostSessions();

    @Query(value =
        "SELECT u.* FROM users u " +
        "JOIN sessions s ON u.id = s.user_id " +
        "WHERE s.device_type = 1 " +
        "GROUP BY u.id " +
        "ORDER BY MAX(s.started_at_utc) DESC ", nativeQuery = true)
    List<User> getUsersWithAtLeastOneMobileSession();
}
