package org.example.expert.domain.todo.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

// ✅ 레벨3 10번 TodoSearchResponse dto 생성
@Getter
public class TodoSearchResponse {

    private final String  title;
    private final Long managerCount;
    private final Long commentCount;

    @QueryProjection
    public TodoSearchResponse(String title, Long managerCount, Long commentCount) {
        this.title = title;
        this.managerCount = managerCount;
        this.commentCount = commentCount;
    }
}
