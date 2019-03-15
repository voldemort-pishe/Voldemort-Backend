package hr.pishe.web.rest.vm;

import java.io.Serializable;

public class CandidateMessageVM implements Serializable {
    private String subject;
    private String message;
    private Long parent;
    private Long candidateId;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }

    @Override
    public String toString() {
        return "CandidateMessageVM{" +
            "subject='" + subject + '\'' +
            ", message='" + message + '\'' +
            ", parent=" + parent +
            ", candidateId=" + candidateId +
            '}';
    }
}
