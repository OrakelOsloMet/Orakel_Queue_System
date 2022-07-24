package com.orakeloslomet.web.controllers.queue;

import com.fasterxml.jackson.core.type.TypeReference;
import com.orakeloslomet.dtos.PlacementDTO;
import com.orakeloslomet.services.queue.PlacementService;
import com.orakeloslomet.utilities.DevDataLoader;
import com.orakeloslomet.utilities.constants.URLs;
import com.orakeloslomet.utilities.exceptions.NoSuchPersistedEntityException;
import com.orakeloslomet.web.controllers.WithMockUserAdmin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext
class PlacementControllerIT extends BaseControllerIT {

    @Autowired
    private PlacementService placementService;

    @Nested
    class getPlacements {

        @Test
        void returnsAllPlacements() throws Exception {
            //when/then
            final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URLs.PLACEMENT_BASE_URL))
                    .andExpect(status().isOk())
                    .andReturn();

            final String jsonResponse = result.getResponse().getContentAsString();
            final List<PlacementDTO> responseDTOs = objectMapper.readValue(jsonResponse, new TypeReference<>() {});
            assertEquals(DevDataLoader.seededPlacements.size(), responseDTOs.size());
            responseDTOs.forEach(Assertions::assertNotNull);
        }
    }

    @Nested
    class postPlacement {

        @Test
        @Transactional
        @WithMockUserAdmin
        void givenValidDTOValidUser_thenIsCreated() throws Exception {
            //given
            final PlacementDTO givenDTO = PlacementDTO.builder()
                    .name("Placements")
                    .number(1)
                    .build();

            //when/then
            final MvcResult result = mockMvc.perform(post(URLs.PLACEMENT_BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapToJson(givenDTO)))
                    .andExpect(status().isCreated())
                    .andReturn();

            final PlacementDTO responseDTO = parseResponseData(result, PlacementDTO.class);
            assertAll("Asserting valid values in ResponseDTO",
                    () -> assertEquals(givenDTO.getName(), responseDTO.getName()),
                    () -> assertEquals(givenDTO.getNumber(), responseDTO.getNumber()),
                    () -> assertNotNull(responseDTO.getId()),
                    () -> assertNotNull(responseDTO.getCreatedDate()));
        }

        @Test
        void givenValidDTOInvalidUser_thenIsUnauthorizedAndNotCreated() throws Exception {
            //given
            final PlacementDTO givenDTO = PlacementDTO.builder()
                    .name("Placements")
                    .number(1)
                    .build();

            //when/then
            mockMvc.perform(post(URLs.PLACEMENT_BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapToJson(givenDTO)))
                    .andExpect(status().isUnauthorized())
                    .andReturn();

            assertEquals(DevDataLoader.seededPlacements.size(), placementService.findAll().size());
        }

        @Test
        @WithMockUserAdmin
        void givenInvalidDTOValidUser_thenIsNotAcceptableAndNotCreated() throws Exception {
            //given
            final PlacementDTO givenDTO = PlacementDTO.builder()
                    .name(null)
                    .number(1)
                    .build();

            //when/then
            mockMvc.perform(post(URLs.PLACEMENT_BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapToJson(givenDTO)))
                    .andExpect(status().isNotAcceptable());

            assertEquals(DevDataLoader.seededPlacements.size(), placementService.findAll().size());
        }
    }

    @Nested
    class editPlacement {

        private final String EDIT_URL = URLs.PLACEMENT_BASE_URL + "edit/";
        private final Long ID = 1L;

        @Test
        @Transactional
        @WithMockUserAdmin
        void givenValidDTOValidUser_thenIsCreatedIdAndCreatedDateNotUpdated() throws Exception {
            //given
            final PlacementDTO originalDTO = placementService.findById(ID);
            final PlacementDTO updatedDTO = PlacementDTO.builder()
                    .id(100L)
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .name("Edited Name")
                    .number(1337)
                    .build();

            //when/then
            final MvcResult result = mockMvc.perform(put(EDIT_URL + ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapToJson(updatedDTO)))
                    .andExpect(status().isCreated())
                    .andReturn();

            final PlacementDTO responseDTO = parseResponseData(result, PlacementDTO.class);
            final PlacementDTO updatedSavedDTO = placementService.findById(ID);
            assertAll("Asserting ResponseDTO is mapped correctly",
                    () -> assertEquals(updatedDTO.getName(), responseDTO.getName()),
                    () -> assertEquals(updatedDTO.getNumber(), responseDTO.getNumber()),
                    () -> assertEquals(originalDTO.getId(), responseDTO.getId()),
                    () -> assertEquals(originalDTO.getCreatedDate(), responseDTO.getCreatedDate()));

            assertAll("Asserting updated saved entity has correct fields updated",
                    () -> assertEquals(updatedDTO.getName(), updatedSavedDTO.getName()),
                    () -> assertEquals(updatedDTO.getNumber(), updatedSavedDTO.getNumber()),
                    () -> assertEquals(originalDTO.getId(), updatedSavedDTO.getId()),
                    () -> assertEquals(originalDTO.getCreatedDate(), updatedSavedDTO.getCreatedDate()));

            assertEquals(DevDataLoader.seededPlacements.size(), placementService.findAll().size());
        }

        @Test
        @WithMockUserAdmin
        void givenValidDTOValidUserInvalidID_thenIsNotAcceptable() throws Exception {
            //given
            final Long invalidID = 9999L;
            final PlacementDTO updatedDTO = PlacementDTO.builder()
                    .id(100L)
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .name("Edited Name")
                    .number(1337)
                    .build();

            //when/then
            mockMvc.perform(put(EDIT_URL + invalidID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapToJson(updatedDTO)))
                    .andExpect(status().isNotAcceptable());
        }

        @Test
        @WithMockUserAdmin
        void givenInvalidDTOValidUser_thenIsNotAcceptableAndNotUpdated() throws Exception {
            //given
            final PlacementDTO originalDTO = placementService.findById(ID);
            final PlacementDTO updatedDTO = PlacementDTO.builder()
                    .id(100L)
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .name(null)
                    .number(1337)
                    .build();

            //when/then
            mockMvc.perform(put(EDIT_URL + ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapToJson(updatedDTO)))
                    .andExpect(status().isNotAcceptable())
                    .andReturn();

            final PlacementDTO updatedSavedDTO = placementService.findById(ID);
            assertEquals(originalDTO, updatedSavedDTO);
        }

        @Test
        void givenValidDTOInvalidUser_thenIsUnauthorizedAndNotUpdated() throws Exception {
            //given
            final PlacementDTO originalDTO = placementService.findById(ID);
            final PlacementDTO updatedDTO = PlacementDTO.builder()
                    .id(100L)
                    .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                    .name("Edited Name")
                    .number(1337)
                    .build();

            //when/then
            mockMvc.perform(put(EDIT_URL + ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapToJson(updatedDTO)))
                    .andExpect(status().isUnauthorized())
                    .andReturn();

            final PlacementDTO updatedSavedDTO = placementService.findById(ID);
            assertEquals(originalDTO, updatedSavedDTO);
        }
    }

    @Nested
    class deletePlacement {

        private final Long ID = 1L;
        private final String DELETE_URL = URLs.PLACEMENT_BASE_URL + "delete/";

        @Test
        @Transactional
        @WithMockUserAdmin
        void givenValidIdValidUser_thenIsDeleted() throws Exception {
            //when/then
            mockMvc.perform(delete(DELETE_URL + ID))
                    .andExpect(status().isOk());

            assertThrows(NoSuchPersistedEntityException.class, () -> placementService.findById(ID));
        }

        @Test
        @WithMockUserAdmin
        void givenInvalidIdValidUser_thenIsNotDeleted() throws Exception {
            //given
            final Long invalidId = 9999L;

            //when/then
            mockMvc.perform(delete(DELETE_URL + invalidId))
                    .andExpect(status().isNotAcceptable());

            assertEquals(DevDataLoader.seededPlacements.size(), placementService.findAll().size());
        }

        @Test
        void givenInvalidUser_isUnauthorized() throws Exception {
            mockMvc.perform(delete(DELETE_URL + ID)).andExpect(status().isUnauthorized());
            assertEquals(DevDataLoader.seededPlacements.size(), placementService.findAll().size());
        }
    }
}