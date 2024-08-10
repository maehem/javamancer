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
import com.maehem.javamancer.neuro.model.RoomBounds;
import static com.maehem.javamancer.neuro.model.RoomExtras.*;
import com.maehem.javamancer.neuro.model.TextResource;
import java.util.logging.Level;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class DialogPopup extends DialogPopupPane {

    private static final int DIALOG_COUNT = 20; // 15 frames == 1 second

    private final TextResource textResource;

    private enum Mode {
        NPC, PLAYER
    }

    private final TextFlow textFlow = new TextFlow();
    private final Text wordText = new Text();
    private final DialogBubble bubble;
    private Mode mode = Mode.NPC;
    private final int[][] dialogChain;
    private int dialogIndex = 2;
    private int dialogSubIndex = -1;
    private int dialogCountDown = 0;

    public DialogPopup(PopupListener l, GameState gs, ResourceManager rm) {
        super(l, gs);

        this.bubble = new DialogBubble(rm, gs.roomPosX, getPrefHeight() - 4);
        this.dialogChain = gs.room.getExtras().getDialogChain();
        this.dialogIndex = gs.room.getExtras().dialogWarmUp(gs);
        this.textResource = rm.getRoomText(gameState.room);

        textFlow.setLineSpacing(LINE_SPACING + 2.0);
        textFlow.setMaxWidth(getPrefWidth() / TEXT_SCALE - 30);
        textFlow.getChildren().add(wordText);
        textFlow.setMinHeight(getPrefHeight());

        VBox box = addBox(textFlow);
        box.setPadding(new Insets(6, 20, 6, 20));

        getChildren().add(bubble);

        wordText.setText(textResource.get(dialogIndex));

        setOnMouseClicked((mouseEvent) -> {
            switch (mouseEvent.getButton()) {
                case PRIMARY -> {
                    handleCode(KeyCode.SPACE); // Space bar
                    mouseEvent.consume();
                }
                case SECONDARY -> {
                    handleCode(KeyCode.ENTER); // Enter
                    mouseEvent.consume();
                }
            }
        });

    }

    public void dialogCounter() {
        if (dialogCountDown < 0) {
            return;
        }
        if (dialogCountDown > 0) {
            dialogCountDown--;
        } else {
            if (mode == Mode.PLAYER) {
                mode = Mode.NPC;
                bubble.setMode(DialogBubble.Mode.NONE);
                dialogSubIndex = 0;
                dialogIndex = dialogChain[dialogIndex][dialogSubIndex];
                // Control character '01' is a token for the player's name. Replace it here.
                wordText.setText(textResource.get(dialogIndex).replace("\1", gameState.name));
                LOGGER.log(Level.CONFIG, "Player countdown finished: new mode: NPC: d[{0}][{1}] = {2}",
                        new Object[]{dialogIndex, dialogSubIndex, dialogChain[dialogIndex][dialogSubIndex]});
                dialogCountDown = -1;
                dialogSubIndex = -1;
                LOGGER.log(Level.CONFIG, "Set dialog index to: " + dialogIndex);
                if (dialogChain[dialogIndex].length > 0) {
                    dialogCountDown = DIALOG_COUNT;
                }
            } else { // NPC count down done.
                LOGGER.log(Level.CONFIG, "NPC: Talk countdown finished.");
                if (dialogChain[dialogIndex][0] >= 50) {
                    processCommand(dialogChain[dialogIndex][0]);
                } else {
                    LOGGER.log(Level.SEVERE, "Dialog arg should be command, but it's not!");
                }
            }
        }

    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();

        if (dialogCountDown > 0) {
            LOGGER.log(Level.WARNING, "KEY IGNORED: Events not allowed during countdown.");
            return false;
        }

        handleCode(code);
        return false;
    }

    private void handleCode(KeyCode code) {

        switch (code) {
            case SPACE -> {
                //LOGGER.log(Level.SEVERE, "SPACE BAR");
                switch (mode) {
                    case NPC -> {
                        mode = Mode.PLAYER;
                        bubble.setMode(DialogBubble.Mode.THINK);
                        LOGGER.log(Level.CONFIG, "Toggle to PLAYER next bubble response.");
                    }
                    case PLAYER -> {
                        // Nothing to do.
                    }
                }
                dialogSubIndex++; //  array[2][0]
                if (dialogSubIndex < 0 || dialogSubIndex >= dialogChain[dialogIndex].length) {
                    dialogSubIndex = 0;
                }
                if (dialogChain[dialogIndex][dialogSubIndex] >= 50) {
                    processCommand(dialogChain[dialogIndex][dialogSubIndex]);
                }

                // Get response for NPC dialog.
                // Display bubbles.
                LOGGER.log(Level.CONFIG, "SPACE: Show new player response. d[{0}][{1}] = {2}",
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
                        bubble.setMode(DialogBubble.Mode.SAY);
                        //LOGGER.log(Level.CONFIG, "ENTER PRESSED. Begin countdown...");
                        // Start one second countdown to show response.
                        dialogCountDown = DIALOG_COUNT;
                        // Display say graphic.

                        dialogIndex = dialogChain[dialogIndex][dialogSubIndex];
                        dialogSubIndex = 0;
                        LOGGER.log(Level.CONFIG, "ENTER: start countdown. current dialog index: {0}   next NPC response. d[{1}][{2}] = {3}",
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
    }

    private void processCommand(int command) {
        switch (command) {
            case DIALOG_END -> { // NPC no longer talks.
                gameState.room.getExtras().dialogNoMore(gameState);
                LOGGER.log(Level.CONFIG, "End of dialog chain reached. NPC has nothing more to say.");
                listener.popupExit();
                return;
            }
            case TO_JAIL -> {
                LOGGER.log(Level.CONFIG, "NPC sends player to jail.");
                listener.popupExit();
                gameState.useDoor = RoomBounds.Door.JAIL;
                return;
            }
            case BUY -> { // Vendor menu
                LOGGER.log(Level.CONFIG, "NPC opens Buy menu.");
                LOGGER.log(Level.CONFIG, "Buy dialog not implemented yet.");
                //listener.popupExit(RoomMode.Popup.BUY);
            }
            case SELL -> { // Vendor menu
                LOGGER.log(Level.CONFIG, "NPC opens Sell menu.");
                LOGGER.log(Level.CONFIG, "Sell dialog not implemented yet.");
                //listener.popupExit(RoomMode.Popup.SELL);
            }
            case EXIT_T -> { // Exit Top
                LOGGER.log(Level.CONFIG, "NPC sends player to new room via top.");
                listener.popupExit();
                gameState.useDoor = RoomBounds.Door.TOP;
                return;
            }
            case EXIT_R -> { // Exit Right
                LOGGER.log(Level.CONFIG, "NPC sends player to new room via right.");
                listener.popupExit();
                gameState.useDoor = RoomBounds.Door.RIGHT;
                return;
            }
            case EXIT_B -> { // Exit Bottom
                LOGGER.log(Level.CONFIG, "NPC sends player to new room via bottom.");
                listener.popupExit();
                gameState.useDoor = RoomBounds.Door.BOTTOM;
                return;
            }
            case EXIT_L -> { // Exit Left
                LOGGER.log(Level.CONFIG, "NPC sends player to new room via left.");
                listener.popupExit();
                gameState.useDoor = RoomBounds.Door.LEFT;
                return;
            }
            case DIALOG_CLOSE -> {
                listener.popupExit();
            }

        }

    }
}
