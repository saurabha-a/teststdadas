package com.abhinavsaurabh.commons.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.jooq.Converter;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class JOOQJSONConverter implements Converter<String, Map> {

    private static final long serialVersionUID = 8619911328767907526L ;

    private static final Logger logger = LoggerFactory.getLogger(JOOQJSONConverter.class);

    @Override
    public Map from(String databaseObject) {

        if (databaseObject == null) return Collections.emptyMap();

        ObjectMapper om = new ObjectMapper();
        Map<String, Object> value = null;
        try {
            value = om.readValue(databaseObject, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            logger.error("Unable to convert {} to object.", databaseObject, e);
        }
        return value;
    }

    @Override
    public String to(Map userObject) {

        if (userObject == null) return null;

        ObjectMapper om = new ObjectMapper();
        String value = null;
        try {
            value = om.writeValueAsString(userObject);
        } catch (JsonProcessingException e) {
            logger.error("Unable to convert {} to string.", userObject, e);
        }
        return value;
    }

    @Override
    public Class<String> fromType() {
        return String.class;
    }

    @Override
    public Class<Map> toType() {
        return Map.class;
    }

}
