package com.doubao.douding.common.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import java.io.IOException;
import java.io.Serial;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Johnson
 * @Description Jackson Config
 */
@Component
public class JacksonConfig {

    static final String DATE_PATTERN = "yyyy-MM-dd";
    static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    static final String TIMEZONE_SHANGHAI = "Asia/Shanghai";

    @Bean
    public ObjectMapper serializingObjectMapper() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
       DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        javaTimeModule.addSerializer(Instant.class, new CustomInstantDateSerializer());

        javaTimeModule.addDeserializer(Instant.class, new CustomInstantDateDeserializer());
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        return new ObjectMapper()
            .registerModule(new ParameterNamesModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(javaTimeModule);
    }

    public static class CustomInstantDateSerializer extends StdSerializer<Instant> {

        @Serial
        private static final long serialVersionUID = 1L;

        private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

        public CustomInstantDateSerializer() {
            this(null);
        }

        public CustomInstantDateSerializer(Class<Instant> t) {
            super(t);
        }

        @Override
        public void serialize(Instant instant, JsonGenerator jsonGenerator, SerializerProvider provider)
            throws IOException {
            if (instant == null) {
                return;
            }
            String jsonValue = FORMAT.format(instant.atZone(ZoneId.systemDefault()));
            jsonGenerator.writeString(jsonValue);
        }
    }

    public static class CustomInstantDateDeserializer extends StdDeserializer<Instant> {

        @Serial
        private static final long serialVersionUID = 1L;

        public CustomInstantDateDeserializer() {
            this(null);
        }

        public CustomInstantDateDeserializer(Class<Instant> clazz) {
            super(clazz);
        }

        @Override
        public Instant deserialize(final JsonParser p,
                                   final DeserializationContext ctxt) throws IOException {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN, Locale.CHINA);
            LocalDateTime localDateTime = LocalDateTime.parse(p.getText(), dateTimeFormatter);

            ZoneId zoneId = ZoneId.of(TIMEZONE_SHANGHAI);
            ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
            return zonedDateTime.toInstant();
        }
    }
}
