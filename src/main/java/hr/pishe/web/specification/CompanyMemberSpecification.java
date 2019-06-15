package hr.pishe.web.specification;

import hr.pishe.domain.entity.jpa.CompanyMemberEntity;
import hr.pishe.web.rest.vm.CompanyMemberFilterVM;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static org.springframework.data.jpa.domain.Specifications.where;

@Component
public class CompanyMemberSpecification extends BaseSpecification<CompanyMemberEntity, CompanyMemberFilterVM> {
    @Override
    public Specification<CompanyMemberEntity> getFilter(CompanyMemberFilterVM request) {
        return where(where(emailContains(request.getEmail())))
            .and(companyContains(request.getCompany()));
    }

    private Specification<CompanyMemberEntity> companyContains(Long value) {
        return attributeContains("company", value);
    }

    private Specification<CompanyMemberEntity> emailContains(String value) {
        return (root, query, cb) -> {
            if (value == null) {
                return null;
            }

            return cb.like(
                cb.lower(root.get("user").get("email")),
                containsLowerCase(value)
            );
        };
    }
}
