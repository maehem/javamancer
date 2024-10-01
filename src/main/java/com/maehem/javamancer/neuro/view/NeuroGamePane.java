/*
 * MIT License
 *
 * Copyright (c) 2024 Mark J. Koch ( @maehem on GitHub )
 *
 * Portions of this software are Copyright (c) 2018 Henadzi Matuts and are
 * derived from their project: https://github.com/HenadziMatuts/Reuromancer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.maehem.javamancer.neuro.view;

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.GameStateUtils;
import com.maehem.javamancer.neuro.model.deck.Cyberspace7DeckItem;
import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.item.RealItem;
import com.maehem.javamancer.neuro.model.room.Room;
import com.maehem.javamancer.neuro.model.room.RoomBounds.Door;
import com.maehem.javamancer.neuro.model.room.RoomMap;
import com.maehem.javamancer.neuro.model.room.RoomPosition;
import com.maehem.javamancer.neuro.model.warez.AcidWarez;
import com.maehem.javamancer.neuro.model.warez.CyberspaceWarez;
import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * Main container for the game engine and manager of game modes.
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class NeuroGamePane extends Pane implements NeuroModePaneListener {

    public static final Logger LOGGER = Logging.LOGGER;

    //private static final Room ROOM_START = Room.R1;
    private static final Room ROOM_START = Room.R1; // Debug value

    public static final int SYSTEM_WINDOW_HEADER_H = 20; // MacOS
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480 + SYSTEM_WINDOW_HEADER_H;
    private EventHandler<ActionEvent> actionHandler;
    private GameState gameState;

    private NeuroModePane mode;
    private AnimationTimer timer;
    private int frameCount = 0;
    private final ResourceManager resourceManager;

    //private boolean pause = false;
    public NeuroGamePane(File resourceFolder) {
        this.setPrefSize(WIDTH, HEIGHT);
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        this.setClip(new Rectangle(WIDTH, HEIGHT));

        resourceManager = new ResourceManager(resourceFolder);
        this.gameState = new GameState(resourceManager);

        // TODO: refernce resource manager from gamestate.
        setMode(new TitleMode(this, gameState));

        initGameLoop();
    }

    /**
     * Called near the end of NEW_GAME action.
     *
     * This should be empty for any release version.
     */
    private void initTestItems() {

        gameState.room = Room.R8; // Gentleman Loser

        // Game Test Items
        Cyberspace7DeckItem testDeckItem = new Cyberspace7DeckItem();
        testDeckItem.needsRepair = true;
        gameState.inventory.add(testDeckItem); // Test item
        gameState.deckSlots = testDeckItem.nSlots;
        gameState.addSoftware(new CyberspaceWarez(1));
        gameState.addSoftware(new AcidWarez(1)); // Should delete when used.

        gameState.inventory.add(new RealItem(Item.Catalog.CAVIAR, 1));

        //bankZurichCreated = "11/16/58"; // Test Item
        //bankZurichBalance = 2000; // Test Item
        gameState.chipBalance = 30; // Test Item

    }

    private void initGameLoop() {
        // toggle the visibility of 'rect' every 500ms
        timer = new AnimationTimer() {

            private long lastToggle;

            @Override
            public void handle(long now) {
                if (lastToggle == 0L) {
                    lastToggle = now;
                } else {
                    long diff = now - lastToggle;
                    if (diff >= 66_000_000L) { // 66,000,000ns == 66ms == 15FPS
                        loop();
                        lastToggle = now;
                    }
                }
            }
        };
        timer.start();
    }

    private void loadSlot(int slot) {
        gameState.cleanUp();

        gameState = new GameState(resourceManager);
        GameStateUtils.loadModel(gameState, slot);

        setMode(new RoomMode(this, gameState));
    }

    private void loop() {
        if (gameState.loadSlot > 0) {
            LOGGER.log(Level.CONFIG, "User requests load slot {0}.", gameState.loadSlot);

            loadSlot(gameState.loadSlot);
            return;
        }
        if (gameState.requestQuit) {
            neuroModeActionPerformed(Action.QUIT, null);
        }

        if (!gameState.useDoor.equals(Door.NONE)) {
            // Switch room
            LOGGER.log(Level.SEVERE, "Use Door: {0}", gameState.useDoor.name());
            gameState.room = RoomMap.getRoom(gameState.room, gameState.useDoor);
            LOGGER.log(Level.SEVERE, "Move to new room: {0}:{1} from previous room door {2}",
                    new Object[]{gameState.room.name(), gameState.room.roomName, gameState.useDoor.name()}
            );
            setMode(new RoomMode(this, gameState));
            RoomPosition roompos = RoomPosition.get(gameState.room);
            gameState.roomPosX = roompos.playerX;
            gameState.roomPosY = roompos.playerY;
            LOGGER.log(Level.SEVERE, "TODO: Set default player position: {0},{1}",
                    new Object[]{gameState.roomPosX, gameState.roomPosY}
            );
        } else {
            if (!gameState.pause) {
                // Handlemusic state.
                if (++frameCount > 15) {
                    gameState.addMinute();
                    frameCount = 0;
                    mode.updateStatus();

                    gameState.updateConstitution();
                }

                mode.tick();
            } else {
                //LOGGER.log(Level.SEVERE, "Paused");
            }
        }
    }

    public void pushProperties(Properties properties) {

    }

    public void pullProperties(Properties properties) {

    }

    @Override
    public void neuroModeActionPerformed(Action action, Object actionObjects[]) {
        switch (action) {
            case QUIT -> {
                // Parent watches this action via actionHandler and will switch
                // screen back to main Javamancer application.
                gameState.resourceManager.musicManager.stopAll();
                LOGGER.log(Level.CONFIG, "User Quit Game.");
                LOGGER.log(Level.CONFIG, "Stop Timer Loop.");
                timer.stop();
                actionHandler.handle(new ActionEvent(action, this));
            }
            case LOAD -> {
                LOGGER.log(Level.CONFIG, "Load Saved Game #{0}", actionObjects[0]);
                gameState.loadSlot((int) actionObjects[0]);
            }
            case NEW_GAME -> {
                Object actionObject = actionObjects[0];
                if (actionObject != null && actionObject instanceof String s) {
                    if (!s.isBlank()) {
                        gameState.name = s;
                    }
                    LOGGER.log(Level.CONFIG, "New Game with player name: {0}", gameState.name);
                    gameState.initNewGame();
                    gameState.room = ROOM_START;
                    initTestItems();

                    LOGGER.log(Level.SEVERE, "Set default player position: {0},{1}",
                            new Object[]{gameState.roomPosX, gameState.roomPosY}
                    );
                    gameState.roomPosX = RoomPosition.get(ROOM_START).playerX;
                    gameState.roomPosY = RoomPosition.get(ROOM_START).playerY;

                    setMode(new RoomMode(this, gameState));
                } else {
                    LOGGER.log(Level.CONFIG, "New Game actionObject[0] was null!");
                }
            }
            case MUTE_MUSIC -> {
                // Toggle music mute state.
                gameState.resourceManager.musicManager.toggleMute();
            }
        }
    }

    private void setMode(NeuroModePane newMode) {
        if (this.mode == null || !this.mode.equals(newMode)) {
            // tell current mode to de-init.
            gameState.pause = true;
            gameState.useDoor = Door.NONE; // Clear any just used door.
            if (this.mode != null) {
                this.mode.destroy();
            }

            getChildren().clear();
            this.mode = newMode;
            getChildren().add(newMode);
            newMode.initCursor();
            gameState.pause = false;
        }
    }

    public final void setOnAction(EventHandler<ActionEvent> handler) {
        actionHandler = handler;
    }

}
