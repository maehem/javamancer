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
import com.maehem.javamancer.neuro.view.PopupListener;
import com.maehem.javamancer.neuro.view.ResourceManager;
import com.maehem.javamancer.neuro.view.popup.LargePopupPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.DIGIT1;
import static javafx.scene.input.KeyCode.DIGIT2;
import static javafx.scene.input.KeyCode.DIGIT3;
import static javafx.scene.input.KeyCode.DIGIT4;
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
public class PaxPopupPane extends LargePopupPane implements PaxNodeListener {

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

    public PaxPopupPane(PopupListener l, GameState gs, ResourceManager rm) {
        super(l, gs);
        this.resourceManager = rm;

        getChildren().add(modeAccess());
    }

    private Node modeAccess() {
        Text title = new Text("PAX - Public Access System");
        Text words = new Text("Matt Shaw\nComlink\nChiba City");
        enterCode.setText(enterPrefix + enterCursor);

        words.setLineSpacing(LINE_SPACING);

        Region region = new Region();
        region.setPrefSize(100, 6);

        HBox wordsBox = new HBox(region, words);
        VBox box = new VBox(
                title,
                wordsBox,
                enterCode);
        box.setSpacing(6);
        box.setPadding(new Insets(0));
        box.getTransforms().add(new Scale(TEXT_SCALE, 1.0));
        box.setLayoutX(30);
        box.setLayoutY(20);

        return box;
    }

    private Node modeMenu() {
        Text exitItem = new Text("X. Exit System\n");
        Text firstTimeItem = new Text("1. First Time PAX User Info.\n");
        Text bankItem = new Text("2. Access Banking Interlink\n");
        Text newsItem = new Text("3. Night City News\n");
        Text bbsItem = new Text("4. Bulletin Board");
        TextFlow menuItems = new TextFlow(
                exitItem,
                firstTimeItem,
                bankItem,
                newsItem,
                bbsItem
        );
        menuItems.setLineSpacing(LINE_SPACING);

        VBox box = new VBox(
                menuItems,
                new Text("\n          " + "choose a function")
        );
        box.setSpacing(6);
        box.getTransforms().add(new Scale(TEXT_SCALE, 1.0));
        box.setLayoutX(30);
        box.setLayoutY(20);

        exitItem.setOnMouseClicked((t) -> {
            t.consume();
            listener.popupExit();
        });
        firstTimeItem.setOnMouseClicked((t) -> {
            handleKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, null, null, DIGIT1, true, true, true, true));
        });
        bankItem.setOnMouseClicked((t) -> {
            handleKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, null, null, DIGIT2, true, true, true, true));
        });
        newsItem.setOnMouseClicked((t) -> {
            handleKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, null, null, DIGIT3, true, true, true, true));
        });
        bbsItem.setOnMouseClicked((t) -> {
            handleKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, null, null, DIGIT4, true, true, true, true));
        });
        return box;
    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        switch (mode) {
            case ACCESS -> {
                keyEvent.consume();
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
                        keyEvent.consume();
                        getChildren().clear();
                        mode = Mode.FIRST;
                        paxNode = new PaxFirstTimeNode(this, resourceManager);
                        getChildren().add(paxNode);
                        return false;
                    }
                    case DIGIT2 -> {
                        LOGGER.log(Level.CONFIG, "Banking.");
                        getChildren().clear();
                        keyEvent.consume();
                        mode = Mode.BANKING;
                        paxNode = new PaxBankingNode(this, gameState);
                        getChildren().add(paxNode);
                        return false;
                    }
                    case DIGIT3 -> {
                        LOGGER.log(Level.CONFIG, "News.");
                        getChildren().clear();
                        keyEvent.consume();
                        mode = Mode.NEWS;
                        paxNode = new PaxNewsNode(this, gameState);
                        getChildren().add(paxNode);
                        return false;
                    }
                    case DIGIT4 -> {
                        LOGGER.log(Level.CONFIG, "BBS.");
                        keyEvent.consume();
                        getChildren().clear();
                        mode = Mode.BBS;
                        paxNode = new PaxBbsNode(this, gameState, resourceManager);
                        getChildren().add(paxNode);
                        return false;
                    }
                }
            }
            case FIRST -> {
                if (paxNode.handleEvent(keyEvent)) {
                    keyEvent.consume();
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
            case BBS -> {
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
         return super.handleKeyEvent(keyEvent);  // Check if X pressed.
    }

    @Override
    public void paxNodeExit() {
        // Exit PAX sub menu
        getChildren().clear();
        mode = Mode.MENU;
        getChildren().add(modeMenu());
    }

    @Override
    public void cleanup() {
    }
}
