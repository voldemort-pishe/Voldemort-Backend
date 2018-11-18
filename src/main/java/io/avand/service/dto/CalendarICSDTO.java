package io.avand.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

public class CalendarICSDTO implements Serializable {
    private ZonedDateTime meetingDate;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private String summery;
    private List<CalendarICSAttendeeDTO> attendeeDTOList;

    public ZonedDateTime getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(ZonedDateTime meetingDate) {
        this.meetingDate = meetingDate;
    }

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

    public List<CalendarICSAttendeeDTO> getAttendeeDTOList() {
        return attendeeDTOList;
    }

    public void setAttendeeDTOList(List<CalendarICSAttendeeDTO> attendeeDTOList) {
        this.attendeeDTOList = attendeeDTOList;
    }

    @Override
    public String toString() {
        return "CalendarICSDTO{" +
            "meetingDate=" + meetingDate +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", summery='" + summery + '\'' +
            ", attendeeDTOList=" + attendeeDTOList +
            '}';
    }
}
