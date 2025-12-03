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
import com.maehem.javamancer.neuro.model.item.CreditsItem;
import com.maehem.javamancer.neuro.model.item.DeckItem;
import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.item.Item.Catalog;
import com.maehem.javamancer.neuro.model.item.SkillItem;
import com.maehem.javamancer.neuro.model.skill.Skill;
import com.maehem.javamancer.neuro.view.PopupListener;
import com.maehem.javamancer.neuro.view.RoomMode;
import java.util.ArrayList;
import java.util.logging.Level;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.DIGIT1;
import static javafx.scene.input.KeyCode.N;
import static javafx.scene.input.KeyCode.O;
import static javafx.scene.input.KeyCode.Y;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class InventoryPopup extends SmallPopupPane {

    private enum Mode {
        MENU, EFFECT, INSTALL, INSTALL_SUMMARY,
        DISCARD, DISCARD_SUMMARY,
        ASK_GIVE,
        CREDITS
    }

    private static final int NUM_ITEMS = 4;
    private final StringBuilder enteredNumber = new StringBuilder();
    private final Text enteredNumberText = new Text();
    private final Text insufficientFunds = new Text("Insufficient Funds!");

    private int itemIndex = 0;
    private int numItems = 0;
    private Mode mode = Mode.MENU;
    private Item currentItem = null;

    public InventoryPopup(PopupListener l, GameState gs) {
        super(l, gs);
        listItems();
        insufficientFunds.setVisible(false);
    }

    private void listItems() {
        getChildren().clear();
        mode = Mode.MENU;
        currentItem = null;

        Text heading = new Text("      Items");
        Text previous = new Text("previous");
        previous.setVisible(itemIndex > 0);
        Text exit = new Text("exit");
        Text more = new Text("more");
        more.setVisible(itemIndex < gameState.inventory.size() - NUM_ITEMS);
        HBox navBox = new HBox(previous, exit, more);
        navBox.setSpacing(20);
        navBox.setPadding(new Insets(0, 0, 0, 10));
        TextFlow tf = new TextFlow();
        tf.setLineSpacing(LINE_SPACING);
        tf.setMinHeight(76);

        addBox(heading, tf, navBox);

        populateList(tf);

        exit.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "Clicked Inventory Exit.");
            listener.popupExit();
        });
        more.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "Clicked Inventory More.");
            itemIndex += NUM_ITEMS;
            listItems();
        });
        previous.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "Clicked Inventory Previous.");
            itemIndex -= NUM_ITEMS;
            listItems();
        });
    }

    private void populateList(TextFlow tf) {
        LOGGER.log(Level.FINER, "Build Inventory List");
        ArrayList<Item> articles = gameState.inventory;
        for (int i = 0; i < NUM_ITEMS; i++) {
            if (i + itemIndex < articles.size()) {
                String newLine = i > 0 ? "\n" : "";
                Item item = articles.get(i + itemIndex);
                Text listItem;
                if (item.item.equals(Catalog.CREDITS)) {
                    listItem = new Text(newLine + (i + 1) + ".  " + item.item.itemName + " " + gameState.chipBalance);
                } else {
                    String needsRepair = " ";
                    if (item instanceof DeckItem di) {
                        if (di.needsRepair) {
                            needsRepair = "-";
                        }
                    }
                    listItem = new Text(newLine + (i + 1) + ". "
                            + needsRepair
                            + item.item.itemName);
                }

                tf.getChildren().add(listItem);
                final int n = i;
                listItem.setOnMouseClicked((t) -> {
                    itemOptions(n);
                });
            }
        }
        numItems = tf.getChildren().size();
        LOGGER.log(Level.FINE, "Num Items: {0}", numItems);
    }

    private void itemOptions(int index) {
        if (itemIndex + index < gameState.inventory.size()) {
            currentItem = gameState.inventory.get(itemIndex + index);
            itemOptions();
        }
    }

    private void itemOptions() {
        getChildren().clear();
        mode = Mode.EFFECT;
        Text heading;
        if (currentItem.item.equals(Catalog.CREDITS)) {
            heading = new Text(currentItem.item.itemName + " " + gameState.chipBalance);
        } else {
            heading = new Text(currentItem.item.itemName);
        }
        Text exitText = new Text("X. Exit\n");
        Text operateText = new Text("O. Operate Item\n");
        Text discardText = new Text("D. Discard Item\n");
        Text giveOrEraseText;
        if (currentItem instanceof DeckItem) {
            giveOrEraseText = new Text("E. Erase Software");
        } else {
            giveOrEraseText = new Text("G. Give Item");
        }

        TextFlow tf = new TextFlow(exitText, operateText, discardText, giveOrEraseText);
        tf.setLineSpacing(LINE_SPACING);

        addBox(heading, tf);

        exitText.setOnMouseClicked((t) -> {
            listItems();
        });
        operateText.setOnMouseClicked((t) -> {
            operateItem();
        });
        discardText.setOnMouseClicked((t) -> {
            discardItem();
        });
        giveOrEraseText.setOnMouseClicked((t) -> {
            if (currentItem instanceof DeckItem) {
                operateItem(true);
            } else {
                giveItem();
            }
        });
    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();

        switch (mode) {
            case MENU -> {
                switch (code) {
                    case DIGIT1 -> {
                        itemOptions(0);
                    }
                    case DIGIT2 -> {
                        itemOptions(1);
                    }
                    case DIGIT3 -> {
                        itemOptions(2);
                    }
                    case DIGIT4 -> {
                        itemOptions(3);
                    }
                    case X -> {
                        return super.handleKeyEvent(keyEvent);
                    }
                }
            }
            case EFFECT -> {
                switch (code) {
                    case X -> {
                        listItems();
                    }
                    case O -> {
                        operateItem();
                    }
                    case D -> {
                        discardItem();
                    }
                    case G -> { // Give item (not for decks)
                        if (!(currentItem instanceof DeckItem)) {
                            giveItem();
                        }
                    }
                    case E -> { // Deck Erase Software
                        if (currentItem instanceof DeckItem) {
                            operateItem(true);
                        }
                    }
                }
            }
            case INSTALL -> {
                switch (code) {
                    case Y -> {
                        Skill installedSkill = SkillItem.installSkillItem((SkillItem) currentItem, gameState.skills);
                        installSkillSummary(installedSkill, installedSkill != null);
                    }
                    case N -> {
                        itemOptions();
                    }
                }
            }
            case ASK_GIVE -> {
                switch (code) {
                    case Y -> {
                        doGiveItem();
                    }
                    case N -> {
                        itemOptions();
                    }
                }
            }
            case INSTALL_SUMMARY, DISCARD_SUMMARY -> {
                listItems();  // Any key event
            }
            case DISCARD -> {
                switch (code) {
                    case Y -> {
                        discardItem();
                    }
                    case N -> {
                        itemOptions();
                    }
                }
            }
            case CREDITS -> {
                switch (code) {
                    case X -> {
                        listItems();
                    }
                    default ->
                        handleEnteredNumber(keyEvent);
                }
            }
        }

        return false;
    }

    private void operateItem() {
        operateItem(false);
    }

    @SuppressWarnings("unchecked")
    private void operateItem(boolean altMode) {
        if (currentItem != null) {
            if (currentItem instanceof SkillItem si) {
                LOGGER.log(Level.INFO, "Operate(Install) Skill: {0}", si);
                askInstallSkillItem(si);
            } else if (currentItem instanceof DeckItem deck) {
                LOGGER.log(Level.INFO, "Operate Deck: {0}", deck);
                gameState.usingDeck = deck;
                gameState.usingDeckErase = altMode;
                deck.setZone(gameState.room.getJack());
                listener.popupExit(RoomMode.Popup.DECK); // Exit inventory
            }
        }
    }

    private void discardItem() {
        LOGGER.log(Level.CONFIG, "Discard: {0}", currentItem);
        // TODO: Don't allow discard of CREDITS. Test on actual game.
        askDiscardItem();
    }

    private void giveItem() {
        LOGGER.log(Level.CONFIG, "Give: {0}", currentItem);
        if (currentItem instanceof CreditsItem) {
            LOGGER.log(Level.FINE, "Give Credits selected.");
            giveCreditsEnterAmount();
        } else {
            LOGGER.log(Level.FINE, "Give Item selected.");
            gameState.room.getExtras().give(gameState, currentItem, 0);
            giveItemAreYouSure();
        }
    }

    private void giveCreditsEnterAmount() {
        getChildren().clear();
        mode = Mode.CREDITS;
        Text heading = new Text("  Give Credits");
        //int length = currentItem.getName().length();
        Text heading2 = new Text("enter amount");

        TextFlow tf = new TextFlow(enteredNumberText, new Text("<"));
        tf.setLineSpacing(LINE_SPACING);
        tf.setPadding(new Insets(0, 0, 0, 30));

        addBox(heading, heading2, tf);
    }

    private void giveItemAreYouSure() {
        getChildren().clear();
        mode = Mode.ASK_GIVE;
        Text heading = new Text("  Give");
        //int length = currentItem.getName().length();
        Text heading2 = new Text("    " + currentItem.getName());

        Text yesText = new Text("Y");
        Text noText = new Text("N");

        TextFlow tf = new TextFlow(
                new Text("\nAre you sure? ("),
                yesText, new Text("/"), noText,
                new Text(")")
        );
        tf.setLineSpacing(LINE_SPACING);
        tf.setPadding(new Insets(0, 0, 0, 10));

        addBox(heading, heading2, tf);

        yesText.setOnMouseClicked((t) -> {
            doGiveItem();
        });
        noText.setOnMouseClicked((t) -> {
            itemOptions();
        });
    }

    private void askInstallSkillItem(SkillItem skillItem) {
        getChildren().clear();
        mode = Mode.INSTALL;
        Text heading = new Text("     Install Skill");
        int length = skillItem.getName().length();
        Text heading2 = new Text(String.format("%1$" + (23 - length) + "s", currentItem.getName() + "?"));
        Text yesText = new Text("Y");
        Text slashText = new Text("/");
        Text noText = new Text("N");

        TextFlow tf = new TextFlow(yesText, slashText, noText);
        tf.setLineSpacing(-10);
        tf.setPadding(new Insets(0, 0, 0, 110));

        addBox(heading, heading2, tf);

        yesText.setOnMouseClicked((t) -> {
            //installSkillItem(skillItem);
            Skill installedSkill = SkillItem.installSkillItem(skillItem, gameState.skills);
            installSkillSummary(installedSkill, installedSkill != null);
            if (installedSkill != null) {
                gameState.removeInventoryItem(skillItem.item);
            }
        });
        noText.setOnMouseClicked((t) -> {
            itemOptions(); // Back to item's menu.
        });
    }

    private void installSkillSummary(Skill skill, boolean state) {
        LOGGER.log(Level.FINE, "Install Summary: {0}  {1}", new Object[]{skill == null ? "null" : "OK", state ? "YES" : "NO"});
        // Skill == null ==  FUBAR
        // state == true == installed.
        // state == false == already exists, not installed.
        getChildren().clear();
        mode = Mode.INSTALL_SUMMARY;
        Text heading = new Text("        Install ");
        int length = currentItem.getName().length();
        int nSpaces = ((24 - length) / 2) + length;
        Text heading2 = new Text(String.format("%" + nSpaces + "s", currentItem.getName()));
        Text statusText;
        if (skill == null) {
            statusText = new Text("       ERROR!");
        } else {
            if (state) {
                statusText = new Text("        SUCCESS!");
            } else {
                statusText = new Text("        NOT ADDED\n    ALREADY INSTALLED");
            }
        }

        TextFlow tf = new TextFlow(statusText);
        tf.setLineSpacing(LINE_SPACING);

        VBox box = addBox(heading, heading2, tf);

        box.setOnMouseClicked((t) -> {
            listItems();
        });

    }

    private void askDiscardItem() {
        getChildren().clear();
        mode = Mode.DISCARD;
        Text heading = new Text("     DISCARD ITEM?");
        int length = currentItem.getName().length();
        int nSpaces = ((24 - length) / 2) + length;
        Text heading2 = new Text(String.format("%" + nSpaces + "s", currentItem.getName()));
        Text yesText = new Text("Y");
        Text slashText = new Text("/");
        Text noText = new Text("N");

        TextFlow tf = new TextFlow(yesText, slashText, noText);
        tf.setPadding(new Insets(0, 0, 0, 110));

        addBox(heading, heading2, tf).setSpacing(20);

        yesText.setOnMouseClicked((t) -> {
            disposeItem();
        });
        noText.setOnMouseClicked((t) -> {
            itemOptions(); // Back to item's menu.
        });

    }

    private void disposeItem() {
        boolean status = gameState.inventory.remove(currentItem);

        getChildren().clear();
        mode = Mode.DISCARD_SUMMARY;
        Text heading = new Text("      DISCARD ITEM");
        int length = currentItem.getName().length();
        int nSpaces = ((24 - length) / 2) + length;
        Text heading2 = new Text(String.format("%" + nSpaces + "s", currentItem.getName()));
        Text statusText;
        if (status) {
            statusText = new Text("        SUCCESS!");
        } else {
            statusText = new Text("         ERROR!");
        }

        TextFlow tf = new TextFlow(statusText);
        //tf.setLineSpacing(30);

        VBox box = addBox(heading, heading2, tf, insufficientFunds);
        box.setSpacing(20);

        box.setOnMouseClicked((t) -> {
            listItems();
        });

    }

    private void handleEnteredNumber(KeyEvent ke) {
        if (enteredNumber.length() < 12 && ke.getCode().isDigitKey()) {
            LOGGER.log(Level.FINEST, "Typed: {0}", ke.getText());
            enteredNumber.append(ke.getText());
            enteredNumberText.setText(enteredNumber.toString());
            //enterCode.setText(enterPrefix + enteredNumber.toString() + enterCursor);
            checkAmount(Integer.parseInt(enteredNumber.toString()));
        } else if (enteredNumber.length() > 0 && ke.getCode().equals(KeyCode.BACK_SPACE)) {
            LOGGER.log(Level.FINEST, "Backspace.");
            enteredNumber.delete(enteredNumber.length() - 1, enteredNumber.length());
            enteredNumberText.setText(enteredNumber.toString());
            //enterCode.setText(enterPrefix + enteredNumber.toString() + enterCursor);
            if (enteredNumber.isEmpty()) {
                checkAmount(0);
            } else {
                checkAmount(Integer.parseInt(enteredNumber.toString()));
            }
        } else if (ke.getCode().equals(KeyCode.ENTER)) {
            int value = Integer.parseInt(enteredNumber.toString());
            if (gameState.chipBalance >= value) {
                if (gameState.room.getExtras().give(gameState, currentItem, value)) {
                    listener.popupExit(RoomMode.Popup.TALK); // Talk to NPC now.
                }
            } else {
                LOGGER.log(Level.INFO, "Insufficient Funds!");
            }
        }
    }

    private void doGiveItem() {
        if (!gameState.room.getExtras().give(gameState, currentItem, -1)) {
            LOGGER.log(Level.WARNING, "NPC did not accept or handle given item. But you gave it away.");
        }
        gameState.inventory.remove(currentItem);
        LOGGER.log(Level.FINE, "Item {0} removed from inventory.", currentItem.getName());
        currentItem = null;
        listener.popupExit(RoomMode.Popup.TALK); // Talk to NPC now.
    }

    private boolean checkAmount(int amount) {
        LOGGER.log(Level.FINER, "Check Give credits amount: {0}", amount);
        // Move to bank
        if (gameState.chipBalance < amount) {
            // can't do it.
            LOGGER.log(Level.INFO, "Not enough money on chip!");
            insufficientFunds.setVisible(true);
            return false;
        } else {
            insufficientFunds.setVisible(false);
        }
        return true;
    }

    @Override
    public void cleanup() {
    }
}
