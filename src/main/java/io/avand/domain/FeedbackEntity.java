package io.avand.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import io.avand.domain.enumeration.FeedbackRate;

/**
 * A FeedbackEntity.
 */
@Entity
@Table(name = "feedback_entity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FeedbackEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Lob
    @Column(name = "feedback_text")
    private String feedbackText;

    @Enumerated(EnumType.STRING)
    @Column(name = "rating")
    private FeedbackRate rating;

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

    public FeedbackEntity userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public FeedbackEntity feedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
        return this;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public FeedbackRate getRating() {
        return rating;
    }

    public FeedbackEntity rating(FeedbackRate rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(FeedbackRate rating) {
        this.rating = rating;
    }

    public CandidateEntity getCandidate() {
        return candidate;
    }

    public FeedbackEntity candidate(CandidateEntity candidateEntity) {
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
        FeedbackEntity feedbackEntity = (FeedbackEntity) o;
        if (feedbackEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), feedbackEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FeedbackEntity{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", feedbackText='" + getFeedbackText() + "'" +
            ", rating='" + getRating() + "'" +
            "}";
    }
}
