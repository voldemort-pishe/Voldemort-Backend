package io.avand.domain.entity.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.avand.domain.enumeration.CandidateState;

/**
 * A CandidateEntity.
 */
@Entity
@Table(name = "candidate_entity")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class CandidateEntity extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private CandidateState state;

    @Column(name = "cellphone")
    private String cellphone;

    @Column(name = "email")
    private String email;

    @Column(name = "candidate_pipeline")
    private Long candidatePipeline;

    @OneToMany(mappedBy = "candidate")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    private Set<FeedbackEntity> feedbacks = new HashSet<>();

    @OneToMany(mappedBy = "candidate")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    private Set<CommentEntity> comments = new HashSet<>();

    @OneToMany(mappedBy = "candidate")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    private Set<CandidateScheduleEntity> candidateSchedules = new HashSet<>();

    @OneToMany(mappedBy = "candidate")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    private Set<CandidateEvaluationCriteriaEntity> candidateEvaluationCriteria = new HashSet<>();

    @OneToMany(mappedBy = "candidate")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONE)
    private Set<CandidateMessageEntity> candidateMessages = new HashSet<>();

    //TODO should un comment this
    @OneToOne(optional = false)
//    @NotNull
    @JoinColumn(unique = true)
    private FileEntity file;

    @ManyToOne
    private JobEntity job;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public CandidateEntity firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public CandidateEntity lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public CandidateState getState() {
        return state;
    }

    public CandidateEntity state(CandidateState state) {
        this.state = state;
        return this;
    }

    public void setState(CandidateState state) {
        this.state = state;
    }

    public String getCellphone() {
        return cellphone;
    }

    public CandidateEntity cellphone(String cellphone) {
        this.cellphone = cellphone;
        return this;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public CandidateEntity email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCandidatePipeline() {
        return candidatePipeline;
    }

    public CandidateEntity candidatePipeline(Long candidatePipeline) {
        this.candidatePipeline = candidatePipeline;
        return this;
    }

    public void setCandidatePipeline(Long candidatePipeline) {
        this.candidatePipeline = candidatePipeline;
    }

    public Set<FeedbackEntity> getFeedbacks() {
        return feedbacks;
    }

    public CandidateEntity feedbacks(Set<FeedbackEntity> feedbackEntities) {
        this.feedbacks = feedbackEntities;
        return this;
    }

    public CandidateEntity addFeedback(FeedbackEntity feedbackEntity) {
        this.feedbacks.add(feedbackEntity);
        feedbackEntity.setCandidate(this);
        return this;
    }

    public CandidateEntity removeFeedback(FeedbackEntity feedbackEntity) {
        this.feedbacks.remove(feedbackEntity);
        feedbackEntity.setCandidate(null);
        return this;
    }

    public void setFeedbacks(Set<FeedbackEntity> feedbackEntities) {
        this.feedbacks = feedbackEntities;
    }

    public Set<CommentEntity> getComments() {
        return comments;
    }

    public CandidateEntity comments(Set<CommentEntity> commentEntities) {
        this.comments = commentEntities;
        return this;
    }

    public CandidateEntity addComment(CommentEntity commentEntity) {
        this.comments.add(commentEntity);
        commentEntity.setCandidate(this);
        return this;
    }

    public CandidateEntity removeComment(CommentEntity commentEntity) {
        this.comments.remove(commentEntity);
        commentEntity.setCandidate(null);
        return this;
    }

    public void setComments(Set<CommentEntity> commentEntities) {
        this.comments = commentEntities;
    }

    public Set<CandidateScheduleEntity> getCandidateSchedules() {
        return candidateSchedules;
    }

    public CandidateEntity candidateSchedules(Set<CandidateScheduleEntity> candidateScheduleEntities) {
        this.candidateSchedules = candidateScheduleEntities;
        return this;
    }

    public CandidateEntity addCandidateSchedule(CandidateScheduleEntity candidateScheduleEntity) {
        this.candidateSchedules.add(candidateScheduleEntity);
        candidateScheduleEntity.setCandidate(this);
        return this;
    }

    public CandidateEntity removeCandidateSchedule(CandidateScheduleEntity candidateScheduleEntity) {
        this.candidateSchedules.remove(candidateScheduleEntity);
        candidateScheduleEntity.setCandidate(null);
        return this;
    }

    public void setCandidateSchedules(Set<CandidateScheduleEntity> candidateScheduleEntities) {
        this.candidateSchedules = candidateScheduleEntities;
    }

    public Set<CandidateEvaluationCriteriaEntity> getCandidateEvaluationCriteria() {
        return candidateEvaluationCriteria;
    }

    public CandidateEntity candidateEvaluationCriteria(Set<CandidateEvaluationCriteriaEntity> candidateEvaluationCriteriaEntities) {
        this.candidateEvaluationCriteria = candidateEvaluationCriteriaEntities;
        return this;
    }

    public CandidateEntity addCandidateEvaluationCriteria(CandidateEvaluationCriteriaEntity candidateEvaluationCriteriaEntity) {
        this.candidateEvaluationCriteria.add(candidateEvaluationCriteriaEntity);
        candidateEvaluationCriteriaEntity.setCandidate(this);
        return this;
    }

    public CandidateEntity removeCandidateEvaluationCriteria(CandidateEvaluationCriteriaEntity candidateEvaluationCriteriaEntity) {
        this.candidateEvaluationCriteria.remove(candidateEvaluationCriteriaEntity);
        candidateEvaluationCriteriaEntity.setCandidate(null);
        return this;
    }

    public void setCandidateEvaluationCriteria(Set<CandidateEvaluationCriteriaEntity> candidateEvaluationCriteriaEntities) {
        this.candidateEvaluationCriteria = candidateEvaluationCriteriaEntities;
    }

    public Set<CandidateMessageEntity> getCandidateMessages() {
        return candidateMessages;
    }

    public void setCandidateMessages(Set<CandidateMessageEntity> candidateMessages) {
        this.candidateMessages = candidateMessages;
    }

    public FileEntity getFile() {
        return file;
    }

    public CandidateEntity file(FileEntity fileEntity) {
        this.file = fileEntity;
        return this;
    }

    public void setFile(FileEntity fileEntity) {
        this.file = fileEntity;
    }

    public JobEntity getJob() {
        return job;
    }

    public CandidateEntity job(JobEntity jobEntity) {
        this.job = jobEntity;
        return this;
    }

    public void setJob(JobEntity jobEntity) {
        this.job = jobEntity;
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
        CandidateEntity candidateEntity = (CandidateEntity) o;
        if (candidateEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), candidateEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CandidateEntity{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", state='" + getState() + "'" +
            ", cellphone='" + getCellphone() + "'" +
            ", email='" + getEmail() + "'" +
            ", candidatePipeline=" + getCandidatePipeline() +
            "}";
    }
}
