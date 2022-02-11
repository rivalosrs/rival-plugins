package com.rivalosrs.autocontinue;

import net.runelite.client.config.*;

@ConfigGroup(AutoContinuePlugin.CONFIG_GROUP)
public interface AutoContinueConfig extends Config {

  @ConfigItem(
      keyName = "continueMode",
      name = "Mode",
      description = "How it automatically presses continue",
      position = 0
  )
  default ContinueMode continueMode() {
    return ContinueMode.MENU_INVOKE;
  }

  @ConfigSection(
      keyName = "debugSection",
      name = "Debug",
      description = "Things for debugging a widget",
      position = 10
  )
  String debugSection = "Debug";

  @ConfigItem(
      keyName = "isDebugMode",
      name = "Enable debug mode",
      description = "Turn this on when wanting to use debug button",
      section = debugSection,
      position = 11
  )
  default boolean isDebugMode() {
    return false;
  }

  @ConfigItem(
      keyName = "identifyWidgetButton",
      name = "Debug widgets",
      description = "Try to identify the continue widget",
      section = debugSection,
      position = 12
  )
  default Button identifyWidgetButton() {
    return new Button();
  }
}
