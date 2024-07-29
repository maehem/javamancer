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

import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.TextResource;
import java.util.logging.Level;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class DialogPopup extends DialogPopupPane {

    private static final int DIALOG_COUNT = 15;

    private final TextResource textResource;

    private enum Mode {
        NPC, PLAYER
    };

    private final TextFlow textFlow = new TextFlow();
    private final Text wordText = new Text();
    private Mode mode = Mode.NPC;
    private final PopupListener listener;
    private final int[][] dialogChain;
    private int dialogIndex = 2;
    private int dialogSubIndex = -1;
    private int dialogCountDown = 0;

    public DialogPopup(PopupListener l, GameState gs, ResourceManager rm) {
        super(gs);
        this.listener = l;

        dialogChain = gs.room.getExtras().getDialogChain();

        dialogIndex = gs.room.getExtras().dialogWarmUp(gs);

        textResource = rm.getText(gameState.room);

        getChildren().add(textFlow);
        textFlow.setLayoutX(6);
        textFlow.setLayoutY(6);
        textFlow.setLineSpacing(-8);
        textFlow.setMaxWidth(getPrefWidth() / TEXT_SCALE - 10);
        textFlow.getTransforms().add(new Scale(TEXT_SCALE, 1.0));
        textFlow.getChildren().add(wordText);

        wordText.setText(textResource.get(dialogIndex));
    }

    public void dialogCounter() {
        if (mode == Mode.PLAYER) {
            if (dialogCountDown < 0) {
                return;
            }
            if (dialogCountDown > 0) {
                //LOGGER.log(Level.SEVERE, "Dialog counts down...");
                dialogCountDown--;
            } else {
                mode = Mode.NPC;
                dialogSubIndex = 0;
                dialogIndex = dialogChain[dialogIndex][dialogSubIndex];
                wordText.setText(textResource.get(dialogIndex));
                LOGGER.log(Level.SEVERE, "Countdown finished: mode: NPC: d[{0}][{1}] = {2}",
                        new Object[]{dialogIndex, dialogSubIndex, dialogChain[dialogIndex][dialogSubIndex]});
                dialogCountDown = -1;
                //dialogIndex = dialogChain[dialogIndex][dialogSubIndex];
                dialogSubIndex = -1;
                LOGGER.log(Level.SEVERE, "Set dialog index to: " + dialogIndex);
            }
        }
    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();

        if (dialogCountDown > 0) {
            LOGGER.log(Level.SEVERE, "KEY IGNORED: Events not allowed during countdown.");
            return false;
        }

        switch (code) {
            case SPACE -> {
                //LOGGER.log(Level.SEVERE, "SPACE BAR");
                switch (mode) {
                    case NPC -> {
                        mode = Mode.PLAYER;
                        LOGGER.log(Level.SEVERE, "Toggle to PLAYER next bubble response.");
                    }
                    case PLAYER -> {
                        // Nothing to do.
                    }
                }
                dialogSubIndex++; //  array[2][0]
                if (dialogSubIndex < 0 || dialogSubIndex >= dialogChain[dialogIndex].length) {
                    dialogSubIndex = 0;
                }
                if (dialogChain[dialogIndex][dialogSubIndex] == 99) { // NPC no longer talks.
                    gameState.room.getExtras().dialogNoMore(gameState);
                    LOGGER.log(Level.CONFIG, "End of dialog chain reached. NPC has nothing more to say.");
                    listener.popupExit();
                    return false;
                }
                // Get response for NPC dialog.
                // Display bubbles.
                LOGGER.log(Level.SEVERE, "SPACE: Show new player response. d[{0}][{1}] = {2}",
                        new Object[]{dialogIndex, dialogSubIndex, dialogChain[dialogIndex][dialogSubIndex]});
                wordText.setText(textResource.get(dialogChain[dialogIndex][dialogSubIndex]));
                dialogCountDown = -1; // No count down until ENTER pressed.
            }
            case ENTER -> {
                switch (mode) {
                    case NPC -> {
                        // Nothing happens.
                    }
                    case PLAYER -> {

                        //LOGGER.log(Level.SEVERE, "ENTER PRESSED. Begin countdown...");
                        // Start one second countdown to show response.
                        dialogCountDown = DIALOG_COUNT;
                        // Display say graphic.

                        dialogIndex = dialogChain[dialogIndex][dialogSubIndex];
                        dialogSubIndex = 0;
                        LOGGER.log(Level.SEVERE, "ENTER: start countdown. current dialog index: {0}   next NPC response. d[{1}][{2}] = {3}",
                                new Object[]{
                                    dialogIndex,
                                    dialogIndex, dialogSubIndex,
                                    dialogChain[dialogIndex][dialogSubIndex]
                                });
                    }
                }
            }
            case ESCAPE -> {
                listener.popupExit();
            }
        }
        return false;
    }

}
