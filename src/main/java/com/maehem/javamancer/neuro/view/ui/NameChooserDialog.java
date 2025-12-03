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
import com.maehem.javamancer.neuro.view.TitleMode;
import com.maehem.javamancer.neuro.view.popup.SmallPopupPane;
import java.util.logging.Level;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class NameChooserDialog extends SmallPopupPane {

    private final StringBuilder typedName = new StringBuilder();
    private final Text typedText = new Text();
    private final TitleMode titleMode;

    public NameChooserDialog(TitleMode l) {
        super(null, null, 220, 90, 80, 266);
        this.titleMode = l;

        TextFlow tf = new TextFlow(
                new Text("YOUR NAME?\n\n"),
                typedText, new Text("<")
        );
        tf.getTransforms().add(new Scale(TEXT_SCALE, 1.0));
        tf.setPadding(new Insets(10));
        getChildren().add(tf);

    }

    public void keyEvent(KeyEvent ke) {
        KeyCode code = ke.getCode();
        if (code.equals(KeyCode.SPACE)) {
            // Ignore
            LOGGER.log(Level.FINE, "Name Chooser: SPACE");
            ke.consume();
        } else if ((code.isLetterKey() | code.isDigitKey())
                && typedName.length() < GameState.NAME_LEN_MAX) {
            typedName.append(ke.getText());
            typedText.setText(typedName.toString());
            ke.consume();
        } else if (code.equals(KeyCode.ENTER)) {
            // Accept and finish
            titleMode.acceptName(typedName.toString());
            ke.consume();
        } else if (code.equals(KeyCode.ESCAPE)) {
            // Escape
            LOGGER.log(Level.FINE, "Name Chooser: Escape pressed.");
            setVisible(false);
        } else if (code.equals(KeyCode.BACK_SPACE)) {
            if (typedName.length() > 0) {
                typedName.delete(typedName.length() - 1, typedName.length());
                typedText.setText(typedName.toString());
            }
        }
    }

    @Override
    public void cleanup() {
    }
}
