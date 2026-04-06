package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

// ✅ 레벨2 8번 TodoCustomRepository 인터페이스 생성
public interface TodoCustomRepository {

    Optional<Todo> findByIdWithUser(Long todoId);

    // ✅ 레벨3 10번 searchTodos 쿼리메서드 생성
    Page<TodoSearchResponse> searchTodos(
            String title,
            String startDate,
            String endDate,
            String nickname,
            Pageable pageable
    );

}
