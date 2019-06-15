package hr.pishe.web.rest.vm;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class CandidateScheduleDateVM implements Serializable {
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "CandidateScheduleDateVM{" +
            "startDate=" + startDate +
            ", endDate=" + endDate +
            '}';
    }
}
