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
import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public static final double RESOURCE_SCALE = 2.0;
    private EventHandler<ActionEvent> actionHandler;
    private final ResourceManager resourceManager;
    private final GameState gameState;

    private NeuroModePane mode;

    public NeuroGamePane(File resourceFolder) {
        this.setPrefSize(WIDTH, HEIGHT);
        this.setWidth(WIDTH);
        this.setHeight(HEIGHT);
        this.setClip(new Rectangle(WIDTH, HEIGHT));

        this.resourceManager = new ResourceManager(resourceFolder);
        this.gameState = new GameState();

        doTitleScreen();
    }

    private void doTitleScreen() {
//        getChildren().clear();
//
//        TitleMode titleMode = new TitleMode(this, resourceManager);
//        getChildren().add(titleMode);
//
//        mode = titleMode;
        setMode(new TitleMode(this, resourceManager));

        //       titleMode.initCursor();
    }

//    private void doRoom( Room r ) {
//        getChildren().clear();
//
//    }
//

    public void pushProperties(Properties properties) {

    }

    public void pullProperties(Properties properties) {

    }

    @Override
    public void neuroModeActionPerformed(Action action, Object actionObjects[]) {
        switch (action) {
            case QUIT -> {
                // Parent watches this action and will switch screen back to
                // main Javamancer application.
                LOGGER.log(Level.CONFIG, "User Quit Game.");
                actionHandler.handle(new ActionEvent(action, this));
            }
            case LOAD -> {
                LOGGER.log(Level.CONFIG, "Load Saved Game #" + actionObjects[0]);
            }
            case NEW_GAME -> {
                Object actionObject = actionObjects[0];
                if (actionObject != null && actionObject instanceof String s) {
                    if (!s.isBlank()) {
                        gameState.name = s;
                    }
                    LOGGER.log(Level.CONFIG, "New Game with player name: " + gameState.name);

                    // Change mode to Room 1.
                    setMode(new RoomMode(this, resourceManager, Room.R1));
                } else {
                    LOGGER.log(Level.CONFIG, "New Game actionObject[0] was null!");
                }
            }
        }
    }

    private void setMode(NeuroModePane mode) {
        if (this.mode == null || !this.mode.equals(mode)) {
            // tell current mode to de-init.
            getChildren().clear();
            this.mode = mode;
            getChildren().add(mode);
            mode.initCursor();
        }
    }

    public final void setOnAction(EventHandler<ActionEvent> handler) {
        actionHandler = handler;
    }
}
