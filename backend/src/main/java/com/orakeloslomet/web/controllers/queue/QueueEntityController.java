package com.orakeloslomet.web.controllers.queue;

import com.orakeloslomet.dtos.QueueEntityDTO;
import com.orakeloslomet.services.queue.QueueEntityService;
import com.orakeloslomet.utilities.constants.URLs;
import com.orakeloslomet.utilities.validation.FieldValidator;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * @author Fredrik Pedersen
 * @since 20/09/2020 at 21:41
 */

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(URLs.QUEUE_BASE_URL)
public class QueueEntityController {

    private final QueueEntityService queueEntityService;
    private final Bucket bucket;

    public QueueEntityController(@Value("${restApi.bucket.limit}") final Integer bucketLimit,
                                 @Value("${restApi.bucket.duration}") final Integer bucketDuration,
                                 final QueueEntityService queueEntityService) {
        this.queueEntityService = requireNonNull(queueEntityService);

        final Bandwidth limit = Bandwidth.classic(bucketLimit, Refill.greedy(bucketLimit, Duration.ofMinutes(bucketDuration)));
        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<QueueEntityDTO>> getAllQueueEntities() {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(queueEntityService.findAll());
        }

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<QueueEntityDTO> postQueueEntity(@RequestBody final QueueEntityDTO queueEntityDTO) {
        if (bucket.tryConsume(1)) {
            if (FieldValidator.validateForNulls(queueEntityDTO)) { //TODO validation logic should be moved to service layer.
                return new ResponseEntity<>(queueEntityService.save(queueEntityDTO), HttpStatus.CREATED);
            }
        }

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PostMapping(URLs.QUEUE_CONFIRM_DONE_URL + "{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean confirmDone(@PathVariable final Long id) {
        return queueEntityService.confirmDone(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteQueueEntity(@PathVariable final Long id) {
        queueEntityService.deleteById(id);
    }
}
