package io.avand.web.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;



@Component
public abstract class BaseSpecification<T, V> {

    public abstract Specification<T> getFilter(V request);

    Specification<T> attributeContains(String attribute, Object value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return cb.equal(
                cb.lower(root.get(attribute)),
                value
            );
        };
    }
}
