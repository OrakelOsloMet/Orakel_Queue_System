package com.orakeloslomet.web.controllers.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orakeloslomet.dtos.PlacementDTO;
import com.orakeloslomet.dtos.QueueEntityDTO;
import com.orakeloslomet.persistance.repositories.PlacementRepository;
import com.orakeloslomet.services.queue.QueueEntityService;
import com.orakeloslomet.utilities.DataLoader;
import com.orakeloslomet.utilities.constants.Profiles;
import com.orakeloslomet.utilities.constants.URLs;
import com.orakeloslomet.utilities.mappers.PlacementMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class relies on entities beeing seed in DataSeeder.
 * Should be updated to create its own entities or at least muddy the context before writing more tests.
 *
 */

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(Profiles.TEST)
class QueueEntityControllerIT extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QueueEntityService queueEntityService;

    @Autowired
    private PlacementRepository placementRepository;

    @Autowired
    private PlacementMapper placementMapper;

    private PlacementDTO placementDTO;

    @BeforeEach
    void setUp() {
        placementDTO = placementRepository.findById(1L).map(placementMapper::toDto).orElseThrow();
    }


    @Nested
    class postQueueEntity {

        @Test
        void givenValidDTO_whenPosted_thenIsCreated() throws Exception {

            //given
            final QueueEntityDTO givenDTO = QueueEntityDTO.builder()
                    .name("Fredrik Pedersen")
                    .subject("Programmering")
                    .placement(placementDTO)
                    .comment("Jeg er kul 8)")
                    .studyYear(2)
                    .digitalConsultation(false)
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
                    () -> assertEquals(givenDTO.isDigitalConsultation(), responseDTO.isDigitalConsultation()),
                    () -> assertNotNull(responseDTO.getId()));
        }
    }

    @Nested
    class confirmDone {

        @Test
        @WithMockUser(roles = "ADMIN")
        void givenValidId_whenConfirmedDone_doneDateIsSet() throws Exception {

            //given
            final QueueEntityDTO givenDTO = queueEntityService.findAll().get(0);

            //when
            mockMvc.perform(post(URLs.QUEUE_BASE_URL + URLs.QUEUE_CONFIRM_DONE_URL + givenDTO.getId()))
                    .andExpect(status().isAccepted());

            //then
            final QueueEntityDTO updatedEntity = queueEntityService.findById(givenDTO.getId());
            assertAll("Assterting valid values in ResponseDTO",
                    () -> assertEquals(givenDTO.getId(), updatedEntity.getId()),
                    () -> assertEquals(givenDTO.getName(), updatedEntity.getName()),
                    () -> assertEquals(givenDTO.getSubject(), updatedEntity.getSubject()),
                    () -> assertEquals(givenDTO.getPlacement(), updatedEntity.getPlacement()),
                    () -> assertEquals(givenDTO.getComment(), updatedEntity.getComment()),
                    () -> assertEquals(givenDTO.getStudyYear(), updatedEntity.getStudyYear()),
                    () -> assertEquals(givenDTO.isDigitalConsultation(), updatedEntity.isDigitalConsultation()));
        }

    }


}