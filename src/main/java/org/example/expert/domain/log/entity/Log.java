package org.example.expert.domain.log.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean action;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long todoId;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public Log(Boolean action, Long userId, Long todoId) {
        this.action = action;
        this.userId = userId;
        this.todoId = todoId;
        this.timestamp = LocalDateTime.now();
    }
}

