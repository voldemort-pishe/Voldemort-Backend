package hr.pishe.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

public class CalendarICSDTO implements Serializable {
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private String summery;
    private CalendarICSCompanyDTO company;
    private List<CalendarICSAttendeeDTO> attendeeDTOList;

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

    public String getSummery() {
        return summery;
    }

    public void setSummery(String summery) {
        this.summery = summery;
    }

    public CalendarICSCompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CalendarICSCompanyDTO company) {
        this.company = company;
    }

    public List<CalendarICSAttendeeDTO> getAttendeeDTOList() {
        return attendeeDTOList;
    }

    public void setAttendeeDTOList(List<CalendarICSAttendeeDTO> attendeeDTOList) {
        this.attendeeDTOList = attendeeDTOList;
    }

    @Override
    public String toString() {
        return "CalendarICSDTO{" +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", summery='" + summery + '\'' +
            ", attendeeDTOList=" + attendeeDTOList +
            '}';
    }
}
