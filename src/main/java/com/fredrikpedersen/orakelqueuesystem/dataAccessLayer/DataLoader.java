package com.fredrikpedersen.orakelqueuesystem.dataAccessLayer;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.QueueEntity;
import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.repositories.QueueEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 20/09/2020 at 21:37
 */

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    private QueueEntityRepository entityRepository;

    public DataLoader(final QueueEntityRepository queueEntityRepository) {
        this.entityRepository = queueEntityRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Seeding data...");
        seedEntities();
        log.info("Seeding done!");
    }

    private void seedEntities() {
        log.info("Seeding Queue Entities");
        QueueEntity queueEntity1 = new QueueEntity("Fredrik", "Programmering", 1, true);
        QueueEntity queueEntity2 = new QueueEntity("Ana-Maria", "Algoritmer", 2, false);
        QueueEntity queueEntity3 = new QueueEntity("Maria", "Diskret Mattematikk", 1, false);

        entityRepository.save(queueEntity1);
        entityRepository.save(queueEntity2);
        entityRepository.save(queueEntity3);
        log.info("Done seeding Queue Entities!");
    }
}
