package com.orakeloslomet.web.controllers.queue;

import com.fasterxml.jackson.core.type.TypeReference;
import com.orakeloslomet.dtos.PlacementDTO;
import com.orakeloslomet.dtos.QueueEntityDTO;
import com.orakeloslomet.dtos.SubjectDTO;
import com.orakeloslomet.persistance.models.statistics.StatisticsEntity;
import com.orakeloslomet.persistance.repositories.statistics.StatisticsRepository;
import com.orakeloslomet.services.queue.PlacementService;
import com.orakeloslomet.services.queue.QueueEntityService;
import com.orakeloslomet.services.queue.SubjectService;
import com.orakeloslomet.utilities.DevDataLoader;
import com.orakeloslomet.utilities.constants.URLs;
import com.orakeloslomet.utilities.exceptions.NoSuchPersistedEntityException;
import com.orakeloslomet.web.controllers.WithMockUserAdmin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class relies on entities beeing seed in DevDataLoad.
 * @see DevDataLoader
 */

@DirtiesContext
class QueueEntityControllerIT extends BaseControllerIT {

    @Autowired
    private QueueEntityService queueEntityService;

    @Autowired
    private PlacementService placementService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private StatisticsRepository statisticsRepository;

    private PlacementDTO placementDTO;

    private SubjectDTO subjectDTO;

    @BeforeEach
    void setUp() {
        placementDTO = placementService.findById(1L);
        subjectDTO = subjectService.findById(1L);
    }

    @Nested
    class getAllQueueEntities {

        @Test
        void returnsAllQueueEntities() throws Exception {

            //when/then
            final MvcResult result = mockMvc.perform(get(URLs.QUEUE_BASE_URL))
                    .andExpect(status().isOk())
                    .andReturn();

            final String jsonResponse = result.getResponse().getContentAsString();
            final List<QueueEntityDTO> responseDTOs = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            assertEquals(DevDataLoader.seededQueueEntities.size(), responseDTOs.size());
            responseDTOs.forEach(Assertions::assertNotNull);
        }
    }

    @Nested
    class postQueueEntity {

        @Test
        @Transactional
        void givenValidDTO_thenIsCreated() throws Exception {
            //given
            final QueueEntityDTO givenDTO = QueueEntityDTO.builder()
                    .name("Fredrik Pedersen")
                    .subject(subjectDTO)
                    .placement(placementDTO)
                    .comment("Jeg er kul 8)")
                    .studyYear(2)
                    .build();

            //when
            final MvcResult result = mockMvc.perform(post(URLs.QUEUE_BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapToJson(givenDTO)))
                    .andExpect(status().isCreated())
                    .andReturn();

            //then
            final QueueEntityDTO responseDTO = parseResponseData(result, QueueEntityDTO.class);
            assertAll("Assterting valid values in ResponseDTO",
                    () -> assertEquals(givenDTO.getName(), responseDTO.getName()),
                    () -> assertEquals(givenDTO.getSubject(), responseDTO.getSubject()),
                    () -> assertEquals(givenDTO.getPlacement(), responseDTO.getPlacement()),
                    () -> assertEquals(givenDTO.getComment(), responseDTO.getComment()),
                    () -> assertEquals(givenDTO.getStudyYear(), responseDTO.getStudyYear()),
                    () -> assertNotNull(responseDTO.getId()),
                    () -> assertNotNull(responseDTO.getCreatedDate()));
        }

        @Test
        void givenInvalidDTO_isNotAcceptable() throws Exception {
            //given
            final QueueEntityDTO givenDTO = QueueEntityDTO.builder()
                    .name(null)
                    .subject(null)
                    .placement(placementDTO)
                    .comment("Jeg er kul 8)")
                    .studyYear(2)
                    .build();

            //when/then
            mockMvc.perform(post(URLs.QUEUE_BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapToJson(givenDTO)))
                    .andExpect(status().isNotAcceptable());

            assertEquals(DevDataLoader.seededQueueEntities.size(), queueEntityService.findAll().size());
        }
    }

    @Nested
    class confirmDone {

        @Test
        @Transactional
        @WithMockUserAdmin
        void givenValidId_doneDateIsSet() throws Exception {
            //given
            final QueueEntityDTO givenDTO = queueEntityService.findAll().get(0);
            final String url = URLs.QUEUE_BASE_URL + URLs.QUEUE_CONFIRM_DONE_URL + givenDTO.getId();

            //when
            final MvcResult result = mockMvc.perform(post(url))
                    .andExpect(status().isAccepted())
                    .andReturn();

            //then
            final Boolean responseValue = parseResponseData(result, Boolean.class);
            assertEquals(true, responseValue);

            final StatisticsEntity statistics = statisticsRepository.findById(1L).orElseThrow();
            assertAll("Assterting valid values in ResponseDTO",
                    () -> assertEquals(givenDTO.getSubject().getId(), statistics.getSubject().getId()),
                    () -> assertEquals(givenDTO.getPlacement().getId(), statistics.getPlacement().getId()),
                    () -> assertEquals(givenDTO.getStudyYear(), statistics.getStudyYear())
            );
        }

        @Test
        void givenInvalidUser_isUnauthorized() throws Exception {
            //given
            final QueueEntityDTO givenDTO = queueEntityService.findAll().get(0);
            final String url = URLs.QUEUE_BASE_URL + URLs.QUEUE_CONFIRM_DONE_URL + givenDTO.getId();

            //when/then
            mockMvc.perform(post(url)).andExpect(status().isUnauthorized());
        }

    }

    @Nested
    class deleteQueueEntity {

        @Test
        @Transactional
        @WithMockUserAdmin
        void givenValidIdValidUser_thenIsDeleted() throws Exception {
            //given
            final Long givenId = queueEntityService.findAll().get(0).getId();

            //when/then
            mockMvc.perform(delete(URLs.QUEUE_BASE_URL + givenId))
                    .andExpect(status().isOk());

            assertEquals(DevDataLoader.seededQueueEntities.size() - 1, queueEntityService.findAll().size());
            assertThrows(NoSuchPersistedEntityException.class, () -> queueEntityService.findById(givenId));
        }

        @Test
        @WithMockUserAdmin
        void givenInvalidIdValidUser_thenIsNotDeleted() throws Exception {
            //given
            final Long invalidId = 9999L;

            //when/then
            mockMvc.perform(delete(URLs.QUEUE_BASE_URL + invalidId))
                    .andExpect(status().isNotAcceptable());

            assertEquals(DevDataLoader.seededQueueEntities.size(), queueEntityService.findAll().size());
        }

        @Test
        void givenInvalidUser_isUnauthorized() throws Exception {
            mockMvc.perform(delete(URLs.QUEUE_BASE_URL + 1)).andExpect(status().isUnauthorized());
            assertEquals(DevDataLoader.seededQueueEntities.size(), queueEntityService.findAll().size());
        }
    }


}