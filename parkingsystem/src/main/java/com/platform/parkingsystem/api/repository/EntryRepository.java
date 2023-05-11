package com.platform.parkingsystem.api.repository;

import com.platform.parkingsystem.api.model.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryRepository extends MongoRepository<Entry, String> {


}