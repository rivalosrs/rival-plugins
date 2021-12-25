package com.rivalosrs.nightmarefps;

import net.runelite.client.config.*;

@ConfigGroup(NightmareFpsPlugin.CONFIG_GROUP)
public interface NightmareFpsConfig extends Config {
	@ConfigSection(
			name = "Configuration",
			description = "Configuration options for Nightmare Fps plugin",
			closedByDefault = false,
			hidden = true,
			position = 2
	)
	String configurationSection = "Configuration";

	@ConfigItem(
			keyName = "toHideGameObjects",
			name = "Hide Game Objects",
			description = "Hide unnecessary game objects",
			hidden = false,
			position = 20,
			section = configurationSection
	)
	default boolean toHideGameObjects()
	{
		return true;
	}

	@ConfigItem(
			keyName = "toHideNpcs",
			name = "Hide NPCs",
			description = "Hide the floating ghost npc",
			hidden = false,
			position = 21,
			section = configurationSection
	)
	default boolean toHideNpcs()
	{
		return false;
	}

	@ConfigItem(
			keyName = "toHideSceneLevels",
			name = "Change scene level",
			description = "Changes the rendered scene level",
			hidden = false,
			position = 22,
			section = configurationSection
	)
	default boolean toHideSceneLevels()
	{
		return true;
	}

	@ConfigItem(
			keyName = "minSceneLevel",
			name = "Minimum scene level",
			description = "Set the minimum level to render in the scene, default is 2",
			hidden = false,
			position = 23,
			section = configurationSection
	)
	default NightmareFpsPlugin.SceneLevel minSceneLevel()
	{
		return NightmareFpsPlugin.SceneLevel.LEVEL_2;
	}
}