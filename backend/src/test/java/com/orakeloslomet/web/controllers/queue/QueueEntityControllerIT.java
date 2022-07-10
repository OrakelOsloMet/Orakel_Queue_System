package com.orakeloslomet.web.controllers.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orakeloslomet.dtos.PlacementDTO;
import com.orakeloslomet.dtos.QueueEntityDTO;
import com.orakeloslomet.dtos.SubjectDTO;
import com.orakeloslomet.persistance.models.statistics.StatisticsEntity;
import com.orakeloslomet.persistance.repositories.statistics.StatisticsRepository;
import com.orakeloslomet.services.queue.PlacementService;
import com.orakeloslomet.services.queue.QueueEntityService;
import com.orakeloslomet.services.queue.SubjectService;
import com.orakeloslomet.utilities.constants.URLs;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class relies on entities beeing seed in DataSeeder.
 * Should be updated to create its own entities or at least muddy the context before writing more tests.
 *
 */


class QueueEntityControllerIT extends BaseControllerTest {

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
            final QueueEntityDTO responseDTO = new ObjectMapper().readValue(result.getResponse().getContentAsString(), QueueEntityDTO.class);
            assertAll("Assterting valid values in ResponseDTO",
                    () -> assertEquals(givenDTO.getName(), responseDTO.getName()),
                    () -> assertEquals(givenDTO.getSubject(), responseDTO.getSubject()),
                    () -> assertEquals(givenDTO.getPlacement(), responseDTO.getPlacement()),
                    () -> assertEquals(givenDTO.getComment(), responseDTO.getComment()),
                    () -> assertEquals(givenDTO.getStudyYear(), responseDTO.getStudyYear()),
                    () -> assertNotNull(responseDTO.getId()),
                    () -> assertNotNull(responseDTO.getCreatedDate()));
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

            //when
            mockMvc.perform(post(URLs.QUEUE_BASE_URL + URLs.QUEUE_CONFIRM_DONE_URL + givenDTO.getId()))
                    .andExpect(status().isAccepted());

            //then
            final StatisticsEntity statistics = statisticsRepository.findById(1l).orElseThrow();
            assertAll("Assterting valid values in ResponseDTO",
                    () -> assertEquals(givenDTO.getSubject().getId(), statistics.getSubject().getId()),
                    () -> assertEquals(givenDTO.getPlacement().getId(), statistics.getPlacement().getId()),
                    () -> assertEquals(givenDTO.getStudyYear(), statistics.getStudyYear())
            );
        }

    }


}