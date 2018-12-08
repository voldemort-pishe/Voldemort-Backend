package io.avand.domain.entity.jpa;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CommentEntity.
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.NONE)
public class CommentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Lob
    @Column(name = "comment_text")
    private String commentText;

    @Column(name = "status")
    private Boolean status;

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

    public CommentEntity userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCommentText() {
        return commentText;
    }

    public CommentEntity commentText(String commentText) {
        this.commentText = commentText;
        return this;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Boolean isStatus() {
        return status;
    }

    public CommentEntity status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public CandidateEntity getCandidate() {
        return candidate;
    }

    public CommentEntity candidate(CandidateEntity candidateEntity) {
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
        CommentEntity commentEntity = (CommentEntity) o;
        if (commentEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commentEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommentEntity{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", commentText='" + getCommentText() + "'" +
            ", status='" + isStatus() + "'" +
            "}";
    }
}
