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
package com.maehem.javamancer.neuro.view.ui;

import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.TextResource;
import com.maehem.javamancer.neuro.view.popup.PopupPane;
import com.maehem.javamancer.neuro.view.popup.SmallPopupPane;
import java.util.logging.Level;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class EndGameDialog extends SmallPopupPane {

    private final Text text;
    private final TextResource textResource;
    private int textIndex = 8;

    public EndGameDialog(GameState gs) {
        super(null, gs, 640);

        setLayoutX(0);
        setLayoutY(300);

        textResource = gs.resourceManager.getTxhText("ENDGAME");

        TextFlow tf = new TextFlow();
        tf.setLineSpacing(LINE_SPACING / 2);
        tf.setPadding(new Insets(4, 20, 0, 20));
        tf.setMaxWidth(getPrefWidth() / TEXT_SCALE - 10);
        text = new Text(
                textResource.get(textIndex).replace(
                        "\01", gameState.name) + "\n\n"
                + textResource.get(textIndex + 1)
                + "\n"
        );

        tf.getChildren().add(text);
        text.setOnMousePressed((t) -> {
            LOGGER.log(Level.FINE, "Mouse click on epilogue text.");
        });

        ScrollPane sp = new ScrollPane(tf);

        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setMinSize(480, 100);
        sp.setMaxSize(480, 100);
        sp.getTransforms().add(new Scale(PopupPane.TEXT_SCALE, 1.0));

        getChildren().add(sp);
        //addBox(/*new TextFlow(new Text("\n")),*/ new TextFlow(sp));
    }

    public boolean keyEvent(KeyEvent ke) {
        KeyCode code = ke.getCode();
        if (code.equals(KeyCode.ESCAPE) || code.equals(KeyCode.X)) {
            //setVisible(false);
            LOGGER.log(Level.CONFIG, "Proceed to Game Credits...");
            return true;
        }
        if (code.equals(KeyCode.SPACE)) {
            LOGGER.log(Level.CONFIG, "Next dialog entry requested.");
            textIndex++;
            if (textIndex > 13) {
                textIndex = 13;
            }
            text.setText(textResource.get(textIndex) + "\n\n");
        }
        return false;
    }

    @Override
    public void cleanup() {
    }
}
