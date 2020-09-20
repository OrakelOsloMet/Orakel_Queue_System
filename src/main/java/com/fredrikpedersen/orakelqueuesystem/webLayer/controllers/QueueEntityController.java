package com.fredrikpedersen.orakelqueuesystem.webLayer.controllers;

import com.fredrikpedersen.orakelqueuesystem.dto.QueueEntityDTO;
import com.fredrikpedersen.orakelqueuesystem.serviceLayer.QueueEntityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 20/09/2020 at 22:10
 */

@RestController
@RequestMapping(QueueEntityController.BASE_URL)
@CrossOrigin
public class QueueEntityController {

    public static final String BASE_URL = "/api/queue";

    private final QueueEntityService queueEntityService;

    public QueueEntityController(final QueueEntityService queueEntityService) {
        this.queueEntityService = queueEntityService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<QueueEntityDTO> getAllQueueEntities() {
        return queueEntityService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QueueEntityDTO postQueueEntity(@RequestBody final QueueEntityDTO queueEntityDTO) {
        return queueEntityService.createNew(queueEntityDTO);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteQueueEntity(@RequestBody final QueueEntityDTO queueEntityDTO) {
        queueEntityService.delete(queueEntityDTO);
    }
}
