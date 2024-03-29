package com.techeer.port.voilio.domain.email;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Email {
  private String address;
  private String title;
  private String context;
}
