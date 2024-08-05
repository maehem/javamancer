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
import com.maehem.javamancer.neuro.model.TextResource;
import java.util.Map;
import java.util.logging.Level;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class ConsumerReviewDatabaseView extends DatabaseView {

    private final TextResource dbTextResource;

    private enum Mode {
        SUB, PAYMENT, MENU, ITEM
    }

    private final Text headingText = new Text();
    private final Text continueText = new Text(CONTINUE_TEXT);

    static final Map<String, Integer> MENU_MAP = Map.of( // limit 10 items. :(
            "1", 24,
            "2", 10,
            "3", 11,
            "A", 120, // we add 100 if item has more text at next index (20 and 21).
            "B", 12,
            "M", 122,
            "N", 114,
            "O", 116,
            "S", 13,
            "Y", 118
    );

    private Mode mode = Mode.SUB;
    private int paymentWait = 0;

    public ConsumerReviewDatabaseView(GameState gameState, Pane pane) {
        super(gameState, pane);

        dbTextResource = gameState.resourceManager.getDatabaseText(gameState.database.number);
        headingText.setText(centeredText(dbTextResource.get(0)) + "\n\n");
        landingPage();
    }

    @Override
    protected void landingPage() {
        pane.getChildren().clear();
        mode = Mode.SUB;

        Text helloText = new Text(dbTextResource.get(2) + "\n\n\n");

        TextFlow tf = pageTextFlow();
        tf.getChildren().addAll(headingText, helloText, continueText);
        pane.getChildren().add(tf);
    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        LOGGER.log(Level.SEVERE, "Handle key event.");
        switch (mode) {
            case MENU -> {
                if (code.equals(KeyCode.X)
                        || code.equals(KeyCode.SPACE)
                        || code.equals(KeyCode.ESCAPE)) {
                    LOGGER.log(Level.SEVERE, "Menu wants to exit system.");
                    return true;
                } else {
                    if ( code.isDigitKey() ) {
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
        }
        return super.handleKeyEvent(keyEvent);
    }

    @Override
    protected void siteContent() {
        LOGGER.log(Level.SEVERE, "Do site content 'payment'.");
        mode = Mode.PAYMENT;
        pane.getChildren().clear();

        Text creditDeduct = new Text();
        Text newLines = new Text("\n\n\n\n");
        if (gameState.chipBalance >= 200) {
            gameState.chipBalance -= 200;
            creditDeduct.setText(dbTextResource.get(25));
            paymentWait = 30; // timer, then proceed to next page (menu).
        } else {
            creditDeduct.setText(dbTextResource.get(26));
            // Don't proceed.
        }
        TextFlow tf = pageTextFlow();
        tf.getChildren().addAll(headingText, newLines, creditDeduct);
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
                } else {
                    LOGGER.log(Level.SEVERE, "Payment wait done.");
                    mainMenu();
                }
            }
        }

    }

    private void itemPage(String itemLetter) {
        mode = Mode.ITEM;
        pane.getChildren().clear();

        TextFlow tf = pageTextFlow();
        tf.getChildren().add(headingText);

        // TODO: Condense into a haskmap <Str, int>
        if (itemLetter.equals("X")) {
            // Exit system
            LOGGER.log(Level.SEVERE, "Exit system");
        } else {
            Integer index = MENU_MAP.get(itemLetter);
            if (index != null) {
                if (index > 100) {
                    tf.getChildren().add(new Text(dbTextResource.get(index - 100)));
                    // Other models text.
                    tf.getChildren().add(new Text(dbTextResource.get(index - 99)));
                } else {
                    tf.getChildren().add(new Text(dbTextResource.get(index)));
                }
            }
        }

        pane.getChildren().add(tf);
    }

}
