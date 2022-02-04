package com.rivalosrs.coinpouchopener;

import com.google.inject.Provides;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.ItemID;
import net.runelite.api.MenuAction;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import org.pf4j.Extension;

@Extension
@PluginDescriptor(
	name = "rCoin Pouch Opener",
	enabledByDefault = true
)
@Slf4j
public class CoinPouchOpenerPlugin extends Plugin
{
	static final String CONFIG_GROUP = "rivalCoinPouchOpener";

	private static final String FULL_COIN_POUCH_MESSAGE = "You need to empty your coin pouches before you can continue pickpocketing.";

	static final List<Integer> coinPouchIds = List.of(ItemID.COIN_POUCH,
			ItemID.COIN_POUCH_22522,
			ItemID.COIN_POUCH_22523,
			ItemID.COIN_POUCH_22524,
			ItemID.COIN_POUCH_22525,
			ItemID.COIN_POUCH_22526,
			ItemID.COIN_POUCH_22527,
			ItemID.COIN_POUCH_22528,
			ItemID.COIN_POUCH_22529,
			ItemID.COIN_POUCH_22530,
			ItemID.COIN_POUCH_22531,
			ItemID.COIN_POUCH_22532,
			ItemID.COIN_POUCH_22533,
			ItemID.COIN_POUCH_22534,
			ItemID.COIN_POUCH_22535,
			ItemID.COIN_POUCH_22536,
			ItemID.COIN_POUCH_22537,
			ItemID.COIN_POUCH_22538,
			ItemID.COIN_POUCH_24703);

	@Inject
	private Client client;

	@Inject
	private CoinPouchOpenerConfig config;

	@Provides
	CoinPouchOpenerConfig getConfig(final ConfigManager configManager)
	{
		return configManager.getConfig(CoinPouchOpenerConfig.class);
	}

	@Override
	protected void startUp()
	{

	}

	@Override
	protected void shutDown()
	{

	}

	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged event) {
		if (event.getContainerId() == InventoryID.INVENTORY.getId()) {
			checkAndOpenPouches();
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		// Only handle game messages
		if (event.getType() != ChatMessageType.GAMEMESSAGE)
			return;


		if (event.getMessage().equals(FULL_COIN_POUCH_MESSAGE)) {
			checkAndOpenPouches();
		}
	}

	private boolean toOpenPouches(int quantity) {
		return quantity >= 28;
	}

	private void checkAndOpenPouches() {
		if (!config.autoCoinPouch()) return;

		Widget inventory = client.getWidget(WidgetInfo.INVENTORY);

		if (inventory == null)
			return;

		Collection<WidgetItem> items = inventory.getWidgetItems();
		for (WidgetItem item : items) {
			if (coinPouchIds.contains(item.getId()) && toOpenPouches(item.getQuantity())) {
				openCoinPouches(item);
			}
		}
	}

	private void openCoinPouches(WidgetItem pouchItem) {
		assert client.isClientThread();

		client.invokeMenuAction("Open-all", "Open-all", pouchItem.getId(), MenuAction.ITEM_FIRST_OPTION.getId(), pouchItem.getIndex(),
				WidgetInfo.INVENTORY.getId());
	}

}
