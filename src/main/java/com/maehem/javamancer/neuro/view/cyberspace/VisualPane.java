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
import com.maehem.javamancer.neuro.view.popup.CyberspacePopup;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class VisualPane extends Pane {

    public static final Logger LOGGER = Logging.LOGGER;

    private final GameState gameState;
    private final GridBasePane gridBasePane;

    public VisualPane(GameState gs) {
        this.gameState = gs;
        this.gridBasePane = new GridBasePane(gs);

        getTransforms().add(new Scale(1.0, 1.14));

        getChildren().addAll(gridBasePane);

        Platform.runLater(() -> {
            gridBasePane.animateTravel(GridBasePane.Direction.FORWARD);
        });
    }

    public void handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        switch (code) {
            case RIGHT -> {
                keyEvent.consume();
                gridBasePane.animateTravel(GridBasePane.Direction.LEFT);
            }
            case LEFT -> {
                keyEvent.consume();
                gridBasePane.animateTravel(GridBasePane.Direction.RIGHT);
            }
            case UP -> {
                keyEvent.consume();
                gridBasePane.animateTravel(GridBasePane.Direction.FORWARD);
            }
            case DOWN -> {
                keyEvent.consume();
                gridBasePane.animateTravel(GridBasePane.Direction.BACKWARD);
            }
        }
    }

    public void configState(CyberspacePopup.State state) {
        gridBasePane.configState(state);
    }

}
