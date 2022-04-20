package net.runelite.client.plugins.eventdebugger;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.GraphicsObject;
import net.runelite.api.GroundObject;
import net.runelite.api.Hitsplat;
import net.runelite.api.ItemContainer;
import net.runelite.api.NPC;
import net.runelite.api.NPCComposition;
import net.runelite.api.Player;
import net.runelite.api.PlayerComposition;
import net.runelite.api.Projectile;
import net.runelite.api.Script;
import net.runelite.api.ScriptEvent;
import net.runelite.api.Skill;
import net.runelite.api.Tile;
import net.runelite.api.TileItem;
import net.runelite.api.WallObject;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.ActorDeath;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.DecorativeObjectChanged;
import net.runelite.api.events.DecorativeObjectDespawned;
import net.runelite.api.events.DecorativeObjectSpawned;
import net.runelite.api.events.GameObjectChanged;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.GraphicChanged;
import net.runelite.api.events.GraphicsObjectCreated;
import net.runelite.api.events.GroundObjectChanged;
import net.runelite.api.events.GroundObjectDespawned;
import net.runelite.api.events.GroundObjectSpawned;
import net.runelite.api.events.HitsplatApplied;
import net.runelite.api.events.InteractingChanged;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.ItemDespawned;
import net.runelite.api.events.ItemQuantityChanged;
import net.runelite.api.events.ItemSpawned;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.MenuOpened;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.NpcChanged;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.api.events.PlayerChanged;
import net.runelite.api.events.PlayerDespawned;
import net.runelite.api.events.PlayerSpawned;
import net.runelite.api.events.ProjectileMoved;
import net.runelite.api.events.ScriptCallbackEvent;
import net.runelite.api.events.ScriptPostFired;
import net.runelite.api.events.ScriptPreFired;
import net.runelite.api.events.StatChanged;
import net.runelite.api.events.VarClientIntChanged;
import net.runelite.api.events.VarClientStrChanged;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.events.WallObjectChanged;
import net.runelite.api.events.WallObjectDespawned;
import net.runelite.api.events.WallObjectSpawned;
import net.runelite.api.events.WidgetClosed;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.events.WidgetMenuOptionClicked;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;

@Extension
@PluginDescriptor(
    name = "Event Debugger",
    description = "A plugin to help debug Runelite API events by Rival",
    enabledByDefault = false
)
@Slf4j
public class EventDebuggerPlugin extends Plugin
{
    static final String CONFIG_GROUP = "rivaleventdebugger";

    @Inject
    private Client client;

    @Inject
    private EventDebuggerConfig config;

    @Provides
    EventDebuggerConfig getConfig(final ConfigManager configManager)
    {
        return configManager.getConfig(EventDebuggerConfig.class);
    }

    @Override
    protected void startUp() throws Exception
    {
    }

    @Override
    protected void shutDown() throws Exception
    {
    }

    @Subscribe
    private void onActorDeath(ActorDeath event)
    {
        if (!config.logActorDeath()) return;

        final Actor actor = event.getActor();

        if (config.filterNullEvents() && actor.getName() == null) return;

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onActorDeath] Actor {} has died", actor.getName());
        }
    }

    @Subscribe
    public void onAnimationChanged(AnimationChanged event)
    {
        if (!config.logAnimationChanged()) return;

        final Actor actor = event.getActor();
        final int animationId = actor.getAnimation();

        if (config.filterNullEvents() && (actor.getName() == null || animationId == -1))
        {
            return;
        }

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onAnimationChanged] {} changed animation {}", actor.getName(), actor.getAnimation());
        }
    }

    @Subscribe
    public void onChatMessage(ChatMessage event)
    {
        if (!config.logChatMessage()) return;

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onChatMessage] Type: {}, Name: {}, Message: {}, Sender: {}", event.getType(), event.getName(), event.getMessage(), event.getSender());
        }
    }

    @Subscribe
    public void onDecorativeObjectChanged(DecorativeObjectChanged event)
    {
        if(!config.logDecorativeObjectChanged()) return;

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onDecorativeObjectChanged] Tile: {}, Previous DecorativeObject: {}, New DecorativeObject: {}", event.getTile(), event.getPrevious(), event.getDecorativeObject());
        }
    }

    @Subscribe
    public void onDecorativeObjectDespawned(DecorativeObjectDespawned event)
    {
        if(!config.logDecorativeObjectDespawned()) return;

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onDecorativeObjectDespawned] Tile: {}, DecorativeObject: {}", event.getTile(), event.getDecorativeObject());
        }
    }

    @Subscribe
    public void onDecorativeObjectSpawned(DecorativeObjectSpawned event)
    {
        if(!config.logDecorativeObjectSpawned()) return;

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onDecorativeObjectSpawned] Tile: {}, DecorativeObject: {}", event.getTile(), event.getDecorativeObject());
        }
    }

    @Subscribe
    public void onGameObjectChanged(GameObjectChanged event)
    {
        if(!config.logGameObjectChanged()) return;

        final GameObject previousGameObject = event.getPrevious();
        final GameObject currentGameObject = event.getGameObject();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onGameObjectChanged] Tile: {}, Previous GameObject id: {}, name: {}, Current GameObject id: {}, name: {}", event.getTile(), previousGameObject.getId(), previousGameObject.getName(), currentGameObject.getId(), currentGameObject.getName());
        }
    }

    @Subscribe
    public void onGameObjectDespawned(GameObjectDespawned event)
    {
        if(!config.logGameObjectDespawned()) return;

        final GameObject gameObject = event.getGameObject();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onGameObjectDespawned] Tile: {}, GameObject id: {}, name: {}", event.getTile(), gameObject.getId(), gameObject.getName());
        }
    }

    @Subscribe
    public void onGameObjectSpawned(GameObjectSpawned event)
    {
        if(!config.logGameObjectSpawned()) return;

        final GameObject gameObject = event.getGameObject();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onGameObjectSpawned] Tile: {}, GameObject id: {}, name: {}", event.getTile(), gameObject.getId(), gameObject.getName());

        }
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged event)
    {
        if(!config.logGameStateChanged()) return;

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onGameStateChanged] GameState: {}", event.getGameState());
        }
    }

    @Subscribe
    private void onGameTick(GameTick event)
    {
        if (client == null || client.getLocalPlayer() == null) return;
        if (!config.logGameTick()) return;

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onGameTick] Tick {}", client.getTickCount());
        }
    }


    @Subscribe
    public void onGraphicChanged(GraphicChanged event)
    {
        if(!config.logGraphicChanged()) return;

        final Actor actor = event.getActor();
        final int graphicId = event.getActor().getGraphic();

        if (config.filterNullEvents() && (actor.getName() == null || graphicId == -1)) return;

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onGraphicChanged] {} changed animation {}", actor.getName(), actor.getAnimation());
        }
    }

    @Subscribe
    public void onGraphicsObjectCreated(GraphicsObjectCreated event)
    {
        if(!config.logGraphicsObjectCreated()) return;

        final GraphicsObject graphicsObject = event.getGraphicsObject();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onGraphicsObjectCreated] GraphicsObject id: {} at LocalPoint (x: {}, y: {})",
                    graphicsObject.getId(), graphicsObject.getLocation().getX(), graphicsObject.getLocation().getY());
        }
    }

    @Subscribe
    public void onGroundObjectChanged(GroundObjectChanged event)
    {
        if(!config.logGroundObjectChanged()) return;

        final GroundObject previous = event.getPrevious();
        final GroundObject current = event.getGroundObject();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onGroundObjectChanged] Tile: {}, Previous GroundObject name: {}, id: {}, Current GroundObject name: {}, id: {}", event.getTile(), previous.getName(), previous.getId(), current.getName(), current.getId());
        }
    }

    @Subscribe
    public void onGroundObjectDespawned(GroundObjectDespawned event)
    {
        if(!config.logGroundObjectDespawned()) return;

        final GroundObject groundObject = event.getGroundObject();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onGroundObjectDespawned] Tile: {}, GroundObject name: {}, id: {}", event.getTile(), groundObject.getName(), groundObject.getId());
        }
    }

    @Subscribe
    public void onGroundObjectSpawned(GroundObjectSpawned event)
    {
        if(!config.logGroundObjectSpawned()) return;

        final GroundObject groundObject = event.getGroundObject();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onGroundObjectSpawned] Tile: {}, GroundObject name: {}, id: {}", event.getTile(), groundObject.getName(), groundObject.getId());
        }
    }

    @Subscribe
    public void onHitsplatApplied(HitsplatApplied event)
    {
        if (!config.logHitsplatApplied()) return;

        final Hitsplat hitsplat = event.getHitsplat();
        final Actor actor = event.getActor();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onHitsplatApplied] Actor name: {}, HitSplat type: {}, amount: {}, disappearsOnGameCycle: {}",
                    actor.getName(), hitsplat.getHitsplatType(), hitsplat.getAmount(), hitsplat.getDisappearsOnGameCycle());
        }
    }

    @Subscribe
    public void onInteractingChanged(InteractingChanged event)
    {
        if(!config.logInteractingChanged()) return;

        final Actor source = event.getSource();
        final Actor target = event.getTarget();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onInteractingChanged] Source Actor name: {}, Target Actor name: {}", source.getName(), target.getName());
        }
    }

    @Subscribe
    public void onItemContainerChanged(ItemContainerChanged event)
    {
        if(!config.logItemContainerChanged()) return;

        final int containerId = event.getContainerId();
        final ItemContainer itemContainer = event.getItemContainer();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onItemContainerChanged] Container id: {}, ItemContainer size: {}", containerId, itemContainer.size());
        }
    }

    @Subscribe
    public void onItemDespawned(ItemDespawned event)
    {
        if(!config.logItemDespawned()) return;

        final TileItem tileItem = event.getItem();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onItemDespawned] Tile: {}, TileItem id: {}, quantity: {}", event.getTile(), tileItem.getId(), tileItem.getQuantity());
        }
    }

    @Subscribe
    public void onItemQuantityChanged(ItemQuantityChanged event)
    {
        if(!config.logItemQuantityChanged()) return;

        final TileItem tileItem = event.getItem();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onItemQuantityChanged] Tile: {}, TileItem id: {}, old quantity: {}, new quantity: {}", event.getTile(), tileItem.getId(), event.getOldQuantity(), event.getNewQuantity());
        }
    }

    @Subscribe
    public void onItemSpawned(ItemSpawned event)
    {
        if(!config.logItemSpawned()) return;

        final TileItem tileItem = event.getItem();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onItemSpawned] Tile: {}, TileItem id: {}, quantity: {}", event.getTile(), tileItem.getId(), tileItem.getQuantity());

        }
    }

    @Subscribe
    public void onMenuEntryAdded(MenuEntryAdded event)
    {
        if(!config.logMenuEntryAdded()) return;

        final MenuEntryAdded menuEntry = event;

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onMenuEntryAdded] MenuEntry option: {}, target: {}, id: {}, opcode: {}, actionParam: {}, actionParam1: {}, forceLeftClick: {}", menuEntry.getOption(), menuEntry.getTarget(), menuEntry.getIdentifier(), menuEntry.getOpcode(), menuEntry.getActionParam0(), menuEntry.getActionParam1(), menuEntry.isForceLeftClick());
        }
    }

    @Subscribe
    public void onMenuOpened(MenuOpened event)
    {
        if(!config.logMenuOpened()) return;

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onMenuOpened] TODO");
        }
    }

    @Subscribe
    public void onMenuOptionClicked(MenuOptionClicked event)
    {

        if(!config.logMenuOptionClicked()) return;

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onMenuOptionClicked] MenuOptionClicked option: {}, target: {}, id: {}, opcode/MenuAction: {}, actionParam: {}, widgetId: {}, consumed: {}", event.getMenuOption(), event.getMenuTarget(), event.getId(), event.getMenuAction(), event.getActionParam(), event.getWidgetId(), event.isConsumed());
        }
    }

    @Subscribe
    public void onNpcChanged(NpcChanged event)
    {
        if(!config.logNpcChanged()) return;

        final NPC npc = event.getNpc();
        final NPCComposition npcComposition = event.getOld();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onNpcChanged] Before NPC id: {}, name: {}", npcComposition.getId(), npcComposition.getName());
            log.info("[onNpcChanged] After NPC id: {}, name: {}, animation id: {}, pose id: {}, graphics id: {}", npc.getId(), npc.getName(), npc.getAnimation(), npc.getPoseAnimation(), npc.getGraphic());
        }
    }

    @Subscribe
    public void onNpcDespawned(NpcDespawned event)
    {
        if(!config.logNpcDespawned()) return;

        final NPC npc = event.getNpc();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onNpcDespawned] NPC id: {}, name: {}", npc.getId(), npc.getName());
        }
    }

    @Subscribe
    public void onNpcSpawned(NpcSpawned event)
    {
        if(!config.logNpcSpawned()) return;

        final NPC npc = event.getNpc();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onNpcSpawned] NPC id: {}, name: {}", npc.getId(), npc.getName());
        }
    }

    @Subscribe
    public void onPlayerChanged(PlayerChanged event)
    {
        if(!config.logPlayerChanged()) return;

        final Player player = event.getPlayer();
        final PlayerComposition playerComposition = player.getPlayerComposition();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onPlayerChanged] Player playerId: {}, name: {}, animation: {}, equipment ids: {}", player.getPlayerId(), player.getName(), player.getAnimation(), playerComposition.getEquipmentIds());
        }
    }

    @Subscribe
    public void onPlayerDespawned(PlayerDespawned event)
    {
        if(!config.logPlayerDespawned()) return;

        final Player player = event.getPlayer();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onPlayerDespawned] Player playerId: {}, name: {}", player.getPlayerId(), player.getName());

        }
    }

    @Subscribe
    public void onPlayerSpawned(PlayerSpawned event)
    {
        if(!config.logPlayerSpawned()) return;

        final Player player = event.getPlayer();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onPlayerSpawned] Player playerId: {}, name: {}", player.getPlayerId(), player.getName());

        }
    }

    @Subscribe
    public void onProjectileMoved(ProjectileMoved event)
    {
        if(!config.logProjectileMoved()) return;

        final Projectile projectile = event.getProjectile();
        final Actor actor = projectile.getInteracting();
        final LocalPoint localPoint = event.getPosition();
        final int z = event.getZ();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onProjectileMoved] Projectile id: {}, (x: {}, y: {}), Actor name: {} at LocalPoint (x: {}, y: {})", projectile.getId(), actor.getName(), localPoint.getY(), localPoint.getY());
        }
    }


    @Subscribe
    public void onScriptCallbackEvent(ScriptCallbackEvent event)
    {
        if(!config.logScriptCallbackEvent()) return;

        final Script script = event.getScript();
        final String eventName = event.getEventName();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onScriptCallbackEvent] Script: {}, eventName: {}", script, eventName);
        }
    }

    @Subscribe
    public void onScriptPostFired(ScriptPostFired event)
    {
        if(!config.logScriptPostFired()) return;

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onScriptPostFired] Script id: {}", event.getScriptId());
        }
    }

    @Subscribe
    public void onScriptPreFired(ScriptPreFired event)
    {
        if(!config.logScriptPreFired()) return;

        final int scriptId = event.getScriptId();
        final ScriptEvent scriptEvent = event.getScriptEvent();
        final Widget eventWidget = scriptEvent.getSource();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onScriptPreFired] Script id: {}, ScriptEvent op: {}, opbase: {}, mouseX: {}, getTypedKeyChar/Code: {}/{}, Widget id: {}, name: {}, type: {}", scriptId, scriptEvent.getOp(), scriptEvent.getOpbase(), scriptEvent.getMouseX(), scriptEvent.getTypedKeyChar(), scriptEvent.getTypedKeyCode(), eventWidget.getId(), eventWidget.getName(), eventWidget.getType());
        }
    }

    @Subscribe
    public void onStatChanged(StatChanged event)
    {
        if(!config.logStatChanged()) return;

        final Skill eventSkill = event.getSkill();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onStatChanged] Skill: {}, xp: {}, level: {}, boostedLevel: {}", eventSkill.getName(), event.getXp(), event.getLevel(), event.getBoostedLevel());
        }
    }

    @Subscribe
    public void onVarbitChanged(VarbitChanged event)
    {
        if(!config.logVarbitChanged()) return;

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onVarbitChanged] Index: {}", event.getIndex());
        }
    }

    @Subscribe
    public void onVarClientIntChanged(VarClientIntChanged event)
    {
        if(!config.logVarClientIntChanged()) return;

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onVarClientIntChanged] Index: {}", event.getIndex());
        }
    }

    @Subscribe
    public void onVarClientStrChanged(VarClientStrChanged event)
    {
        if(!config.logVarClientStrChanged()) return;

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onVarClientStrChanged] Index: {}", event.getIndex());
        }
    }

    @Subscribe
    public void onWallObjectChanged(WallObjectChanged event)
    {
        if(!config.logWallObjectChanged()) return;

        final Tile tile = event.getTile();
        final WallObject previousWallObject = event.getPrevious();
        final WallObject currentWallObject = event.getWallObject();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onWallObjectChanged] Tile: {}, Previous WallObject id: {}, name: {}, Current WallObject id: {}, name: {} ", tile, previousWallObject.getId(), previousWallObject.getName(), currentWallObject.getId(), currentWallObject.getName());
        }
    }

    @Subscribe
    public void onWallObjectDespawned(WallObjectDespawned event)
    {
        if(!config.logWallObjectDespawned()) return;

        final Tile tile = event.getTile();
        final WallObject wallObject = event.getWallObject();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onWallObjectDespawned] Tile: {}, WallObject id: {}, name: {}", tile, wallObject.getId(), wallObject.getName());
        }
    }

    @Subscribe
    public void onWallObjectSpawned(WallObjectSpawned event)
    {
        if(!config.logWallObjectSpawned()) return;

        final Tile tile = event.getTile();
        final WallObject wallObject = event.getWallObject();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onWallObjectSpawned] Tile: {}, WallObject id: {}, name: {}", tile, wallObject.getId(), wallObject.getName());
        }
    }

    @Subscribe
    public void onWidgetClosed(WidgetClosed event)
    {
        if(!config.logWidgetClosed()) return;

        final int groupId = event.getGroupId();
        final int modalMode = event.getModalMode();
        final boolean unload = event.isUnload();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onWidgetClosed] Event groupId: {}, modalMode: {}, unload: {}", groupId, modalMode, unload);
        }
    }

    @Subscribe
    public void onWidgetLoaded(WidgetLoaded event)
    {
        if(!config.logWidgetLoaded()) return;

        final int groupId = event.getGroupId();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onWidgetLoaded] Event groupId: {}", groupId);
        }
    }

    @Subscribe
    public void onWidgetMenuOptionClicked(WidgetMenuOptionClicked event)
    {
        if(!config.logWidgetMenuOptionClicked()) return;

        final String menuOption = event.getMenuOption();
        final String menuTarget = event.getMenuTarget();
        final WidgetInfo widget = event.getWidget();
        final int widgetId = event.getWidgetId();

        if (config.logToGame())
        {

        }

        if (config.logToLogger()) {
            log.info("[onWidgetMenuOptionClicked] menuOption: {}, menuTarget: {}, Widget id: {}, info: {}", menuOption, menuTarget, widgetId, widget);
        }
    }
}