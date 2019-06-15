package hr.pishe.web.specification;

import hr.pishe.domain.entity.jpa.CandidateEntity;
import hr.pishe.domain.enumeration.CandidateState;
import hr.pishe.domain.enumeration.CandidateType;
import hr.pishe.web.rest.vm.CandidateFilterVM;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static org.springframework.data.jpa.domain.Specifications.where;

@Component
public class CandidateSpecification extends BaseSpecification<CandidateEntity, CandidateFilterVM> {
    @Override
    public Specification<CandidateEntity> getFilter(CandidateFilterVM request) {
        return
            where(
                where(firstNameContains(request.getSearch()))
                    .or(lastNameContains(request.getSearch()))
                    .or(stateContains(request.getState()))
                    .or(employerContains(request.getSearch()))
                    .or(typeContains(request.getType()))
                    .or(pipelineContains(request.getPipeline()))
                    .and(jobContains(request.getJob())))
                .and(
                    where(companyContains(request.getCompany())));
    }

    private Specification<CandidateEntity> firstNameContains(String value) {
        return nameAttributeContains("firstName", value);
    }

    private Specification<CandidateEntity> lastNameContains(String value) {
        return nameAttributeContains("lastName", value);
    }

    private Specification<CandidateEntity> employerContains(String value) {
        return nameAttributeContains("employer", value);
    }

    private Specification<CandidateEntity> stateContains(CandidateState value) {
        return attributeContains("state", value);
    }

    private Specification<CandidateEntity> typeContains(CandidateType value) {
        return attributeContains("type", value);
    }

    private Specification<CandidateEntity> pipelineContains(Long value) {
        return attributeContains("candidatePipeline", value);
    }

    private Specification<CandidateEntity> jobContains(Long value) {
        return attributeContains("job", value);
    }

    private Specification<CandidateEntity> companyContains(Long value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }
            return cb.equal(
                cb.lower(root.get("job").get("company")),
                value
            );
        };
    }
}
