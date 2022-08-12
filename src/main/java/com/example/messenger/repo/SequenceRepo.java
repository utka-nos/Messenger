package com.example.messenger.repo;

import com.example.messenger.model.Sequence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceRepo extends MongoRepository<Sequence, String> {

    Sequence findSequenceById(String collectionName);
}
