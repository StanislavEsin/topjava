package ru.javawebinar.topjava.model;

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
}