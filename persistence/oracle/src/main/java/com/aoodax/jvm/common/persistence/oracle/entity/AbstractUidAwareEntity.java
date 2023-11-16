package com.aoodax.jvm.common.persistence.oracle.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public abstract class AbstractUidAwareEntity extends AbstractEntity {

    @Column(name = "uid", updatable = false, nullable = false, unique = true)
    private String uid;

    protected AbstractUidAwareEntity() {
        super();
    }

    protected AbstractUidAwareEntity(final String uid) {
        this.uid = uid;
    }

    protected AbstractUidAwareEntity(
            final Long id,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt,
            final boolean isDeleted,
            final String uid) {
        super(id, createdAt, updatedAt, isDeleted);
        this.uid = uid;
    }
}
