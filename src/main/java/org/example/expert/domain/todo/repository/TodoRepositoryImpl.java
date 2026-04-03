package org.example.expert.domain.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.entity.Todo;

import java.util.Optional;

import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

// ✅ 레벨2 8번 TodoRepositoryImpl 생성
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoCustomRepository {

    private final JPAQueryFactory queryFactory;

    // ✅ 레벨2 8번 QueryDSL로 구현 + fetchJoin()으로 N+1 방지
    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(todo)
                .leftJoin(todo.user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne());
    }
}
