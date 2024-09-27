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
package com.maehem.javamancer.neuro.view.popup;

import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.item.DeckItem;
import com.maehem.javamancer.neuro.view.PopupListener;
import com.maehem.javamancer.neuro.view.cyberspace.ControlPanelPane;
import com.maehem.javamancer.neuro.view.cyberspace.VisualPane;
import com.maehem.javamancer.neuro.view.database.DatabaseView;
import java.util.logging.Level;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class CyberspacePopup extends PopupPane {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;
    private static final int CPANEL_TOP = 240;

    private static final int LARGE_WIDTH = 640;
    private static final int LARGE_HEIGHT = 288;
    private static final int LARGE_X = 0;
    private static final int LARGE_Y = 0;

    private final ControlPanelPane controlPanel;
    private final VisualPane visualPane;
    private final Pane databsePane;

    private DatabaseView databaseView;

    public enum State {
        EXPLORE, BATTLE
    }

    public CyberspacePopup(PopupListener l, GameState gs) {
        super(l, gs);
        setPrefSize(WIDTH, HEIGHT);
        setMinSize(WIDTH, HEIGHT);
        setLayoutX(0);
        setLayoutY(0);

        Rectangle backdrop = new Rectangle(WIDTH, HEIGHT, Color.BLACK);

        visualPane = new VisualPane(gs);
        controlPanel = new ControlPanelPane(l, gs, visualPane);
        controlPanel.setLayoutY(CPANEL_TOP);

        databsePane = new Pane();
        configDatabasePane();

        getChildren().addAll(
                backdrop, visualPane, controlPanel, databsePane
        );

        LOGGER.log(Level.SEVERE, "Cyberspace Popup: Set deck mode to CYBERSPACE.");
        gs.usingDeck.setMode(DeckItem.Mode.CYBERSPACE);
    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        LOGGER.log(Level.SEVERE, "CyberPopup: Key Event.");
        if (databaseView != null) {
            if (databaseView != null) {
                if (databaseView.handleKeyEvent(keyEvent)) {
                    LOGGER.log(Level.SEVERE, "CyberPopup: Key Event: True returned from Database View.");
                    databaseView = null;
                    cleanup();
                    return true;
                }
            }
        } else if (controlPanel.handleKeyEvent(keyEvent)) {
            LOGGER.log(Level.SEVERE, "CyberPopup: Key Event: True returned from control panel.");
            return true;
        }

        return false;
    }

    @Override
    public void cleanup() {
        LOGGER.log(Level.SEVERE, "Cyberspace Popup: Cleanup called.");
        gameState.usingDeck.setMode(DeckItem.Mode.NONE);
        gameState.databaseBattle = false;
        databaseView = null;
    }

    public void setState(State state) {
        visualPane.configState(state);
        controlPanel.configState(state);
    }

    public void tick() {
        if (gameState.databaseBattle && gameState.databaseBattleBegin) {
            gameState.databaseBattleBegin = false;
            setState(State.BATTLE);
        }
        controlPanel.tick();

        if (gameState.isFlatline()) {
            listener.popupExit();
        }
        if (databaseView != null) {
            databaseView.tick();
        } else {
            visualPane.tick();
        }
        if (gameState.isIceBroken()) {
            // Unset iceBroken
            gameState.setIceBroken(false);
            // Set Database View
            this.databaseView = DatabaseView.getView(gameState, databsePane, listener);
            databsePane.setVisible(true);
        }


    }

    private void configDatabasePane() {
        databsePane.setPrefSize(LARGE_WIDTH, LARGE_HEIGHT);
        databsePane.setMinSize(LARGE_WIDTH, LARGE_HEIGHT);
        databsePane.setMaxSize(LARGE_WIDTH, LARGE_HEIGHT);
        databsePane.setLayoutX(LARGE_X);
        databsePane.setLayoutY(LARGE_Y);
        databsePane.setId("neuro-popup");
        databsePane.setVisible(false);
    }

}
