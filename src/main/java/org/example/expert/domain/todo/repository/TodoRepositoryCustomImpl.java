package org.example.expert.domain.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.user.entity.QUser;

import java.util.Optional;

@RequiredArgsConstructor
public class TodoRepositoryCustomImpl implements TodoRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        QTodo td = QTodo.todo;
        QUser us = QUser.user;

        Todo result = queryFactory
                .select(td)
                .from(td)
                .leftJoin(td.user, us).fetchJoin()
                .where(td.id.eq(todoId))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
