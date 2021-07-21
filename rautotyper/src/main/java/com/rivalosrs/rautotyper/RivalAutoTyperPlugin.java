package com.rivalosrs.rautotyper;

import java.awt.event.KeyEvent;

import com.google.common.base.Strings;
import com.google.inject.Provides;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.client.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Point;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.HotkeyListener;
import org.pf4j.Extension;

import net.runelite.client.events.ConfigChanged;

// Credit for the delay and pressKey stuff to Ganom and Illumine

@Extension
@PluginDescriptor(
	name = "rAuto Typer",
	enabledByDefault = false
)
@Slf4j
public class RivalAutoTyperPlugin extends Plugin
{
	static final String CONFIG_GROUP = "rivalAutoTyper";

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private RivalAutoTyperConfig config;

	@Inject
	private RivalAutoTyperOverlay pluginOverlay;

	@Inject
	private KeyManager keyManager;

	private ExecutorService executorService;
	private Random random;


	@Getter(AccessLevel.PACKAGE)
	private boolean running;

	@Getter(AccessLevel.PACKAGE)
	private static final List<String> messagesList = new ArrayList<>();

	private int currentMessageIndex;

	@Provides
	RivalAutoTyperConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(RivalAutoTyperConfig.class);
	}

	@Override
	protected void startUp()
	{
		stopRunning();
		keyManager.registerKeyListener(hotkeyListener);
		executorService = Executors.newSingleThreadExecutor();
		random = new Random();
		loadMessages(config.messages());
		overlayManager.add(pluginOverlay);
	}

	@Override
	protected void shutDown()
	{
		stopRunning();
		keyManager.unregisterKeyListener(hotkeyListener);
		executorService.shutdown();
		random = null;
		messagesList.clear();
		overlayManager.remove(pluginOverlay);
	}

	@Subscribe
	private void onConfigChanged(ConfigChanged event)
	{
		if (!event.getGroup().equals(CONFIG_GROUP)) {
			return;
		}

		loadMessages(config.messages());
	}

	private final HotkeyListener hotkeyListener = new HotkeyListener(() -> config.toggle())
	{
		@Override
		public void hotkeyReleased() {
			running = !running;

			if (!running) return;

			executorService.submit(() -> {
				while (running)
				{
					if (client.getGameState() != GameState.LOGGED_IN)
					{
						log.info("Not logged in, breaking out of run loop");
						stopRunning();
						break;
					}

					if (messagesList.isEmpty())
					{
						log.info("messagesList is empty, breaking out of run loop");
						stopRunning();
						break;
					}

					String messageToSend = messagesList.get(getCurrentMessageIndex());
					sendMessage(messageToSend);
					currentMessageIndex++;

					try
					{
						Thread.sleep(randomDelay());
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			});
		}
	};

	private void stopRunning()
	{
		running = false;
	}

	private void loadMessages(String messages)
	{
		if (!Strings.isNullOrEmpty(messages)) {
			stopRunning();
			List<String> messageEntries = Arrays.asList(messages.split(System.lineSeparator()));
			messagesList.clear();
			messagesList.addAll(messageEntries);
			currentMessageIndex = 0;
			log.info("Loaded {} messages", messageEntries.size());
		}
	}

	public int getCurrentMessageIndex()
	{
		if (currentMessageIndex < 0 || currentMessageIndex >= messagesList.size())
		{
			currentMessageIndex = 0;
		}
		return currentMessageIndex;
	}

	private void sendMessage(String message)
	{
		typeString(message);
		pressKey(KeyEvent.VK_ENTER);
	}

	private void typeString(String string)
	{
		assert !client.isClientThread();

		for (char c : string.toCharArray()) {
			pressKey(c);
		}
	}

	private void pressKey(char key)
	{
		keyEvent(401, key);
		keyEvent(402, key);
		keyEvent(400, key);
	}

	private void pressKey(int key) {
		keyEvent(401, key);
		keyEvent(402, key);
	}

	private void keyEvent(int id, char key) {
		KeyEvent e = new KeyEvent(
				client.getCanvas(), id, System.currentTimeMillis(),
				0, KeyEvent.VK_UNDEFINED, key
		);
		client.getCanvas().dispatchEvent(e);
	}

	private void keyEvent(int id, int key) {
		KeyEvent e = new KeyEvent(
				client.getCanvas(), id, System.currentTimeMillis(),
				0, key, KeyEvent.CHAR_UNDEFINED
		);
		client.getCanvas().dispatchEvent(e);
	}

	/**
	 * Generate a gaussian random (average at 0.0, std dev of 1.0)
	 * take the absolute value of it (if we don't, every negative value will be clamped at the minimum value)
	 * get the log base e of it to make it shifted towards the right side
	 * invert it to shift the distribution to the other end
	 * clamp it to min max, any values outside of range are set to min or max
	 */
	private long randomDelay()
	{
		if (config.weightedDistribution())
		{
			return (long) clamp(
				(-Math.log(Math.abs(random.nextGaussian()))) * config.delayDeviation() + config.targetDelay()
			);
		}
		else
		{
			/* generate a normal even distribution random */
			return (long) clamp(
				Math.round(random.nextGaussian() * config.delayDeviation() + config.targetDelay())
			);
		}
	}

	private double clamp(double val)
	{
		return Math.max(config.minDelay(), Math.min(config.maxDelay(), val));
	}
}
