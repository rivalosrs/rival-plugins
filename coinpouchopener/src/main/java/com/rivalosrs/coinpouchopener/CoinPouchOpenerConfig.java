package com.rivalosrs.coinpouchopener;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(CoinPouchOpenerPlugin.CONFIG_GROUP)
public interface CoinPouchOpenerConfig extends Config {
  @ConfigItem(
      keyName = "autoCoinPouch",
      name = "Open coin pouches",
      description = "Automatically open coin pouches",
      position = 0
  )
  default boolean autoCoinPouch()
  {
    return true;
  }
}
