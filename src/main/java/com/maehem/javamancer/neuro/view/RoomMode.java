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

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.Room;
import com.maehem.javamancer.neuro.model.TextResource;
import com.maehem.javamancer.neuro.view.pax.PaxPopupPane;
import com.maehem.javamancer.neuro.view.room.RoomDescriptionPane;
import com.maehem.javamancer.neuro.view.room.RoomPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.DIGIT1;
import static javafx.scene.input.KeyCode.I;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class RoomMode extends NeuroModePane implements PopupListener {

    public static final Logger LOGGER = Logging.LOGGER;

    private enum Status {
        DATE, TIME, CREDIT, CONSTITUTION
    }

    private enum Popup {
        INVENTORY, PAX, TALK, SKILLS, ROM, DISK
    }

    private static final int ROW_1_Y = 292;
    private static final int ROW_2_Y = 340;
    private static final int COL_1_X = 32;
    private static final int COL_2_X = 80;
    private static final int COL_3_X = 128;
    private static final int BUTTON_W = 42;
    private static final int BUTTON_H = 42;

    private static final int STAT_1_X = 224;
    private static final int STAT_2_X = 258;
    private static final int STAT_1_Y = 336;
    private static final int STAT_2_Y = 358;
    private static final int STAT_W = 28;
    private static final int STAT_H = 18;

    private final RoomPane roomPane;
    private PopupPane popup = null;
    private final TextResource roomText;
    protected Room room;
    private final Text statusText = new Text("* STATUS *");
    private final Text scrollHint = new Text("+");

    private final Rectangle inventoryButton = button(COL_1_X, ROW_1_Y, BUTTON_W, BUTTON_H);
    private final Rectangle paxButton = button(COL_2_X, ROW_1_Y, BUTTON_W, BUTTON_H);
    private final Rectangle talkButton = button(COL_3_X, ROW_1_Y, BUTTON_W, BUTTON_H);
    private final Rectangle skillsButton = button(COL_1_X, ROW_2_Y, BUTTON_W, BUTTON_H);
    private final Rectangle romButton = button(COL_2_X, ROW_2_Y, BUTTON_W, BUTTON_H);
    private final Rectangle diskButton = button(COL_3_X, ROW_2_Y, BUTTON_W, BUTTON_H);

    private final Rectangle dateButton = button(STAT_1_X, STAT_1_Y, STAT_W, STAT_H);
    private final Rectangle timeButton = button(STAT_2_X, STAT_1_Y, STAT_W, STAT_H);
    private final Rectangle credButton = button(STAT_1_X, STAT_2_Y, STAT_W, STAT_H);
    private final Rectangle constButton = button(STAT_2_X, STAT_2_Y, STAT_W, STAT_H);

    private final RoomDescriptionPane roomDescriptionPane;
    private Status statusMode = Status.DATE;
    private boolean firstTime = true;

    public RoomMode(NeuroModePaneListener listener, ResourceManager resourceManager, GameState gameState, Room room) {
        super(listener, resourceManager, gameState);
        this.room = room;
        if (gameState.roomIsVisited[room.getIndex()]) {
            firstTime = false;
        }

        roomText = resourceManager.getText(room);

        ImageView cPanelView = new ImageView(getResourceManager().getSprite("NEURO_1"));
        roomPane = new RoomPane(resourceManager, room);

        roomDescriptionPane = new RoomDescriptionPane();

        // Plus (+) character appears if user has not scrolled to bottom of
        // scene description.
        scrollHint.setLayoutX(616);
        scrollHint.setLayoutY(388);
        scrollHint.setScaleX(1.333);

        getChildren().addAll(
                cPanelView, roomPane, statusText, roomDescriptionPane, scrollHint,
                inventoryButton, paxButton, talkButton,
                skillsButton, romButton, diskButton,
                dateButton, timeButton, credButton, constButton
        );

        statusText.setId("neuro-status");
        statusText.setLayoutX(190);
        statusText.setLayoutY(313);
        statusText.getTransforms().add(new Scale(1.333, 1.0));

        setOnMouseClicked((t) -> {
            handleMouseClick(t.getX(), t.getY());
        });

        if (firstTime) {
            roomDescriptionPane.setText(roomText.getDescription());
        } else {
            roomDescriptionPane.setText(roomText.getShortDescription());
        }

        initButtonHandlers();
        initKeyboardEvents();

        Platform.runLater(() -> {
            updateStatus();
            roomPane.updatePlayerPosition(gameState, gameState.roomPosX, gameState.roomPosY);
        });

        roomDescriptionPane.vvalueProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    LOGGER.log(Level.FINEST, "Description Scroll: " + newValue);
                    scrollHint.setVisible(newValue.doubleValue() != 1.0);
                });

        setFocusTraversable(true);
        requestFocus();
    }

    private void initButtonHandlers() {
        inventoryButton.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "User clicked Inventory.");
            showPopup(Popup.INVENTORY);
        });
        paxButton.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "User clicked PAX.");
            showPopup(Popup.PAX);
        });
        talkButton.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "User clicked Talk.");
            showPopup(Popup.TALK);
        });
        skillsButton.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "User clicked Skills.");
            showPopup(Popup.SKILLS);
        });
        romButton.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "User clicked ROM.");
            showPopup(Popup.ROM);
        });
        diskButton.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "User clicked Disk.");
            showPopup(Popup.DISK);
        });

        dateButton.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "User clicked Date.");
            statusMode = Status.DATE;
            updateStatus();
        });
        timeButton.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "User clicked Time.");
            statusMode = Status.TIME;
            updateStatus();
        });
        credButton.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "User clicked Credits.");
            statusMode = Status.CREDIT;
            updateStatus();
        });
        constButton.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "User clicked Constitution.");
            statusMode = Status.CONSTITUTION;
            updateStatus();
        });
    }

    private void initKeyboardEvents() {
        setOnKeyPressed((keyEvent) -> {
            if (popup != null) {
                if (popup.handleKeyEvent(keyEvent)) { // Returns true on X or ESC.
                    popupExit();
                }
            } else {
                switch (keyEvent.getCode()) {
                    case I -> {
                        LOGGER.log(Level.FINER, "User pressed Inventory Key.");
                        showPopup(Popup.INVENTORY);
                    }
                    case P -> {
                        LOGGER.log(Level.FINER, "User pressed PAX Key.");
                        showPopup(Popup.PAX);
                    }
                    case T -> {
                        LOGGER.log(Level.FINER, "User pressed Talk Key.");
                        showPopup(Popup.TALK);
                    }
                    case S -> {
                        LOGGER.log(Level.FINER, "User pressed Skills Key.");
                        showPopup(Popup.SKILLS);
                    }
                    case R -> {
                        LOGGER.log(Level.FINER, "User pressed ROM Key.");
                        showPopup(Popup.ROM);
                    }
                    case D -> {
                        LOGGER.log(Level.FINER, "User pressed Disk Key.");
                        showPopup(Popup.DISK);
                    }
                    case DIGIT1 -> {
                        LOGGER.log(Level.FINER, "User pressed 1 Key.");
                    }
                    case DIGIT2 -> {
                        LOGGER.log(Level.FINER, "User pressed 2 Key.");
                    }
                    case DIGIT3 -> {
                        LOGGER.log(Level.FINER, "User pressed 3 Key.");
                    }
                    case DIGIT4 -> {
                        LOGGER.log(Level.FINER, "User pressed 4 Key.");
                    }
                }
            }
        });
    }

    @Override
    public void tick() {
        if (firstTime) {
            // We are scrolling the description and may not yet be done
            // incrementing through it.

            // For now, no scrolling like the original game. Let the
            // player use the mouse scroll.
        }
        // ???
        // if( just loaded) {
        //     * First view, scroll room description in lower right frame.
        //     pause and scroll more if text too long.

        //     * Second view (room visited). Show second description.
        // }
        // then allow
        roomPane.tick(getGameState());
    }

    private void showPopup(Popup pop) {
        switch (pop) {
            case INVENTORY -> {
                popup = new InventoryPopup(this, getGameState());
            }
            case PAX -> {
                popup = new PaxPopupPane(this, getGameState(), getResourceManager());
            }
            case TALK -> {
                popup = new DialogPopup(this, getGameState(), getResourceManager());
            }
            case SKILLS -> {
                popup = new SkillsPopup(this, getGameState());
            }
            case ROM -> {
                popup = new RomPopup(this, getGameState());
            }
            case DISK -> {
                popup = new DiskPopup(this, getGameState());
            }
        }
        getChildren().add(popup);
        getGameState().pause = true;
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
                statusText.setText(" " + gs.getDateString());
            }
            case TIME -> {
                String hour = String.format("%02d", gs.timeHour);
                String minute = String.format("%02d", gs.timeMinute);
                String time = String.format("%1$10s", hour + ":" + minute);
                statusText.setText(time);
            }
            case CREDIT -> {
                statusText.setText("$" + String.format("%1$9s", String.valueOf(getGameState().chipBalance)));
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
            LOGGER.log(Level.SEVERE, "User clicked roomPane at: {0},{1}", new Object[]{x, y});
            roomPane.mouseClick(x - RoomPane.PANE_X, y - RoomPane.PANE_Y, getGameState());
        }
    }

    private static Rectangle button(int x, int y, int w, int h) {
        Rectangle r = new Rectangle(x, y, w, h);
        r.setFill(Color.MAGENTA);
        r.setBlendMode(BlendMode.HARD_LIGHT);
        r.setOpacity(0.01);
        r.addEventHandler(MouseEvent.MOUSE_PRESSED, (t) -> {
            r.setOpacity(0.4);
        });
        r.addEventHandler(MouseEvent.MOUSE_RELEASED, (t) -> {
            r.setOpacity(0.01);
        });
        return r;
    }

    @Override
    public void popupExit() {
        popup.setVisible(false);
        getChildren().remove(popup);
        popup = null;
        getGameState().pause = false;
    }

}
