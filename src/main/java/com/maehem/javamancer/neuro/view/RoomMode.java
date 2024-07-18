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

import com.maehem.javamancer.neuro.model.Room;
import com.maehem.javamancer.neuro.view.room.RoomPane;
import java.util.logging.Level;
import javafx.scene.image.ImageView;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class RoomMode extends NeuroModePane {

    protected Room room;

    public RoomMode(NeuroModePaneListener listener, ResourceManager resourceManager, Room room) {
        super(listener, resourceManager);
        this.room = room;

        ImageView cPanelView = new ImageView(getResourceManager().getSprite("NEURO_1"));
        RoomPane roomPane = new RoomPane(resourceManager, room);
        getChildren().addAll(cPanelView, roomPane);

        setOnMouseClicked((t) -> {
            handleMouseClick(t.getX(), t.getY());
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
        } else if (y > 334 && y < 356 && x > 224 && x < 256) {
            // disk
            LOGGER.log(Level.SEVERE, "User clicked Date Status.");
        } else if (y > 334 && y < 356 && x > 256 && x < 288) {
            // disk
            LOGGER.log(Level.SEVERE, "User clicked Time Status.");
        } else if (y > 356 && y < 378 && x > 224 && x < 256) {
            // disk
            LOGGER.log(Level.SEVERE, "User clicked Credits Status.");
        } else if (y > 356 && y < 378 && x > 256 && x < 288) {
            // disk
            LOGGER.log(Level.SEVERE, "User clicked Constitution Status.");
        }
    }
}
