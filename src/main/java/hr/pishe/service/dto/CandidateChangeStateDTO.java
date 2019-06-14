package hr.pishe.service.dto;

import hr.pishe.domain.enumeration.CandidateState;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CandidateChangeStateDTO implements Serializable {

    @NotNull
    private CandidateState state;

    private Long pipeline;

    public CandidateState getState() {
        return state;
    }

    public void setState(CandidateState state) {
        this.state = state;
    }

    public Long getPipeline() {
        return pipeline;
    }

    public void setPipeline(Long pipeline) {
        this.pipeline = pipeline;
    }

    @Override
    public String toString() {
        return "CandidateChangeStateDTO{" +
            "state=" + state +
            ", pipeline=" + pipeline +
            '}';
    }
}
