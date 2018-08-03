package io.avand.web.rest.vm;

import io.avand.service.dto.CandidateDTO;
import io.avand.service.dto.UserDTO;

import java.io.Serializable;

public class CommentVM implements Serializable {

    private Long id;
    private String commentText;
    private boolean status;
    private CommentUserVM user;
    private CommentCandidateVM candidate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public CommentUserVM getUser() {
        return user;
    }

    public void setUser(CommentUserVM user) {
        this.user = user;
    }

    public CommentCandidateVM getCandidate() {
        return candidate;
    }

    public void setCandidate(CommentCandidateVM candidate) {
        this.candidate = candidate;
    }

    @Override
    public String toString() {
        return "CommentVM{" +
            "id=" + id +
            ", commentText='" + commentText + '\'' +
            ", status=" + status +
            ", user=" + user +
            ", candidate=" + candidate +
            '}';
    }
}
