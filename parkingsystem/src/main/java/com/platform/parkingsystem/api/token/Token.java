package com.platform.parkingsystem.api.token;


import com.platform.parkingsystem.api.model.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Token {

  @Id
  private String id;

  @DBRef
  private User user;

  private String token;

  private TokenType tokenType = TokenType.BEARER;

  private boolean revoked;

  private boolean expired;
}
