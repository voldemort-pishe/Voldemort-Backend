package io.avand.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CommentDTO extends AbstractAuditingDTO implements Serializable {

    private String commentText;

    private Boolean status;

    private Long userId;

    @NotNull
    private Long candidateId;

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
            ", commentText='" + commentText + '\'' +
            ", status=" + status +
            ", userId=" + userId +
            ", candidateId=" + candidateId +
            '}';
    }
}
