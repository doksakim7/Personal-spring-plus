package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;

import java.util.Optional;

// ✅ 레벨2 8번 TodoCustomRepository 인터페이스 생성
public interface TodoCustomRepository {

    Optional<Todo> findByIdWithUser(Long todoId);

}
