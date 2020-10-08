package com.fredrikpedersen.orakelqueuesystem.serviceLayer.queue;

import com.fredrikpedersen.orakelqueuesystem.dataAccessLayer.models.queue.ESubject;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@NoArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    @Override
    public ArrayList<String> getSubjectList() {
        final ArrayList<String> subjects = new ArrayList<>();

        for(ESubject subject : ESubject.values()) {
            subjects.add(subject.label);
        }

        return subjects;
    }
}
