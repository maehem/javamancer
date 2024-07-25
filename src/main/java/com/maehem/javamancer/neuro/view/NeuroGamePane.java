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
import com.maehem.javamancer.neuro.model.Room;
import com.maehem.javamancer.neuro.model.RoomPosition;
import com.maehem.javamancer.neuro.model.item.Item.Catalog;
import com.maehem.javamancer.neuro.model.item.RealItem;
import com.maehem.javamancer.neuro.model.item.SkillItem;
import com.maehem.javamancer.neuro.model.skill.BarganingSkill;
import com.maehem.javamancer.neuro.model.skill.DebugSkill;
import com.maehem.javamancer.neuro.model.skill.IceBreakingSkill;
import com.maehem.javamancer.neuro.model.skill.SophistrySkill;
import com.maehem.javamancer.neuro.model.skill.WarezAnalysisSkill;
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
    public static final int SYSTEM_WINDOW_HEADER_H = 20; // MacOS
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480 + SYSTEM_WINDOW_HEADER_H;
    private EventHandler<ActionEvent> actionHandler;
    private final ResourceManager resourceManager;
    private final GameState gameState;

    private NeuroModePane mode;
    private AnimationTimer timer;
    private int frameCount = 0;

    //private boolean pause = false;

    public NeuroGamePane(File resourceFolder) {
        this.setPrefSize(WIDTH, HEIGHT);
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        this.setClip(new Rectangle(WIDTH, HEIGHT));

        this.resourceManager = new ResourceManager(resourceFolder);
        this.gameState = new GameState();

        setMode(new TitleMode(this, resourceManager, gameState));

        initGameLoop();
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

    private void loop() {
        if (!gameState.pause) {
            // Handlemusic state.
            if (++frameCount > 15) {
                gameState.addMinute();
                frameCount = 0;
                mode.updateStatus();
            }
            mode.tick();
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
                LOGGER.log(Level.CONFIG, "User Quit Game.");
                LOGGER.log(Level.CONFIG, "Stop Timer Loop.");
                timer.stop();
                actionHandler.handle(new ActionEvent(action, this));
            }
            case LOAD -> {
                LOGGER.log(Level.CONFIG, "Load Saved Game #{0}", actionObjects[0]);
            }
            case NEW_GAME -> {
                Object actionObject = actionObjects[0];
                if (actionObject != null && actionObject instanceof String s) {
                    if (!s.isBlank()) {
                        gameState.name = s;
                    }
                    LOGGER.log(Level.CONFIG, "New Game with player name: {0}", gameState.name);
                    initGame();

                    // Change mode to Room 1.
                    setMode(new RoomMode(this, resourceManager, gameState, Room.R1));
                    gameState.roomPosX = RoomPosition.R1.playerX;
                    gameState.roomPosY = RoomPosition.R1.playerY;
                    LOGGER.log(Level.SEVERE, "Set default player position: {0},{1}",
                            new Object[]{RoomPosition.R1.playerX, RoomPosition.R1.playerY}
                    );
                } else {
                    LOGGER.log(Level.CONFIG, "New Game actionObject[0] was null!");
                }
            }
        }
    }

    private void setMode(NeuroModePane newMode) {
        if (this.mode == null || !this.mode.equals(newMode)) {
            // tell current mode to de-init.
            gameState.pause = true;
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

    private void initGame() {
        resourceManager.initNewsArticles(
                gameState.news,
                gameState.name,
                gameState.getDateString()
        );
        resourceManager.initBbsMessages(
                gameState.bbs,
                gameState.name
        );
        gameState.inventory.add(new RealItem(Catalog.CREDITS));
        gameState.inventory.add(new RealItem(Catalog.PAWNTICKET));
        gameState.inventory.add(new RealItem(Catalog.SAKE));
        gameState.inventory.add(new RealItem(Catalog.CAVIAR));
        gameState.inventory.add(new SkillItem(Catalog.HARDWAREREPAIR));
        gameState.inventory.add(new SkillItem(Catalog.COPTALK));

        gameState.skills.add(new BarganingSkill());
        gameState.skills.add(new DebugSkill());
        gameState.skills.add(new IceBreakingSkill());
        gameState.skills.add(new SophistrySkill());
        gameState.skills.add(new WarezAnalysisSkill());

    }
}
