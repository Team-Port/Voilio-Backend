package com.techeer.port.voilio.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
  IT(1),
  DESIGN(2),
  DANCE(3),
  EXERCISE(4),
  LANGUAGE(5),
  SALES(6),
  ETC(7),
  ALL(8);

  private final int value;
}
