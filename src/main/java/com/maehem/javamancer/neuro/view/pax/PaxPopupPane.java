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
package com.maehem.javamancer.neuro.view.pax;

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.view.PopupPane;
import com.maehem.javamancer.neuro.view.ResourceManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.DIGIT1;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class PaxPopupPane extends PopupPane {

    public static final Logger LOGGER = Logging.LOGGER;
    private final ResourceManager resourceManager;

    private enum Mode {
        ACCESS, MENU, FIRST, BANKING, NEWS, BBS
    }

    private final String enterPrefix = "Enter verification code:";
    private final String enterCursor = "<";
    private final Text enterCode = new Text();
    StringBuilder enteredCode = new StringBuilder();

    private Mode mode = Mode.ACCESS;
    private PaxNode paxNode = null;

    public PaxPopupPane(GameState gs, ResourceManager rm) {
        super(gs);
        this.resourceManager = rm;

        getChildren().add(modeAccess());
    }

    private Node modeAccess() {
        Text title = new Text("PAX - Public Access System");
        Text words = new Text("Matt Shaw\nComlink\nChiba City");
        enterCode.setText(enterPrefix + enterCursor);

        words.setLineSpacing(-8);

        Region region = new Region();
        region.setPrefSize(100, 6);

        HBox wordsBox = new HBox(region, words);
        VBox box = new VBox(
                title,
                wordsBox,
                enterCode);
        box.setSpacing(6);
        box.setPadding(new Insets(0));
        box.getTransforms().add(new Scale(1.3, 1.0));
        box.setLayoutX(30);
        box.setLayoutY(20);

        return box;
    }

    private Node modeMenu() {
        Text exitItem = new Text("X. Exit\n");
        Text firstTimeItem = new Text("1. First Time PAX User Info.\n");
        Text bankItem = new Text("2. Access Banking Interlink\n");
        Text newsItem = new Text("3. Might City News\n");
        Text bbsItem = new Text("4. Bulletin Board");
        TextFlow menuItems = new TextFlow(
                exitItem,
                firstTimeItem,
                bankItem,
                newsItem,
                bbsItem
        );
        menuItems.setLineSpacing(-6);

        VBox box = new VBox(
                menuItems,
                new Text("          " + "choose a function")
        );
        box.setSpacing(6);
        box.setPadding(new Insets(0));
        box.getTransforms().add(new Scale(1.3, 1.0));
        box.setLayoutX(30);
        box.setLayoutY(20);

        return box;
    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        switch (mode) {
            case ACCESS -> {
                if (enteredCode.length() < 12 && keyEvent.getCode().isDigitKey()) {
                    LOGGER.log(Level.FINEST, "Typed." + keyEvent.getText());
                    enteredCode.append(keyEvent.getText());
                    enterCode.setText(enterPrefix + enteredCode.toString() + enterCursor);
                } else if (enteredCode.length() > 0 && keyEvent.getCode().equals(KeyCode.BACK_SPACE)) {
                    LOGGER.log(Level.FINEST, "Backspace.");
                    enteredCode.delete(enteredCode.length() - 1, enteredCode.length());
                    enterCode.setText(enterPrefix + enteredCode.toString() + enterCursor);
                } else if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    getChildren().clear();
                    mode = Mode.MENU;
                    getChildren().add(modeMenu());
                    return false;
                }
            }
            case MENU -> {
                switch (keyEvent.getCode()) {
                    case DIGIT1 -> {
                        LOGGER.log(Level.CONFIG, "First Time User.");
                        getChildren().clear();
                        mode = Mode.FIRST;
                        getChildren().add(new PaxFirstTimeNode(resourceManager));
                        return false;
                    }
                    case DIGIT2 -> {
                        LOGGER.log(Level.CONFIG, "Banking.");
                        getChildren().clear();
                        mode = Mode.BANKING;
                        paxNode = new PaxBankingNode(getGameState());
                        getChildren().add(paxNode);
                        return false;
                    }
                    case DIGIT3 -> {
                        LOGGER.log(Level.CONFIG, "News.");
                        getChildren().clear();
                        mode = Mode.NEWS;
                        paxNode = new PaxNewsNode(getGameState(), resourceManager);
                        getChildren().add(paxNode);
                        return false;
                    }
                    case DIGIT4 -> {
                        LOGGER.log(Level.CONFIG, "BBS.");
                    }
                }
            }
            case FIRST -> {
                if (keyEvent.getCode().isLetterKey()) {
                    getChildren().clear();
                    mode = Mode.MENU;
                    getChildren().add(modeMenu());
                    return false;
                }
            }
            case BANKING -> {
                if (paxNode.handleEvent(keyEvent)) {
                    // Exit bank menu
                    getChildren().clear();
                    mode = Mode.MENU;
                    getChildren().add(modeMenu());
                    return false;
                }
                return false;
            }
            case NEWS -> {
                if (paxNode.handleEvent(keyEvent)) {
                    // Exit bank menu
                    getChildren().clear();
                    mode = Mode.MENU;
                    getChildren().add(modeMenu());
                }
                return false;
            }
        }

        // No one else handled the X or ESC, so quit PAX.
        if (super.handleKeyEvent(keyEvent)) { // Check if X pressed.
            return true;
        }

        return false;
    }

}
