package com.rivalosrs.autocontinue;

import lombok.Getter;

@Getter
public enum ContinueMode {
  KEY_PRESS("Key Press"),
  MENU_INVOKE("Invoke Action");

  private final String mode;

  ContinueMode(String mode) {
    this.mode = mode;
  }
}
