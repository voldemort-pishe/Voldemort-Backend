package io.avand.web.rest.vm;

import io.avand.domain.enumeration.JobStatus;

import javax.validation.constraints.NotNull;

public class JobStatusVM {
    @NotNull
    private Long id;

    @NotNull
    private JobStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "JobStatusVM{" +
            "id=" + id +
            ", status=" + status +
            '}';
    }
}
