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
package com.maehem.javamancer.neuro.view.room;

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.Room;
import com.maehem.javamancer.neuro.model.RoomBounds;
import com.maehem.javamancer.neuro.model.RoomBounds.Door;
import com.maehem.javamancer.neuro.model.RoomPosition;
import com.maehem.javamancer.neuro.view.ResourceManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class RoomPane extends Pane {

    public static final Logger LOGGER = Logging.LOGGER;

    public static final double PANE_X = 15.5;
    public static final double PANE_Y = 19;

    private static final int DOOR_THICK = 12;
    private final Rectangle boundsRect;
    private final Rectangle topDoor;
    private final Rectangle rightDoor;
    private final Rectangle bottomDoor;
    private final Rectangle leftDoor;

    private int walkToX = 0;
    private int walkToY = 0;

    private final Room room;
    private final PlayerNode player;
    private final Rectangle playerFeet = new Rectangle(4, 4);
    private final Group playerGroup;
    private final double stepSizeRL = 8.0;
    private final double stepSizeTA = 4.0;
    private final double BOUNDS_OPACITY = 0.5;

    public RoomPane(ResourceManager resourceManager, Room room) {
        this.room = room;
        ImageView roomView = new ImageView(resourceManager.getBackdrop(room));
        setLayoutX(PANE_X);
        setLayoutY(PANE_Y);

        RoomBounds bounds = RoomBounds.get(room);
        boundsRect = initBoundsRect(bounds);
        topDoor = initDoorTop(bounds);
        rightDoor = initDoorRight(bounds);
        bottomDoor = initDoorBottom(bounds);
        leftDoor = initDoorLeft(bounds);

        getChildren().addAll(roomView, boundsRect);
        if (topDoor != null) {
            getChildren().add(topDoor);
        }
        if (rightDoor != null) {
            getChildren().add(rightDoor);
        }
        if (bottomDoor != null) {
            getChildren().add(bottomDoor);
        }
        if (leftDoor != null) {
            getChildren().add(leftDoor);
        }

        player = new PlayerNode(resourceManager);
        RoomPosition rp = RoomPosition.get(room);
        playerFeet.setTranslateX(-playerFeet.getWidth() / 2);
        playerFeet.setTranslateY(-playerFeet.getHeight() / 2);
        playerGroup = new Group(player, playerFeet);
        playerGroup.setLayoutX(rp.playerX);
        playerGroup.setLayoutY(rp.playerY);

        getChildren().add(playerGroup);
    }

    private Rectangle initBoundsRect(RoomBounds rb) {
        Rectangle r = new Rectangle(
                rb.lBound, rb.tBound,
                rb.rBound - rb.lBound, rb.bBound - rb.tBound);
        r.setFill(Color.YELLOW);
        r.setOpacity(BOUNDS_OPACITY);
        r.setStroke(null);

        return r;
    }

    private Rectangle initDoorTop(RoomBounds rb) {
        if (rb.tx != 0 && rb.tw != 0) {
            Rectangle r = new Rectangle(rb.tx, rb.tBound, rb.tw, DOOR_THICK);
            r.setFill(Color.RED);
            r.setOpacity(BOUNDS_OPACITY);
            r.setStroke(null);
            return r;
        }

        return null;
    }

    private Rectangle initDoorRight(RoomBounds rb) {
        if (rb.ry != 0 && rb.rw != 0) {
            Rectangle r = new Rectangle(rb.rBound, rb.ry, DOOR_THICK, rb.rw);
            r.setFill(Color.RED);
            r.setOpacity(BOUNDS_OPACITY);
            r.setStroke(null);
            return r;
        }

        return null;
    }

    private Rectangle initDoorBottom(RoomBounds rb) {
        if (rb.bx != 0 && rb.bw != 0) {
            Rectangle r = new Rectangle(rb.bx, rb.bBound - DOOR_THICK, rb.bw, DOOR_THICK);
            r.setFill(Color.RED);
            r.setOpacity(BOUNDS_OPACITY);
            r.setStroke(null);
            return r;
        }

        return null;
    }

    private Rectangle initDoorLeft(RoomBounds rb) {
        if (rb.ly != 0 && rb.lw != 0) {
            Rectangle r = new Rectangle(rb.tBound, rb.ly, DOOR_THICK, rb.lw);
            r.setFill(Color.RED);
            r.setOpacity(BOUNDS_OPACITY);
            r.setStroke(null);
            return r;
        }

        return null;
    }

    public void tick(GameState gs) {
        if (processWalk(gs)) {
            handleDoors(gs);
        }
    }

    private boolean processWalk(GameState gs) {
        boolean playerMoved = false; // Helps door checker not run every frame.
        if (!(walkToX == 0 && walkToY == 0)) {
            int newPosX = gs.roomPosX;
            int newPosY = gs.roomPosY;

            if (walkToX != 0) {
                if (Math.abs(walkToX - gs.roomPosX) < stepSizeRL) {
                    walkToX = 0;
                    walkToY = 0;
                    //LOGGER.log(Level.SEVERE, "End walking L-R");
                    return true;
                }
                if (walkToX > gs.roomPosX) {
                    newPosX += stepSizeRL;
                    player.setDirection(PlayerNode.Direction.RIGHT);
                } else if (walkToX < gs.roomPosX) {
                    newPosX -= stepSizeRL;
                    player.setDirection(PlayerNode.Direction.LEFT);
                }
                player.step();
                playerMoved = true;
            } else {
                if (Math.abs(walkToY - gs.roomPosY) < stepSizeTA) {
                    walkToX = 0;
                    walkToY = 0;
                    //LOGGER.log(Level.SEVERE, "End walking T-A");
                    return true;
                }
                if (walkToY > gs.roomPosY) {
                    newPosY += stepSizeTA;
                    player.setDirection(PlayerNode.Direction.TOWARD);
                } else {
                    newPosY -= stepSizeTA;
                    player.setDirection(PlayerNode.Direction.AWAY);
                }
                player.step();
                playerMoved = true;
            }
            if (!updatePlayerPosition(gs, newPosX, newPosY)) {
                // Player hit something. Stop waling.
                walkToX = 0;
                walkToY = 0;
                playerMoved = false;
            }
        }
        return playerMoved;
    }

    private void handleDoors(GameState gs) {
        if (topDoor != null
                && Shape.intersect(playerFeet, topDoor).getBoundsInLocal().getWidth() != -1) {
            gs.useDoor = Door.TOP;
        } else if (rightDoor != null
                && Shape.intersect(playerFeet, rightDoor).getBoundsInLocal().getWidth() != -1) {
            gs.useDoor = Door.RIGHT;
        } else if (bottomDoor != null
                && Shape.intersect(playerFeet, bottomDoor).getBoundsInLocal().getWidth() != -1) {
            gs.useDoor = Door.BOTTOM;
        } else if (leftDoor != null
                && Shape.intersect(playerFeet, leftDoor).getBoundsInLocal().getWidth() != -1) {
            gs.useDoor = Door.LEFT;
        } else {
            gs.useDoor = Door.NONE;
        }
        LOGGER.log(Level.SEVERE, "Door State: {0}", gs.useDoor.name());
    }

    public boolean updatePlayerPosition(GameState gs, int newPosX, int newPosY) {

        boolean retVal = true;
        playerGroup.setLayoutX(newPosX);
        playerGroup.setLayoutY(newPosY);
        if (Shape.intersect(playerFeet, boundsRect).getBoundsInLocal().getWidth() != -1) {
            // Inside room - keep change.
            gs.roomPosX = newPosX;
            gs.roomPosY = newPosY;
            retVal = true;
        } else {
            // Out of bounds. Move back.
            LOGGER.log(Level.SEVERE, "Bumped Wall. No moving.");
            playerGroup.setLayoutX(gs.roomPosX);
            playerGroup.setLayoutY(gs.roomPosY);
            retVal = false;
        }
        LOGGER.log(Level.SEVERE, "Player Position: {0},{1}", new Object[]{gs.roomPosX, gs.roomPosY});
        return retVal;
    }

    public void mouseClick(double x, double y, GameState gs) {
        double absX = Math.abs(x - gs.roomPosX);
        double absY = Math.abs(y - gs.roomPosY);
        if (absX > 6 && absX > absY) {
            LOGGER.log(Level.SEVERE, "Init Walk L-R");
            walkToX = (int) x;
            walkToY = 0;
        } else if (absY > 6) {
            LOGGER.log(Level.SEVERE, "Init Walk T-A");
            walkToX = 0;
            walkToY = (int) y;
        }
        LOGGER.log(Level.SEVERE, "Walk to: {0},{1}", new Object[]{walkToX, walkToY});
    }
}
