package com.orakeloslomet.web.controllers.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.orakeloslomet.dtos.SubjectDTO;
import com.orakeloslomet.persistance.models.queue.ESemester;
import com.orakeloslomet.services.queue.SubjectService;
import com.orakeloslomet.utilities.DevDataLoader;
import com.orakeloslomet.utilities.constants.URLs;
import com.orakeloslomet.web.controllers.WithMockUserAdmin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SubjectControllerIT extends BaseControllerIT {

    @Autowired
    private SubjectService subjectService;

    @Nested
    class getSubjects {

        @Test
        void returnsAllSubjects() throws Exception {
            //when/then
            final MvcResult result = mockMvc.perform(get(URLs.SUBJECT_BASE_URL))
                    .andExpect(status().isOk())
                    .andReturn();

            final List<SubjectDTO> responseDTOs = parseResponseList(result);
            assertEquals(DevDataLoader.seededSubjects.size(), responseDTOs.size());
            responseDTOs.forEach(Assertions::assertNotNull);
        }
    }

    @Nested
    class getSubjectsCurrentSemester {

        @Test
        void returnsListOfCurrentSemesterSubjects() throws Exception {
            //given
            final ESemester expectedSemester = ESemester.currentSemester(Month.from(ZonedDateTime.now(ZoneId.of("Europe/Paris"))));

            //when/then
            final MvcResult result = mockMvc.perform(get(URLs.SUBJECT_BASE_URL + "current"))
                    .andExpect(status().isOk())
                    .andReturn();

            final List<SubjectDTO> responseDTOs = parseResponseList(result);
            responseDTOs.forEach(responseDTO -> {
                assertNotNull(responseDTO);
                assertEquals(expectedSemester.label, responseDTO.getSemester());
            });
        }
    }

    @Nested
    class postSubject {

        @Test
        @Transactional
        @WithMockUserAdmin
        void givenValidDtoValidUser_thenIsCreated() throws Exception {
            //given
            final SubjectDTO givenDTO = SubjectDTO.builder()
                    .name("Spring Boot 1000")
                    .semester(ESemester.AUTUMN.label)
                    .build();

            //when/then
            final MvcResult result = mockMvc.perform(post(URLs.SUBJECT_BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapToJson(givenDTO)))
                    .andExpect(status().isCreated())
                    .andReturn();

            final SubjectDTO responseDTO = parseResponseData(result, SubjectDTO.class);
            assertAll("Asserting valid values in ResponseDTO",
                    () -> assertEquals(givenDTO.getName(), responseDTO.getName()),
                    () -> assertEquals(givenDTO.getSemester(), responseDTO.getSemester()),
                    () -> assertNotNull(givenDTO.getId()),
                    () -> assertNotNull(givenDTO.getCreatedDate()));
        }

        @Test
        void givenValidDTOInvalidUser_isNotAcceptableAndNotCreated() throws Exception {
            //given
            final SubjectDTO givenDTO = SubjectDTO.builder()
                    .name("Spring Boot 1000")
                    .semester(ESemester.AUTUMN.name())
                    .build();

            //when/then
            mockMvc.perform(post(URLs.SUBJECT_BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapToJson(givenDTO)))
                    .andExpect(status().isUnauthorized())
                    .andReturn();

            assertEquals(DevDataLoader.seededSubjects.size(), subjectService.findAll().size());
        }

        @Test
        @WithMockUserAdmin
        void givenInvalidDTOValidUser_isNotAcceptableAndNotCreated() throws Exception {
            //given
            final SubjectDTO givenDTO = SubjectDTO.builder()
                    .name(null)
                    .semester(ESemester.AUTUMN.name())
                    .build();

            //when/then
            mockMvc.perform(post(URLs.SUBJECT_BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapToJson(givenDTO)))
                    .andExpect(status().isNotAcceptable())
                    .andReturn();

            assertEquals(DevDataLoader.seededSubjects.size(), subjectService.findAll().size());
        }
    }

    @Nested
    class editSubject {
    }

    @Nested
    class deleteSubject {
    }

    private List<SubjectDTO> parseResponseList(final MvcResult mvcResult) throws JsonProcessingException, UnsupportedEncodingException {
        final String jsonResponse = mvcResult.getResponse().getContentAsString();
        return objectMapper.readValue(jsonResponse, new TypeReference<>(){});
    }
}