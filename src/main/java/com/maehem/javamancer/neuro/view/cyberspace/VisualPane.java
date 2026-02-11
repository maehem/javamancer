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
package com.maehem.javamancer.neuro.view.cyberspace;

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.JackZone;
import com.maehem.javamancer.neuro.model.item.DeckItem;
import com.maehem.javamancer.neuro.model.warez.EasyRiderWarez;
import com.maehem.javamancer.neuro.view.popup.CyberspacePopup;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class VisualPane extends Pane {

    public static final Logger LOGGER = Logging.LOGGER;

    private final GameState gameState;
    private final ExploreGridPane gridBasePane;
    private final BattleGridPane battlePane;

    public VisualPane(GameState gs) {
        this.gameState = gs;
        this.gridBasePane = new ExploreGridPane(gs);
        this.battlePane = new BattleGridPane(gs);

        getChildren().addAll(gridBasePane, battlePane);

        configState(CyberspacePopup.State.EXPLORE);

    }

    public void handleKeyEvent(KeyEvent keyEvent) {
        if (gridBasePane.isVisible()) {
           LOGGER.log(Level.FINEST, "VisualPane: Grid Base Pane Key event.");
           handleExploreKeys(keyEvent);
        } else if (battlePane.isVisible()) {
            // Battle Keys are handled by ControlPanelPane and effect gameState
            // when actuated.  So, in this class we should monitor the gameState
            // at each tick().
            battlePane.handleKeyEvent(keyEvent); // Handles Neuromancer initial encounter.
        }
    }

    public void animateInitialTravel() {
        Platform.runLater(() -> {
            gridBasePane.animateTravel(ExploreGridPane.Direction.FORWARD);
        });
    }
    
    /**
     * Player is moving around cyberspace.
     *
     * @param keyEvent
     */
    private void handleExploreKeys(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        DeckItem deck = gameState.usingDeck;
        boolean hasEasyRider = (deck.getCurrentWarez() instanceof EasyRiderWarez);
        switch (code) {
            case RIGHT -> {
                keyEvent.consume();
                if (hasEasyRider || deck.getCordX() < GameState.GRID_MAX - GameState.GRID_SIZE) {
                    JackZone destZone = JackZone.lookUp(
                            deck.getCordX() + GameState.GRID_SIZE,
                            deck.getCordY()
                    );
                    LOGGER.log(Level.INFO, "StartZone: {0}  ==> Destination Zone: {1}", new Object[]{deck.getZone(), destZone});
                    if (deck.getZone().equals(destZone) || hasEasyRider) {
                        gridBasePane.animateTravel(ExploreGridPane.Direction.LEFT);
                    } else {
                        LOGGER.log(Level.WARNING, "Zone change not allowed.");
                    }
                } else {
                    LOGGER.log(Level.INFO, "Max matrix X reached.");
                }
            }
            case LEFT -> {
                keyEvent.consume();
                if (hasEasyRider || deck.getCordX() >= GameState.GRID_SIZE) {
                    JackZone destZone = JackZone.lookUp(
                            deck.getCordX() - GameState.GRID_SIZE,
                            deck.getCordY()
                    );
                    LOGGER.log(Level.INFO, "StartZone: {0}  ==> Destination Zone: {1}", new Object[]{deck.getZone(), destZone});
                    if (deck.getZone().equals(destZone) || hasEasyRider) {
                        gridBasePane.animateTravel(ExploreGridPane.Direction.RIGHT);
                    } else {
                        LOGGER.log(Level.WARNING, "Zone change not allowed.");
                    }
                } else {
                    LOGGER.log(Level.INFO, "Min matrix X reached.");
                }
            }
            case UP -> {
                keyEvent.consume();
                if (hasEasyRider || deck.getCordY() < GameState.GRID_MAX - GameState.GRID_SIZE) {
                    JackZone destZone = JackZone.lookUp(
                            deck.getCordX(),
                            deck.getCordY() + GameState.GRID_SIZE
                    );
                    LOGGER.log(Level.INFO, "StartZone: {0}  ==> Destination Zone: {1}", new Object[]{deck.getZone(), destZone});
                    if (deck.getZone().equals(destZone) || hasEasyRider) {
                        gridBasePane.animateTravel(ExploreGridPane.Direction.FORWARD);
                    } else {
                        LOGGER.log(Level.WARNING, "Zone change not allowed.");
                    }
                } else {
                    LOGGER.log(Level.INFO, "Max matrix Y reached.");
                }
            }
            case DOWN -> {
                keyEvent.consume();
                if (hasEasyRider || deck.getCordY() > GameState.GRID_SIZE) {
                    JackZone destZone = JackZone.lookUp(
                            deck.getCordX(),
                            deck.getCordY() - GameState.GRID_SIZE
                    );
                    LOGGER.log(Level.INFO, "StartZone: {0}  ==> Destination Zone: {1}", new Object[]{deck.getZone(), destZone});
                    if (deck.getZone().equals(destZone) || hasEasyRider) {
                        gridBasePane.animateTravel(ExploreGridPane.Direction.BACKWARD);
                    } else {
                        LOGGER.log(Level.WARNING, "Zone change not allowed.");
                    }
                } else {
                    LOGGER.log(Level.INFO, "Min matrix Y reached.");
                }
            }
        }
    }

    public final void configState(CyberspacePopup.State state) {
        switch (state) {
            case EXPLORE -> {
                gridBasePane.setVisible(true);
                battlePane.setVisible(false);
                // Tell GameState our state.
            }
            case BATTLE -> {
                gridBasePane.setVisible(false);
                battlePane.setVisible(true);
                // Tell GameState our state.
                battlePane.resetBattle();
            }
        }
    }
    
    /**
     * 
     * @return true if battleMode finished and ICE and AI are defeated.
     */
    public boolean readyForDatabase() {
        return battlePane.isVisible() && battlePane.isDone();
    }

    public void tick() {
        if (battlePane.isVisible()) {
            battlePane.tick();
        }
    }

    public void setNeuromancerFinalFight() {
        battlePane.setupNeuromancerFinalBattle();
    }

}
