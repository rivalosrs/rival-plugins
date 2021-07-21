package net.runelite.client.plugins.eventdebugger;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@SuppressWarnings({"unused"})
@ConfigGroup(EventDebuggerPlugin.CONFIG_GROUP)
public interface EventDebuggerConfig extends Config
{
	@ConfigSection(
		name = "Log Options",
		description = "Pick different places to log to",
		position = 0
	)
	String logOptionsSection = "Log Options";

	@ConfigSection(
		name = "Event Logging",
		description = "Select what events to log",
		position = 1
	)
	String eventLoggingSection = "Events to Log";

	@ConfigItem(
		keyName = "logToLogger",
		name = "Log to Logger",
		description = "Log to logger.info",
		section = logOptionsSection
	)
	default boolean logToLogger() { return true; }

	@ConfigItem(
		keyName = "logToGame",
		name = "Log to Game",
		description = "Log to the game as messages",
		section = logOptionsSection
	)
	default boolean logToGame() { return false; }

	@ConfigItem(
		keyName = "filterNullEvents",
		name = "Filter Null Events",
		description = "Don't log null events",
		section = logOptionsSection
	)
	default boolean filterNullEvents() { return true; }

	// --- Events ---

	@ConfigItem(
		keyName = "logActorDeath",
		name = "Log onActorDeath",
		description = "Log the onActorDeath event",
		section = eventLoggingSection
	)
	default boolean logActorDeath() { return false; }

	@ConfigItem(
		keyName = "logAnimationChanged",
		name = "Log onAnimationChanged",
		description = "Log the onAnimationChanged event",
		section = eventLoggingSection
	)
	default boolean logAnimationChanged() { return false; }

//	@ConfigItem(
//		keyName = "logAreaSoundEffectPlayed",
//		name = "Log onAreaSoundEffectPlayed",
//		description = "Log the onAreaSoundEffectPlayed event",
//		section = eventLoggingSection
//	)
//	default boolean logAreaSoundEffectPlayed() { return false; }

//	@ConfigItem(
//		keyName = "logBeforeMenuRender",
//		name = "Log onBeforeMenuRender",
//		description = "Log the onBeforeMenuRender event",
//		section = eventLoggingSection
//	)
//	default boolean logBeforeMenuRender() { return false; }

//	@ConfigItem(
//		keyName = "logBeforeRender",
//		name = "Log onBeforeRender",
//		description = "Log the onBeforeRender event",
//		section = eventLoggingSection
//	)
//	default boolean logBeforeRender() { return false; }

//	@ConfigItem(
//		keyName = "logCanvasSizeChanged",
//		name = "Log onCanvasSizeChanged",
//		description = "Log the onCanvasSizeChanged event",
//		section = eventLoggingSection
//	)
//	default boolean logCanvasSizeChanged() { return false; }

	@ConfigItem(
		keyName = "logChatMessage",
		name = "Log onChatMessage",
		description = "Log the onChatMessage event",
		section = eventLoggingSection
	)
	default boolean logChatMessage() { return false; }

//	@ConfigItem(
//		keyName = "logClanMemberJoined",
//		name = "Log onClanMemberJoined",
//		description = "Log the onClanMemberJoined event",
//		section = eventLoggingSection
//	)
//	default boolean logClanMemberJoined() { return false; }

//	@ConfigItem(
//		keyName = "logClanMemberLeft",
//		name = "Log onClanMemberLeft",
//		description = "Log the onClanMemberLeft event",
//		section = eventLoggingSection
//	)
//	default boolean logClanMemberLeft() { return false; }

//	@ConfigItem(
//		keyName = "logClientTick",
//		name = "Log onClientTick",
//		description = "Log the onClientTick event",
//		section = eventLoggingSection
//	)
//	default boolean logClientTick() { return false; }

//	@ConfigItem(
//		keyName = "logCommandExecuted",
//		name = "Log onCommandExecuted",
//		description = "Log the onCommandExecuted event",
//		section = eventLoggingSection
//	)
//	default boolean logCommandExecuted() { return false; }

	@ConfigItem(
		keyName = "logDecorativeObjectChanged",
		name = "Log onDecorativeObjectChanged",
		description = "Log the onDecorativeObjectChanged event",
		section = eventLoggingSection
	)
	default boolean logDecorativeObjectChanged() { return false; }

	@ConfigItem(
		keyName = "logDecorativeObjectDespawned",
		name = "Log onDecorativeObjectDespawned",
		description = "Log the onDecorativeObjectDespawned event",
		section = eventLoggingSection
	)
	default boolean logDecorativeObjectDespawned() { return false; }

	@ConfigItem(
		keyName = "logDecorativeObjectSpawned",
		name = "Log onDecorativeObjectSpawned",
		description = "Log the onDecorativeObjectSpawned event",
		section = eventLoggingSection
	)
	default boolean logDecorativeObjectSpawned() { return false; }

//	@ConfigItem(
//		keyName = "logDraggingWidgetChanged",
//		name = "Log onDraggingWidgetChanged",
//		description = "Log the onDraggingWidgetChanged event",
//		section = eventLoggingSection
//	)
//	default boolean logDraggingWidgetChanged() { return false; }

//	@ConfigItem(
//		keyName = "logFakeXpDrop",
//		name = "Log onFakeXpDrop",
//		description = "Log the onFakeXpDrop event",
//		section = eventLoggingSection
//	)
//	default boolean logFakeXpDrop() { return false; }

//	@ConfigItem(
//		keyName = "logFocusChanged",
//		name = "Log onFocusChanged",
//		description = "Log the onFocusChanged event",
//		section = eventLoggingSection
//	)
//	default boolean logFocusChanged() { return false; }

//	@ConfigItem(
//		keyName = "logFriendsChatChanged",
//		name = "Log onFriendsChatChanged",
//		description = "Log the onFriendsChatChanged event",
//		section = eventLoggingSection
//	)
//	default boolean logFriendsChatChanged() { return false; }

//	@ConfigItem(
//		keyName = "logFriendsChatMemberJoined",
//		name = "Log onFriendsChatMemberJoined",
//		description = "Log the onFriendsChatMemberJoined event",
//		section = eventLoggingSection
//	)
//	default boolean logFriendsChatMemberJoined() { return false; }

//	@ConfigItem(
//		keyName = "logFriendsChatMemberLeft",
//		name = "Log onFriendsChatMemberLeft",
//		description = "Log the onFriendsChatMemberLeft event",
//		section = eventLoggingSection
//	)
//	default boolean logFriendsChatMemberLeft() { return false; }

	@ConfigItem(
		keyName = "logGameObjectChanged",
		name = "Log onGameObjectChanged",
		description = "Log the onGameObjectChanged event",
		section = eventLoggingSection
	)
	default boolean logGameObjectChanged() { return false; }

	@ConfigItem(
		keyName = "logGameObjectDespawned",
		name = "Log onGameObjectDespawned",
		description = "Log the onGameObjectDespawned event",
		section = eventLoggingSection
	)
	default boolean logGameObjectDespawned() { return false; }

	@ConfigItem(
		keyName = "logGameObjectSpawned",
		name = "Log onGameObjectSpawned",
		description = "Log the onGameObjectSpawned event",
		section = eventLoggingSection
	)
	default boolean logGameObjectSpawned() { return false; }

	@ConfigItem(
		keyName = "logGameStateChanged",
		name = "Log onGameStateChanged",
		description = "Log the onGameStateChanged event",
		section = eventLoggingSection
	)
	default boolean logGameStateChanged() { return false; }

	@ConfigItem(
		keyName = "logGameTick",
		name = "Log onGameTick",
		description = "Log the onGameTick event",
		section = eventLoggingSection
	)
	default boolean logGameTick() { return false; }

//	@ConfigItem(
//		keyName = "logGrandExchangeOfferChanged",
//		name = "Log onGrandExchangeOfferChanged",
//		description = "Log the onGrandExchangeOfferChanged event",
//		section = eventLoggingSection
//	)
//	default boolean logGrandExchangeOfferChanged() { return false; }

//	@ConfigItem(
//		keyName = "logGrandExchangeSearched",
//		name = "Log onGrandExchangeSearched",
//		description = "Log the onGrandExchangeSearched event",
//		section = eventLoggingSection
//	)
//	default boolean logGrandExchangeSearched() { return false; }

	@ConfigItem(
		keyName = "logGraphicChanged",
		name = "Log onGraphicChanged",
		description = "Log the onGraphicChanged event",
		section = eventLoggingSection
	)
	default boolean logGraphicChanged() { return false; }

	@ConfigItem(
		keyName = "logGraphicsObjectCreated",
		name = "Log onGraphicsObjectCreated",
		description = "Log the onGraphicsObjectCreated event",
		section = eventLoggingSection
	)
	default boolean logGraphicsObjectCreated() { return false; }

	@ConfigItem(
		keyName = "logGroundObjectChanged",
		name = "Log onGroundObjectChanged",
		description = "Log the onGroundObjectChanged event",
		section = eventLoggingSection
	)
	default boolean logGroundObjectChanged() { return false; }

	@ConfigItem(
		keyName = "logGroundObjectDespawned",
		name = "Log onGroundObjectDespawned",
		description = "Log the onGroundObjectDespawned event",
		section = eventLoggingSection
	)
	default boolean logGroundObjectDespawned() { return false; }

	@ConfigItem(
		keyName = "logGroundObjectSpawned",
		name = "Log onGroundObjectSpawned",
		description = "Log the onGroundObjectSpawned event",
		section = eventLoggingSection
	)
	default boolean logGroundObjectSpawned() { return false; }

	@ConfigItem(
		keyName = "logHitsplatApplied",
		name = "Log onHitsplatApplied",
		description = "Log the onHitsplatApplied event",
		section = eventLoggingSection
	)
	default boolean logHitsplatApplied() { return false; }

	@ConfigItem(
		keyName = "logInteractingChanged",
		name = "Log onInteractingChanged",
		description = "Log the onInteractingChanged event",
		section = eventLoggingSection
	)
	default boolean logInteractingChanged() { return false; }

	@ConfigItem(
		keyName = "logItemContainerChanged",
		name = "Log onItemContainerChanged",
		description = "Log the onItemContainerChanged event",
		section = eventLoggingSection
	)
	default boolean logItemContainerChanged() { return false; }

	@ConfigItem(
		keyName = "logItemDespawned",
		name = "Log onItemDespawned",
		description = "Log the onItemDespawned event",
		section = eventLoggingSection
	)
	default boolean logItemDespawned() { return false; }

	@ConfigItem(
		keyName = "logItemQuantityChanged",
		name = "Log onItemQuantityChanged",
		description = "Log the onItemQuantityChanged event",
		section = eventLoggingSection
	)
	default boolean logItemQuantityChanged() { return false; }

	@ConfigItem(
		keyName = "logItemSpawned",
		name = "Log onItemSpawned",
		description = "Log the onItemSpawned event",
		section = eventLoggingSection
	)
	default boolean logItemSpawned() { return false; }

	@ConfigItem(
		keyName = "logMenuEntryAdded",
		name = "Log onMenuEntryAdded",
		description = "Log the onMenuEntryAdded event",
		section = eventLoggingSection
	)
	default boolean logMenuEntryAdded() { return false; }

	@ConfigItem(
		keyName = "logMenuOpened",
		name = "Log onMenuOpened",
		description = "Log the onMenuOpened event",
		section = eventLoggingSection
	)
	default boolean logMenuOpened() { return false; }

	@ConfigItem(
		keyName = "logMenuOptionClicked",
		name = "Log onMenuOptionClicked",
		description = "Log the onMenuOptionClicked event",
		section = eventLoggingSection
	)
	default boolean logMenuOptionClicked() { return false; }

//	@ConfigItem(
//		keyName = "logMenuShouldLeftClick",
//		name = "Log onMenuShouldLeftClick",
//		description = "Log the onMenuShouldLeftClick event",
//		section = eventLoggingSection
//	)
//	default boolean logMenuShouldLeftClick() { return false; }

//	@ConfigItem(
//		keyName = "logNameableNameChanged",
//		name = "Log onNameableNameChanged",
//		description = "Log the onNameableNameChanged event",
//		section = eventLoggingSection
//	)
//	default boolean logNameableNameChanged() { return false; }

	@ConfigItem(
		keyName = "logNpcChanged",
		name = "Log onNpcChanged",
		description = "Log the onNpcChanged event",
		section = eventLoggingSection
	)
	default boolean logNpcChanged() { return false; }

	@ConfigItem(
		keyName = "logNpcDespawned",
		name = "Log onNpcDespawned",
		description = "Log the onNpcDespawned event",
		section = eventLoggingSection
	)
	default boolean logNpcDespawned() { return false; }

	@ConfigItem(
		keyName = "logNpcSpawned",
		name = "Log onNpcSpawned",
		description = "Log the onNpcSpawned event",
		section = eventLoggingSection
	)
	default boolean logNpcSpawned() { return false; }

//	@ConfigItem(
//		keyName = "logOverheadTextChanged",
//		name = "Log onOverheadTextChanged",
//		description = "Log the onOverheadTextChanged event",
//		section = eventLoggingSection
//	)
//	default boolean logOverheadTextChanged() { return false; }

	@ConfigItem(
		keyName = "logPlayerChanged",
		name = "Log onPlayerChanged",
		description = "Log the onPlayerChanged event",
		section = eventLoggingSection
	)
	default boolean logPlayerChanged() { return false; }

	@ConfigItem(
		keyName = "logPlayerDespawned",
		name = "Log onPlayerDespawned",
		description = "Log the onPlayerDespawned event",
		section = eventLoggingSection
	)
	default boolean logPlayerDespawned() { return false; }

//	@ConfigItem(
//		keyName = "logPlayerMenuOptionsChanged",
//		name = "Log onPlayerMenuOptionsChanged",
//		description = "Log the onPlayerMenuOptionsChanged event",
//		section = eventLoggingSection
//	)
//	default boolean logPlayerMenuOptionsChanged() { return false; }

	@ConfigItem(
		keyName = "logPlayerSpawned",
		name = "Log onPlayerSpawned",
		description = "Log the onPlayerSpawned event",
		section = eventLoggingSection
	)
	default boolean logPlayerSpawned() { return false; }

//	@ConfigItem(
//		keyName = "logPostHealthBar",
//		name = "Log onPostHealthBar",
//		description = "Log the onPostHealthBar event",
//		section = eventLoggingSection
//	)
//	default boolean logPostHealthBar() { return false; }

//	@ConfigItem(
//		keyName = "logPostItemComposition",
//		name = "Log onPostItemComposition",
//		description = "Log the onPostItemComposition event",
//		section = eventLoggingSection
//	)
//	default boolean logPostItemComposition() { return false; }

//	@ConfigItem(
//		keyName = "logPostStructComposition",
//		name = "Log onPostStructComposition",
//		description = "Log the onPostStructComposition event",
//		section = eventLoggingSection
//	)
//	default boolean logPostStructComposition() { return false; }

	@ConfigItem(
		keyName = "logProjectileMoved",
		name = "Log onProjectileMoved",
		description = "Log the onProjectileMoved event",
		section = eventLoggingSection
	)
	default boolean logProjectileMoved() { return false; }

//	@ConfigItem(
//		keyName = "logRemovedFriend",
//		name = "Log onRemovedFriend",
//		description = "Log the onRemovedFriend event",
//		section = eventLoggingSection
//	)
//	default boolean logRemovedFriend() { return false; }

//	@ConfigItem(
//		keyName = "logResizeableChanged",
//		name = "Log onResizeableChanged",
//		description = "Log the onResizeableChanged event",
//		section = eventLoggingSection
//	)
//	default boolean logResizeableChanged() { return false; }

	@ConfigItem(
		keyName = "logScriptCallbackEvent",
		name = "Log onScriptCallbackEvent",
		description = "Log the onScriptCallbackEvent event",
		section = eventLoggingSection
	)
	default boolean logScriptCallbackEvent() { return false; }

	@ConfigItem(
		keyName = "logScriptPostFired",
		name = "Log onScriptPostFired",
		description = "Log the onScriptPostFired event",
		section = eventLoggingSection
	)
	default boolean logScriptPostFired() { return false; }

	@ConfigItem(
		keyName = "logScriptPreFired",
		name = "Log onScriptPreFired",
		description = "Log the onScriptPreFired event",
		section = eventLoggingSection
	)
	default boolean logScriptPreFired() { return false; }

//	@ConfigItem(
//		keyName = "logSoundEffectPlayed",
//		name = "Log onSoundEffectPlayed",
//		description = "Log the onSoundEffectPlayed event",
//		section = eventLoggingSection
//	)
//	default boolean logSoundEffectPlayed() { return false; }

	@ConfigItem(
		keyName = "logStatChanged",
		name = "Log onStatChanged",
		description = "Log the onStatChanged event",
		section = eventLoggingSection
	)
	default boolean logStatChanged() { return false; }

//	@ConfigItem(
//		keyName = "logUsernameChanged",
//		name = "Log onUsernameChanged",
//		description = "Log the onUsernameChanged event",
//		section = eventLoggingSection
//	)
//	default boolean logUsernameChanged() { return false; }

	@ConfigItem(
		keyName = "logVarbitChanged",
		name = "Log onVarbitChanged",
		description = "Log the onVarbitChanged event",
		section = eventLoggingSection
	)
	default boolean logVarbitChanged() { return false; }

	@ConfigItem(
		keyName = "logVarClientIntChanged",
		name = "Log onVarClientIntChanged",
		description = "Log the onVarClientIntChanged event",
		section = eventLoggingSection
	)
	default boolean logVarClientIntChanged() { return false; }

	@ConfigItem(
		keyName = "logVarClientStrChanged",
		name = "Log onVarClientStrChanged",
		description = "Log the onVarClientStrChanged event",
		section = eventLoggingSection
	)
	default boolean logVarClientStrChanged() { return false; }

//	@ConfigItem(
//		keyName = "logVolumeChanged",
//		name = "Log onVolumeChanged",
//		description = "Log the onVolumeChanged event",
//		section = eventLoggingSection
//	)
//	default boolean logVolumeChanged() { return false; }

	@ConfigItem(
		keyName = "logWallObjectChanged",
		name = "Log onWallObjectChanged",
		description = "Log the onWallObjectChanged event",
		section = eventLoggingSection
	)
	default boolean logWallObjectChanged() { return false; }

	@ConfigItem(
		keyName = "logWallObjectDespawned",
		name = "Log onWallObjectDespawned",
		description = "Log the onWallObjectDespawned event",
		section = eventLoggingSection
	)
	default boolean logWallObjectDespawned() { return false; }

	@ConfigItem(
		keyName = "logWallObjectSpawned",
		name = "Log onWallObjectSpawned",
		description = "Log the onWallObjectSpawned event",
		section = eventLoggingSection
	)
	default boolean logWallObjectSpawned() { return false; }

	@ConfigItem(
		keyName = "logWidgetClosed",
		name = "Log onWidgetClosed",
		description = "Log the onWidgetClosed event",
		section = eventLoggingSection
	)
	default boolean logWidgetClosed() { return false; }

	@ConfigItem(
		keyName = "logWidgetLoaded",
		name = "Log onWidgetLoaded",
		description = "Log the onWidgetLoaded event",
		section = eventLoggingSection
	)
	default boolean logWidgetLoaded() { return false; }

	@ConfigItem(
		keyName = "logWidgetMenuOptionClicked",
		name = "Log onWidgetMenuOptionClicked",
		description = "Log the onWidgetMenuOptionClicked event",
		section = eventLoggingSection
	)
	default boolean logWidgetMenuOptionClicked() { return false; }

//	@ConfigItem(
//		keyName = "logWorldChanged",
//		name = "Log onWorldChanged",
//		description = "Log the onWorldChanged event",
//		section = eventLoggingSection
//	)
//	default boolean logWorldChanged() { return false; }

//	@ConfigItem(
//		keyName = "logWorldListLoad",
//		name = "Log onWorldListLoad",
//		description = "Log the onWorldListLoad event",
//		section = eventLoggingSection
//	)
//	default boolean logWorldListLoad() { return false; }
}