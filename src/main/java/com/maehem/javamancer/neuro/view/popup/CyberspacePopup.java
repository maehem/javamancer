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
import com.maehem.javamancer.neuro.view.PopupListener;
import com.maehem.javamancer.neuro.view.cyberspace.ControlPanelPane;
import com.maehem.javamancer.neuro.view.cyberspace.VisualPane;
import java.util.logging.Level;
import javafx.scene.input.KeyEvent;
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
    private final ControlPanelPane controlPanel;
    private final VisualPane visualPane;

    public enum State {
        EXPLORE, BATTLE
    }

    private State state;

    public CyberspacePopup(PopupListener l, GameState gs) {
        super(l, gs);
        setPrefSize(WIDTH, HEIGHT);
        setMinSize(WIDTH, HEIGHT);
        setLayoutX(0);
        setLayoutY(0);

        Rectangle backdrop = new Rectangle(WIDTH, HEIGHT, Color.BLACK);

        controlPanel = new ControlPanelPane(l, gs);
        controlPanel.setLayoutY(CPANEL_TOP);

        visualPane = new VisualPane(gs);

        getChildren().addAll(
                backdrop, visualPane, controlPanel
        );
    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        LOGGER.log(Level.SEVERE, "CyberPopup: Key Event.");
        if (controlPanel.handleKeyEvent(keyEvent)) {
            LOGGER.log(Level.SEVERE, "CyberPopup: Key Event: True returned from control panel.");
            return true;
        }

        if (!keyEvent.isConsumed()) {
            visualPane.handleKeyEvent(keyEvent);
        } else {
            LOGGER.log(Level.SEVERE, "CyberPopup: Key event consumed by ControlPanel, not sent to VisualPane.");
        }

        return false;
    }

    @Override
    public void cleanup() {
        gameState.usingDeck = null;
        gameState.database = null;
        gameState.databaseBattle = false;
    }

    public void setState(State state) {
        this.state = state;
        visualPane.configState(state);
        controlPanel.configState(state);
    }

    public void tick() {
        if (gameState.databaseBattle && gameState.databaseBattleBegin) {
            gameState.databaseBattleBegin = false;
            setState(State.BATTLE);
        }
        controlPanel.tick();
        visualPane.tick();
    }
}
