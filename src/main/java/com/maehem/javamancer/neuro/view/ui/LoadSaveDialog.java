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
import com.maehem.javamancer.neuro.view.popup.SmallPopupPane;
import java.util.Map;
import java.util.logging.Level;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class LoadSaveDialog extends SmallPopupPane {

    private final Type type;

    public enum Type {
        LOAD, SAVE
    };

    private static final String SAVE_STR = "Save";
    private static final String LOAD_STR = "Load";

    private static final Map<Integer, Text> OPTIONS = Map.of(
            1, new Text("1"),
            2, new Text("2"),
            3, new Text("3"),
            4, new Text("4")
    );
    private static final Text exitButton = new Text("exit");
    private final static String SPACER = "  ";
    private final String modeString;

    public LoadSaveDialog(Type type, GameState gs) {
        super(null, gs, 240);
        this.type = type;

        if (type.equals(Type.LOAD)) {
            modeString = LOAD_STR;
        } else {
            modeString = SAVE_STR;
        }

        setLayoutX(82);
        setLayoutY(256);

        TextFlow tf = new TextFlow();
        tf.setLineSpacing(LINE_SPACING);
        tf.setPadding(new Insets(10, 0, 0, 20));
        tf.getChildren().add(new Text(" " + modeString + " Game"));
        tf.getChildren().add(new Text("\n\n"));

        for (int n = 1; n <= OPTIONS.size(); n++) {
            Text text = OPTIONS.get(n);
            final int nn = n;
            text.setOnMousePressed((event) -> {
                LOGGER.log(Level.SEVERE, "Mouse Click: {0} game {1}", new Object[]{modeString, nn});
                select(nn);
            });
            tf.getChildren().add(text);
            tf.getChildren().add(new Text(SPACER));
        }

        tf.getChildren().add(new Text("\n\n\n   "));
        tf.getChildren().add(exitButton);

        addBox(tf);

        exitButton.setOnMouseClicked((t) -> {
            setVisible(false);
        });
    }

    public boolean keyEvent(KeyEvent ke) {
        KeyCode code = ke.getCode();
        if (code.isDigitKey()) {
            return select(Integer.parseInt(code.getChar()));
        } else if (code.equals(KeyCode.ESCAPE) || code.equals(KeyCode.X)) {
            setVisible(false);
            return true;
        }
        return false;
    }

    private boolean select(int n) {
        if (n > 0 && n <= OPTIONS.size()) {
            switch (type) {
                case LOAD -> {
                    gameState.loadSlot(n);
                }
                case SAVE -> {
                    gameState.saveSlot(n);
                }
            }
            setVisible(false);
            return true;
        }
        return false;
    }
}
