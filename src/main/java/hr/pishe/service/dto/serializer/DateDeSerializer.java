package hr.pishe.service.dto.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import hr.pishe.service.util.date.DateUtil;

import java.io.IOException;
import java.time.ZonedDateTime;

public class DateDeSerializer extends JsonSerializer<ZonedDateTime> {
    @Override
    public void serialize(ZonedDateTime zonedDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        String date = DateUtil.gregorianDateToFullPersianTextWithoutDayName(DateUtil.toDate(zonedDateTime));
        jsonGenerator.writeString(date);
    }
}
