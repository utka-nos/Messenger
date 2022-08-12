package com.example.messenger.service;

import com.example.messenger.model.Sequence;
import com.example.messenger.repo.SequenceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SequenceService {

    @Autowired
    private SequenceRepo sequenceRepo;

    public Long getId(Class<?> className) {
        String simpleName = className.getSimpleName();
        Sequence seq = sequenceRepo.findSequenceById(simpleName);
        if (seq == null) {
            seq = new Sequence();
            seq.setId(simpleName);
        }
        if (seq.getCurId() == null) {
            seq.setCurId(1L);
        }
        seq.setCurId(seq.getCurId() + 1L);
        sequenceRepo.save(seq);
        return seq.getCurId();
    }

}
