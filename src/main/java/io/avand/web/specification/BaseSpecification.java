package io.avand.web.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;

import static org.springframework.data.jpa.domain.Specifications.where;

@Component
public class BaseSpecification<T> {

    public Specification<T> getFilter(Map<String, String> request) {
        return (root, query, cb) -> {

            query.distinct(false);
            Specifications<T> specification;

            request.remove("page");
            request.remove("size");
            request.remove("sort");

            if (request.isEmpty()) {
                return null;
            } else {
                Iterator<String> keys = request.keySet().iterator();

                String key = keys.next();
                specification = where(attributeContains(key, request.get(key)));

                while (keys.hasNext()) {
                    key = keys.next();
                    specification = specification.and(where(attributeContains(key, request.get(key))));
                }
                return specification.toPredicate(root, query, cb);
            }
        };
    }

    private Specification<T> attributeContains(String attribute, String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            char[] asciiCode = value.toCharArray();
            boolean number = true;
            for (char c : asciiCode) {
                if (!(c >= 48 && c <= 57)) {
                    number = false;
                }
            }
            if (number) {
                Long longValue = new Long(value);
                return cb.equal(root.get(attribute), longValue);
            } else {
                return cb.equal(
                    cb.lower(root.get(attribute)),
                    value
                );
            }
        };
    }

    private boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
}
