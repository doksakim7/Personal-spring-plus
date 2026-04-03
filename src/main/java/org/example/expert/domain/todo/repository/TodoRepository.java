package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

// ✅ 레벨2 8번 TodoCustomRepository를 상속 받고 기존 findByIdWithUser 쿼리 메서드 삭제
public interface TodoRepository extends JpaRepository<Todo, Long>, TodoCustomRepository {

    // ✅ 레벨1 3번 weather와 modifiedAt 기준으로 검색 가능하게 쿼리 메서드 생성 & 기존 쿼리 메서드 삭제
    @Query("SELECT t FROM Todo t " +
            "LEFT JOIN FETCH t.user u " +
            "WHERE (:weather IS NULL OR t.weather = :weather)" +
            "AND (:startDate IS NULL OR t.modifiedAt >= :startDate)" +
            "AND (:endDate IS NULL OR t.modifiedAt <= :endDate)" +
            "ORDER BY t.modifiedAt DESC")
    Page<Todo> searchTodos(
            @Param("weather") String weather,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
            );
}
