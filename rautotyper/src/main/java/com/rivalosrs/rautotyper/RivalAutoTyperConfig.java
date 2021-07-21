package com.rivalosrs.rautotyper;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Keybind;
import net.runelite.client.config.Range;

@ConfigGroup(RivalAutoTyperPlugin.CONFIG_GROUP)
public interface RivalAutoTyperConfig extends Config
{
	@ConfigItem(
		keyName = "startStopKey",
		name = "Start/Stop",
		description = "Starts or stops the auto typer",
		position = 0
	)
	default Keybind toggle()
	{
		return Keybind.NOT_SET;
	}

	@ConfigItem(
		keyName = "minDelay",
		name = "Min delay",
		description = "Minimum delay between messages, in milliseconds",
		position = 1
	)
	default int minDelay()
	{
		return 1000;
	}

	@ConfigItem(
		keyName = "maxDelay",
		name = "Max delay",
		description = "Maximum delay between messages, in milliseconds",
		position = 2
	)
	default int maxDelay()
	{
		return 3000;
	}

	@ConfigItem(
		keyName = "targetDelay",
		name = "Target delay",
		description = "Target delay between messages, in milliseconds",
		position = 3
	)
	default int targetDelay()
	{
		return 1500;
	}

	@ConfigItem(
		keyName = "delayDeviation",
		name = "Delay Deviation",
		description = "Deviation between delay, in milliseconds",
		position = 4
	)
	default int delayDeviation()
	{
		return 300;
	}

	@ConfigItem(
		keyName = "weightedDistribution",
		name = "Weighted Distribution",
		description = "Shifts the random distribution towards the lower end at the target, otherwise it will be an even distribution",
		position = 5
	)
	default boolean weightedDistribution()
	{
		return false;
	}

	@ConfigItem(
			keyName = "messages",
			name = "Messages",
			description = "Messages to auto type" +
					"<br>Messages are sent sequentially, meaning one after the other",
			position = 6
	)
	default String messages()
	{
		return "";
	}
}
