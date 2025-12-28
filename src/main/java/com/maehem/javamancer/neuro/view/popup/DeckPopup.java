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
import com.maehem.javamancer.neuro.model.database.Database;
import com.maehem.javamancer.neuro.model.item.DeckItem;
import com.maehem.javamancer.neuro.model.warez.CyberspaceWarez;
import com.maehem.javamancer.neuro.model.warez.Warez;
import com.maehem.javamancer.neuro.view.PopupListener;
import com.maehem.javamancer.neuro.view.RoomMode;
import com.maehem.javamancer.neuro.view.database.DatabaseView;
import java.util.logging.Level;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class DeckPopup extends PopupPane {

    private DatabaseView databaseView;

    enum Mode {
        SOFTWARE, CHOOSE_MODE, ENTER_LINKCODE, ENTER_PASSWORD, RESPONSE, DATABASE
    }

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
    private static final String LINK_CODE_COMLINK_COMPAT = "\nIncompatible link.\n";

    private static final int LARGE_WIDTH = 640;
    private static final int LARGE_HEIGHT = 288;
    private static final int LARGE_X = 0;
    private static final int LARGE_Y = 0;

    private static final int SOFT_LIST_SIZE = 4;
    private final Text linkEnterheading = new Text(LINK_CODE_ENTER_CODE);

    private final DeckItem deck;
    private final StringBuilder typedLinkCode = new StringBuilder();
    private final Text typedLinkEntryText = new Text();

    private int slotBase = 0; // Slot menu in groups of 4.
    private Mode mode = Mode.SOFTWARE;
    private boolean linkCodeErr;

    public DeckPopup(PopupListener l, GameState gameState) {
        super(l, gameState);
        this.deck = gameState.usingDeck;

        softwarePrompt();
    }

    private void softwarePrompt() {
        LOGGER.log(Level.FINE, "Show Deck Popup Software Prompt");
        mode = Mode.SOFTWARE;

        configSmallWindow();

        getChildren().clear();
        Text softwareHeading = new Text(gameState.usingDeckErase ? "Erase Software" : "Software");
        Text exitButton = new Text("exit");
        Text prevButton = new Text("prev");
        Text nextButton = new Text("next");
        TextFlow tf = textFlow(softwareHeading);
        //TextFlow tf = new TextFlow(softwareHeading);
        //tf.setLineSpacing(LINE_SPACING);
        tf.setPrefSize(SOFT_LIST_WIDTH, SOFT_LIST_HEIGHT);
        //tf.setPadding(new Insets(4, 0, 0, 16));

        HBox navBox = new HBox(prevButton, exitButton, nextButton);
        navBox.setSpacing(20);
        navBox.setPadding(new Insets(6, 0, 0, 32));

        for (int i = 0; i < SOFT_LIST_SIZE; i++) {
            try {
                Warez w = gameState.software.get(slotBase + i);
                Text itemText = new Text("\n" + (i + 1) + ". " + w.getMenuString());
                tf.getChildren().add(itemText);

                // Add onMouseClick()
                itemText.setOnMouseClicked((t) -> {
                    if (gameState.usingDeckErase) {
                        eraseSoftware(w);
                    } else {
                        useSoftware(w);
                    }
                });
            } catch (IndexOutOfBoundsException ex) {
                tf.getChildren().add(new Text("\n"));
            }
        }
        tf.getChildren().add(new Text("\n"));
        tf.getChildren().add(navBox);
        prevButton.setVisible(slotBase >= SOFT_LIST_SIZE);
        nextButton.setVisible(slotBase + SOFT_LIST_SIZE < gameState.software.size());

        addBox(tf);

        if (prevButton.isVisible()) {
            prevButton.setOnMouseClicked((t) -> {
                slotBase -= SOFT_LIST_SIZE;
                softwarePrompt();
            });
        }
        if (nextButton.isVisible()) {
            nextButton.setOnMouseClicked((t) -> {
                slotBase += SOFT_LIST_SIZE;
                t.consume();
                softwarePrompt();
            });
        }
        exitButton.setOnMouseClicked((t) -> {
            cleanup();
            LOGGER.log(Level.INFO, "Exit Deck (via mouse click).");

            t.consume();
            listener.popupExit();
        });

    }

    private void useSoftware(Warez w) {
        LOGGER.log(Level.FINE, () -> "DeckPopup Use Software: " + w.getSimpleName());
        String useReponse = w.use(gameState);
        if (!useReponse.equals(Warez.USE_OK)) {
            displayResponse(useReponse);
        } else {
            deck.setCurrentWarez(w);
            if (deck.cyberspaceCapable) {
                connectMenu();
            } else {
                enterLinkCode();
            }
        }
    }

    private void eraseSoftware(Warez w) {
        LOGGER.log(Level.FINE, "DeckPopup Erase Software: {0}", w.getSimpleName());
        gameState.eraseSoftware(w);
        softwarePrompt();
    }

    private void connectMenu() {
        LOGGER.log(Level.FINE, "Show Deck Connect Mode Menu");
        mode = Mode.CHOOSE_MODE;

        configEntryWindow();

        // TODO: Link code or cyberspace.
        getChildren().clear();
        TextFlow tf = textFlow(new Text("Deck Connect Menu"));

        Text linkText = new Text("\n" + 1 + ". Enter Link Code");
        Text csText = new Text("\n" + 2 + ". Enter Cyberspace");
        tf.getChildren().addAll(linkText, csText);

        linkText.setOnMouseClicked((t) -> {
            t.consume();
            enterLinkCode();
        });

        csText.setOnMouseClicked((t) -> {
            t.consume();
            enterCyberspace();
        });
        addBox(tf);
    }

    private void enterLinkCode() {
        LOGGER.log(Level.FINE, "Show Deck Link Code Prompt");
        mode = Mode.ENTER_LINKCODE;

        configEntryWindow();

        // Clear out any previous link code.
        typedLinkCode.setLength(0);
        typedLinkEntryText.setText(typedLinkCode.toString());

        getChildren().clear();
        Text currentSoft = new Text("    " + gameState.usingDeck.getCurrentWarez().getSimpleName());
        Text cursor = new Text("<\n");
        TextFlow tf = textFlow(currentSoft, linkEnterheading, typedLinkEntryText, cursor);

        addBox(tf);
    }

    private void enterCyberspace() {
        LOGGER.log(Level.FINE, "Enter Cyberspace.");

        //gameState.usingDeck.setMode(DeckItem.Mode.CYBERSPACE);
        // Leave Deck pupup and open cyberspace popup.
        listener.popupExit(RoomMode.Popup.CYBERSPACE);
    }

    private void displayResponse(String response) {
        LOGGER.log(Level.FINE, "Show Deck use() response");
        configSmallWindow();

        mode = Mode.RESPONSE;

        getChildren().clear();
        Text heading = new Text(response);

        addBox(textFlow(heading));

        setOnMouseClicked((t) -> {
            // Allow user to view response and go back to menu when clicked.
            setOnMouseClicked(null);
            softwarePrompt();
        });
    }

    private void siteContent() {
        LOGGER.log(Level.INFO, () -> "Open site: " + gameState.database.name);
        mode = Mode.DATABASE;
        configDatabaseWindow();

        getChildren().clear();

        // Database View takes over content and keyevents until it exits.
        this.databaseView = DatabaseView.getView(gameState, this, listener);
    }

    private void configSmallWindow() {
        setPrefSize(SOFT_LIST_WIDTH, SOFT_LIST_HEIGHT);
        setMinSize(SOFT_LIST_WIDTH, SOFT_LIST_HEIGHT);
        setMaxSize(SOFT_LIST_WIDTH, SOFT_LIST_HEIGHT);
        setLayoutX(SOFT_LIST_X);
        setLayoutY(SOFT_LIST_Y);
        //setId("neuro-popup");
    }

    private void configEntryWindow() {
        setPrefSize(LINK_CODE_WIDTH, LINK_CODE_HEIGHT);
        setMinSize(LINK_CODE_WIDTH, LINK_CODE_HEIGHT);
        setMaxSize(LINK_CODE_WIDTH, LINK_CODE_HEIGHT);
        setLayoutX(LINK_CODE_X);
        setLayoutY(LINK_CODE_Y);
        //setId("neuro-popup");
    }

    private void configDatabaseWindow() {
        setPrefSize(LARGE_WIDTH, LARGE_HEIGHT);
        setMinSize(LARGE_WIDTH, LARGE_HEIGHT);
        setMaxSize(LARGE_WIDTH, LARGE_HEIGHT);
        setLayoutX(LARGE_X);
        setLayoutY(LARGE_Y);
        //setId("neuro-popup");
    }

    @Override
    public void inventoryClicked() {
        if ( mode == Mode.DATABASE ) {
            databaseView.inventoryClicked();
        }
    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();

        switch (mode) {
            case SOFTWARE -> {
                if (code.equals(KeyCode.X)) {
                    cleanup();
                    LOGGER.log(Level.FINE, "Exit Deck (via key event).");
                    return true;
                } else if (code.equals(KeyCode.DIGIT1)) {
                    // Item 1
                }
            }
            case ENTER_LINKCODE -> {
                if (code.equals(KeyCode.ESCAPE)) {
                    LOGGER.log(Level.FINE, "Exit LinkCode Entry (via key event).");
                    softwarePrompt();
                } else {
                    handleEnteredLinkCode(keyEvent);
                }
            }
            case DATABASE -> {
                if (databaseView != null) {
                    if (databaseView.handleKeyEvent(keyEvent)) {
                        databaseView = null;
                        cleanup();
                        return true;
                    }
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
            typedLinkEntryText.setText(typedLinkCode.toString());
        } else if (typedLinkCode.length() > 0 && ke.getCode().equals(KeyCode.BACK_SPACE)) {
            LOGGER.log(Level.FINEST, "Backspace.");
            if (linkCodeErr) {
                typedLinkCode.setLength(0);
                linkCodeErr = false;
                linkEnterheading.setText(LINK_CODE_ENTER_CODE);
            } else {
                typedLinkCode.delete(typedLinkCode.length() - 1, typedLinkCode.length());
            }
            typedLinkEntryText.setText(typedLinkCode.toString());
        } else if (ke.getCode().equals(KeyCode.ENTER)) {
            Database whoIs = gameState.dbList.whoIs(typedLinkCode.toString());
            if (whoIs != null) {
                // Connect
                if ((deck.getCurrentWarez() instanceof CyberspaceWarez)
                        || whoIs.comlink <= deck.getCurrentWarez().version) {
                    gameState.database = whoIs;
                    gameState.usingDeck.setMode(DeckItem.Mode.LINKCODE);

                    siteContent(); // hand off to custom site handler.
                } else {
                    // Error comlink version
                    LOGGER.log(Level.CONFIG, "ComLink {0} required for this site.", whoIs.comlink);
                    gameState.usingDeck.setMode(DeckItem.Mode.NONE);
                    linkEnterheading.setText(LINK_CODE_COMLINK_COMPAT);
                    linkCodeErr = true;
                }
            } else {
                LOGGER.log(Level.WARNING, () -> "Could not find link called " + typedLinkCode.toString());
                gameState.usingDeck.setMode(DeckItem.Mode.NONE);
                linkEnterheading.setText(LINK_CODE_UNKOWN_LINK);
                linkCodeErr = true;
            }
        }
    }

    public void tick() {
        if (mode == Mode.DATABASE && databaseView != null) {
            databaseView.tick();
        }
        if (gameState.chipBalance <= 0) {
            cleanup();
            LOGGER.log(Level.INFO, "Exit Deck (via out of credits).");
            listener.popupExit();
        }
    }

    @Override
    public void cleanup() {
        gameState.usingDeckErase = false;
        gameState.usingDeck = null;
        deck.cleanUp();
    }
}
