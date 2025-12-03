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
package com.maehem.javamancer.neuro.view.database;

import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.item.DeckItem;
import com.maehem.javamancer.neuro.view.PopupListener;
import java.util.Map;
import java.util.logging.Level;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class ConsumerReviewDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, PAYMENT, OUT_OF_CREDITS, MENU, ITEM
    }

    static final Map<String, int[]> MENU_MAP = Map.of( // limit 10 items. :(
            "1", new int[]{24},
            "2", new int[]{10, 3, 4, 5},
            "3", new int[]{11, 6, 7, 8},
            "A", new int[]{20, 4, 21},
            "B", new int[]{12, 5},
            "M", new int[]{22, 9, 23},
            "N", new int[]{14, 8, 15},
            "O", new int[]{16, 6, 17},
            "S", new int[]{13, 7},
            "Y", new int[]{18, 3, 19}
    );

    private Mode mode = Mode.SUB;
    private int paymentWait = 0;

    public ConsumerReviewDatabaseView(GameState gameState, Pane pane, PopupListener l) {
        super(gameState, pane, l);

        if (gameState.usingDeck.getMode() == DeckItem.Mode.CYBERSPACE) {
            mainMenu();
        } else {
            landingPage();
        }
    }

    @Override
    protected final void landingPage() {
        pane.getChildren().clear();
        mode = Mode.SUB;

        Text helloText = new Text(dbTextResource.get(2) + "\n\n\n");

        TextFlow tf = pageTextFlow(headingText, helloText, CONTINUE_TEXT);
        pane.getChildren().add(tf);
    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        LOGGER.log(Level.FINE, "Handle key event.");
        switch (mode) {
            case MENU -> {
                if (code.equals(KeyCode.X)
                        || code.equals(KeyCode.SPACE)
                        || code.equals(KeyCode.ESCAPE)) {
                    LOGGER.log(Level.INFO, "Menu wants to exit system.");
                    return true;
                } else {
                    if (code.isDigitKey()) {
                        itemPage(code.getChar());
                    } else {
                        itemPage(code.getChar().toUpperCase());
                    }
                } // else do nothing
            }
            case ITEM -> {
                if (code.equals(KeyCode.X)
                        || code.equals(KeyCode.SPACE)
                        || code.equals(KeyCode.ESCAPE)) {
                    mainMenu();
                    return false;
                }
            }
            case OUT_OF_CREDITS -> {
                if (code.equals(KeyCode.X)
                        || code.equals(KeyCode.SPACE)
                        || code.equals(KeyCode.ESCAPE)) {
                    LOGGER.log(Level.INFO, "Out of credits wants to exit system.");
                    return true;
                }
            }
        }
        return super.handleKeyEvent(keyEvent);
    }

    @Override
    protected void siteContent() {
        LOGGER.log(Level.INFO, "Do site content 'payment'.");
        mode = Mode.PAYMENT;
        pane.getChildren().clear();

        Text creditDeduct = new Text();
        Text newLines = new Text("\n\n\n\n\n\n\n\n\n\n");
        if (gameState.chipBalance >= 200) {
            gameState.chipBalance -= 200;
            LOGGER.log(Level.INFO, "Deducted 200 credits from player.");
            creditDeduct.setText(dbTextResource.get(25));
            paymentWait = 30; // timer, then proceed to next page (menu).
        } else {
            LOGGER.log(Level.INFO, "Player does not have credits to pay.");
            //creditDeduct.setText(dbTextResource.get(26));
            // Don't proceed.
            outOfCredits();
            return;
        }
        TextFlow tf = pageTextFlow();
        tf.getChildren().addAll(headingText, newLines, creditDeduct);
        LOGGER.log(Level.FINE, "Add text flow for payment wait.");
        pane.getChildren().add(tf);
    }

    private void mainMenu() {
        pane.getChildren().clear();
        mode = Mode.MENU;

        TextFlow tf = pageTextFlow();

        tf.getChildren().add(headingText);
        tf.getChildren().add(new Text(centeredText(dbTextResource.get(27)) + "\n"));
        String[] split = dbTextResource.get(1).split("\\r");
        for (String s : split) {
            Text menuItem = new Text("\n         " + s);
            tf.getChildren().add(menuItem);
            menuItem.setOnMouseClicked((t) -> {
                itemPage(s.substring(0, 1));
            });
        }

        pane.getChildren().add(tf);
    }

    @Override
    public void tick() {
        super.tick();

        switch (mode) {
            case PAYMENT -> {
                if (paymentWait > 0) {
                    paymentWait--;
                    LOGGER.log(Level.FINEST, "Payment wait tick.");
                } else {
                    LOGGER.log(Level.CONFIG, "Payment wait done.");
                    mainMenu();
                }
            }
        }

    }

    private void itemPage(String itemLetter) {
        mode = Mode.ITEM;
        pane.getChildren().clear();

        // Plus (+) character appears if user has not scrolled to bottom of
        // item description.
        Text scrollHint = pageText("+");
        scrollHint.setLayoutX(620);
        scrollHint.setLayoutY(280);

        Text headText = pageText(centeredText(dbTextResource.get(0)));

        TextFlow tf = pageTextFlow();
        //tf.getChildren().add(headingText);
        ScrollPane sp = new ScrollPane(tf);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setMinSize(620, 264);
        sp.setMaxSize(620, 264);

        if (itemLetter.equals("X")) {
            // Exit system
            LOGGER.log(Level.SEVERE, "Exit system");
            listener.popupExit();
        } else {
            int index[] = MENU_MAP.get(itemLetter);
            if (index != null) {
                for (int ix : index) {
                    tf.getChildren().add(new Text(dbTextResource.get(ix)));
                }
            }
        }

        VBox box = new VBox(headText, sp);
        pane.getChildren().addAll(box, scrollHint);
        scrollHint.setVisible(sp.getVvalue() != 1.0);

        sp.vvalueProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    LOGGER.log(Level.FINEST, "Item Scroll: {0}", newValue);
                    scrollHint.setVisible(newValue.doubleValue() != 1.0);
                }
        );

        box.setOnMouseClicked((t) -> {
            mainMenu();
        });
    }

    private void outOfCredits() {
        mode = Mode.OUT_OF_CREDITS;
        pane.getChildren().clear();

        Text message = new Text(
                "\n\n\n\n"
                + centeredText(dbTextResource.get(26))
                + "\n\n\n\n\n\n\n\n\n\n"
        );

        TextFlow tf = pageTextFlow();
        tf.getChildren().addAll(message, CONTINUE_TEXT);

        pane.getChildren().add(tf);

        tf.setOnMouseClicked((t) -> {
            // Exit deck.
            listener.popupExit();
        });
    }

}
