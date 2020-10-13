package com.fredrikpedersen.orakelqueuesystem.webLayer.controllers.queue;

import com.fredrikpedersen.orakelqueuesystem.dto.QueueEntityDTO;
import com.fredrikpedersen.orakelqueuesystem.serviceLayer.queue.QueueEntityService;
import com.fredrikpedersen.orakelqueuesystem.utilities.constants.URLs;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(URLs.QUEUE_BASE_URL)
public class QueueEntityController {

    private final QueueEntityService queueEntityService;
    private final Bucket bucket;

    public QueueEntityController(final QueueEntityService queueEntityService) {
        this.queueEntityService = queueEntityService;

        Bandwidth limit = Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<QueueEntityDTO>> getAllQueueEntities() {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(queueEntityService.findALlNotDone());
        }

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<QueueEntityDTO> postQueueEntity(@RequestBody final QueueEntityDTO queueEntityDTO) {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(queueEntityService.createNew(queueEntityDTO));
        }

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PostMapping(URLs.QUEUE_CONFIRM_DONE + "{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('ADMIN')")
    public void confirmDone(@PathVariable final Long id) {
        queueEntityService.confirmDone(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteQueueEntity(@PathVariable final Long id) {
        queueEntityService.deleteById(id);
    }
}
