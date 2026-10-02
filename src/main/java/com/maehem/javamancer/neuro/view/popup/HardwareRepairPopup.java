/*
 * MIT License
 *
 * Copyright (c) 2024 Mark J. Koch ( @maehem on GitHub )
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
import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.item.Item.Catalog;
import com.maehem.javamancer.neuro.model.skill.HardwareRepairSkill;
import com.maehem.javamancer.neuro.model.skill.Skill;
import com.maehem.javamancer.neuro.view.PopupListener;
import com.maehem.javamancer.neuro.view.SoundEffectsManager;
import static java.util.logging.Level.INFO;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class HardwareRepairPopup extends SmallPopupPane {

    public HardwareRepairPopup(PopupListener l, GameState gs) {
        super(l, gs);
        //doRepairAction();
    }

    public void repairItem(Item item) {
        LOGGER.log(INFO, "Repair Item invoked.");
        getChildren().clear();
        if (item != null) {
            LOGGER.log(INFO, "Present Hardware Repair Popup to repair item: {0}", item.getName());
        } else {
            // No item. Nothing to do.
            listener.popupExit();
            return;
        }
        Text heading = new Text("    Hardware Repair");
        TextFlow tf = new TextFlow();
        tf.setLineSpacing(LINE_SPACING - 1);
        tf.setMinHeight(76);
        Text spacingText = new Text("\n");
        Text responseText = new Text();
        Text spacebarText = new Text("\n  Button or [space]");

        Skill repairSkill = gameState.getInstalledSkill(Catalog.HARDWAREREPAIR);

        if (repairSkill instanceof HardwareRepairSkill rs) {
            switch (rs.doRepair(item)) {
                case SUCCESS -> {
                    // skill level OK. Do repair.
                    responseText.setText(item.getName() + "\nDamage Repaired.\nHardware OK\n");
                    // Play good sound.
                    gameState.resourceManager.soundFxManager.playTrack(SoundEffectsManager.Sound.TRANSMIT);
                }
                case LOW_SKILL -> {
                    responseText.setText(item.getName() + "\nUnable to repair damage.\n");
                    gameState.resourceManager.soundFxManager.playTrack(SoundEffectsManager.Sound.DENIED);
                }
                case NOT_HARDWARE -> {
                    responseText.setText(item.getName() + "\nItem not repairable.\n");
                }
                case NO_BUGS -> {
                    responseText.setText(item.getName() + "\nHardware has no bugs.\n");
                }
                case NO_ITEM -> {
                    listener.popupExit();
                }
            }
        }

        tf.getChildren().addAll(spacingText, responseText, spacebarText);

        addBox(heading, tf);

        setOnMouseClicked((t) -> {
            listener.popupExit();
        });

        Platform.runLater(() -> {
            requestLayout();
        });
    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        LOGGER.log(INFO, "Key press: {0}", code.name());

        switch (code) {
            case X,SPACE,ESCAPE -> {
                listener.popupExit();
            }
        }

        return false;
    }

    @Override
    public void cleanup() {
    }
}
