package com.example.java_ifortex_test_task.repository;

import com.example.java_ifortex_test_task.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.repository.query.Param;

public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query(value =
        "SELECT " +
            "s.id AS id, " +
            "s.ended_at_utc AS ended_at_utc, " +
            "s.started_at_utc AS started_at_utc, " +
            "s.user_id AS user_id, " +
            "1 AS device_type " +
            "FROM sessions s " +
            "WHERE s.device_type = 2 " +
            "ORDER BY s.started_at_utc ASC " +
            "LIMIT 1", nativeQuery = true)
    Session getFirstDesktopSession();

    @Query(value =
         "SELECT " +
         "s.id AS id, " +
         "(CASE s.device_type " +
         "WHEN 1 THEN 0 "+
         "WHEN 2 THEN 1 "+
         "END) AS device_type, " +
         "s.ended_at_utc AS ended_at_utc, " +
         "s.started_at_utc AS started_at_utc, " +
         "s.user_id AS user_id " +
         "FROM sessions s " +
         "JOIN users u ON s.user_id = u.id " +
         "WHERE u.deleted = false " +
         "AND s.ended_at_utc IS NOT NULL " +
         "AND s.ended_at_utc < :endDate " +
         "ORDER BY s.started_at_utc DESC", nativeQuery = true)
    List<Session> getSessionsFromActiveUsersEndedBefore2025(@Param("endDate") LocalDateTime endDate);
}