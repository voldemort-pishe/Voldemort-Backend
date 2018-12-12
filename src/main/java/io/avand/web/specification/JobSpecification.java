package io.avand.web.specification;

import io.avand.domain.entity.jpa.JobEntity;
import io.avand.domain.entity.jpa.JobHireTeamEntity;
import io.avand.domain.enumeration.JobStatus;
import io.avand.web.rest.vm.JobFilterVM;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;


import static org.springframework.data.jpa.domain.Specifications.where;

@Component
public class JobSpecification extends BaseSpecification<JobEntity, JobFilterVM> {
    @Override
    public Specification<JobEntity> getFilter(JobFilterVM request) {
        return where(
            where(statusContains(request.getStatus()))
                .or(managerContains(request.getManager()))
                .and(where(nameContains(request.getSearch()))
                    .or(descriptionContains(request.getSearch()))
                    .or(departmentContains(request.getSearch()))))
            .and(companyContains(request.getCompany()));
    }


    private Specification<JobEntity> nameContains(String search) {
        return nameAttributeContains("nameFa", search);
    }

    private Specification<JobEntity> descriptionContains(String search) {
        return nameAttributeContains("descriptionFa", search);
    }

    private Specification<JobEntity> departmentContains(String search) {
        return nameAttributeContains("department", search);
    }

    private Specification<JobEntity> statusContains(JobStatus value) {
        return attributeContains("status", value);
    }

    private Specification<JobEntity> managerContains(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            query.distinct(true);
            SetJoin<JobEntity, JobHireTeamEntity> jobHireTeam = root.joinSet("jobHireTeam", JoinType.LEFT);

            Predicate user = cb.equal(jobHireTeam.get("user"), value);
            jobHireTeam.on(user);

            return jobHireTeam.getOn();
        };
    }

    private Specification<JobEntity> companyContains(Long value) {
        return attributeContains("company", value);
    }
}
