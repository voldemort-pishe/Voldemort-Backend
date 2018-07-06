package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CommentDTO implements Serializable {

    @NotNull
    private Long id;

    private Long userId;

    private String commentText;

    private Boolean Status;

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

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
            "id=" + id +
            ", userId=" + userId +
            ", commentText='" + commentText + '\'' +
            ", Status=" + Status +
            '}';
    }
}
