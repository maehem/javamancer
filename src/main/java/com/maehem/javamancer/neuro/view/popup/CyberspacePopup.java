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
import com.maehem.javamancer.neuro.view.RoomMode;
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
public class CyberspacePopup extends PopupPane implements PopupListener {

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

        LOGGER.log(Level.FINE, "Cyberspace Popup: Set deck mode to CYBERSPACE.");
        DeckItem deck = gs.usingDeck;
        deck.setMode(DeckItem.Mode.CYBERSPACE);
        deck.setCordX(gs.room.getJack().x);
        deck.setCordY(gs.room.getJack().y);
    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        LOGGER.log(Level.FINE, "CyberspacePopup: Key Event.");
        if (databaseView != null) {
            if (databaseView.handleKeyEvent(keyEvent)) {
                LOGGER.log(Level.FINE, "CyberspacePopup: Key Event: True returned from Database View.");
                databaseView = null;
                //cleanup();
                return false;
            }
        } else if (controlPanel.handleKeyEvent(keyEvent)) {
            LOGGER.log(Level.FINE, "CyberspacePopup: Key Event: True returned from control panel.");
            return true;
        }

        return false;
    }

    @Override
    public void cleanup() {
        LOGGER.log(Level.FINE, "Cyberspace Popup: Cleanup called.");
        gameState.usingDeck.setMode(DeckItem.Mode.NONE);
        gameState.databaseBattle = false;
        databaseView = null;
        gameState.usingDeck = null;
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

        // Player got past ICE and AI. Open the Database page.
        if (visualPane.isVisible() && visualPane.readyForDatabase()) {
            setState(State.EXPLORE);
            // Set Database View
            this.databaseView = DatabaseView.getView(gameState, databsePane, this);
            databsePane.setVisible(true);
            visualPane.setVisible(false);
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

    /**
     * This is called when the player exits a DB in Cyberspace.
     * 
     * @return 
     */
    @Override
    public boolean popupExit() {
        setState(State.EXPLORE);
        databaseView = null;
        databsePane.setVisible(false);
        visualPane.setVisible(true);
        
        // Restore any warez that was running before ICE/AI battle.
        // Ex.: EasyRider
        gameState.usingDeck.popWarez();
        
        return true;
    }

    /**
     * Should never be called so we defer it to the default popupExit() method.
     *
     * @param newPopup
     */
    @Override
    public void popupExit(RoomMode.Popup newPopup) {
        LOGGER.log(Level.WARNING, "popupExit() called with next popup specified! CyberspacePopup does not support that.");
        popupExit();
    }

    /**
     * Should never be called. We will log it just in case.
     *
     * @param message
     */
    @Override
    public void showMessage(String message) {
        LOGGER.log(Level.WARNING, "CyberspacePopup.showMessage() called  unexpectedly. Nothing happens.");
    }

}
