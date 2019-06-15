package hr.pishe.domain.entity.jpa;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CandidateEvaluationCriteriaEntity.
 */
@Entity
@Table(name = "candidate_evaluation_criteria")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class CandidateEvaluationCriteriaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Lob
    @Column(name = "user_comment")
    private String userComment;

    @Column(name = "evaluation_criteria_id")
    private Long evaluationCriteriaId;

    @ManyToOne
    private CandidateEntity candidate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public CandidateEvaluationCriteriaEntity userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserComment() {
        return userComment;
    }

    public CandidateEvaluationCriteriaEntity userComment(String userComment) {
        this.userComment = userComment;
        return this;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public Long getEvaluationCriteriaId() {
        return evaluationCriteriaId;
    }

    public CandidateEvaluationCriteriaEntity evaluationCriteriaId(Long evaluationCriteriaId) {
        this.evaluationCriteriaId = evaluationCriteriaId;
        return this;
    }

    public void setEvaluationCriteriaId(Long evaluationCriteriaId) {
        this.evaluationCriteriaId = evaluationCriteriaId;
    }

    public CandidateEntity getCandidate() {
        return candidate;
    }

    public CandidateEvaluationCriteriaEntity candidate(CandidateEntity candidateEntity) {
        this.candidate = candidateEntity;
        return this;
    }

    public void setCandidate(CandidateEntity candidateEntity) {
        this.candidate = candidateEntity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CandidateEvaluationCriteriaEntity candidateEvaluationCriteriaEntity = (CandidateEvaluationCriteriaEntity) o;
        if (candidateEvaluationCriteriaEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), candidateEvaluationCriteriaEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CandidateEvaluationCriteriaEntity{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", userComment='" + getUserComment() + "'" +
            ", evaluationCriteriaId=" + getEvaluationCriteriaId() +
            "}";
    }
}
