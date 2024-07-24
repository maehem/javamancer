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
        MENU, DISCARD, OPERATE
    }

    private static final int NUM_ITEMS = 4;
    private int itemIndex = 0;
    private int numItems = 0;
    private Mode mode = Mode.MENU;

    public InventoryPopup(PopupListener l, GameState gs) {
        super(gs);
        this.listener = l;
        itemListPage();
    }

    private void itemListPage() {
        getChildren().clear();
        mode = Mode.MENU;
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
            listener.popupExit();
        });
    }

    private void populateList(TextFlow tf) {
        LOGGER.log(Level.FINER, "Build Inventory List");
        ArrayList<InventoryItem> articles = gameState.inventory;
        for (int i = 0; i < NUM_ITEMS; i++) {
            if (i + itemIndex < articles.size()) {
                String newLine = i > 0 ? "\n" : "";
                InventoryItem article = articles.get(i + itemIndex);
                Text messageItem;
                if (article.item.equals(Item.CREDITS)) {
                    messageItem = new Text(newLine + (i + 1) + ". " + article.item.itemName + " " + gameState.chipBalance);
                } else {
                    messageItem = new Text(newLine + (i + 1) + ". " + article.item.itemName);
                }
                tf.getChildren().add(messageItem);
                final int n = i + itemIndex;
                messageItem.setOnMouseClicked((t) -> {
                    //showMessage(gameState.bbs.get(n));
                });
            }
        }
        numItems = tf.getChildren().size();
        LOGGER.log(Level.SEVERE, "Num Messages: {0}", numItems);

//        for (InventoryItem ii : gameState.inventory) {
//            switch (ii.item.type) {
//                case CREDITS -> {
//                    Text itemText = new Text("\n" + itemIndex + ". " + ii.item.itemName + " " + gameState.chipBalance);
//                    // set on mouse clicked...
//                    tf.getChildren().add(tf);
//                    itemIndex++;
//                }
//                case REAL -> {
//                    Text itemText = new Text("\n" + itemIndex + ". " + ii.item.itemName);
//                    // set on mouse clicked...
//                    tf.getChildren().add(tf);
//                }
//                case SKILL -> {
//                    Text itemText = new Text("\n" + itemIndex + ". " + ii.item.itemName);
//                    // set on mouse clicked...
//                    tf.getChildren().add(tf);
//                }
//                case DECK -> {
//                    Text itemText = new Text("\n" + itemIndex + ". " + ii.item.itemName);
//                    // set on mouse clicked...
//                    tf.getChildren().add(tf);
//                }
//            }
//        }

    }
}
