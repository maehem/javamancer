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
import com.maehem.javamancer.neuro.model.database.Database;
import com.maehem.javamancer.neuro.model.item.DeckItem;
import com.maehem.javamancer.neuro.model.warez.Warez;
import java.util.logging.Level;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.X;
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

    private static final int SOFT_LIST_WIDTH = 360;
    private static final int SOFT_LIST_HEIGHT = 130;
    private static final int SOFT_LIST_X = 114;
    private static final int SOFT_LIST_Y = 256;

    private static final int LINK_CODE_WIDTH = 350;
    private static final int LINK_CODE_HEIGHT = 70;
    private static final int LINK_CODE_X = 0;
    private static final int LINK_CODE_Y = 256;
    private static final String LINK_CODE_ENTER_CODE = "\nEnter link code:\n";
    private static final String LINK_CODE_UNKOWN_LINK = "\nUnknown link.\n";

    private static final int LARGE_WIDTH = 600;
    private static final int LARGE_HEIGHT = 320;
    private static final int LARGE_X = 16;
    private static final int LARGE_Y = 16;

    private static final int SOFT_LIST_SIZE = 4;

    private final DeckItem deck;
    private final StringBuilder typedLinkCode = new StringBuilder();
    private final Text typedLinkCodeText = new Text();

    private Mode mode = Mode.SOFTWARE;

    private int slotBase = 0; // Slot menu in groups of 4.
    private final Text heading = new Text(LINK_CODE_ENTER_CODE);
    private boolean linkCodeErr;

    enum Mode {
        SOFTWARE, ENTER_LINKCODE, RESPONSE, DATABASE
    }

    public DeckPopup(PopupListener l, DeckItem deck, GameState gameState) {
        super(l, gameState);
        this.deck = deck;

        softwarePrompt();
    }

    private void softwarePrompt() {
        LOGGER.log(Level.SEVERE, "Show Deck Popup Software Prompt");
        mode = Mode.SOFTWARE;

        configSmallWindow();

        getChildren().clear();
        Text softwareHeading = new Text("Software");
        Text exitButton = new Text("exit");
        Text prevButton = new Text("prev");
        Text nextButton = new Text("next");
        TextFlow tf = new TextFlow(softwareHeading);
        tf.setLineSpacing(-8);
        tf.setPrefSize(SOFT_LIST_WIDTH, SOFT_LIST_HEIGHT);
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
                itemText.setOnMouseClicked((t) -> {
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

    private void useSoftware(Warez w) {
        String useReponse = w.use(gameState);
        if (!useReponse.equals(Warez.USE_OK)) {
            useResponse(useReponse);
        } else {
            deck.setCurrentWarez(w);
            enterLinkCode();
        }
    }

    private void enterLinkCode() {
        LOGGER.log(Level.SEVERE, "Show Deck Link Code Prompt");
        mode = Mode.ENTER_LINKCODE;

        configEntryWindow();

        getChildren().clear();
        Text currentSoft = new Text("       " + gameState.usingDeck.getCurrentWarez().getMenuString());
        Text cursor = new Text("<\n");
        TextFlow tf = new TextFlow(currentSoft, heading, typedLinkCodeText, cursor);
        tf.setLineSpacing(-8);
        //tf.setPrefSize(SOFT_LIST_WIDTH, SOFT_LIST_HEIGHT);
        tf.setPadding(new Insets(4, 0, 0, 4));
        tf.getTransforms().add(new Scale(1.333, 1.0));

        getChildren().add(tf);

    }

    private void useResponse(String response) {
        LOGGER.log(Level.SEVERE, "Show Deck use() response");
        configSmallWindow();

        mode = Mode.RESPONSE;

        getChildren().clear();
        Text heading = new Text(response);
        TextFlow tf = new TextFlow(heading);
        tf.setLineSpacing(-8);
        tf.setPadding(new Insets(4, 0, 0, 4));
        tf.getTransforms().add(new Scale(1.333, 1.0));

        getChildren().add(tf);
    }

    private void configSmallWindow() {
        setPrefSize(SOFT_LIST_WIDTH, SOFT_LIST_HEIGHT);
        setMinSize(SOFT_LIST_WIDTH, SOFT_LIST_HEIGHT);
        setMaxSize(SOFT_LIST_WIDTH, SOFT_LIST_HEIGHT);
        setLayoutX(SOFT_LIST_X);
        setLayoutY(SOFT_LIST_Y);
        setId("neuro-popup");
    }

    private void configEntryWindow() {
        setPrefSize(LINK_CODE_WIDTH, LINK_CODE_HEIGHT);
        setMinSize(LINK_CODE_WIDTH, LINK_CODE_HEIGHT);
        setMaxSize(LINK_CODE_WIDTH, LINK_CODE_HEIGHT);
        setLayoutX(LINK_CODE_X);
        setLayoutY(LINK_CODE_Y);
        setId("neuro-popup");
    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();

        switch (mode) {
            case SOFTWARE -> {
                if (code.equals(KeyCode.X)) {
                    gameState.usingDeck = null;
                    LOGGER.log(Level.SEVERE, "Exit Deck (via key event).");
                    return true;
                } else if (code.equals(KeyCode.DIGIT1)) {
                    // Item 1
                }
            }
            case ENTER_LINKCODE -> {
                switch (code) {
                    case X -> {
                        gameState.usingDeck = null;
                        return true;
                    }
                    default ->
                        handleEnteredLinkCode(keyEvent);
                }
            }

        }
        return false;
    }

    private void handleEnteredLinkCode(KeyEvent ke) {
        LOGGER.log(Level.FINEST, "Handle Link Code entry key typed.");
        if (typedLinkCode.length() < 12 && ke.getCode().isLetterKey()) {
            LOGGER.log(Level.FINEST, "Typed: {0}", ke.getText());
            typedLinkCode.append(ke.getText());
            typedLinkCodeText.setText(typedLinkCode.toString());
        } else if (typedLinkCode.length() > 0 && ke.getCode().equals(KeyCode.BACK_SPACE)) {
            LOGGER.log(Level.FINEST, "Backspace.");
            if ( linkCodeErr ) {
                typedLinkCode.setLength(0);
                linkCodeErr = false;
                heading.setText(LINK_CODE_ENTER_CODE);
            } else {
                typedLinkCode.delete(typedLinkCode.length() - 1, typedLinkCode.length());
            }
            typedLinkCodeText.setText(typedLinkCode.toString());
        } else if (ke.getCode().equals(KeyCode.ENTER)) {
            // TODO: Check Link Code Valid

            // TODO: Find link.
            Database whoIs = gameState.dbList.whoIs(typedLinkCode.toString());
            if (whoIs != null) {
                // Connect
                if (whoIs.password1 != null) {
                    // Password prompt
                    LOGGER.log(Level.SEVERE, "Password prompt for: " + whoIs.name);

                    // Check if proper zone.
                } else {
                    // Should never get here. If there is a link code, there should
                    // be at least one passowrd.
                    LOGGER.log(Level.SEVERE, "Only from cyberspace");
                }
            } else {
                heading.setText(LINK_CODE_UNKOWN_LINK);
                linkCodeErr = true;
            }
        }
    }
}
