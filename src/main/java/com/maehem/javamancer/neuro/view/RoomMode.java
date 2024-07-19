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
import com.maehem.javamancer.neuro.model.Room;
import com.maehem.javamancer.neuro.view.room.RoomPane;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class RoomMode extends NeuroModePane {

    private enum Status {
        DATE, TIME, CREDIT, CONSTITUTION
    }

    protected Room room;
    private final Text statusText = new Text("* STATUS *");

    private Status statusMode = Status.DATE;

    public RoomMode(NeuroModePaneListener listener, ResourceManager resourceManager, GameState gameState, Room room) {
        super(listener, resourceManager, gameState);
        this.room = room;

        ImageView cPanelView = new ImageView(getResourceManager().getSprite("NEURO_1"));
        RoomPane roomPane = new RoomPane(resourceManager, room);
        getChildren().addAll(cPanelView, roomPane, statusText);

        statusText.setId("neuro-status");
        statusText.setLayoutX(198);
        statusText.setLayoutY(314);

        setOnMouseClicked((t) -> {
            handleMouseClick(t.getX(), t.getY());
        });

        Platform.runLater(() -> {
            updateStatus();
        });
    }

    @Override
    public void tick() {
        // ???
    }

    @Override
    public void destroy() {
        room = null;
    }

    @Override
    public void updateStatus() {
        GameState gs = getGameState();
        switch (statusMode) {
            case DATE -> {
                String month = String.format("%02d", gs.dateMonth);
                String day = String.format("%02d", gs.dateDay);
                String year = String.format("%04d", gs.dateYear).substring(2);
                statusText.setText(" " + month + "/" + day + "/" + year);
            }
            case TIME -> {
                String hour = String.format("%02d", gs.timeHour);
                String minute = String.format("%02d", gs.timeMinute);
                String time = String.format("%1$10s", hour + ":" + minute);
                statusText.setText(time);
            }
            case CREDIT -> {
                statusText.setText("$" + String.format("%1$9s", String.valueOf(getGameState().cash)));
            }
            case CONSTITUTION -> {
                statusText.setText(String.format("%1$10s", String.valueOf(gs.constitution)));
            }
        }
    }

    private void handleMouseClick(double x, double y) {
        LOGGER.log(Level.SEVERE, "Mouse Click at: {0},{1}", new Object[]{x, y});
        if ((y > 16 && y < 240) && (x > 16 && x < 624)) {
            // User clicked in room scene.
            LOGGER.log(Level.SEVERE, "User clicked in scene at: {0},{1}", new Object[]{x, y});
        } else if (y > 292 && y < 336 && x > 32 && x < 81) {
            // inventory
            LOGGER.log(Level.SEVERE, "User clicked Inventory.");
        } else if (y > 292 && y < 336 && x > 70 && x < 108) {
            // pax
            LOGGER.log(Level.SEVERE, "User clicked PAX.");
        } else if (y > 292 && y < 336 && x > 109 && x < 172) {
            // talk
            LOGGER.log(Level.SEVERE, "User clicked Talk.");
        } else if (y > 336 && y < 380 && x > 32 && x < 81) {
            // skills
            LOGGER.log(Level.SEVERE, "User clicked Skills.");
        } else if (y > 336 && y < 380 && x > 70 && x < 108) {
            // rom
            LOGGER.log(Level.SEVERE, "User clicked ROM.");
        } else if (y > 336 && y < 380 && x > 109 && x < 172) {
            // disk
            LOGGER.log(Level.SEVERE, "User clicked Disk.");
        } else if (y > 334 && y < 356 && x > 224 && x < 256) { // Date Status
            LOGGER.log(Level.SEVERE, "User clicked Date Status.");
            statusMode = Status.DATE;
            updateStatus();
        } else if (y > 334 && y < 356 && x > 256 && x < 288) { // Time Status
            LOGGER.log(Level.SEVERE, "User clicked Time Status.");
            statusMode = Status.TIME;
            updateStatus();
        } else if (y > 356 && y < 378 && x > 224 && x < 256) { // Credits Status
            LOGGER.log(Level.SEVERE, "User clicked Credits Status.");
            statusMode = Status.CREDIT;
            updateStatus();
        } else if (y > 356 && y < 378 && x > 256 && x < 288) { // Constitution Status
            LOGGER.log(Level.SEVERE, "User clicked Constitution Status.");
            statusMode = Status.CONSTITUTION;
            updateStatus();
        }
    }
}
