package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.avand.domain.enumeration.CandidateState;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class CandidateDTO extends AbstractAuditingDTO implements Serializable {

    private String firstName;

    private String lastName;

    private CandidateState state;

    private String cellphone;

    private String email;

    private Long candidatePipeline;

    @JsonIgnore
    private Set<FeedbackDTO> feedback = new HashSet<>();

    @JsonIgnore
    private Set<CommentDTO> comments = new HashSet<>();

    @JsonIgnore
    private Set<CandidateEvaluationCriteriaDTO> candidateEvaluationCriteria = new HashSet<>();

    @JsonIgnore
    private Set<CandidateScheduleDTO> candidateSchedule = new HashSet<>();

    @JsonIgnore
    private Set<CandidateMessageDTO> candidateMessages = new HashSet<>();

    private Long fileId;

    @NotNull
    private Long jobId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public CandidateState getState() {
        return state;
    }

    public void setState(CandidateState state) {
        this.state = state;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCandidatePipeline() {
        return candidatePipeline;
    }

    public void setCandidatePipeline(Long candidatePipeline) {
        this.candidatePipeline = candidatePipeline;
    }

    public Set<FeedbackDTO> getFeedback() {
        return feedback;
    }

    public void setFeedback(Set<FeedbackDTO> feedback) {
        this.feedback = feedback;
    }

    public Set<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(Set<CommentDTO> comments) {
        this.comments = comments;
    }

    public Set<CandidateEvaluationCriteriaDTO> getCandidateEvaluationCriteria() {
        return candidateEvaluationCriteria;
    }

    public void setCandidateEvaluationCriteria(Set<CandidateEvaluationCriteriaDTO> candidateEvaluationCriteria) {
        this.candidateEvaluationCriteria = candidateEvaluationCriteria;
    }

    public Set<CandidateScheduleDTO> getCandidateSchedule() {
        return candidateSchedule;
    }

    public void setCandidateSchedule(Set<CandidateScheduleDTO> candidateSchedule) {
        this.candidateSchedule = candidateSchedule;
    }

    public Set<CandidateMessageDTO> getCandidateMessages() {
        return candidateMessages;
    }

    public void setCandidateMessages(Set<CandidateMessageDTO> candidateMessages) {
        this.candidateMessages = candidateMessages;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        return "CandidateDTO{" +
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", state=" + state +
            ", cellphone='" + cellphone + '\'' +
            ", email='" + email + '\'' +
            ", candidatePipeline=" + candidatePipeline +
            ", feedback=" + feedback +
            ", comments=" + comments +
            ", candidateEvaluationCriteria=" + candidateEvaluationCriteria +
            ", candidateSchedule=" + candidateSchedule +
            ", fileId=" + fileId +
            '}';
    }
}
