package com.orakeloslomet.services.queue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class PlacementServiceImplTest {

    @Autowired
    private PlacementService placementService;

    @Test
    void test() {
    }

}