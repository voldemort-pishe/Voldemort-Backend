package hr.pishe.service;

import hr.pishe.service.dto.CalendarICSDTO;

import java.io.IOException;
import java.net.URISyntaxException;

public interface CalendarService {
    String createICSFile(CalendarICSDTO calendarICSDTO) throws URISyntaxException, IOException;
}
