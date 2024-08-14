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

import com.maehem.javamancer.neuro.model.BodyPart;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.item.SkillItem;
import com.maehem.javamancer.neuro.view.PopupListener;
import com.maehem.javamancer.neuro.view.RoomMode;
import static com.maehem.javamancer.neuro.view.popup.PopupPane.LINE_SPACING;
import java.util.ArrayList;
import java.util.logging.Level;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.DIGIT1;
import static javafx.scene.input.KeyCode.DIGIT2;
import static javafx.scene.input.KeyCode.DIGIT3;
import static javafx.scene.input.KeyCode.DIGIT4;
import static javafx.scene.input.KeyCode.X;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class SkillsVendPopup extends SmallPopupPane {

    private final Mode mode;
    private final ArrayList<SkillItem> vendItems;

    public enum Mode {
        BUY, SELL
    }

    private static final int NUM_ITEMS = 4; // show per page
    private int itemIndex = 0;

    public SkillsVendPopup(Mode mode, PopupListener l, GameState gs, ArrayList<SkillItem> vendItems) {
        super(l, gs, 440, HEIGHT, 1, Y);
        this.mode = mode;
        this.vendItems = vendItems;

        itemListPage();
    }

    private void itemListPage() {
        getChildren().clear();
        Text heading = new Text(mode.name() + " SKILLS"
                + "    credits-" + gameState.chipBalance);
        Text previous = new Text("previous");
        previous.setVisible(itemIndex > 0);
        Text exit = new Text("exit");
        Text more = new Text("more");
        more.setVisible(itemIndex < (vendItems.size() - NUM_ITEMS));
        HBox navBox = new HBox(previous, exit, more);
        navBox.setSpacing(20);
        navBox.setPadding(new Insets(0, 0, 0, 10));
        TextFlow tf = new TextFlow();
        tf.setLineSpacing(LINE_SPACING);
        tf.setMinHeight(76);

        populateList(tf);
        addBox(heading, tf, navBox);

        exit.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "Clicked Skill Vend Exit.");
            if (gameState.bodyShopRecent != GameState.BodyShopRecent.NONE) {
                listener.popupExit(RoomMode.Popup.TALK);
                LOGGER.log(Level.SEVERE, "End bodyshop dialog and open TALK.");
                t.consume();
            } else {
                t.consume();
                listener.popupExit();
            }
        });
        more.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "Clicked Skill Vend More.");
            t.consume();
            itemIndex += NUM_ITEMS;
            itemListPage();
        });
        previous.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "Clicked Skill Vend Previous.");
            t.consume();
            itemIndex -= NUM_ITEMS;
            itemListPage();
        });
    }

    private void populateList(TextFlow tf) {
        LOGGER.log(Level.FINER, "Build Skill Vend {0} List", mode.name());
        //ArrayList<Skill> installedSkills = gameState.skills;
        for (int i = 0; i < NUM_ITEMS; i++) {
            if (i + itemIndex < vendItems.size()) {
                String newLine = i > 0 ? "\n" : "";

                SkillItem skillItem = vendItems.get(i + itemIndex);
                boolean inventoryHasSkillItem = gameState.hasInventoryItem(skillItem);
                String soldmarker = (gameState.hasInstalledSkill(skillItem) || inventoryHasSkillItem) ? "-" : " ";
                String itemName = String.format("%-20s", skillItem.item.itemName);
                String priceRaw = "";
                if (mode == Mode.BUY) {
                    priceRaw = String.valueOf(skillItem.price);
                } else {
                    //No sell mode for now.
                    //priceRaw = String.valueOf(skill.sellPrice);
                }
                String price = String.format("%4s", priceRaw);
                Text listItem = new Text(newLine + (i + 1) + ". " + soldmarker + itemName + price);

                tf.getChildren().add(listItem);
                final int n = i;
                listItem.setOnMouseClicked((t) -> {
                    vend(n);
                    itemListPage();
                });
            } else {
                tf.getChildren().add(new Text("\n"));
            }
        }
    }

    private void vend(int index) {
        SkillItem skillItem = vendItems.get(index + itemIndex);

        switch (mode) {
            case BUY -> {
                if (!gameState.hasInventoryItem(skillItem)
                        && !gameState.skills.contains(skillItem)) {
                    // Try to buy it.
                    int price = skillItem.price;
                    if (gameState.chipBalance >= price) {
                        LOGGER.log(Level.SEVERE, "Player bought " + skillItem.item.itemName);
                        gameState.chipBalance -= price;
                        gameState.inventory.add(skillItem);
                        //vendItems.remove(part);
                        //gameState.bodyShopRecent = GameState.BodyShopRecent.BUY;
                    } else {
                        LOGGER.log(Level.SEVERE, "Not enough money to buy skill.");
                    }
                } else {
                    LOGGER.log(Level.SEVERE, "Can't buy skill. We already have one.");
                }
            }
            case SELL -> {
//                if (!gameState.soldBodyParts.contains(part)) {
//                    // Try to sell it.
//                    int price = part.sellPrice;
//                    LOGGER.log(Level.SEVERE, "Player sold " + part.itemName);
//                    gameState.chipBalance += price;
//                    gameState.constitution -= part.constDamage;
//                    gameState.soldBodyParts.add(part);
//                    gameState.bodyShopRecent = GameState.BodyShopRecent.SELL;
//                } else {
//                    LOGGER.log(Level.SEVERE, "Can't sell part as we already sold it.");
//                }
            }
        }
    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();

        switch (code) {
            case DIGIT1 -> {
                vend(0);
            }
            case DIGIT2 -> {
                vend(1);
            }
            case DIGIT3 -> {
                vend(2);
            }
            case DIGIT4 -> {
                vend(3);
            }
            case X -> {
                return super.handleKeyEvent(keyEvent);
            }
            case M -> {
                if (itemIndex < (BodyPart.values().length - NUM_ITEMS)) {
                    LOGGER.log(Level.SEVERE, "User pressed M (more)");
                    itemIndex += NUM_ITEMS;
                    itemListPage();
                }
            }
            case N -> {
                if (itemIndex >= NUM_ITEMS) {
                    LOGGER.log(Level.SEVERE, "User pressed N (previous)");
                    itemIndex -= NUM_ITEMS;
                    itemListPage();
                }
            }
        }

        return false;
    }

}
