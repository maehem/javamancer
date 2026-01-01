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
import com.maehem.javamancer.neuro.model.TextResource;
import com.maehem.javamancer.neuro.model.item.DeckItem;
import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.item.SkillItem;
import com.maehem.javamancer.neuro.model.item.SoftwareItem;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.LONG_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.SHORT_DESC;
import com.maehem.javamancer.neuro.model.room.Room;
import com.maehem.javamancer.neuro.model.room.RoomBounds;
import com.maehem.javamancer.neuro.model.room.RoomExtras;
import com.maehem.javamancer.neuro.model.room.RoomMap;
import com.maehem.javamancer.neuro.view.pax.PaxPopupPane;
import com.maehem.javamancer.neuro.view.popup.BodyShopPopup;
import com.maehem.javamancer.neuro.view.popup.CyberspacePopup;
import com.maehem.javamancer.neuro.view.popup.DeckPopup;
import com.maehem.javamancer.neuro.view.popup.DialogPopup;
import com.maehem.javamancer.neuro.view.popup.DiskPopup;
import com.maehem.javamancer.neuro.view.popup.InventoryPopup;
import com.maehem.javamancer.neuro.view.popup.PawnshopVendPopup;
import com.maehem.javamancer.neuro.view.popup.PopupPane;
import com.maehem.javamancer.neuro.view.popup.RomPopup;
import com.maehem.javamancer.neuro.view.popup.SkillsPopup;
import com.maehem.javamancer.neuro.view.popup.SkillsVendPopup;
import com.maehem.javamancer.neuro.view.popup.SoftwareVendPopup;
import com.maehem.javamancer.neuro.view.room.RoomDescriptionPane;
import com.maehem.javamancer.neuro.view.room.RoomMusic;
import com.maehem.javamancer.neuro.view.room.RoomPane;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.DIGIT1;
import static javafx.scene.input.KeyCode.I;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class RoomMode extends NeuroModePane implements PopupListener {

    private enum Status {
        DATE, TIME, CREDIT, CONSTITUTION
    }

    public enum Popup {
        INVENTORY, PAX, TALK, SKILLS, ROM, DISK, DECK, BODYSHOP_BUY, BODYSHOP_SELL, SKILLS_BUY, SKILLS_UPGRADE, ITEMS_BUY, SOFTWARE_BUY, CYBERSPACE
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

    private final Shape descriptionFocusBorder = RoomDescriptionPane.createDescriptionBorder();

    private final Shape descriptionGreyOut = RoomDescriptionPane.descrptionGreyOut(TEXT_SCALE);

    private final RoomDescriptionPane roomDescriptionPane;
    private Status statusMode = Status.DATE;
    private boolean firstTime = true;

    public RoomMode(NeuroModePaneListener listener, GameState gameState) {
        super(listener, gameState);

        gameState.roomMode = this;
        // TODO: generate Room from gameState.roomNumber
        this.room = gameState.room;
        LOGGER.log(Level.INFO, "Create new RoomMode for {0}.", room.name());

        // A room is 'visited' once player fully reads/scrolls the room description.
        firstTime = !gameState.visited.contains(room);
        roomText = gameState.resourceManager.getRoomText(room);

        roomDescriptionPane = new RoomDescriptionPane(TEXT_SCALE);
        roomDescriptionPane.vvalueProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    LOGGER.log(Level.FINEST, "Description Scroll: {0}", newValue);
                    scrollHint.setVisible(newValue.doubleValue() != 1.0);
                    if (updateGreyOutState(newValue.doubleValue())) {
                        showPopup(Popup.TALK);
                    }
                });

        ImageView cPanelView = new ImageView(getResourceManager().getSprite("NEURO_1"));
        roomPane = new RoomPane(gameState.resourceManager, room);

        if (room.getExtras() != null) {
            LOGGER.log(Level.CONFIG, "Room has `extras`. Configuring...");
            RoomExtras extras = room.getExtras();
            // TODO: Can we run initRoom() when we create the room?
            extras.initRoom(gameState);
            int[][] dc = extras.getDialogChain();
            if (firstTime) {
                LOGGER.log(Level.CONFIG, "First time visit of room. Use long description.");
                //int[] dc0 = extras.getDialogChain()[0]; // Long Description
                if (dc != null && dc[0].length == 1 && dc[0][0] == LONG_DESC.num) { // long desc. here
                    LOGGER.log(Level.CONFIG, "Found long room description in dialog chain.");
                    roomDescriptionPane.setText(roomText.getDescription());
                    roomPane.setEffect(new GaussianBlur(3.0));
                } else {
                    LOGGER.log(Level.CONFIG, "No room description found in dialog chain.");
                    roomDescriptionPane.setText("\n\n");
                    //roomDescriptionPane.vvalueProperty().setValue(1.0);
                    Platform.runLater(() -> {
                        roomDescriptionPane.setVvalue(1.0); // Triggers dialog pop.
                    });
                }
            } else {
                LOGGER.log(Level.CONFIG, "We`ve been here before. Use short description.");
                //int[] dc1 = extras.getDialogChain()[1]; // Short Description
                if (dc != null && dc[1].length == 1 && dc[1][0] == SHORT_DESC.num) { // short desc. here
                    LOGGER.log(Level.CONFIG, "Found short room description in dialog chain.");
                    roomDescriptionPane.setText(roomText.getShortDescription());
                } else {
                    LOGGER.log(Level.CONFIG, "No room description found in dialog chain.");
                    roomDescriptionPane.setText("\n\n");
                }
                //roomDescriptionPane.vvalueProperty().set(1.0);
                Platform.runLater(() -> {
                    roomDescriptionPane.setVvalue(1.0); // Triggers dialog pop.
                });
            }

            roomPane.initAnimations(getResourceManager(), room);

        } else {
            LOGGER.log(Level.CONFIG, "Room does not have `extras`.");
            //updateGreyOutState(1.0);
            if (firstTime) {
                LOGGER.log(Level.CONFIG, "First time visit of room. Use long description.");
                roomDescriptionPane.setText(roomText.getDescription());
            } else {
                LOGGER.log(Level.CONFIG, "We've been here before. Use short description.");
                roomDescriptionPane.setText(roomText.getShortDescription());
            }
            Platform.runLater(() -> {
                roomDescriptionPane.setVvalue(1.0); // Triggers dialog pop.
            });
        }

        doorStateMessages();

        // TODO: Move this into description pane?
        // Plus (+) character appears if user has not scrolled to bottom of
        // scene description.
        scrollHint.setLayoutX(614);
        scrollHint.setLayoutY(388);
        scrollHint.getTransforms().add(TEXT_SCALE);

        getChildren().addAll(
                cPanelView, roomPane, statusText, roomDescriptionPane, scrollHint,
                inventoryButton, paxButton, talkButton,
                skillsButton, romButton, diskButton,
                dateButton, timeButton, credButton, constButton,
                descriptionGreyOut, descriptionFocusBorder
        );

        statusText.setId("neuro-status");
        statusText.setLayoutX(190);
        statusText.setLayoutY(313);
        statusText.getTransforms().add(TEXT_SCALE);

        setOnMouseClicked((t) -> {
            t.consume();
            handleMouseClick(t.getX(), t.getY());
        });

        initButtonHandlers();
        initKeyboardEvents();

        Platform.runLater(() -> {
            updateStatus();
            roomPane.updatePlayerPosition(gameState, gameState.roomPosX, gameState.roomPosY);
            RoomMusic mus = RoomMusic.get(room);
            if (mus != null) {
                gameState.resourceManager.musicManager.playTrack(mus);
            } else {
                LOGGER.log(Level.WARNING, "No soundtrack for room {0}", room.name());
            }
            RoomExtras extras = gameState.room.getExtras();
            if (extras != null) {
                roomPane.startAnimations(extras);
            }
        });

        setFocusTraversable(true);
        requestFocus();

        if (!getGameState().showMessageNextRoom.isBlank()) {
            roomDescriptionPane.addMessage(getGameState().showMessageNextRoom);
            getGameState().showMessageNextRoom = "";
        }

    }

    private boolean updateGreyOutState(double scrollValue) {
        LOGGER.log(Level.FINEST, "Update Grey Out State... ");
        scrollHint.setVisible(scrollValue != 1.0);
        if (scrollValue == 1.0) {
            roomPane.setEffect(null);
            descriptionGreyOut.setVisible(false);
            descriptionFocusBorder.setVisible(false);
            firstTime = false;
            return true;
        }

        return false;
    }

    private void initButtonHandlers() {
        inventoryButton.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "User clicked Inventory.");
            t.consume();
            showPopup(Popup.INVENTORY);
        });
        paxButton.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "User clicked PAX.");

            t.consume();
            showPopup(Popup.PAX);
        });
        talkButton.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "User clicked Talk.");
            t.consume();
            showPopup(Popup.TALK);
        });
        skillsButton.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "User clicked Skills.");
            t.consume();
            showPopup(Popup.SKILLS);
        });
        romButton.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "User clicked ROM.");
            t.consume();
            showPopup(Popup.ROM);
        });
        diskButton.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "User clicked Disk.");
            t.consume();
            showPopup(Popup.DISK);
        });

        dateButton.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "User clicked Date.");
            statusMode = Status.DATE;
            t.consume();
            updateStatus();
        });
        timeButton.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "User clicked Time.");
            statusMode = Status.TIME;
            t.consume();
            updateStatus();
        });
        credButton.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "User clicked Credits.");
            statusMode = Status.CREDIT;
            t.consume();
            updateStatus();
        });
        constButton.setOnMouseClicked((t) -> {
            LOGGER.log(Level.CONFIG, "User clicked Constitution.");
            statusMode = Status.CONSTITUTION;
            t.consume();
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
                if (firstTime) { // Only space bar works on new room.
                    keyEvent.consume();
                    LOGGER.log(Level.FINER, "No key interaction until room description is read.");
                    roomDescriptionPane.handleEvent(keyEvent);
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
                        case COMMA -> {
                            LOGGER.log(Level.CONFIG, "User pressed COMMA Key. Toggle Sound Mute");
                            getListener().neuroModeActionPerformed(NeuroModePaneListener.Action.MUTE_MUSIC, null);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void tick() {
        if (!getGameState().showMessage.isBlank()) {
            roomDescriptionPane.addMessage(getGameState().showMessage);
            getGameState().showMessage = "";
        }
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
        if (popup != null) {
            LOGGER.log(Level.FINEST, () -> "Popup is: " + popup.getClass().getSimpleName());
            switch (popup) {
                case DialogPopup dp -> {
                    dp.dialogCounter();
                    roomPane.tick(getGameState());
                }
                case DeckPopup dp ->
                    dp.tick();
                case CyberspacePopup dp ->
                    dp.tick();
                default -> {
                }
            }
        } else {
            if (room.extras != null) {
                if (room.extras.isRequestDialogPopup()) {
                    room.extras.setRequestDialogPopup(false);
                    showPopup(Popup.TALK);
                } else {
                    room.extras.tick(getGameState());
                }
            }
            roomPane.tick(getGameState());
        }

        if (getGameState().previousSkill != getGameState().activeSkill) {
            // The skill in use has changed.
            // If the player has visited, trigger dialog. Should load new dialog.
        }

        if (getGameState().isFlatline()) {
            LOGGER.log(Level.INFO, "Player is flatlined.");
            popup = null;

            // Load revive room scene?
            // Player has died in previous tick.
            // Apply low costitution and revive in Body Shop.
            getGameState().revive();
        }
    }

    /**
     * Note to future self: Deck cannot open another deck.
     *
     * @param pop
     */
    public void showPopup(Popup pop) {
        if (popup != null) {
            if ( pop == Popup.INVENTORY ) {
                LOGGER.log(Level.CONFIG, "popup: optional inventoryClicked().");
                // Deck has a software inventory.
                popup.inventoryClicked();
            }
            
            return; // User must exit current popup first!
        }
        switch (pop) {
            case INVENTORY -> {
                popup = new InventoryPopup(this, getGameState());
            }
            case PAX -> {
                popup = new PaxPopupPane(this, getGameState(), getResourceManager());
            }
            case TALK -> {
                GameState gs = getGameState();
                boolean canTalk = gs.roomCanTalk();
                LOGGER.log(Level.FINE, "Room {0} has dialog: {1}", new Object[]{gs.room.getIndex() + 1, canTalk ? "YES" : "NO"});
                if (canTalk) {
                    LOGGER.log(Level.INFO, "Create new TALK popup.");
                    popup = new DialogPopup(this, gs, getResourceManager());
                } else {
                    LOGGER.log(Level.FINE, "No NPC or NPC has no more to talk about.");
                }
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
            case DECK -> {
                // Let's do matrix stuff.
                DeckItem deck = getGameState().usingDeck;
                if (deck != null) {
                    LOGGER.log(Level.INFO, "Popup created for Deck: {0}", deck.getName());
                    popup = new DeckPopup(this, getGameState());
                } else {
                    LOGGER.log(Level.SEVERE, "Room tried to use a null deck!  Did something go wrong?");
                    popup = null;
                }
            }
            case BODYSHOP_BUY -> {
                popup = new BodyShopPopup(BodyShopPopup.Mode.BUY, this, getGameState());
                LOGGER.log(Level.INFO, "Set popup to: BodyShop - BUY");
            }
            case BODYSHOP_SELL -> {
                popup = new BodyShopPopup(BodyShopPopup.Mode.SELL, this, getGameState());
                LOGGER.log(Level.INFO, "Set popup to: BodyShop - SELL");
            }
            case SKILLS_BUY -> {
                RoomExtras roomExtras = room.getExtras();
                if (roomExtras != null) {
                    ArrayList<SkillItem> items = roomExtras.getVendSkillItems(getGameState());
                    if (items != null) {
                        popup = new SkillsVendPopup(
                                SkillsVendPopup.Mode.BUY,
                                this, getGameState(), items
                        );
                        LOGGER.log(Level.INFO, "Set popup to: Skills - BUY");
                    }
                }
            }
            case SKILLS_UPGRADE -> {
                RoomExtras roomExtras = room.getExtras();
                if (roomExtras != null) {
                    ArrayList<SkillItem> items = roomExtras.getUpgradeSkillItems(getGameState());
                    if (items != null) {
                        popup = new SkillsVendPopup(
                                SkillsVendPopup.Mode.UPGRADE,
                                this, getGameState(), items
                        );
                        LOGGER.log(Level.INFO, "Set popup to: Skills - UPGRADE");
                    }
                }
            }
            case ITEMS_BUY -> {
                RoomExtras roomExtras = room.getExtras();
                if (roomExtras != null) {
                    ArrayList<Item> vendItems = roomExtras.getVendItems(getGameState());
                    if (vendItems != null) {
                        // TODO: Rename popup to ItemsVendPopup
                        popup = new PawnshopVendPopup(
                                PawnshopVendPopup.Mode.BUY,
                                this, getGameState(), vendItems
                        );
                        LOGGER.log(Level.INFO, "Set popup to: Items - BUY");
                    }
                }
            }
            case SOFTWARE_BUY -> {
                RoomExtras roomExtras = room.getExtras();
                if (roomExtras != null) {
                    ArrayList<SoftwareItem> vendItems = roomExtras.getVendSoftwareItems(getGameState());
                    if (vendItems != null) {
                        popup = new SoftwareVendPopup(
                                SoftwareVendPopup.Mode.BUY,
                                this, getGameState(), vendItems
                        );
                        LOGGER.log(Level.INFO, "Set popup to: Software - BUY");
                    }
                }
            }
            case CYBERSPACE -> {
                popup = new CyberspacePopup(this, getGameState());
                LOGGER.log(Level.INFO, "Set popup to: Cyberspace");
            }
        }
        if (popup != null) {
            LOGGER.log(Level.INFO, "Add popup to scene: {0}", popup.getClass().getSimpleName());
            getChildren().add(popup);
            //getGameState().pause = true;
        }
    }

    @Override
    public void destroy() {
        if (!getGameState().visited.contains(room)) {
            LOGGER.log(Level.FINE, "Set room {0} as visited.", room.name());
            getGameState().visited.add(room);
        }

        RoomMusic mus = RoomMusic.get(room);
        if (mus != null) {
            getGameState().resourceManager.musicManager.fadeOutTrack(
                    mus.track, mus.fadeOut
            );
        }
        roomPane.cleanup();

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
                String hour = String.format("%02d", gs.dateHour);
                String minute = String.format("%02d", gs.dateMinute);
                String time = String.format("%1$10s", hour + ":" + minute);
                statusText.setText(time);
            }
            case CREDIT -> {
                statusText.setText("$" + String.format("%1$9s", String.valueOf(getGameState().chipBalance)));
            }
            case CONSTITUTION -> {
                statusText.setText(String.format("%1$10s", String.valueOf(gs.getConstitution())));
            }
        }
    }

    @Override
    public void showMessage(String message) {
        roomDescriptionPane.addMessage(message);
    }

    private void handleMouseClick(double x, double y) {
        if (firstTime) {
            LOGGER.log(Level.CONFIG, "No mouse interaction until room description is read.");
            return;
        }
        LOGGER.log(Level.FINER, "Mouse Click at: {0},{1}", new Object[]{(int) x, (int) y});
        if ((y > 16 && y < 240) && (x > 16 && x < 624)) {
            // User clicked in room scene.
            if (popup == null) {
                LOGGER.log(Level.FINER, "User clicked roomPane at: {0},{1}", new Object[]{(int) x, (int) y});
                roomPane.mouseClick(x - RoomPane.PANE_X, y - RoomPane.PANE_Y, getGameState());
            }
        }
        if (popup instanceof DialogPopup dp) {
            room.getExtras().onPopupExit(getGameState(), dp);
            popupExit();
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

    /**
     * Close the popup.
     *
     * @return true if OK to open any new requested popup.
     */
    @Override
    public boolean popupExit() {
        if (popup == null) { // Used by sense/net room to boot player.
            return false;
        }
        LOGGER.log(Level.INFO, "Remove popup from scene: {0}", popup.getClass().getSimpleName());
        popup.setVisible(false);
        boolean doNextPopup;
        if (popup instanceof SkillsVendPopup) {
            doNextPopup = room.getExtras().onSkillVendFinished(getGameState());
        } else if (popup instanceof DeckPopup) {
            doNextPopup = room.getExtras().onDeckFinished(getGameState());
//            if ( dp.requestCyberspace() ) {
//                doNextPopup = true;
//            }
        } else if (popup instanceof PawnshopVendPopup vendPop) {
            doNextPopup = room.getExtras().onVendItemsFinished(getGameState(),vendPop.itemPurchased());
        } else {
            doNextPopup = true;
        }
        
        getChildren().remove(popup);
        popup.cleanup();
        popup = null;
        getGameState().pause = false;

        return doNextPopup;
    }

    @Override
    public void popupExit(Popup newPopup) {
        if (popupExit()) {
            LOGGER.log(Level.CONFIG, "Popup exited. Now open new Popup: {0}", newPopup.name());
            showPopup(newPopup);
        } else {
            LOGGER.log(Level.CONFIG, "Popup exited. Pevious popup denied any new popup.");
        }
    }

    /**
     * Check each door and add description message if they are locked.
     *
     */
    private void doorStateMessages() {
        MessageFormat msg = new MessageFormat("The door to {0} is locked.");
        if (room.isDoorLocked(RoomBounds.Door.TOP)) {
            roomDescriptionPane.addMessage(msg.format(new Object[]{RoomMap.getRoom(room, RoomBounds.Door.TOP).roomName}));
        }
        if (room.isDoorLocked(RoomBounds.Door.RIGHT)) {
            roomDescriptionPane.addMessage(msg.format(new Object[]{RoomMap.getRoom(room, RoomBounds.Door.RIGHT).roomName}));
        }
        if (room.isDoorLocked(RoomBounds.Door.BOTTOM)) {
            roomDescriptionPane.addMessage(msg.format(new Object[]{RoomMap.getRoom(room, RoomBounds.Door.BOTTOM).roomName}));
        }
        if (room.isDoorLocked(RoomBounds.Door.LEFT)) {
            roomDescriptionPane.addMessage(msg.format(new Object[]{RoomMap.getRoom(room, RoomBounds.Door.LEFT).roomName}));
        }
    }
}
