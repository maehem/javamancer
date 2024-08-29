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
import com.maehem.javamancer.neuro.view.popup.PopupPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class YesNoPane extends Pane {

    public static final Logger LOGGER = Logging.LOGGER;

    private static final int PANE_WIDTH = 358;
    private static final int PANE_HEIGHT = 50;
    private static final int PANE_X = 130;
    private static final int PANE_Y = 64;
    private static final int TF_PADDING = 12;

    private final GameState gameState;

    public YesNoPane(GameState gs) {
        this.gameState = gs;

        setPrefSize(PANE_WIDTH, PANE_HEIGHT);
        setMinSize(PANE_WIDTH, PANE_HEIGHT);
        setMaxSize(PANE_WIDTH, PANE_HEIGHT);
        setLayoutX(PANE_X);
        setLayoutY(PANE_Y);
        setId("neuro-popup");

        prompt();
    }

    public final void prompt() {
        LOGGER.log(Level.SEVERE, "Cyberspace: Show Yes/No Prompt");
        //mode = Mode.SOFTWARE;

        setVisible(true);
        getChildren().clear();
        Text softwareHeading = new Text("Enter Database (");
        Text yesText = new Text("Y");
        Text slashText = new Text("/");
        Text noText = new Text("N");
        Text closeParenthText = new Text(")");
        TextFlow tf = PopupPane.textFlow(
                softwareHeading,
                yesText, slashText, noText,
                closeParenthText
        );
        tf.setPrefSize(PANE_WIDTH, PANE_HEIGHT);
        tf.setPadding(new Insets(TF_PADDING));

        getChildren().add(PopupPane.makeBox(this, tf));

        yesText.setOnMouseClicked((t) -> {
            t.consume();
            enterDatabase();
        });
        noText.setOnMouseClicked((t) -> {
            t.consume();
            setVisible(false);
        });

    }

    public boolean handleKeyEvent(KeyEvent ke) {

        //ke.consume();
        KeyCode code = ke.getCode();
        setVisible(false);
        switch (code) {
            case N, X, ESCAPE -> {
                LOGGER.log(Level.SEVERE, "Cyberspace: YesNoPane: Exit/Cancel Pressed...");
                ke.consume();
                return true;
            }
            case LEFT, RIGHT, UP, DOWN -> {
                LOGGER.log(Level.SEVERE, "Cyberspace: YesNoPane: Navigate away...");
                // Don't consume the keyEvent.
                return true;
            }
            case Y -> { // Inventory/Software/Slots
                LOGGER.log(Level.SEVERE, "Cyberspace: YesNoPane: Yes Pressed...");
                ke.consume();
                enterDatabase();
            }
        }

        return false;
    }

    private void enterDatabase() {
        LOGGER.log(Level.SEVERE, "User enters database...");
        gameState.database = gameState.dbList.whatsAt(
                gameState.usingDeck.getCordX(), gameState.usingDeck.getCordY()
        );
        gameState.battleStart();
        setVisible(false);
    }
}
