package com.orakeloslomet.web.controllers.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.orakeloslomet.dtos.PlacementDTO;
import com.orakeloslomet.dtos.SubjectDTO;
import com.orakeloslomet.persistance.models.queue.ESemester;
import com.orakeloslomet.services.queue.SubjectService;
import com.orakeloslomet.utilities.DevDataLoader;
import com.orakeloslomet.utilities.constants.URLs;
import com.orakeloslomet.utilities.exceptions.NoSuchPersistedEntityException;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
            System.out.println(responseDTO);
            assertAll("Asserting valid values in ResponseDTO",
                    () -> assertEquals(givenDTO.getName(), responseDTO.getName()),
                    () -> assertEquals(givenDTO.getSemester(), responseDTO.getSemester()),
                    () -> assertNotNull(responseDTO.getId()),
                    () -> assertNotNull(responseDTO.getCreatedDate()));
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

        private final String EDIT_URL = URLs.SUBJECT_BASE_URL + "edit/";
        private final Long ID = 1L;

        @Test
        @Transactional
        @WithMockUserAdmin
        void givenValidDTOValidUser_thenIsCreatedAndCreatedDateNotUpdated() throws Exception {
            //given
            final SubjectDTO originalDTO = subjectService.findById(ID);
            final SubjectDTO updatedDTO = SubjectDTO.builder()
                    .id(100L)
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .name("Edited name")
                    .semester(ESemester.AUTUMN.label)
                    .build();

            //when/then
            final MvcResult result = mockMvc.perform(put(EDIT_URL + ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapToJson(updatedDTO)))
                    .andExpect(status().isCreated())
                    .andReturn();

            final SubjectDTO responseDTO = parseResponseData(result, SubjectDTO.class);
            final SubjectDTO updatedSavedDTO = subjectService.findById(ID);
            assertAll("Asserting ResponseDTO is mapped correctly",
                    () -> assertEquals(updatedDTO.getName(), responseDTO.getName()),
                    () -> assertEquals(updatedDTO.getSemester(), responseDTO.getSemester()),
                    () -> assertEquals(originalDTO.getId(), responseDTO.getId()),
                    () -> assertEquals(originalDTO.getCreatedDate(), responseDTO.getCreatedDate()));

            assertAll("Asserting updated saved entity has correct fields updated",
                    () -> assertEquals(updatedDTO.getName(), updatedSavedDTO.getName()),
                    () -> assertEquals(updatedDTO.getSemester(), updatedSavedDTO.getSemester()),
                    () -> assertEquals(originalDTO.getId(), updatedSavedDTO.getId()),
                    () -> assertEquals(originalDTO.getCreatedDate(), updatedSavedDTO.getCreatedDate()));

            assertEquals(DevDataLoader.seededSubjects.size(), subjectService.findAll().size());
        }

        @Test
        @WithMockUserAdmin
        void givenValidDTOValidUserInvalidID_thenIsNotAcceptable() throws Exception {
            //given
            final Long invalidId = 9999L;
            final SubjectDTO updatedDTO = SubjectDTO.builder()
                    .id(100L)
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .name("Edited name")
                    .semester(ESemester.AUTUMN.label)
                    .build();

            //when/then
            mockMvc.perform(put(EDIT_URL + invalidId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapToJson(updatedDTO)))
                    .andExpect(status().isNotAcceptable());
        }

        @Test
        @WithMockUserAdmin
        void givenInvalidDTOValidUser_thenIsNotAcceptableAndNotUpdated() throws Exception {
            //given
            final SubjectDTO originalDTO = subjectService.findById(ID);
            final SubjectDTO updatedDTO = SubjectDTO.builder()
                    .id(100L)
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .name(null)
                    .semester(ESemester.AUTUMN.label)
                    .build();

            //when/then
            mockMvc.perform(put(EDIT_URL + ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapToJson(updatedDTO)))
                    .andExpect(status().isNotAcceptable())
                    .andReturn();

            final SubjectDTO updatedSavedDTO = subjectService.findById(ID);
            assertEquals(originalDTO, updatedSavedDTO);
        }

        @Test
        void givenValidDTOInvalidUser_thenIsUnauthorizedAndNotUpdated() throws Exception {
            //given
            final SubjectDTO originalDTO = subjectService.findById(ID);
            final SubjectDTO updatedDTO = SubjectDTO.builder()
                    .id(100L)
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .name("Edited Name")
                    .semester(ESemester.AUTUMN.label)
                    .build();

            //when/then
            mockMvc.perform(put(EDIT_URL + ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapToJson(updatedDTO)))
                    .andExpect(status().isUnauthorized())
                    .andReturn();

            final SubjectDTO updatedSavedDTO = subjectService.findById(ID);
            assertEquals(originalDTO, updatedSavedDTO);
        }
    }

    @Nested
    class deleteSubject {
        private final Long ID = 1L;
        private final String DELETE_URL = URLs.SUBJECT_BASE_URL + "delete/";

        @Test
        @Transactional
        @WithMockUserAdmin
        void givenValidIdValidUser_thenIsDeleted() throws Exception {
            //when/then
            mockMvc.perform(delete(DELETE_URL + ID))
                    .andExpect(status().isOk());

            assertThrows(NoSuchPersistedEntityException.class, () -> subjectService.findById(ID));
        }

        @Test
        @WithMockUserAdmin
        void givenInvalidIdValidUser_thenIsNotDeleted() throws Exception {
            //given
            final Long invalidId = 9999L;

            //when/then
            mockMvc.perform(delete(DELETE_URL + invalidId))
                    .andExpect(status().isNotAcceptable());

            assertEquals(DevDataLoader.seededSubjects.size(), subjectService.findAll().size());
        }

        @Test
        void givenInvalidUser_isUnauthorized() throws Exception {
            mockMvc.perform(delete(DELETE_URL + ID)).andExpect(status().isUnauthorized());
            assertEquals(DevDataLoader.seededSubjects.size(), subjectService.findAll().size());
        }
    }

    private List<SubjectDTO> parseResponseList(final MvcResult mvcResult) throws JsonProcessingException, UnsupportedEncodingException {
        final String jsonResponse = mvcResult.getResponse().getContentAsString();
        return objectMapper.readValue(jsonResponse, new TypeReference<>(){});
    }
}