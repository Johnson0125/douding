package com.doubao.douding.util;

import com.doubao.douding.common.config.JacksonConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * @author Johnson
 */
public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(
                                                                            new JavaTimeModule()
                                                                                .addSerializer(Instant.class, new JacksonConfig.CustomInstantDateSerializer())
                                                                                .addDeserializer(Instant.class, new JacksonConfig.CustomInstantDateDeserializer())
                                                                        )
                                                                        .findAndRegisterModules()
                                                                        .disable(
                                                                            SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public JsonUtils() {
    }

    public static <K, V> Map<K, V> toMap(String json, Class<K> keyType, Class<V> valueType) {
        try {
            return OBJECT_MAPPER.readValue(
                json, new TypeReference<Map<K, V>>() {
                }
            );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <K, V> Map<K, V> toMap(String json, Type type) {
        TypeReference<Map<K, V>> typeReference = new TypeReference<Map<K, V>>() {

            public Type getType() {
                return type;
            }
        };
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> toList(String json, Class<T> type) {
        try {
            return OBJECT_MAPPER.readValue(
                json, new TypeReference<List<T>>() {
                }
            );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> toList(String json, Type type) {
        TypeReference<List<T>> typeReference = new TypeReference<List<T>>() {

            public Type getType() {
                return type;
            }
        };
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts a JSON string to a List of objects using the provided TypeReference.
     * Example usage: {@code TypeReference<List<UserInfoDTO>> listTypeReference = new
     * TypeReference<List<UserInfoDTO>>(){};}
     * @param json The JSON string to be converted.
     * @param type The TypeReference representing the target List type.
     * @param <T> The generic type of the elements in the List.
     * @return A List of objects parsed from the JSON string.
     * @throws RuntimeException If there is an exception during the deserialization
     * process.
     */
    public static <T> List<T> toList(String json, TypeReference<List<T>> type) {
        try {
            return OBJECT_MAPPER.readValue(json, type);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJsonString(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJsonString(Object object, String dateFormatPattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
        try {
            return OBJECT_MAPPER.writer(dateFormat).writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toObject(String json, Class<T> valueType) {
        try {
            return OBJECT_MAPPER.readValue(json, valueType);
        }
        catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    /**
     * Converts a JSON string to an object using the provided TypeReference. Example
     * usage: {@code JsonUtils.parseObject(response, new
     * TypeReference<ResultBean<UserInfo>>(){});}
     * @param str The JSON string to be converted.
     * @param type The TypeReference representing the target List type.
     * @param <T> The generic type of the elements in the List.
     * @return An object parsed from the JSON string.
     * @throws RuntimeException If there is an exception during the deserialization
     * process.
     */
    public static <T> T parseObject(String str, TypeReference<T> type) {
        try {
            return OBJECT_MAPPER.readValue(str, type);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
