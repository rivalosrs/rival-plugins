package com.rivalosrs.nightmarefps;

import javax.inject.Inject;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.api.queries.GameObjectQuery;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import org.pf4j.Extension;
import com.google.common.collect.ImmutableSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Extension
@PluginDescriptor(
    name = "Nightmare Fps",
    description = "A plugin to improve fps at Nightmare of Ashihama and Phosani's Nightmare by Rival",
    enabledByDefault = false
)
@Slf4j
public class NightmareFpsPlugin extends Plugin {
    static final String CONFIG_GROUP = "rivalNightmareFps";

    public static enum SceneLevel {
        LEVEL_0(0),
        LEVEL_1(1),
        LEVEL_2(2),
        LEVEL_3(3);

        public final int level;

        private SceneLevel(int level) {
            this.level = level;
        }
    }

    // Ids
    private static final Set<Integer> objectsToHide = ImmutableSet.of(
            37636,
            37637,
            37638,
            37639,
            37640,
            37642,
            37643,
            37644,
            37732,
            37733,
            37734,
            37735,
            37736,
            37737,
            29709 // Phosani's nightmare only
    );

    // Null ghost npc id
    private static final int NULL_GHOST_ID = 9458;

    // Npcs
    private static final Set<Integer> npcsToHide = ImmutableSet.of(
            NULL_GHOST_ID
    );

    private Set<Integer> hiddenNpcIndicesSet;

    @Inject
    private Client client;

    @Inject
    private ClientThread clientThread;

    @Inject
    private NightmareFpsConfig config;

    @Provides
    NightmareFpsConfig getConfig(final ConfigManager configManager)
    {
        return configManager.getConfig(NightmareFpsConfig.class);
    }

    @Override
    protected void startUp()
    {
        hiddenNpcIndicesSet = new HashSet<Integer>();
        if (client.getGameState() == GameState.LOGGED_IN)
        {
            clientThread.invoke(() -> {
                if (isInNightmare()) {
                    hideAll();
                }
            });
        }
    }

    @Override
    protected void shutDown()
    {
        client.setIsHidingEntities(false);

        clientThread.invoke(() ->
        {
            client.setGameState(GameState.LOADING);
            unhideAll();
        });
    }

    @Subscribe
    private void onConfigButtonPressed(ConfigButtonClicked configButtonClicked) {
        if (!configButtonClicked.getGroup().equalsIgnoreCase(CONFIG_GROUP))
        {
            return;
        }

        log.debug("button {} pressed!", configButtonClicked.getKey());
        switch (configButtonClicked.getKey()) {
            case "hideGameObjects":
                clientThread.invoke(this::hideObjects);
                break;
            case "hideNpcs":
                clientThread.invoke(this::hideNpcs);
                break;
        }
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged gameStateChanged)
    {
        log.debug("[NightmareFps] onGameStateChanged: {}", gameStateChanged);
        if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
        {
            if (isInNightmare()) {
                hideAll();
            }
        }
    }

    private void hideAll()
    {
        if (config.toHideSceneLevels())
            hideLowerPlanes();

        if (config.toHideGameObjects())
            hideObjects();

        if (config.toHideNpcs())
            hideNpcs();
    }

    private void unhideAll() {
        unhideLowerPlanes();
        unhideNpcs();
        // Game objects should automatically be unhidden whenever the game state changes
    }

    private void hideLowerPlanes() {
        int sceneLevel = config.minSceneLevel().level;
        client.getScene().setMinLevel(sceneLevel);
    }

    private void unhideLowerPlanes() {
        client.getScene().setMinLevel(SceneLevel.LEVEL_0.level);
    }

    private void hideNpcs()
    {
        List<NPC> npcs = client.getNpcs();
        log.debug("npcs size: {}", npcs.size());
        List<Integer> newHiddenNpcList = client.getHiddenNpcIndices();
        log.debug("[Before] newHiddenNpcList: {}, size: {}", newHiddenNpcList, newHiddenNpcList.size());

        npcs.forEach((npc) -> {
            // If the npc is one that we're trying to hide
            if (npcsToHide.contains(npc.getId()) && !hiddenNpcIndicesSet.contains(npc.getIndex())) {
                // Then we add the index of it
                log.debug("Added Npc id {} with index {} to hiddenNpcIndicesSet", npc.getId(), npc.getIndex());
                hiddenNpcIndicesSet.add(npc.getIndex());
            }
        });

        newHiddenNpcList.addAll(hiddenNpcIndicesSet);

        // Dedupe the list
        List<Integer> finalNewHiddenNpcList = newHiddenNpcList.stream().distinct().collect(Collectors.toList());

        client.setIsHidingEntities(true);
        client.setHiddenNpcIndices(finalNewHiddenNpcList);
        log.debug("[After] newHiddenNpcList: {}, size: {}", finalNewHiddenNpcList, finalNewHiddenNpcList.size());
    }

    private void unhideNpcs()
    {
        List<Integer> newHiddenNpcsList = client.getHiddenNpcIndices();
        log.debug("unhideNpcs starting with {} npcs in newHiddenNpcsList", newHiddenNpcsList.size());
        clearHiddenNpcs();
        log.debug("unhideNpcs removed {} npcs from newHiddenNpcsList (unhiding those npcs)", newHiddenNpcsList.size());
    }

    private void hideObjects()
    {
        Scene scene = client.getScene();

        List<GameObject> gameObjects = new GameObjectQuery().idEquals(objectsToHide).result(client).list;

        gameObjects.forEach(gameObject ->
                {
                    scene.removeGameObject(gameObject);
                    log.debug("Removed game object id: {} name: {}", gameObject.getId(), gameObject.getName());
                }
        );
        log.debug("Removed {} objects from Nightmare", gameObjects.size());
    }

    private boolean isInNightmare() {
        List<NPC> npcs = client.getNpcs();
        return npcs.stream().anyMatch(npc -> npc.getId() == NULL_GHOST_ID);
    }

    private void clearHiddenNpcs() {
        if (!hiddenNpcIndicesSet.isEmpty()) {
            List<Integer> newHiddenNpcIndicesList = client.getHiddenNpcIndices();
            newHiddenNpcIndicesList.removeAll(hiddenNpcIndicesSet);
            client.setHiddenNpcIndices(newHiddenNpcIndicesList);
            hiddenNpcIndicesSet.clear();
        }
    }
}