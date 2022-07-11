package com.orakeloslomet.web.controllers.queue;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.orakeloslomet.utilities.constants.Profiles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 30/09/2021 at 14:43
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(Profiles.TEST)
public abstract class BaseControllerTest {

    /**
     * @param object POJO to be mapped to a JSON object
     * @return JSON mapping of the  param object as a String
     * @throws JsonProcessingException if the conversion fails
     */
    protected String mapToJson(final Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    /**
     * @param json  JSON object to be converted to a POJO
     * @param clazz Provides the class which is going to be used as a template for mapping the JSON object
     * @param <T>   Generic enabling us to return whatever Java class we want
     * @return A POJO of the specified class
     * @throws JsonParseException   if the conversion fails
     * @throws JsonMappingException if the conversion fails
     * @throws IOException          if the conversion fails
     */
    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {
        return new ObjectMapper().readValue(json, clazz);
    }
}
