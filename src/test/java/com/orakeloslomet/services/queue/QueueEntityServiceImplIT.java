package com.orakeloslomet.services.queue;

import com.orakeloslomet.dtos.QueueEntityDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class QueueEntityServiceImplIT {

    @Autowired
    private QueueEntityServiceImpl queueEntityService;

    @Test
    void test() {
        final List<QueueEntityDTO> notDone = queueEntityService.findALlNotDone();
        final List<QueueEntityDTO> done = queueEntityService.findAllDone();

        System.out.println("NOT DONE!");
        notDone.forEach(System.out::println);

        System.out.println("DDNE");
        done.forEach(System.out::println);
    }

}