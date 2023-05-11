package com.platform.parkingsystem.api.repository;

import com.platform.parkingsystem.api.model.Exit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExitRepository extends MongoRepository<Exit, String> {


}