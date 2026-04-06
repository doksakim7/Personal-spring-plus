package org.example.expert.domain.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.response.QTodoSearchResponse;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;

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

    // ✅ 레벨3 10번 TodoRepositoryImpl에 searchTodos를 추가
    @Override
    public Page<TodoSearchResponse> searchTodos(
            String title,
            String startDate,
            String endDate,
            String nickname,
            Pageable pageable
    ) {

        // 1. BooleanBuilder로 동적 조건 처리(title, nickname, 생성일 범위)
        BooleanBuilder builder = new BooleanBuilder();
        if(title != null) builder.and(todo.title.containsIgnoreCase(title));
        if(nickname != null) builder.and(manager.user.nickname.containsIgnoreCase(nickname));
        if (startDate != null) builder.and(todo.createdAt.goe(LocalDateTime.parse(startDate)));
        if (endDate != null) builder.and(todo.createdAt.loe(LocalDateTime.parse(endDate)));

        // 2. Projections 사용
        List<TodoSearchResponse> results = queryFactory
                .select(new QTodoSearchResponse(
                        todo.title,
                        manager.count(),
                        comment.count()
                ))
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(todo.comments, comment)
                .where(builder)
                .groupBy(todo.id)
                .orderBy(todo.createdAt.desc())
                .fetch();

        // 3. 전체 카운트 쿼리
        Long total = queryFactory
                .select(todo.count())
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(todo.comments, comment)
                .where(builder)
                .fetchOne();

        // 4. null 방지
        long totalCount = total != null ? total : 0L;

        return new PageImpl<>(results, pageable, totalCount);
    }
}
