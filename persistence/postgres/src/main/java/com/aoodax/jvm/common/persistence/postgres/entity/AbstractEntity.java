package com.aoodax.jvm.common.persistence.postgres.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.Id;

@MappedSuperclass
@Data
@EqualsAndHashCode(exclude = {"createdAt", "updatedAt"})
@SuperBuilder
public abstract class AbstractEntity implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @GenericGenerator(
            name = "id_Sequence",
            strategy = "enhanced-sequence",
            parameters = {
                    @Parameter(name = "prefer_sequence_per_entity", value = "true"),
                    @Parameter(name = "optimizer", value = "hilo"),
                    @Parameter(name = "increment_size", value = "1")})
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    protected AbstractEntity() {
    }

    protected AbstractEntity(
            final Long id,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt,
            final boolean isDeleted) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
    }

    public <T extends AbstractEntity> Long getIdOrNull(final T entity) {
        return entity == null ? null : entity.getId();
    }

    @PrePersist
    protected void onPrePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onPreUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}