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
package com.maehem.javamancer.neuro.view.popup;

import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.skill.CryptologySkill;
import com.maehem.javamancer.neuro.model.skill.Skill;
import com.maehem.javamancer.neuro.view.PopupListener;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.X;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Disk Operations Popup Load, Save Pause and Quit functions.
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class CryptologyPopup extends SmallPopupPane {

    //private final PopupListener listener;
    private enum Mode {
        DECODE
    }

    private Mode mode = Mode.DECODE;

    public CryptologyPopup(PopupListener l, GameState gs) {
        super(l, gs, 380, 128, 100, 240);
        //this.listener = l;
        mainMenu();
    }
    Text typedText = new Text("");
    Text responseText = new Text("RESPONSE");
    Text cursorText = new Text("<");

    private void mainMenu() {
        getChildren().clear();
        mode = Mode.DECODE;
        Text HEADING_TXT = new Text("       Cryptology");

        Text PROMPT_TXT = new Text("Decode Word: ");
        Text EXIT_TXT = new Text("          exit");

        TextFlow tf = new TextFlow(
                PROMPT_TXT, typedText, cursorText,
                new Text("\n\n       "),
                responseText,
                new Text("\n")
        );
        tf.setLineSpacing(LINE_SPACING);

        addBox(HEADING_TXT, tf, EXIT_TXT);

        EXIT_TXT.setOnMouseClicked((t) -> {
            LOGGER.log(Level.FINE, "Cryptology Popup Exit clicked.");
            listener.popupExit();
        });
        
        Platform.runLater(() -> {
            responseText.setVisible(false);
        });
    }

    private void doDecode() {
        LOGGER.log(Level.FINE, "Decode typed word.");
        Skill activeSkill = gameState.activeSkill;
        if ( activeSkill instanceof CryptologySkill cs) {
            String decoded = cs.decode(typedText.getText());
            if ( decoded == null ) {
                responseText.setText("*upgrade*");
            } else {
                responseText.setText(decoded);
            }
        } else {
            LOGGER.log(Level.SEVERE, "Cryptology popup visible when not active Skill!");
        }
    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();

        switch (mode) {
            case DECODE -> {
                switch (code) {
                    case ENTER -> {
                        doDecode();
                        responseText.setVisible(true);
                        cursorText.setVisible(false);
                    }
                    case BACK_SPACE -> {
                        // response invis.
                        if ( responseText.isVisible() ) {
                            typedText.setText("");
                        }
                        responseText.setVisible(false);
                        cursorText.setVisible(true);
                        
                        String typed = typedText.getText();
                        if (!typed.isEmpty()) {
                            typedText.setText(typed.substring(0, typed.length() - 1));
                        }
                    }
                    case X, ESCAPE -> {
                        return super.handleKeyEvent(keyEvent);
                    }
                    default -> {
                        if (code.isLetterKey()) {
                            responseText.setVisible(false);
                            cursorText.setVisible(true);
                            String typed = typedText.getText();
                            if (typed.length() < 10) {
                                typedText.setText(typed + code.getChar().toUpperCase());
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void cleanup() {
    }
}
