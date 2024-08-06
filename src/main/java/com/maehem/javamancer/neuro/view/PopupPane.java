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

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.neuro.model.GameState;
import static com.maehem.javamancer.neuro.view.PopupPane.LOGGER;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.Node;
import static javafx.scene.input.KeyCode.ESCAPE;
import static javafx.scene.input.KeyCode.X;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class PopupPane extends Pane {

    public static final Logger LOGGER = Logging.LOGGER;

    protected static final double TEXT_SCALE = 1.5;
    protected static final double LINE_SPACING = -7;

    public final GameState gameState;
    public final PopupListener listener;

    public PopupPane(PopupListener l, GameState gameState) {
        this.listener = l;
        this.gameState = gameState;
    }

    public boolean handleKeyEvent(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case X, ESCAPE -> {
                LOGGER.log(Level.FINER, "User pressed X or ESC Key.");
                return true;
            }
            default -> {
            }
        }

        return false;
    }

    protected void quitGame() {
        gameState.requestQuit = true;
    }

    protected VBox addBox(Node... nodes) {
        VBox box = new VBox(nodes);
        box.setSpacing(0);
        box.getTransforms().add(new Scale(TEXT_SCALE, 1.0));
        box.setMinWidth(getPrefWidth());
        box.setPrefWidth(getPrefWidth());
        box.setMinHeight(getPrefHeight());
        box.setMaxHeight(getPrefWidth());
        box.setPadding(new Insets(0, 0, 0, 10));

        getChildren().add(box);

        return box;
    }
}
