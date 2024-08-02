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

import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.item.DeckItem;
import com.maehem.javamancer.neuro.model.warez.Warez;
import java.util.logging.Level;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class DeckPopup extends PopupPane {

    private static final int SMALL_WIDTH = 360;
    private static final int SMALL_HEIGHT = 130;
    private static final int SMALL_X = 114;
    private static final int SMALL_Y = 256;

    private static final int LARGE_WIDTH = 600;
    private static final int LARGE_HEIGHT = 320;
    private static final int LARGE_X = 16;
    private static final int LARGE_Y = 16;

    private static final int SOFT_LIST_SIZE = 4;

    private final DeckItem deck;
    private Mode mode = Mode.SOFTWARE;

    private int slotBase = 0; // Slot menu in groups of 4.

    enum Mode {
        SOFTWARE, PASSWORD, DATABASE
    }

    public DeckPopup(PopupListener l, DeckItem deck, GameState gameState) {
        super(l, gameState);
        this.deck = deck;

        softwarePrompt();
    }

    private void softwarePrompt() {
        LOGGER.log(Level.SEVERE, "Show Deck Popup Software Prompt");
        mode = Mode.SOFTWARE;

        setPrefSize(SMALL_WIDTH, SMALL_HEIGHT);
        setMinSize(SMALL_WIDTH, SMALL_HEIGHT);
        setMaxSize(SMALL_WIDTH, SMALL_HEIGHT);
        setLayoutX(SMALL_X);
        setLayoutY(SMALL_Y);
        setId("neuro-popup");


        getChildren().clear();
        Text heading = new Text("Software");
        Text exitButton = new Text("exit");
        Text prevButton = new Text("prev");
        Text nextButton = new Text("next");
        TextFlow tf = new TextFlow(heading);
        tf.setLineSpacing(-8);
        tf.setPrefSize(SMALL_WIDTH, SMALL_HEIGHT);
        tf.setPadding(new Insets(4, 0, 0, 16));
        tf.getTransforms().add(new Scale(1.333, 1.0));

        HBox navBox = new HBox(prevButton, exitButton, nextButton);
        navBox.setSpacing(20);
        navBox.setPadding(new Insets(6, 0, 0, 32));

        for (int i = 0; i < SOFT_LIST_SIZE; i++) {
            try {
                    Warez w = deck.softwarez.get(slotBase + i);
                Text itemText = new Text("\n" + (i + 1) + ". " + w.getMenuString());
                    tf.getChildren().add(itemText);

                    // Add onMouseClick()
                } catch (IndexOutOfBoundsException ex) {
            }
        }
        tf.getChildren().add(new Text("\n"));
        tf.getChildren().add(navBox);
        prevButton.setVisible(slotBase >= SOFT_LIST_SIZE);
        nextButton.setVisible(slotBase + SOFT_LIST_SIZE < deck.softwarez.size());

        getChildren().add(tf);

        if (prevButton.isVisible()) {
            prevButton.setOnMouseClicked((t) -> {
                slotBase -= SOFT_LIST_SIZE;
                softwarePrompt();
            });
        }
        if (nextButton.isVisible()) {
            nextButton.setOnMouseClicked((t) -> {
                slotBase += SOFT_LIST_SIZE;
                softwarePrompt();
            });
        }
        exitButton.setOnMouseClicked((t) -> {
            gameState.usingDeck = null;
            LOGGER.log(Level.SEVERE, "Exit Deck (via mouse click).");

            listener.popupExit();
        });

    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();

        switch (mode) {
            case SOFTWARE -> {
                if (code.equals(KeyCode.X)) {
                    gameState.usingDeck = null;
                    LOGGER.log(Level.SEVERE, "Exit Deck (via key event).");
                } else if (code.equals(KeyCode.DIGIT1)) {
                    // Item 1
                }
            }

        }
        return false;
    }

}
