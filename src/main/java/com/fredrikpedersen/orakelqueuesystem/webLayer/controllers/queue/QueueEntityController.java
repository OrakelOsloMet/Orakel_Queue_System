package com.fredrikpedersen.orakelqueuesystem.webLayer.controllers.queue;

import com.fredrikpedersen.orakelqueuesystem.dto.QueueEntityDTO;
import com.fredrikpedersen.orakelqueuesystem.serviceLayer.queue.QueueEntityService;
import com.fredrikpedersen.orakelqueuesystem.utilities.constants.URLs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 20/09/2020 at 22:10
 */

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(URLs.QUEUE_BASE_URL)
public class QueueEntityController {

    private final QueueEntityService queueEntityService;

    public QueueEntityController(final QueueEntityService queueEntityService) {
        this.queueEntityService = queueEntityService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<QueueEntityDTO> getAllQueueEntities() {
        return queueEntityService.findALlNotDone();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QueueEntityDTO postQueueEntity(@RequestBody final QueueEntityDTO queueEntityDTO) {
        return queueEntityService.createNew(queueEntityDTO);
    }

    @PostMapping(URLs.QUEUE_CONFIRM_DONE + "/{id}")
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
