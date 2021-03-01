package com.epam.jwd.core_final.domain;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Expected fields:
 * <p>
 * id {@link Long} - entity id
 * name {@link String} - entity name
 */
public abstract class AbstractBaseEntity implements BaseEntity {
    private static final AtomicLong count = new AtomicLong(0);
    private Long id;
    private String name;

    public AbstractBaseEntity(String name) {
        id = count.incrementAndGet();
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
