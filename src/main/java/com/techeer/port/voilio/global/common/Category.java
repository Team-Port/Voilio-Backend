package com.techeer.port.voilio.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
  IT(1),
  BACKEND(2),
  JAVA(3),
  KOTLIN(4),
  PYTHON(5),
  REACT(6),
  DANCE(7),
  LANGUAGE(8);

  private final int value;
}
