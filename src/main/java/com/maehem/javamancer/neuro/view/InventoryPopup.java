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
import com.maehem.javamancer.neuro.model.InventoryItem;
import com.maehem.javamancer.neuro.model.Item;
import java.util.ArrayList;
import java.util.logging.Level;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.DIGIT1;
import static javafx.scene.input.KeyCode.O;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class InventoryPopup extends SmallPopupPane {

    private final PopupListener listener;

    private enum Mode {
        MENU, EFFECT
    }

    private static final int NUM_ITEMS = 4;
    private int itemIndex = 0;
    private int numItems = 0;
    private Mode mode = Mode.MENU;
    private InventoryItem currentItem = null;

    public InventoryPopup(PopupListener l, GameState gs) {
        super(gs);
        this.listener = l;
        itemListPage();
    }

    private void itemListPage() {
        getChildren().clear();
        mode = Mode.MENU;
        currentItem = null;

        Text heading = new Text("Inventory");
        Text previous = new Text("previous");
        previous.setVisible(itemIndex > 0);
        Text exit = new Text("exit");
        Text more = new Text("more");
        more.setVisible(itemIndex < gameState.inventory.size() - NUM_ITEMS);
        HBox navBox = new HBox(previous, exit, more);
        //navBox.setAlignment(Pos.BASELINE_CENTER);
        navBox.setSpacing(20);
        navBox.setPadding(new Insets(0, 0, 0, 10));
        //exit.setTextAlignment(TextAlignment.CENTER);
        TextFlow tf = new TextFlow();
        tf.setLineSpacing(-10);
        tf.setMinHeight(76);
        VBox box = new VBox(
                heading,
                tf,
                navBox
        );
        box.setSpacing(0);
        box.getTransforms().add(new Scale(1.33, 1.0));
        box.setMinWidth(400);
        box.setPrefWidth(400);
        box.setMinHeight(160);
        box.setMaxHeight(160);
        box.setPadding(new Insets(0, 0, 0, 10));

        populateList(tf);

        getChildren().add(box);

        exit.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "Clicked Inventory Exit.");
            listener.popupExit();
        });
        more.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "Clicked Inventory More.");
            itemIndex += NUM_ITEMS;
            itemListPage();
        });
        previous.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "Clicked Inventory Previous.");
            itemIndex -= NUM_ITEMS;
            itemListPage();
        });
    }

    private void populateList(TextFlow tf) {
        LOGGER.log(Level.FINER, "Build Inventory List");
        ArrayList<InventoryItem> articles = gameState.inventory;
        for (int i = 0; i < NUM_ITEMS; i++) {
            if (i + itemIndex < articles.size()) {
                String newLine = i > 0 ? "\n" : "";
                InventoryItem item = articles.get(i + itemIndex);
                Text listItem;
                if (item.item.equals(Item.CREDITS)) {
                    listItem = new Text(newLine + (i + 1) + ". " + item.item.itemName + " " + gameState.chipBalance);
                } else {
                    listItem = new Text(newLine + (i + 1) + ". " + item.item.itemName);
                }

                tf.getChildren().add(listItem);
                final int n = i;
                listItem.setOnMouseClicked((t) -> {
                    effectItem(n);
                });
            }
        }
        numItems = tf.getChildren().size();
        LOGGER.log(Level.SEVERE, "Num Messages: {0}", numItems);
    }

    private void effectItem(int index) {
        if (itemIndex + index < gameState.inventory.size()) {
            currentItem = gameState.inventory.get(itemIndex + index);

            getChildren().clear();
            mode = Mode.EFFECT;
            Text heading;
            if (currentItem.item.equals(Item.CREDITS)) {
                heading = new Text(currentItem.item.itemName + " " + gameState.chipBalance);
            } else {
                heading = new Text(currentItem.item.itemName);
            }
            Text exitText = new Text("X. Exit\n");
            Text operateText = new Text("O. Operate Item\n");
            Text discardText = new Text("D. Discard Item\n");
            Text giveText = new Text("G. Give Item");

            TextFlow tf = new TextFlow(exitText, operateText, discardText, giveText);
            tf.setLineSpacing(-10);

            VBox box = new VBox(
                    heading,
                    tf
            );
            box.setSpacing(0);
            box.getTransforms().add(new Scale(1.33, 1.0));
            box.setMinWidth(400);
            box.setPrefWidth(400);
            box.setMinHeight(160);
            box.setMaxHeight(160);
            box.setPadding(new Insets(0, 0, 0, 10));

            getChildren().add(box);

            exitText.setOnMouseClicked((t) -> {
                itemListPage();
            });
            operateText.setOnMouseClicked((t) -> {
                operateItem();
            });
            discardText.setOnMouseClicked((t) -> {
                discardItem();
            });
            giveText.setOnMouseClicked((t) -> {
                giveItem();
            });
        }
    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();

        switch (mode) {
            case MENU -> {
                switch (code) {
                    case DIGIT1 -> {
                        effectItem(0);
                    }
                    case DIGIT2 -> {
                        effectItem(1);
                    }
                    case DIGIT3 -> {
                        effectItem(2);
                    }
                    case DIGIT4 -> {
                        effectItem(3);
                    }
                    case X -> {
                        return super.handleKeyEvent(keyEvent);
                    }
                }
            }
            case EFFECT -> {
                switch (code) {
                    case X -> {
                        itemListPage();
                    }
                    case O -> {
                        operateItem();
                    }
                    case D -> {
                        discardItem();
                    }
                    case G -> {
                        giveItem();
                    }
                }
            }
        }

        return false;
    }

    private void operateItem() {
        LOGGER.log(Level.CONFIG, "Operate: {0}", currentItem);
    }

    private void discardItem() {
        LOGGER.log(Level.CONFIG, "Discard: {0}", currentItem);
    }

    private void giveItem() {
        LOGGER.log(Level.CONFIG, "Give: {0}", currentItem);
    }
}
