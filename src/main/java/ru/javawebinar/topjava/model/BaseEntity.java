package ru.javawebinar.topjava.model;

import java.util.Objects;

/**
 * BaseEntity
 *
 * @author Stanislav (376825@gmail.com)
 * @since 30.06.2018
 */
abstract public class BaseEntity {
    protected Long id;

    public BaseEntity() {
    }

    public BaseEntity(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public boolean isNew() {
        return id == null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}