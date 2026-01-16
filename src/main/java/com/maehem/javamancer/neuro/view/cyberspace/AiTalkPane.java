/*
 * MIT License
 *
 * Copyright (c) 2026 Mark J. Koch ( @maehem on GitHub )
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

import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.TextResource;
import com.maehem.javamancer.neuro.model.ai.AI;
import com.maehem.javamancer.neuro.view.PopupListener;
import com.maehem.javamancer.neuro.view.popup.DialogPopupPane;
import static com.maehem.javamancer.neuro.view.popup.PopupPane.LINE_SPACING;
import static com.maehem.javamancer.neuro.view.popup.PopupPane.TEXT_SCALE;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author mark
 */
public class AiTalkPane extends DialogPopupPane {

    private AI ai;
    private final TextFlow textFlow = new TextFlow();
    private final Text wordText = new Text();
    private final VBox box;

    public enum Message {
        FIRST, RANDOM, LAST
    };

    public AiTalkPane(PopupListener listener, GameState gameState) {
        super(listener, gameState);

        textFlow.setLineSpacing(LINE_SPACING + 1.0);
        textFlow.setMaxWidth(getPrefWidth() / TEXT_SCALE - 30);
        textFlow.getChildren().addAll(wordText);

        box = addBox(textFlow);
        box.setPadding(new Insets(2, 20, 4, 20));

        // TODO once debuged.
        //setVisible(false);
    }

    public void setBubbleText(String text) {
        wordText.setText(text);
        layoutChildren(); // Computes new wordText bounds.
        setPrefHeight(box.getBoundsInLocal().getHeight());
    }

    public void setAi(AI ai) {
        this.ai = ai;
    }

    public void say(Message msg) {
        TextResource dialogs = ai.getDialogs(gameState);
        int index;
        switch (msg) {
            default -> {
                index = ai.TALK[0];
            }
            case LAST -> {
                index = ai.TALK.length - 1;
            }
            case RANDOM -> {
                index = (int)(Math.random() * ai.TALK.length - 2) + 1;
            }
        }
        setBubbleText(dialogs.get(index));
        setVisible(true);
    }

    @Override
    public void cleanup() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
