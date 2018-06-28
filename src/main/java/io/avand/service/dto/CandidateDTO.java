package io.avand.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.avand.domain.enumeration.CandidateState;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class CandidateDTO implements Serializable {

    @NotNull
    private Long id;

    private String firstName;

    private String lastName;

    private CandidateState state;

    private String cellphone;

    private String email;

    private Long candidatePipeline;

    private Set<FeedbackDTO> feedbacks = new HashSet<>();

    private Set<CommentDTO> comments = new HashSet<>();

    private Set<CandidateEvaluationCriteriaDTO> candidateEvaluationCriteria = new HashSet<>();

    private Set<CandidateScheduleDTO> candidateSchedule = new HashSet<>();

    private FileDTO file;

    @JsonIgnore
    private JobDTO job;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Set<FeedbackDTO> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Set<FeedbackDTO> feedbacks) {
        this.feedbacks = feedbacks;
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

    public FileDTO getFile() {
        return file;
    }

    public void setFile(FileDTO file) {
        this.file = file;
    }

    public JobDTO getJob() {
        return job;
    }

    public void setJob(JobDTO job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "CandidateDTO{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", state=" + state +
            ", cellphone='" + cellphone + '\'' +
            ", email='" + email + '\'' +
            ", candidatePipeline=" + candidatePipeline +
            ", feedbacks=" + feedbacks +
            ", comments=" + comments +
            ", candidateEvaluationCriteria=" + candidateEvaluationCriteria +
            ", candidateSchedule=" + candidateSchedule +
            ", file=" + file +
            '}';
    }
}
