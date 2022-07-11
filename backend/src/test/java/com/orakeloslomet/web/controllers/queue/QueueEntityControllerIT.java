package com.orakeloslomet.web.controllers.queue;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class relies on entities beeing seed in DevDataLoad.
 * @see DevDataLoader
 */

//@DirtiesContext
class QueueEntityControllerIT extends BaseControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

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
        void whenCalled_returnsAllQueueEntities() throws Exception {

            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URLs.QUEUE_BASE_URL))
                    .andExpect(status().isOk())
                    .andReturn();

            final String jsonResponse = result.getResponse().getContentAsString();
            final List<QueueEntityDTO> responseDTOs = objectMapper.readValue(jsonResponse, new TypeReference<>() {
            });
            assertEquals(DevDataLoader.seededQueueEntities.size(), responseDTOs.size());
            responseDTOs.forEach(Assertions::assertNotNull);
        }
    }

    @Nested
    class postQueueEntity {

        @Test
        @Transactional
        void givenValidDTO_whenPosted_thenIsCreated() throws Exception {
            //given
            final QueueEntityDTO givenDTO = QueueEntityDTO.builder()
                    .name("Fredrik Pedersen")
                    .subject(subjectDTO)
                    .placement(placementDTO)
                    .comment("Jeg er kul 8)")
                    .studyYear(2)
                    .build();

            //when
            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URLs.QUEUE_BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapToJson(givenDTO)))
                    .andExpect(status().isCreated())
                    .andReturn();

            //then
            final String jsonResponse = result.getResponse().getContentAsString();
            final QueueEntityDTO responseDTO = objectMapper.readValue(jsonResponse, QueueEntityDTO.class);
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
        void givenInvalidDTO_whenPosted_isNotAcceptable() throws Exception {
            //given
            final QueueEntityDTO givenDTO = QueueEntityDTO.builder()
                    .name(null)
                    .subject(null)
                    .placement(placementDTO)
                    .comment("Jeg er kul 8)")
                    .studyYear(2)
                    .build();

            //when/then
            mockMvc.perform(MockMvcRequestBuilders.post(URLs.QUEUE_BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapToJson(givenDTO)))
                    .andExpect(status().isNotAcceptable());
        }
    }

    @Nested
    class confirmDone {

        @Test
        @Transactional
        @WithMockUser(roles = "ADMIN")
        void givenValidId_whenConfirmedDone_doneDateIsSet() throws Exception {
            //given
            final QueueEntityDTO givenDTO = queueEntityService.findAll().get(0);
            final String url = URLs.QUEUE_BASE_URL + URLs.QUEUE_CONFIRM_DONE_URL + givenDTO.getId();

            //when
            final MvcResult result = mockMvc.perform(post(url))
                    .andExpect(status().isAccepted())
                    .andReturn();

            //then
            final Boolean responseValue = objectMapper.readValue(result.getResponse().getContentAsString(), Boolean.class);
            assertEquals(true, responseValue);

            final StatisticsEntity statistics = statisticsRepository.findById(1L).orElseThrow();
            assertAll("Assterting valid values in ResponseDTO",
                    () -> assertEquals(givenDTO.getSubject().getId(), statistics.getSubject().getId()),
                    () -> assertEquals(givenDTO.getPlacement().getId(), statistics.getPlacement().getId()),
                    () -> assertEquals(givenDTO.getStudyYear(), statistics.getStudyYear())
            );
        }

        @Test
        void givenInvalidUser_whenCalled_isUnauthorized() throws Exception {
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
        @WithMockUser(roles = "ADMIN")
        void givenValidId_whenCalled_EntityIsDeleted() throws Exception {
            //given
            final Long givenId = queueEntityService.findAll().get(0).getId();

            //when/then
            mockMvc.perform(delete(URLs.QUEUE_BASE_URL + givenId)).andExpect(status().isOk());
        }

        @Test
        void givenInvalidUser_whenCalled_isUnauthorized() throws Exception {
            mockMvc.perform(delete(URLs.QUEUE_BASE_URL + 1)).andExpect(status().isUnauthorized());
        }
    }


}