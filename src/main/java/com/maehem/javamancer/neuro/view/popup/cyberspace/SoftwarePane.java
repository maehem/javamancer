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
package com.maehem.javamancer.neuro.view.popup.cyberspace;

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.item.DeckItem;
import com.maehem.javamancer.neuro.model.warez.Warez;
import com.maehem.javamancer.neuro.view.popup.PopupPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class SoftwarePane extends Pane {

    public static final Logger LOGGER = Logging.LOGGER;

    private static final int SOFT_LIST_WIDTH = 360;
    private static final int SOFT_LIST_HEIGHT = 130;
    private static final int SOFT_LIST_X = 114;
    private static final int SOFT_LIST_Y = 0;
    private final static int SOFT_LIST_SIZE = 4;

    private int slotBase = 0;
    private final GameState gameState;
    private final DeckItem deck;

    public SoftwarePane(GameState gs) {
        this.gameState = gs;
        deck = gameState.usingDeck;

        setPrefSize(SOFT_LIST_WIDTH, SOFT_LIST_HEIGHT);
        setMinSize(SOFT_LIST_WIDTH, SOFT_LIST_HEIGHT);
        setMaxSize(SOFT_LIST_WIDTH, SOFT_LIST_HEIGHT);
        setLayoutX(SOFT_LIST_X);
        setLayoutY(SOFT_LIST_Y);
        setId("neuro-popup");

        softwarePrompt();
    }

    public final void softwarePrompt() {
        LOGGER.log(Level.SEVERE, "Cyberspace: Show Software Prompt");
        //mode = Mode.SOFTWARE;

        setVisible(true);
        getChildren().clear();
        Text softwareHeading = new Text("Software");
        Text exitButton = new Text("exit");
        Text prevButton = new Text("prev");
        Text nextButton = new Text("next");
        TextFlow tf = PopupPane.textFlow(softwareHeading);
        //TextFlow tf = new TextFlow(softwareHeading);
        //tf.setLineSpacing(LINE_SPACING);
        tf.setPrefSize(SOFT_LIST_WIDTH, SOFT_LIST_HEIGHT);
        //tf.setPadding(new Insets(4, 0, 0, 16));

        HBox navBox = new HBox(prevButton, exitButton, nextButton);
        navBox.setSpacing(20);
        navBox.setPadding(new Insets(6, 0, 0, 32));

        for (int i = 0; i < SOFT_LIST_SIZE; i++) {
            try {
                Warez w = deck.softwarez.get(slotBase + i);
                Text itemText = new Text("\n" + (i + 1) + ". " + w.getMenuString());
                tf.getChildren().add(itemText);

                // Add onMouseClick()
                itemText.setOnMouseClicked((t) -> {
                    t.consume();
                    useSoftware(w);
                });
            } catch (IndexOutOfBoundsException ex) {
                tf.getChildren().add(new Text("\n"));
            }
        }
        tf.getChildren().add(new Text("\n"));
        tf.getChildren().add(navBox);
        prevButton.setVisible(slotBase >= SOFT_LIST_SIZE);
        nextButton.setVisible(slotBase + SOFT_LIST_SIZE < deck.softwarez.size());

        getChildren().add(PopupPane.makeBox(this, tf));

        if (prevButton.isVisible()) {
            prevButton.setOnMouseClicked((t) -> {
                t.consume();
                slotBase -= SOFT_LIST_SIZE;
                softwarePrompt();
            });
        }
        if (nextButton.isVisible()) {
            nextButton.setOnMouseClicked((t) -> {
                t.consume();
                slotBase += SOFT_LIST_SIZE;
                softwarePrompt();
            });
        }
        exitButton.setOnMouseClicked((t) -> {
            LOGGER.log(Level.SEVERE, "Cyberspace: Exit Software Menu (via mouse click).");
            t.consume();
            setVisible(false);
            //listener.popupExit();
        });

    }

    private void useSoftware(Warez w) {
        LOGGER.log(Level.SEVERE, "Cyberspace: Use Software: {0}", w.item.itemName);
        String useReponse = w.use(gameState);
        if (!useReponse.equals(Warez.USE_OK)) {
            LOGGER.log(Level.SEVERE, "Use Software Not OK: {0}", useReponse);
            displayResponse(useReponse);
        } else {
            gameState.usingDeck.setCurrentWarez(w);
            LOGGER.log(Level.SEVERE, "Use Software OK.");
        }
    }

    private void displayResponse(String response) {
        LOGGER.log(Level.SEVERE, "Show Deck use() response");

        getChildren().clear();
        setVisible(true);
        Text heading = new Text(response);

        getChildren().add(PopupPane.makeBox(this, PopupPane.textFlow(heading)));

        setOnMouseClicked((t) -> {
            t.consume();
            // Allow user to view response and go back to menu when clicked.
            setOnMouseClicked(null);
            softwarePrompt();
        });
    }

    public boolean handleKeyEvent(KeyEvent ke) {

        ke.consume();
        KeyCode code = ke.getCode();
        ke.consume();
        switch (code) {
            case X, ESCAPE -> {
                LOGGER.log(Level.SEVERE, "Cyberspace: SoftwarePane: Exit Pressed...");
                setVisible(false);
            }
            case DIGIT1 -> { // Inventory/Software/Slots
                LOGGER.log(Level.SEVERE, "Cyberspace: SoftwarePane: 1 Pressed...");
            }
            case DIGIT2 -> {
                LOGGER.log(Level.SEVERE, "Cyberspace: SoftwarePane: 2 Pressed...");
            }
            case DIGIT3 -> {
                LOGGER.log(Level.SEVERE, "Cyberspace: SoftwarePane: 3 Pressed...");
            }
            case DIGIT4 -> {
                LOGGER.log(Level.SEVERE, "Cyberspace: SoftwarePane: 4 Pressed...");
            }
            case N -> {
                LOGGER.log(Level.SEVERE, "Cyberspace: SoftwarePane: Previous Pressed...");
            }
            case M -> {
                LOGGER.log(Level.SEVERE, "Cyberspace: SoftwarePane: More Pressed...");
            }
        }


        return false;
    }
}
