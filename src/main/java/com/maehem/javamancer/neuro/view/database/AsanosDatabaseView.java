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
import java.util.logging.Level;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * <pre>
 * [0] :: * Asano Computing *
 * [1] :: X. Exit System 1. Catalog
 * [2] :: 2. Manufacturers
 * [3] :: 3. Inventory
 * [4] :: When you use the best, you are never
 * disappointed.... To look at our current hardware list,just enter the
 * password, "CUSTOMER".
 * [5] :: Current Hardware For Sale -------------------------------------- Manufacturer and Model RAM --------------------------------------
 * [6] :: -------------------------------------- Come to our store for the latest in up to date software and hardware.
 * [7] :: Manufacturer Link Code -------------------------------------- Fuji Electric FUJI Hosaka HOSAKACORP
 * Musabori Industries MUSABORIND -------------------------------------- For
 * reordering, these mfg. sales reps. must be seen personally: Cray Yamamitsu
 * Moriyama Ono-Sendai Ausgezeichnet Ninja
 * [8] :: exit more
 * [9] :: COST
 * [10] :: Yamamitsu UXB
 * [11] :: Yamamitsu XXB
 * [12] :: Yamamitsu ZXB
 * [13] :: Blue Light Special
 * [14] :: Ausgezeichnet 188 BJB
 * [15] :: Ausgezeichnet 350 SL
 * [16] :: Ausgezeichnet 440 SDI
 * [17] :: Ausgezeichnet 550 GT
 * [18] :: Moriyama Hiki-Gaeru
 * [19] :: Moriyama Gaijin
 * [20] :: Moriyama Bushido
 * [21] :: Moriyama Edokko
 * [22] :: Moriyama Katana
 * [23] :: Moriyama Tofu
 * [24] :: Moriyama Shogun
 * [25] :: Ninja 2000
 * [26] :: Ninja 3000
 * [27] :: Ninja 4000
 * [28] :: Ninja 5000
 * [29] :: Samurai Seven
 * [30] :: Ono-Sendai Cyberspace II
 * [31] :: Ono-Sendai Cyberspace III
 * [32] :: Ono-Sendai Cyberspace VI
 * [33] :: Ono-Sendai Cyberspace VII
 * [34] :: --------------------------------------
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class AsanosDatabaseView extends DatabaseView {

    /*
     * RAM (extracted from game data)
     * 05, 06, 0A, 0A, 05, 0B, 0F, 14, 05, 0A, 0C, 0F, 12,
     * 14, 18, 0A, 0C, 14, 19, 19, 0B, 0F, 14, 19
     */
    private static final int RAM[] = {
        5, 6, 10, 10, 5, 11, 15, 20, 5, 10, 12, 15,
        18, 20, 24, 10, 12, 20, 25, 25, 11, 15, 20, 25
    };

    private enum Mode {
        SUB, MENU, CATALOG, MFG, INVENTORY
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public AsanosDatabaseView(GameState gs, Pane p, PopupListener l) {
        super(gs, p, l);

        //dbTextResource.dumpList();
        if (gameState.usingDeck.getMode() == DeckItem.Mode.CYBERSPACE) {
            accessLevel = 3;
            siteContent();
        } else {
            landingPage();
        }
    }

    @Override
    protected final void landingPage() {
        pane.getChildren().clear();
        mode = Mode.SUB;

        Text helloText = new Text(dbTextResource.get(4) + "\n\n\n");

        TextFlow tf = pageTextFlow(headingText, helloText, CONTINUE_TEXT);
        pane.getChildren().add(tf);
    }

    @Override
    protected final void siteContent() {
        mainMenu();
    }

    private void mainMenu() {
        pane.getChildren().clear();
        mode = Mode.MENU;

        TextFlow tf = pageTextFlow(headingText);

        String menuString = dbTextResource.get(1);
        if (accessLevel > 1) {
            menuString += "\r" + dbTextResource.get(2);
        }
        if (accessLevel > 2) {
            menuString += "\r" + dbTextResource.get(3);
        }
        String[] split = menuString.split("\\r");
        for (String s : split) {
            Text menuItem = new Text("\n         " + s);
            tf.getChildren().add(menuItem);
            menuItem.setOnMouseClicked((t) -> {
                t.consume();
                itemPage(s.trim().substring(0, 1));
            });
        }

        pane.getChildren().add(tf);
        pane.setOnMouseClicked(null);
    }

    private void itemPage(String itemLetter) {
        switch (itemLetter) {
            case "1" -> {
                catalog();
            }
            case "2" -> {
                if (accessLevel > 1) {
                    manufacturers();
                }
            }
            case "3" -> {
                if (accessLevel > 2) {
                    inventory();
                }
            }
        }
    }

    private void catalog() {
        LOGGER.log(Level.FINE, "Asano's Catalog.");
        pane.getChildren().clear();
        mode = Mode.CATALOG;

        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(dbTextResource.get(5));
        // List of items.
        int i = 10;
        for (int ram : RAM) {
            sb.append("\n")
                    .append(String.format("%-34s", dbTextResource.get(i)))
                    .append(String.format("%4d", ram));
            i++;
        }
        sb.append("\n").append(dbTextResource.get(6));

        Text text = new Text(sb.toString());
        text.setLineSpacing(LINE_SPACING);
        TextFlow pageTf = pageTextScrolledFlow(headingText, text);

        pane.getChildren().add(pageTf);
        pane.setOnMouseClicked((t) -> {
            t.consume();
            mainMenu();
        });
    }

    private void manufacturers() {
        LOGGER.log(Level.FINE, "Asano's Manufacturers.");
        pane.getChildren().clear();
        mode = Mode.MFG;

        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(dbTextResource.get(7));

        Text text = new Text(sb.toString());
        text.setLineSpacing(LINE_SPACING);
        TextFlow pageTf = pageTextScrolledFlow(headingText, text);

        pane.getChildren().add(pageTf);
        pane.setOnMouseClicked((t) -> {
            t.consume();
            mainMenu();
        });
    }

    private void inventory() {
        LOGGER.log(Level.FINE, "Asano's Inventory.");
        pane.getChildren().clear();
        mode = Mode.INVENTORY;

        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("------ Inventory ------\n\n        TODO");

        Text text = new Text(sb.toString());
        text.setLineSpacing(LINE_SPACING);
        TextFlow pageTf = pageTextScrolledFlow(headingText, text);

        pane.getChildren().add(pageTf);
        pane.setOnMouseClicked((t) -> {
            t.consume();
            mainMenu();
        });
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
                    LOGGER.log(Level.WARNING, "Menu wants to exit system.");
                    keyEvent.consume();
                    return true;
                } else if (code.isDigitKey()) {
                    keyEvent.consume();
                    itemPage(code.getChar());
                    return false;
                }
            }
            case CATALOG, MFG, INVENTORY -> {
                if (code.equals(KeyCode.X)
                        || code.equals(KeyCode.ESCAPE)) {
                    LOGGER.log(Level.FINE, "Go back up menu level.");
                    mainMenu();
                    keyEvent.consume();
                    return false;
                }
            }
            // else ignore key

        }
        return super.handleKeyEvent(keyEvent);
    }
}
