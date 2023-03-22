package com.techeer.port.voilio.global.common;

public enum Category {
  IT(1);

  private final int value;

  Category(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
