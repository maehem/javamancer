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

import com.maehem.javamancer.neuro.model.BankTransaction;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.TextResource;
import com.maehem.javamancer.neuro.model.room.RoomBounds;
import com.maehem.javamancer.neuro.model.room.RoomExtras;
import static com.maehem.javamancer.neuro.model.room.RoomExtras.*;
import com.maehem.javamancer.neuro.view.PopupListener;
import com.maehem.javamancer.neuro.view.ResourceManager;
import com.maehem.javamancer.neuro.view.RoomMode;
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

    public static final int DIALOG_COUNT = 50; // 15 frames == 1 second
    private static final String WORD_FILL_MN = "@---------------";
    private static final String FILL_STRING = "_______________";
    private static final String CURSOR_STRING = "<";
    private final Text CURSOR_FILL = new Text("");

    private final TextResource textResource;

    private enum Mode {
        NPC, PLAYER
    }

    private final TextFlow textFlow = new TextFlow();
    private final Text wordText = new Text();
    private final Text typedText = new Text(); // For typing in word
    private final DialogBubble bubble;
    private Mode mode = Mode.NPC;
    private final int[][] dialogChain;
    private int dialogIndex = 2;
    private int dialogSubIndex = -1;
    public int dialogCountDown = 0;
    private int fillingText = 0; // 0 == none,  1 == map1, 2 == map2

    public DialogPopup(PopupListener l, GameState gs, ResourceManager rm) {
        super(l, gs);

        this.bubble = new DialogBubble(rm, gs.roomPosX, getPrefHeight() - 4);
        this.dialogChain = gs.room.getExtras().getDialogChain();
        this.dialogIndex = gs.room.getExtras().dialogWarmUp(gs);
        this.textResource = rm.getRoomText(gameState.room);
        if (gameState.bodyShopRecent != GameState.BodyShopRecent.NONE) {
            dialogCountDown = DIALOG_COUNT;
            gameState.bodyShopRecent = GameState.BodyShopRecent.NONE;
        }

        textFlow.setLineSpacing(LINE_SPACING + 2.0);
        textFlow.setMaxWidth(getPrefWidth() / TEXT_SCALE - 30);
        textFlow.getChildren().addAll(wordText, typedText, CURSOR_FILL);
        textFlow.setMinHeight(getPrefHeight());

        VBox box = addBox(textFlow);
        box.setPadding(new Insets(6, 20, 6, 20));

        getChildren().add(bubble);

        LOGGER.log(Level.CONFIG, "first text: {0} == {1}",
                new Object[]{
                    dialogIndex,
                    textResource.get(dialogIndex)
                });
        wordText.setText(textResource.get(dialogIndex));
        dialogCountDown = DIALOG_COUNT;

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
        if (dialogCountDown < 0) { // Nothing to count down
            return;
        }
        if (dialogCountDown > 0) { // Count one.
            dialogCountDown--;
        } else { // Finished countdown. Do something.
            dialogCountDown = -1;
            if (mode == Mode.PLAYER) {
                LOGGER.log(Level.CONFIG, "Player: Talk countdown finished.");
                npcResponse(0);
            } else { // NPC count down done.
                LOGGER.log(Level.CONFIG, "NPC: Talk countdown finished.");
                if (dialogChain[dialogIndex][0] >= 50) {
                    dialogSubIndex = 0;
                    LOGGER.log(Level.SEVERE, "Process command: " + dialogChain[dialogIndex][0]);
                    processCommand(dialogChain[dialogIndex][dialogSubIndex]);
                } else {
                    //LOGGER.log(Level.SEVERE, "Dialog arg should be command, but it's not!");
                }
            }
        }

    }

    private void npcResponse(int sub) {
        LOGGER.log(Level.CONFIG, "NPC: Do response.");
        mode = Mode.NPC;
        LOGGER.log(Level.SEVERE, "[140] Mode = NPC");

        bubble.setMode(DialogBubble.Mode.NONE); // The thing that hangs under the words.
        dialogSubIndex = sub;

        // Fill in NPC Response
        int newDialog = dialogChain[dialogIndex][dialogSubIndex];
        LOGGER.log(Level.CONFIG, "new dialog: d[{0}][{1}] = {2}",
                new Object[]{dialogIndex, dialogSubIndex,
                    newDialog
                });
        //dialogSubIndex = 0;
        if (newDialog >= 50) {
            LOGGER.log(Level.SEVERE, "NPC runs command: " + dialogIndex);
            processCommand(newDialog);
            dialogSubIndex++;
        } else {
            dialogIndex = dialogChain[dialogIndex][dialogSubIndex];
            LOGGER.log(Level.CONFIG, "NPC: Set dialog index to: " + dialogIndex);
            // Control character '01' is a token for the player's name. Replace it here.
            wordText.setText(textResource.get(dialogIndex).replace("\1", gameState.name));
            LOGGER.log(Level.SEVERE, "Text: " + wordText.getText());
            dialogCountDown = -1;
            dialogSubIndex = -1;
            if (dialogChain[dialogIndex].length > 0) {
                dialogCountDown = DIALOG_COUNT;
            }
        }
    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        keyEvent.consume();

        if (dialogCountDown > 0) {
            LOGGER.log(Level.WARNING, "KEY IGNORED: Events not allowed during countdown.");
            return false;
        }
        if (fillingText > 0) {
            handleTypedText(keyEvent);
        } else {
            handleCode(code);
        }
        return false;
    }

    private void handleTypedText(KeyEvent ke) {
        KeyCode code = ke.getCode();
        if (code == KeyCode.ENTER) {
            // Submit typed thing.
            LOGGER.log(Level.SEVERE, "Set Cursor In-Visible.");
            CURSOR_FILL.setText("");

            RoomExtras extras = gameState.room.getExtras();

            if (extras != null) {
                int askWord = -1;
                switch (fillingText) {
                    case 1 -> {
                        askWord = extras.askWord1(typedText.getText().toLowerCase());
                    }
                    case 2 -> {
                        askWord = extras.askWord2(typedText.getText().toLowerCase());
                    }
                }
                if (askWord > 0) {
                    LOGGER.log(Level.SEVERE, "askWord() returned: " + askWord);
                    dialogIndex = askWord;
                    dialogSubIndex = 0;
                    typedText.setText("");
                    //npcResponse(0);
                    wordText.setText(textResource.get(dialogIndex));
                    dialogCountDown = DIALOG_COUNT;
                } else {
                    LOGGER.log(Level.SEVERE, "RoomExtras.askWord has returned unexpected value! -1");
                }
            }
            fillingText = 0;

        } else if (code == KeyCode.BACK_SPACE) {
            String typedString = typedText.getText();
            if (!typedString.isEmpty()) {
                typedText.setText(typedString.substring(0, typedString.length() - 2));
            }
        } else if (code.isLetterKey()) {
            CURSOR_FILL.setText("<");
            typedText.setText(typedText.getText() + code.getChar());
        }
    }

    private void handleCode(KeyCode code) {

        switch (code) {
            case SPACE -> {
                //LOGGER.log(Level.SEVERE, "SPACE BAR");
                switch (mode) {
                    case PLAYER -> {
                        // Nothing to do.
                        //LOGGER.log(Level.CONFIG, "SPACE bar for NPC.");
                    }
                    case NPC -> {
                        mode = Mode.PLAYER;
                        bubble.setMode(DialogBubble.Mode.THINK);
                        LOGGER.log(Level.CONFIG, "Toggle to PLAYER next bubble response.");
                    }
                }
                dialogSubIndex++; //  array[2][0]
                // Cycle player response back to beginning.
                if (dialogSubIndex < 0 || dialogSubIndex >= dialogChain[dialogIndex].length) {
                    dialogSubIndex = 0;
                }
                LOGGER.log(Level.SEVERE, "Dialog is now:  d[{0}][{1}] = {2}",
                        new Object[]{dialogIndex, dialogSubIndex,
                            dialogChain[dialogIndex][dialogSubIndex]
                        });
                // Is it a command?
                if (dialogChain[dialogIndex][dialogSubIndex] >= 50) {
                    LOGGER.log(Level.SEVERE, "Command found for response.");
                    processCommand(dialogChain[dialogIndex][dialogSubIndex]);
                } else {
                    // Get response for NPC dialog.
                    // Display bubbles.
                    LOGGER.log(Level.CONFIG, "SPACE: Show new player response. d[{0}][{1}] = {2} == {3}",
                            new Object[]{
                                dialogIndex, dialogSubIndex,
                                dialogChain[dialogIndex][dialogSubIndex],
                                textResource.get(dialogChain[dialogIndex][dialogSubIndex])
                            });
                    String toSay = textResource.get(dialogChain[dialogIndex][dialogSubIndex]);
                    if (toSay.contains(WORD_FILL_MN)) {
                        toSay = toSay.replace(WORD_FILL_MN, "");
                        CURSOR_FILL.setText(FILL_STRING);
                    } else {
                        CURSOR_FILL.setText("");
                    }
                    wordText.setText(toSay);
                    dialogCountDown = -1; // No count down until ENTER pressed.
                }
            }
            case ENTER -> {
                switch (mode) {
                    case NPC -> {
                        // Nothing happens.
                    }
                    case PLAYER -> {
                        LOGGER.log(Level.SEVERE, "handle code: ENTER -> PLAYER");
                        bubble.setMode(DialogBubble.Mode.SAY);

                        dialogIndex = dialogChain[dialogIndex][dialogSubIndex];
                        dialogSubIndex = 0;
                        String newText = textResource.get(dialogIndex);
                        int command = dialogChain[dialogIndex][dialogSubIndex];
                        //LOGGER.log(Level.SEVERE, "Evaluate text: " + newText);
                        if (newText.contains(WORD_FILL_MN)
                                && (command == WORD1 || command == WORD2)) {
                            LOGGER.log(Level.SEVERE, "Text is a fill-in.");
                            switch (command) {
                                case WORD1 -> {
                                    fillingText = 1;
                                }
                                case WORD2 -> {
                                    fillingText = 2;
                                }
                            }
                            CURSOR_FILL.setText(FILL_STRING);

                        } else {
                            //LOGGER.log(Level.CONFIG, "ENTER PRESSED. Begin countdown...");
                            // Start one second countdown to show response.
                            dialogCountDown = DIALOG_COUNT;

                            LOGGER.log(Level.CONFIG, "ENTER: start countdown. current dialog index: {0}   next NPC response. d[{1}][{2}] = {3}",
                                    new Object[]{
                                        dialogIndex,
                                        dialogIndex, dialogSubIndex,
                                        dialogChain[dialogIndex][dialogSubIndex]
                                    });
                        }
                    }
                }
            }

            case ESCAPE -> {
                listener.popupExit();
            }
        }
    }

    private void processCommand(int command) {
        LOGGER.log(Level.SEVERE, "Process command: " + command);
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
            case BODY_BUY -> { // Bodyshop  buy menu
                LOGGER.log(Level.CONFIG, "NPC opens Body Shop Buy menu.");
                listener.popupExit(RoomMode.Popup.BODYSHOP_BUY);
            }
            case BODY_SELL -> { // Bodyshop menu
                LOGGER.log(Level.CONFIG, "NPC opens Body Shop Sell menu.");
                listener.popupExit(RoomMode.Popup.BODYSHOP_SELL);
            }
            case SKILL_BUY -> { // Bodyshop menu
                LOGGER.log(Level.CONFIG, "NPC opens Skill Buy menu.");
                listener.popupExit(RoomMode.Popup.SKILLS_BUY);
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
            case EXIT_ST_CHAT -> { // Exit to Street Outside Chatsubo
                LOGGER.log(Level.CONFIG, "NPC sends player to Street Chatsubo.");
                listener.popupExit();
                gameState.useDoor = RoomBounds.Door.STREET_CHAT;
                return;
            }
            case EXIT_BDSHOP -> { // Exit to Body Shop after Death
                LOGGER.log(Level.CONFIG, "NPC sends player to Body Shop.");
                listener.popupExit();
                gameState.useDoor = RoomBounds.Door.BODY_SHOP;
                return;
            }
            case DIALOG_CLOSE -> {
                listener.popupExit();
            }
            case NPC -> {
                LOGGER.log(Level.SEVERE, "NPC Command.");
                //dialogSubIndex++;
                npcResponse(1);
                mode = Mode.PLAYER; // Causes next loop to toggle to NPC again.
                LOGGER.log(Level.SEVERE, "[301] Mode = PLAYER");
                return;
            }
            case FINE_BANK_500 -> {
                LOGGER.log(Level.SEVERE, "Fine from bank 500.");
                int amt = 500;
                gameState.bankBalance -= amt;
                if (gameState.bankBalance < 0) {
                    amt = -gameState.bankBalance;
                    gameState.bankBalance = 0;
                }
                gameState.bankTransactionRecord.add(new BankTransaction(
                        gameState.getDateString(),
                        BankTransaction.Operation.Fine,
                        amt
                ));
                npcResponse(1);
            }
            case FINE_BANK_20K -> {
                LOGGER.log(Level.SEVERE, "Fine from bank 20K.");
                int amt = 20000;
                gameState.bankBalance -= amt;
                if (gameState.bankBalance < 0) {
                    amt = -gameState.bankBalance;
                    gameState.bankBalance = 0;
                }
                gameState.bankTransactionRecord.add(new BankTransaction(
                        gameState.getDateString(),
                        BankTransaction.Operation.Fine,
                        amt
                ));
                npcResponse(1);
            }
            case WORD1 -> {
                LOGGER.log(Level.SEVERE, "Type word into dialog area.");
                typedText.setText(""); // Clear it.
                npcResponse(1); // Show text.

            }
        }

    }

    @Override
    public void cleanup() {
    }
}
