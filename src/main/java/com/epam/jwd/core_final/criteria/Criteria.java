package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.AbstractBaseEntity;
import com.epam.jwd.core_final.domain.BaseEntity;

/**
 * Should be a builder for {@link BaseEntity} fields
 */
public abstract class Criteria<T extends BaseEntity> {


    protected static abstract class BaseBuilder<T extends  BaseEntity> {
        protected T thisClass;

        protected abstract T getThisClass();

        protected BaseBuilder() {
            thisClass = getThisClass();
        }

        public T build() {
            return thisClass;
        }
    }

}


