package com.platform.parkingsystem.api.token;

import java.util.List;
import java.util.Optional;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TokenRepository extends MongoRepository<Token, Integer> {

  @Query(value = "{ 'user.id' : ?0, $or: [ { 'expired' : false }, { 'revoked' : false } ] }")
    List<Token> findAllValidTokenByUser(String id);

  Optional<Token> findByToken(String token);
}
