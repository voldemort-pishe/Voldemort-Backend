package io.avand.service.impl;

import io.avand.service.CalendarService;
import io.avand.service.dto.CalendarICSAttendeeDTO;
import io.avand.service.dto.CalendarICSDTO;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.Role;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.RandomUidGenerator;
import net.fortuna.ical4j.util.UidGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

@Service
public class CalendarServiceImpl implements CalendarService {

    private final Logger log = LoggerFactory.getLogger(CalendarServiceImpl.class);

    @Override
    public String createICSFile(CalendarICSDTO calendarICSDTO) throws URISyntaxException, IOException {

        log.debug("Request to create ics file for : {}", calendarICSDTO);

        TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
        TimeZone timezone = registry.getTimeZone("Asia/Tehran");
        VTimeZone tz = timezone.getVTimeZone();

        DateTime start = new DateTime(Date.from(calendarICSDTO.getStartDate().toInstant()));
        DateTime end = new DateTime(Date.from(calendarICSDTO.getEndDate().toInstant()));

        VEvent meeting = new VEvent(start, end, calendarICSDTO.getSummery());

        meeting.getProperties().add(new Location(calendarICSDTO.getCompany().getAddress()));

        Organizer organizer = new Organizer("mailto:" + calendarICSDTO.getCompany().getEmail());
        organizer.getParameters().add(new Cn(calendarICSDTO.getCompany().getName()));
        meeting.getProperties().add(organizer);

        meeting.getProperties().add(tz.getTimeZoneId());
        UidGenerator ug = new RandomUidGenerator();
        Uid uid = ug.generateUid();
        meeting.getProperties().add(uid);

        for (CalendarICSAttendeeDTO calendarICSAttendeeDTO : calendarICSDTO.getAttendeeDTOList()) {
            Attendee attendee = new Attendee(URI.create(String.format("mailto:%s", calendarICSAttendeeDTO.getEmail())));
            attendee.getParameters().add(Role.REQ_PARTICIPANT);
            attendee.getParameters().add(new Cn(calendarICSAttendeeDTO.getName()));
            meeting.getProperties().add(attendee);
        }

        meeting.getProperties().add(Status.VEVENT_CONFIRMED);
        meeting.getProperties().add(Transp.OPAQUE);

        net.fortuna.ical4j.model.Calendar icsCalendar = new net.fortuna.ical4j.model.Calendar();
        icsCalendar.getProperties().add(new ProdId("-//Events Calendar//iCal4j 1.0//EN"));
        icsCalendar.getProperties().add(CalScale.GREGORIAN);
        icsCalendar.getProperties().add(Version.VERSION_2_0);
        icsCalendar.getProperties().add(Method.REQUEST);

        icsCalendar.getComponents().add(meeting);

        System.out.println(icsCalendar);

        FileOutputStream fileOutputStream = new FileOutputStream(String.valueOf(calendarICSDTO.getStartDate().toEpochSecond()) + ".ics");
        CalendarOutputter calendarOutputter = new CalendarOutputter();
        calendarOutputter.output(icsCalendar, fileOutputStream);

        return String.valueOf(calendarICSDTO.getStartDate().toEpochSecond()) + ".ics";
    }
}
