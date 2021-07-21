package com.rivalosrs.nightmarefps;

import net.runelite.client.config.*;

@SuppressWarnings({"unused"})
@ConfigGroup(NightmareFpsPlugin.CONFIG_GROUP)
public interface NightmareFpsConfig extends Config {
	@ConfigSection(
			name = "Manual Hide",
			description = "Buttons for manually hiding things",
			closedByDefault = false,
			hidden = true,
			position = 1
	)
	String manualHideSection = "Manual Hide";

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
			hidden = true,
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
			hidden = true,
			position = 21,
			section = configurationSection
	)
	default boolean toHideNpcs()
	{
		return true;
	}

	@ConfigItem(
			keyName = "toHideSceneLevels",
			name = "Change scene level",
			description = "Changes the rendered scene level",
			hidden = true,
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
			hidden = true,
			position = 23,
			section = configurationSection
	)
	default NightmareFpsPlugin.SceneLevel minSceneLevel()
	{
		return NightmareFpsPlugin.SceneLevel.LEVEL_2;
	}

	@ConfigItem(
			keyName = "hideGameObjects",
			name = "Hide Game Objects",
			description = "Hide game objects with button",
			position = 10,
			title = "Hide/Unhide Game Objects",
			hidden = true,
			section = manualHideSection
	)
	default Button hideGameObjects() {
		return new Button();
	}

	@ConfigItem(
			keyName = "hideNpcs",
			name = "Hide NPCs",
			description = "Hide npcs with button",
			position = 11,
			title = "Hide/Unhide Npcs",
			hidden = true,
			section = manualHideSection
	)
	default Button hideNpcs() {
		return new Button();
	}
}