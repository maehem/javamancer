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
package com.maehem.javamancer.neuro.view.cyberspace;

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.view.PopupListener;
import com.maehem.javamancer.neuro.view.RoomMode;
import com.maehem.javamancer.neuro.view.popup.DiskPopup;
import com.maehem.javamancer.neuro.view.popup.RomPopup;
import com.maehem.javamancer.neuro.view.popup.SkillsPopup;
import com.maehem.javamancer.neuro.view.popup.cyberspace.SoftwarePane;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class ControlPanelPane extends Pane implements PopupListener {

    public static final Logger LOGGER = Logging.LOGGER;

    private static final int CORD_Y = 143;
    private static final double BTN1_SIZE = 26;
    private static final double BTN2_W = 72;
    private static final double BTN2_H = 20;
    private static final Color BTN_COLOR = Color.RED;

    private final GameState gameState;
    private final PopupListener listener;
    private final Text zoneText = cpText("9", 80, CORD_Y);
    private final Text xText = cpText("999", 144, CORD_Y);
    private final Text yText = cpText("999", 210, CORD_Y);
    private final Text cashText = cpText("$9999999", 306, CORD_Y);
    private final Rectangle invRect = cpButton1(324, 14);
    private final Rectangle skillRect = cpButton1(372, 14);
    private final Rectangle romRect = cpButton1(324, 44);
    private final Rectangle gameRect = cpButton1(372, 44);
    private final Rectangle eraseRect = cpButton2(324, 74);
    private final Rectangle exitRect = cpButton2(324, 98);

    private final SoftwarePane softwarePane;
    private final SkillsPopup skillsPopup;
    private final DiskPopup diskPopup;
    private final RomPopup romPopup;

    public ControlPanelPane(PopupListener l, GameState gs) {
        this.gameState = gs;
        this.listener = l;
        softwarePane = new SoftwarePane(gs);
        softwarePane.setVisible(false);
        skillsPopup = new SkillsPopup(this, gs);
        skillsPopup.setVisible(false);
        skillsPopup.setLayoutY(0);
        skillsPopup.setLayoutX(100);
        diskPopup = new DiskPopup(this, gs);
        diskPopup.setVisible(false);
        diskPopup.setLayoutX(100);
        diskPopup.setLayoutY(0);
        romPopup = new RomPopup(this, gs);
        romPopup.setVisible(false);
        romPopup.setLayoutX(100);
        romPopup.setLayoutY(0);

        ImageView cPanelView = new ImageView(gs.resourceManager.getSprite("CSPANEL_1"));
        getChildren().add(cPanelView);

        getChildren().addAll(
                zoneText, xText, yText,
                cashText,
                invRect, skillRect, romRect, gameRect,
                eraseRect, exitRect,
                softwarePane, skillsPopup, diskPopup, romPopup
        );
        tick();
        initButtons();

    }

    private void updateText() {
        zoneText.setText(String.valueOf(gameState.usingDeck.getZone()));
        xText.setText(String.format("%03d", gameState.usingDeck.getCordX()));
        yText.setText(String.format("%03d", gameState.usingDeck.getCordY()));
        cashText.setText("$" + String.format("%8d", gameState.chipBalance));
    }

    private void initButtons() {
        invRect.setOnMouseClicked((t) -> {
            LOGGER.log(Level.SEVERE, "Cyberspace: Software Button Clicked...");
            softwarePane.softwarePrompt();
            t.consume();
        });
        skillRect.setOnMouseClicked((t) -> {
            LOGGER.log(Level.SEVERE, "Cyberspace: Skill Button Clicked...");
            skillsPopup.setVisible(true);
            t.consume();
        });
        romRect.setOnMouseClicked((t) -> {
            LOGGER.log(Level.SEVERE, "Cyberspace: ROM Button Clicked...");
            romPopup.setVisible(true);
            t.consume();
        });
        gameRect.setOnMouseClicked((t) -> {
            LOGGER.log(Level.SEVERE, "Cyberspace: Game Button Clicked...");
            diskPopup.setVisible(true);
            t.consume();
        });
        eraseRect.setOnMouseClicked((t) -> {
            LOGGER.log(Level.SEVERE, "Cyberspace: Erase Button Clicked...");
            t.consume();
        });
        exitRect.setOnMouseClicked((t) -> {
            LOGGER.log(Level.SEVERE, "Cyberspace: Exit Button Clicked...");
            t.consume();
            listener.popupExit();
        });
    }

    public boolean handleKeyEvent(KeyEvent ke) {
        if (softwarePane.isVisible()) {
            softwarePane.handleKeyEvent(ke);
        } else if (skillsPopup.isVisible()) {
            if (skillsPopup.handleKeyEvent(ke)) {
                skillsPopup.setVisible(false);
            }
        } else if (diskPopup.isVisible()) {
            if (diskPopup.handleKeyEvent(ke)) {
                diskPopup.setVisible(false);
            }
        } else if (romPopup.isVisible()) {
            if (romPopup.handleKeyEvent(ke)) {
                romPopup.setVisible(false);
            }
        } else {

            KeyCode code = ke.getCode();
            ke.consume();
            switch (code) {
                case X, ESCAPE -> {
                    if (softwarePane.isVisible()) {
                        softwarePane.setVisible(false);
                    } else {
                        return true;
                    }
                }
                case I -> { // Inventory/Software/Slots
                    LOGGER.log(Level.SEVERE, "Cyberspace: Software Key Pressed...");
                    // Close other popups.
                    softwarePane.softwarePrompt();
                    skillsPopup.setVisible(false);
                    diskPopup.setVisible(false);
                    romPopup.setVisible(false);
                }
                case R -> { // ROM
                    LOGGER.log(Level.SEVERE, "Cyberspace: Skills Key Pressed...");
                    // Close other popups.
                    skillsPopup.setVisible(false);
                    softwarePane.setVisible(false);
                    diskPopup.setVisible(false);
                    romPopup.setVisible(true);
                }
                case S -> { // Skills
                    LOGGER.log(Level.SEVERE, "Cyberspace: Skills Key Pressed...");
                    // Close other popups.
                    skillsPopup.setVisible(true);
                    softwarePane.setVisible(false);
                    diskPopup.setVisible(false);
                    romPopup.setVisible(false);
                }
                case D -> { // Disk
                    LOGGER.log(Level.SEVERE, "Cyberspace: Disk Key Pressed...");
                    // Close other popups.
                    skillsPopup.setVisible(false);
                    softwarePane.setVisible(false);
                    diskPopup.setVisible(true);
                    romPopup.setVisible(false);
                }
                case E -> { // Erase

                }
            }

        }
        return false;
    }

    private Text cpText(String val, int x, int y) {
        Text t = new Text(val);
        t.setLayoutX(x);
        t.setLayoutY(y);
        t.getTransforms().add(new Scale(1.5, 1.0));

        return t;
    }

    private Rectangle cpButton1(int x, int y) {
        Rectangle r = new Rectangle(BTN1_SIZE, BTN1_SIZE, BTN_COLOR);
        r.setLayoutX(x);
        r.setLayoutY(y);
        r.setOpacity(0.01);

        return r;
    }

    private Rectangle cpButton2(int x, int y) {
        Rectangle r = new Rectangle(BTN2_W, BTN2_H, BTN_COLOR);
        r.setLayoutX(x);
        r.setLayoutY(y);
        r.setOpacity(0.01);

        return r;
    }

    public final void tick() {
        updateText();
    }

    @Override
    public void popupExit() {
        softwarePane.setVisible(false);
        skillsPopup.setVisible(false);
        diskPopup.setVisible(false);
        romPopup.setVisible(false);
    }

    @Override
    public void popupExit(RoomMode.Popup newPopup) {
        // Not supported
    }

//    private void softwarePrompt() {
//        LOGGER.log(Level.SEVERE, "Show Deck Popup Software Prompt");
//        mode = Mode.SOFTWARE;
//
//        softwarePane.setVisible(true);
//        softwarePane.getChildren().clear();
//        Text softwareHeading = new Text("Software");
//        Text exitButton = new Text("exit");
//        Text prevButton = new Text("prev");
//        Text nextButton = new Text("next");
//        TextFlow tf = PopupPane.textFlow(softwareHeading);
//        //TextFlow tf = new TextFlow(softwareHeading);
//        //tf.setLineSpacing(LINE_SPACING);
//        tf.setPrefSize(SOFT_LIST_WIDTH, SOFT_LIST_HEIGHT);
//        //tf.setPadding(new Insets(4, 0, 0, 16));
//
//        HBox navBox = new HBox(prevButton, exitButton, nextButton);
//        navBox.setSpacing(20);
//        navBox.setPadding(new Insets(6, 0, 0, 32));
//
//        DeckItem deck = gameState.usingDeck;
//
//        for (int i = 0; i < SOFT_LIST_SIZE; i++) {
//            try {
//                Warez w = deck.softwarez.get(slotBase + i);
//                Text itemText = new Text("\n" + (i + 1) + ". " + w.getMenuString());
//                tf.getChildren().add(itemText);
//
//                // Add onMouseClick()
//                itemText.setOnMouseClicked((t) -> {
//                    useSoftware(w);
//                });
//            } catch (IndexOutOfBoundsException ex) {
//                tf.getChildren().add(new Text("\n"));
//            }
//        }
//        tf.getChildren().add(new Text("\n"));
//        tf.getChildren().add(navBox);
//        prevButton.setVisible(slotBase >= SOFT_LIST_SIZE);
//        nextButton.setVisible(slotBase + SOFT_LIST_SIZE < deck.softwarez.size());
//
//        softwarePane.getChildren().add(makeBox(tf));
//
//        if (prevButton.isVisible()) {
//            prevButton.setOnMouseClicked((t) -> {
//                slotBase -= SOFT_LIST_SIZE;
//                softwarePrompt();
//            });
//        }
//        if (nextButton.isVisible()) {
//            nextButton.setOnMouseClicked((t) -> {
//                slotBase += SOFT_LIST_SIZE;
//                t.consume();
//                softwarePrompt();
//            });
//        }
//        exitButton.setOnMouseClicked((t) -> {
//            LOGGER.log(Level.SEVERE, "Cyberspace: Exit Software Menu (via mouse click).");
//            t.consume();
//            softwarePane.setVisible(false);
//            //listener.popupExit();
//        });
//
//    }
//    private static Pane smallWindow() {
//        Pane p = new Pane();
//
//        p.setPrefSize(SOFT_LIST_WIDTH, SOFT_LIST_HEIGHT);
//        p.setMinSize(SOFT_LIST_WIDTH, SOFT_LIST_HEIGHT);
//        p.setMaxSize(SOFT_LIST_WIDTH, SOFT_LIST_HEIGHT);
//        p.setLayoutX(SOFT_LIST_X);
//        p.setLayoutY(SOFT_LIST_Y);
//        p.setId("neuro-popup");
//
//        return p;
//    }
//
//    private void useSoftware(Warez w) {
//        LOGGER.log(Level.SEVERE, "Cyberspace: Use Software: " + w.item.itemName);
//        String useReponse = w.use(gameState);
//        if (!useReponse.equals(Warez.USE_OK)) {
//            displayResponse(useReponse);
//        } else {
//            gameState.usingDeck.setCurrentWarez(w);
//            LOGGER.log(Level.SEVERE, "Use Software: " + w.toString());
//        }
//    }
//
//    protected VBox makeBox(Node... nodes) {
//        VBox box = new VBox(nodes);
//        box.setSpacing(0);
//        box.getTransforms().add(new Scale(PopupPane.TEXT_SCALE, 1.0));
//        box.setMinWidth(getPrefWidth());
//        box.setPrefWidth(getPrefWidth());
//        box.setMinHeight(getPrefHeight());
//        box.setMaxHeight(getPrefWidth());
//        box.setPadding(new Insets(0, 0, 0, 10));
//
//        return box;
//    }
//
//    private void displayResponse(String response) {
//        LOGGER.log(Level.SEVERE, "Show Deck use() response");
//
//        mode = Mode.RESPONSE;
//
//        softwarePane.getChildren().clear();
//        softwarePane.setVisible(true);
//        Text heading = new Text(response);
//
//        softwarePane.getChildren().add(makeBox(PopupPane.textFlow(heading)));
//
//        softwarePane.setOnMouseClicked((t) -> {
//            // Allow user to view response and go back to menu when clicked.
//            setOnMouseClicked(null);
//            softwarePrompt();
//        });
//    }
}
