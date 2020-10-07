package com.fredrikpedersen.orakelqueuesystem.webLayer.controllers.queue;

import com.fredrikpedersen.orakelqueuesystem.serviceLayer.queue.SubjectService;
import com.fredrikpedersen.orakelqueuesystem.utilities.constants.URLs;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping(URLs.SUBJECT_BASE_URL)
public class SubjectController {

    private SubjectService subjectService;

    public SubjectController(final SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public ArrayList<String> getSubjects() {
        return subjectService.getSubjectList();
    }
}
