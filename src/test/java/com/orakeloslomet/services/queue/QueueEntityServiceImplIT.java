package com.orakeloslomet.services.queue;

import com.orakeloslomet.dtos.QueueEntityDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
@ComponentScan(basePackages = {"com.orakeloslomet.persistance.repositories"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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