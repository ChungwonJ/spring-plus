package org.example.expert.domain.todo.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TodoSearchResponse {
    private final Long id;
    private final String title;
    private final Long commentCount;
    private final Long managerCount;

    @QueryProjection
    public TodoSearchResponse(Long id, String title, Long commentCount, Long managerCount) {
        this.id = id;
        this.title = title;
        this.commentCount = commentCount;
        this.managerCount = managerCount;
    }
}
