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
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class MessagePane extends Pane {

    public static final Logger LOGGER = Logging.LOGGER;

    private static final int PANE_WIDTH = 248;
    private static final int PANE_HEIGHT = 100;
    private static final int PANE_X = 50; // Tuned in parent Node.
    private static final int PANE_Y = 0;  // Tuned in parent Node.

    private final GameState gameState;
    
    TextFlow tf = PopupPane.textFlow();

    public MessagePane(GameState gs) {
        this.gameState = gs;

        setPrefSize(PANE_WIDTH, PANE_HEIGHT);
        setMinSize(PANE_WIDTH, PANE_HEIGHT);
        setMaxSize(PANE_WIDTH, PANE_HEIGHT);
        setLayoutX(PANE_X);
        setLayoutY(PANE_Y);
        //setId("neuro-popup");
        //setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        prompt();
    }

    public final void prompt() {
        LOGGER.log(Level.INFO, "Cyberspace: Show Message Pane");
        //mode = Mode.SOFTWARE;

        setVisible(true);
        getChildren().clear();
        tf.getChildren().addAll(
                new Text("\n"),
                new Text("\n"),
                new Text("\n"),
                new Text("\n"),
                new Text("Deck Connected\n")
        );
        tf.setPrefSize(PANE_WIDTH, PANE_HEIGHT);
        tf.setPadding(Insets.EMPTY);
        tf.getTransforms().add(new Scale(PopupPane.TEXT_SCALE, 1.0));
        //VBox box = PopupPane.makeBox(this, tf);
        //box.setPadding(Insets.EMPTY);
        getChildren().add(tf);
    }

    public void addMessage( String message ) {
        LOGGER.log(Level.INFO, "MessagePane: Add Message ==>{0}", message);
        Text m = new Text( message + "\n");
        tf.getChildren().removeFirst();
        tf.getChildren().add(m);
        Platform.runLater(() -> {
            tf.requestLayout();
        });
    }
}
