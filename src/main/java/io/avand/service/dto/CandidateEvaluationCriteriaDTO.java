package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CandidateEvaluationCriteriaDTO implements Serializable {

    @NotNull
    private Long id;

    private Long userId;

    private String userComment;

    private Long evaluationCriteriaId;

    @NotNull
    private Long candidateId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public Long getEvaluationCriteriaId() {
        return evaluationCriteriaId;
    }

    public void setEvaluationCriteriaId(Long evaluationCriteriaId) {
        this.evaluationCriteriaId = evaluationCriteriaId;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    @Override
    public String toString() {
        return "CandidateEvaluationCriteriaDTO{" +
            "id=" + id +
            ", userId=" + userId +
            ", userComment='" + userComment + '\'' +
            ", evaluationCriteriaId=" + evaluationCriteriaId +
            '}';
    }
}
