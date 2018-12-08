package io.avand.service;

import io.avand.service.dto.CalendarICSDTO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

public interface CalendarService {
    String createICSFile(CalendarICSDTO calendarICSDTO) throws URISyntaxException, IOException;
}
