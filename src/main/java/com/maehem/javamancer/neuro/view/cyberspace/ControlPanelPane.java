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
import com.maehem.javamancer.neuro.model.database.Database;
import com.maehem.javamancer.neuro.view.PopupListener;
import com.maehem.javamancer.neuro.view.RoomMode;
import com.maehem.javamancer.neuro.view.popup.CyberspacePopup;
import com.maehem.javamancer.neuro.view.popup.DiskPopup;
import com.maehem.javamancer.neuro.view.popup.RomPopup;
import com.maehem.javamancer.neuro.view.popup.SkillsPopup;
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

    private final DamageGauge playerDamage = new DamageGauge(DamageGauge.Orientation.VERT);
    private final DamageGauge opponentDamage = new DamageGauge(DamageGauge.Orientation.HORIZ);

    private final SoftwarePane softwarePane;
    private final YesNoPane yesNoPane;
    private final SkillsPopup skillsPopup;
    private final DiskPopup diskPopup;
    private final RomPopup romPopup;
    private final VisualPane visualPane;

    public ControlPanelPane(PopupListener l, GameState gs, VisualPane visualPane) {
        this.gameState = gs;
        this.listener = l;
        this.visualPane = visualPane;

        softwarePane = new SoftwarePane(gs);
        softwarePane.setVisible(false);
        yesNoPane = new YesNoPane(gs);
        yesNoPane.setVisible(false);
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

        playerDamage.setLayoutX(439);
        playerDamage.setLayoutY(101);
        opponentDamage.setLayoutX(482);
        opponentDamage.setLayoutY(31);

        ImageView cPanelView = new ImageView(gs.resourceManager.getSprite("CSPANEL_1"));
        getChildren().add(cPanelView);

        getChildren().addAll(
                zoneText, xText, yText,
                cashText,
                invRect, skillRect, romRect, gameRect,
                playerDamage, opponentDamage,
                eraseRect, exitRect,
                softwarePane, yesNoPane,
                skillsPopup, diskPopup, romPopup
        );
        tick();
        initButtons();

    }

    public void updateText() {
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
        } else if (yesNoPane.isVisible()) {
            if (!yesNoPane.handleKeyEvent(ke) && gameState.databaseBattle) {
                // User pressed Y
                configState(CyberspacePopup.State.BATTLE);
            }
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
            switch (code) {
                case X, ESCAPE -> {
                    ke.consume();
                    if (softwarePane.isVisible()) {
                        softwarePane.setVisible(false);
                    } else {
                        return true;
                    }
                }
                case I -> { // Inventory/Software/Slots
                    LOGGER.log(Level.SEVERE, "Cyberspace: Software Key Pressed...");
                    ke.consume();
                    // Close other popups.
                    softwarePane.softwarePrompt();
                    skillsPopup.setVisible(false);
                    diskPopup.setVisible(false);
                    romPopup.setVisible(false);
                }
                case R -> { // ROM
                    LOGGER.log(Level.SEVERE, "Cyberspace: Skills Key Pressed...");
                    ke.consume();
                    // Close other popups.
                    skillsPopup.setVisible(false);
                    softwarePane.setVisible(false);
                    diskPopup.setVisible(false);
                    romPopup.setVisible(true);
                }
                case S -> { // Skills
                    LOGGER.log(Level.SEVERE, "Cyberspace: Skills Key Pressed...");
                    ke.consume();
                    // Close other popups.
                    skillsPopup.setVisible(true);
                    softwarePane.setVisible(false);
                    diskPopup.setVisible(false);
                    romPopup.setVisible(false);
                }
                case D -> { // Disk
                    LOGGER.log(Level.SEVERE, "Cyberspace: Disk Key Pressed...");
                    ke.consume();
                    // Close other popups.
                    skillsPopup.setVisible(false);
                    softwarePane.setVisible(false);
                    diskPopup.setVisible(true);
                    romPopup.setVisible(false);
                }
                case E -> { // Erase
                    ke.consume();

                }
            }

        }

        if (!ke.isConsumed()) {
            visualPane.handleKeyEvent(ke);
        } else {
            LOGGER.log(Level.FINE, "Cyberspace CP: Key event consumed, not sent to VisualPane.");
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
        updateGauges();

        if (gameState.databaseArrived) {
            gameState.databaseArrived = false;
            yesNoPane.setVisible(true);
        }
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

    public void configState(CyberspacePopup.State state) {

    }

    private void updateGauges() {
        Database db = gameState.database;
        if (db != null) {
            // DB gauge
            int dmgPercent = 100 * (db.ICE_MAX - db.getIce()) / db.ICE_MAX;
            opponentDamage.setValue(dmgPercent);

            // Player Gauge
            dmgPercent = 100 * (gameState.CONSTITUTION_MAX - gameState.constitution) / gameState.CONSTITUTION_MAX;
            playerDamage.setValue(dmgPercent);

        } else {
            opponentDamage.setValue(0);
            playerDamage.setValue(0);
        }
    }

}
