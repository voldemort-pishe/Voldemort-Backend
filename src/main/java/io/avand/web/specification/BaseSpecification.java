package io.avand.web.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;



@Component
public abstract class BaseSpecification<T, V> {

    private final String wildcard = "%";

    public abstract Specification<T> getFilter(V request);

    protected String containsLowerCase(String searchField) {
        return wildcard + searchField.toLowerCase() + wildcard;
    }

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

    Specification<T> nameAttributeContains(String attribute, String value) {
        return (root, query, cb) -> {
            if(value == null) {
                return null;
            }

            return cb.like(
                cb.lower(root.get(attribute)),
                containsLowerCase(value)
            );
        };
    }
}
