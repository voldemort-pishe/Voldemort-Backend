package io.avand.web.specification;

import io.avand.domain.entity.jpa.EventEntity;
import io.avand.domain.enumeration.EventStatus;
import io.avand.domain.enumeration.EventType;
import io.avand.web.rest.vm.EventFilterVM;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static org.springframework.data.jpa.domain.Specifications.where;

@Component
public class EventSpecification extends BaseSpecification<EventEntity, EventFilterVM> {
    @Override
    public Specification<EventEntity> getFilter(EventFilterVM request) {
        return where(where(typeContains(request == null ? null : request.getType()))
            .or(statusContains(request == null ? null : request.getStatus()))
            .or(flagContains(request == null ? null : request.getFlag())))
            .and(ownerContains(request == null ? null : request.getOwnerId()));
    }

    private Specification<EventEntity> typeContains(EventType value) {
        return attributeContains("type", value);
    }

    private Specification<EventEntity> statusContains(EventStatus value) {
        return attributeContains("status", value);
    }

    private Specification<EventEntity> flagContains(Boolean value) {
        return attributeContains("flag", value);
    }

    private Specification<EventEntity> ownerContains(Long value) {
        return attributeContains("owner", value);
    }
}
