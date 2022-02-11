package com.rivalosrs.autocontinue;

import com.google.inject.Provides;
import java.awt.event.KeyEvent;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.events.ConfigButtonClicked;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.WidgetClosed;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.util.Deque;

import net.runelite.client.plugins.PluginManager;
import org.pf4j.Extension;

@Extension
@PluginDescriptor(
    name = "Auto Continue",
    description = "Automatically press continue on dialogue boxes",
    tags = {"rival", "continue", "dialogue"},
    enabledByDefault = true
)
@Slf4j
public class AutoContinuePlugin extends Plugin {

  static final String CONFIG_GROUP = "rivalAutoContinue";

  @Inject
  private Client client;

  @Inject
  private ClientThread clientThread;

  @Inject
  private PluginManager pluginManager;

  @Inject
  private AutoContinueConfig config;

  private ExecutorService executorService;

  private static final List<ContinueWidget> widgetsToContinue;

  static {
    ContinueWidget playerChat = new ContinueWidget(217, 5);
    ContinueWidget npcChat = new ContinueWidget(231, 5);
    ContinueWidget itemChat = new ContinueWidget(WidgetInfo.DIALOG2_SPRITE_CONTINUE);
    ContinueWidget special = new ContinueWidget(193, 0, 1);
    ContinueWidget miniGameDialogue = new ContinueWidget(WidgetInfo.MINIGAME_DIALOG_CONTINUE);
    ContinueWidget sprite = new ContinueWidget(633, 0, 1);
    ContinueWidget levelUp = new ContinueWidget(WidgetInfo.LEVEL_UP_CONTINUE);
    ContinueWidget dialogueNotification = new ContinueWidget(
        WidgetInfo.DIALOG_NOTIFICATION_CONTINUE);

    widgetsToContinue = List.of(
        playerChat,
        npcChat,
        itemChat,
        special,
        miniGameDialogue,
        sprite,
        levelUp,
        dialogueNotification
    );
  }

  @Provides
  AutoContinueConfig getConfig(ConfigManager configManager) {
    return configManager.getConfig(AutoContinueConfig.class);
  }

  @Override
  protected void startUp() {
    executorService = Executors.newSingleThreadExecutor();
  }

  @Override
  protected void shutDown() {
    executorService.shutdown();
  }

  private ContinueMode getContinueMode() {
    return config.continueMode();
  }

  private boolean isDebugMode() {
    return config.isDebugMode();
  }

  @Subscribe
  private void onConfigButtonPressed(ConfigButtonClicked event) {
    if (!event.getGroup().equalsIgnoreCase(CONFIG_GROUP)) {
      return;
    }

    if (event.getKey().equals("identifyWidgetButton")) {
      logContinueWidget();
    }
  }

  private void logContinueWidget() {
    clientThread.invokeLater(() -> {
      Set<Integer> visitedWigets = new HashSet<>();
      Deque<Widget> queue = new LinkedList<>();

      queue.addAll(Arrays.asList(client.getWidgetRoots()));

      client.addChatMessage(ChatMessageType.GAMEMESSAGE, "",
          "Debugging widgets, trying to find widgets with continue text.", null);

      while (!queue.isEmpty()) {
        Widget widget = queue.poll();

        if (visitedWigets.contains(widget.getId())) {
          continue;
        }

        visitedWigets.add(widget.getId());
        log.debug("Popped widget: {}", widgetToString(widget));

        if (widget.getText().toLowerCase().contains("continue")) {
          log.info("Widget contains continue: {}", widgetToString(widget));
          String widgetIds =
              WidgetInfo.TO_GROUP(widget.getId()) + "." + WidgetInfo.TO_CHILD(widget.getId());
          client.addChatMessage(ChatMessageType.GAMEMESSAGE, "",
              "Continue widget found with id " + widgetIds + ".", null);
        }

        List<Widget> nestedChildren = Arrays.asList(widget.getNestedChildren());
        queue.addAll(nestedChildren);
        List<Widget> dynamicChildren = Arrays.asList(widget.getDynamicChildren());
        queue.addAll(dynamicChildren);
        List<Widget> staticChildren = Arrays.asList(widget.getStaticChildren());
        queue.addAll(staticChildren);

        log.debug("Nested children: {}, dynamic: {}, static: {}", nestedChildren.size(),
            dynamicChildren.size(), staticChildren.size());
      }

      client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Debugging widgets finished.", null);
    });
  }

  private String widgetToString(Widget widget) {
    StringBuilder sb = new StringBuilder();
    sb.append("Widget id: ");
    sb.append(widget.getId());
    sb.append(", ");
    sb.append(WidgetInfo.TO_GROUP(widget.getId()));
    sb.append(".");
    sb.append(WidgetInfo.TO_CHILD(widget.getId()));
    sb.append(", type: ");
    sb.append(widget.getType());
    sb.append(", text: ");
    sb.append(widget.getText());

    return sb.toString();
  }

  @Subscribe
  private void onGameTick(GameTick event) {
    if (isDebugMode()) {
      return;
    }

    Optional<Widget> widget = getContinueWidget();

    tryToContinue(widget);
  }

  private void tryToContinue(Optional<Widget> widget) {
    if (widget.isEmpty()) {
      return;
    }

    if (getContinueMode().equals(ContinueMode.KEY_PRESS)) {
      executorService.execute(() -> {
        sendKeyPress();
      });
    } else {
      // continue mode is MENU_INVOKE
      invokeContinue(widget.get());
    }
  }

  private Optional<Widget> getWidget(ContinueWidget continueWidget) {
    switch (continueWidget.getType()) {
      case WIDGET_INFO:
        return getWidget(continueWidget.getWidgetInfo());
      case CHILD:
        return getWidget(continueWidget.getGroupId(), continueWidget.getChildId());
      case DYNAMIC_CHILD:
        return getWidget(continueWidget.getGroupId(), continueWidget.getFileId(),
            continueWidget.getChildId());
      default:
        return Optional.empty();
    }
  }

  private Optional<Widget> getWidget(WidgetInfo widgetInfo) {
    Optional<Widget> widget = Optional.ofNullable(client.getWidget(widgetInfo));

    return widget;
  }

  private Optional<Widget> getWidget(int group, int file, int dynamicChild) {
    Optional<Widget> widget = Optional.ofNullable(client.getWidget(group, file));

    if (widget.isPresent()) {
      return Optional.ofNullable(widget.get().getDynamicChildren()[dynamicChild]);
    } else {
      return Optional.empty();
    }
  }

  private Optional<Widget> getWidget(int group, int child) {
    Optional<Widget> widget = Optional.ofNullable(client.getWidget(group, child));

    return widget;
  }

  private Optional<Widget> getContinueWidget() {
    for (ContinueWidget continueWidget : widgetsToContinue) {
      var widget = getWidget(continueWidget);

      if (widget.isPresent() && !widget.get().isHidden()) {
        log.debug("Detected widget {}", continueWidget);
        return widget;
      }
    }

    return Optional.empty();
  }

  private void invokeContinue(Widget widget) {
    client.invokeMenuAction("", "", 0, MenuAction.WIDGET_TYPE_6.getId(), widget.getIndex(),
        widget.getId());
  }

  private void sendKeyDown() {
    assert !client.isClientThread();
    log.debug("Sending key down");
    keyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_SPACE);
  }

  private void sendKeyUp() {
    assert !client.isClientThread();
    log.debug("Sending key up");
    keyEvent(KeyEvent.KEY_RELEASED, KeyEvent.VK_SPACE);
  }

  private void sendKeyPress() {
    assert !client.isClientThread();
    log.debug("Sending key press");
    keyEvent(KeyEvent.KEY_PRESSED, KeyEvent.VK_SPACE);
    keyEvent(KeyEvent.KEY_RELEASED, KeyEvent.VK_SPACE);
  }

  private void keyEvent(int id, int key) {
    KeyEvent e = new KeyEvent(
        client.getCanvas(), id, System.currentTimeMillis(),
        0, key, KeyEvent.CHAR_UNDEFINED
    );
    client.getCanvas().dispatchEvent(e);
  }
}
