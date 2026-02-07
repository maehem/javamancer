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
package com.maehem.javamancer.neuro.view.database;

import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.room.Room;
import com.maehem.javamancer.neuro.model.room.RoomBounds;
import com.maehem.javamancer.neuro.model.room.RoomBounds.Door;
import com.maehem.javamancer.neuro.view.PopupListener;
import java.util.logging.Level;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/*
 * <pre>
[0] :: * Maas BioLabs *
[1] :: X. Exit System 1. CyberEye Report 2. Security Report 3. Security Systems
[2] :: CyberEye Development Report For release to MAAS BIOLAB stockholders and administrative headquarters staff November 2058  Until now, RAM limits and cyberspace matrix simulator quality have been the limiting factor in high-efficiency utilization of cyberspace time.  The best matrix simulators can only multitask 25 different types of software at one time due to limits on available working RAM. Our goal has been to build what is effectively a cyberspace "superdeck," capable of being implanted directly into the users head and making use of the unlimited RAM in the owners cerebrum.  By utilizing standard microsoft brain jacks for loading the necessary softwarez, a perfect symbiotic relationship is developed between the owners brain and his superdeck, which we have dubbed, "CyberEyes."
[3] :: While the price of CyberEyes will be prohibitive for most potential buyers when they are first introduced, it is expected that the price can be brought down after development costs are recovered over a period of seven years.  The small number of initial buyers will form an elite core clientele which will be easier to service when and if any problems should arise after the initial implantations.
[4] :: Final testing of the CyberEye prototype, with an automated implantation system, is presently underway at our Chiba City facility.  All tests so far have been favorable and no problems are anticipated before the final checkout scheduled for tomorrow.  A small trial run of production CyberEyes has been completed and Quality Control has passed them without any rejects. It would appear that we have developed yet another successful product!
[5] :: Security Report November 2058  Our genetically-engineered "Security Guard Virus," installed last week in our Chiba City research and development facility, seems to be operating at peak efficiency.  An intruder, identified as an industrial spy from Hitachi Biotech, bypassed our normal Lawbot alarm system last night and gained access to the floor of our CyberEye development lab.  Unaware that the Guard Virus is released into the air of the facility after the last employees leave each evening, the intruder inhaled the Guard Virus and went into convulsions. Paralyzed and unable to breathe, he died a few minutes later.  The next morning, after evacuating the Guard Virus from the air supply, we discovered the intruders body.  After decontamination, his remains were mailed back to Hitachi Biotech for disposal.  Hitachi responded with an offer to license use of our Guard Virus to protect their own facility in Chiba City.  We feel that this proves the viability of the Guard Virus design.
[6] ::                WARNING!  This system is for Security personnel only. Non-security personnel who tamper with this system are subject to immediate dismissal and criminal prosecution.      X. Exit To Main     1. Lawbot Alarm is:      2. Main Entrance is:
[7] :: OFF
[8] :: ON
[9] :: UNLOCKED
[10] :: LOCKED
 * </pre>
 */
/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class MaasBiolabsDatabaseView extends DatabaseView {

    private enum Mode {
        SUB, MENU, SECURITY
    }
    private Mode mode = Mode.SUB; // Sub-mode handled by superclass.

    public MaasBiolabsDatabaseView(GameState gs, Pane p, PopupListener l) {
        super(gs, p, l);
        landingPage();
    }

    @Override
    protected final void landingPage() {
        pane.getChildren().clear();
        mode = Mode.SUB;

        Text paddingText = new Text("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        TextFlow tf = pageTextFlow(headingText, paddingText, CONTINUE_TEXT);
        pane.getChildren().add(tf);
    }

    @Override
    protected final void siteContent() {
        mainMenu();
    }

    private void mainMenu() {
        pane.getChildren().clear();
        mode = Mode.MENU;

        TextFlow tf = pageTextFlow(headingText);

        String menuString = dbTextResource.get(1);
        String[] split = menuString.split("\\r");
        for (String s : split) {
            Text menuItem = new Text("\n         " + s);
            tf.getChildren().add(menuItem);
            menuItem.setOnMouseClicked((t) -> {
                t.consume();
                itemPage(s.trim().substring(0, 1));
            });
        }

        pane.getChildren().add(tf);
        pane.setOnMouseClicked(null);
    }

    private void itemPage(String itemLetter) {
        switch (itemLetter) {
            case "X" -> {
                listener.popupExit();
            }
            case "1" -> { // Cyber Eye Report
                viewText(2, 3, 4);
            }
            case "2" -> { // Alarm System Report
                viewText(5);
            }
            case "3" -> {
                security(); // Control Door and Alarm System
            }
        }
    }

    private void security() {
        pane.getChildren().clear();
        mode = Mode.SECURITY;

        TextFlow tf = pageTextFlow(headingText);
        tf.setPadding(new Insets(0, 0, 0, 30));

        String menuString = dbTextResource.get(6);
        String[] split = menuString.split("\\r");
        for (String s : split) {
            Text menuItem = new Text("\n" + s);

            String item = s.trim();
            if (item.startsWith("1")) {
                // TODO: Does this thing ever get toggled to off?
                // How and why?

                // Dress with status
                String alarmStatus = dbTextResource.get(8); // ON 
                menuItem.setText(menuItem.getText() + " " + alarmStatus);
                menuItem.setOnMouseClicked((t) -> {
                    security();
                });
            } else if (item.startsWith("2")) {
                Room room = Room.R17;
                // Dress with status
                String doorStatus;
                if (room.isDoorLocked(RoomBounds.Door.LEFT)) {
                    doorStatus = dbTextResource.get(10); // LOCKED 
                    menuItem.setText(menuItem.getText() + " " + doorStatus);
                    menuItem.setOnMouseClicked((t) -> {
                        room.unlockDoor(RoomBounds.Door.LEFT);
                        security(); // Toggle and reload.
                    });
                } else {
                    doorStatus = dbTextResource.get(9); // UNLOCKED
                    menuItem.setText(menuItem.getText() + " " + doorStatus);
                    menuItem.setOnMouseClicked((t) -> {
                        room.lockDoor(RoomBounds.Door.LEFT);
                        security(); // Toggle and reload.
                    });
                }
            } else if (item.startsWith("X")) {
                menuItem.setOnMouseClicked((t) -> {
                    mainMenu();
                });
            }
            tf.getChildren().add(menuItem);
        }

        pane.getChildren().add(tf);
        pane.setOnMouseClicked(null);
        pane.layout();
    }

    @Override
    public boolean handleKeyEvent(KeyEvent keyEvent) {
        KeyCode code = keyEvent.getCode();
        LOGGER.log(Level.FINE, "Handle key event.");
        switch (mode) {
            case MENU -> {
                if (code.equals(KeyCode.X)
                        || code.equals(KeyCode.SPACE)
                        || code.equals(KeyCode.ESCAPE)) {
                    LOGGER.log(Level.INFO, "Menu wants to exit system.");
                    keyEvent.consume();
                    return true;
                } else if (code.isDigitKey()) {
                    keyEvent.consume();
                    itemPage(code.getChar());
                    return false;
                }
            }
            case SECURITY -> {
                if (code.equals(KeyCode.X)
                        || code.equals(KeyCode.SPACE)
                        || code.equals(KeyCode.ESCAPE)) {
                    LOGGER.log(Level.INFO, "Menu go back one level.");
                    keyEvent.consume();
                    mainMenu();
                    return false;
                } else if (code.isDigitKey()) {
                    keyEvent.consume();
                    toggleStatus(Integer.parseInt(code.getChar()));
                    security();
                    return false;
                }
            }
        }
        return super.handleKeyEvent(keyEvent);
    }

    private void toggleStatus(int i) {
        if (i == 1) {
            LOGGER.log(Level.WARNING, "Toggle alarm. TODO.");
        } else if (i == 2) {
            Room room = Room.R17;
            Door door = RoomBounds.Door.LEFT;
            if (room.isDoorLocked(door)) {
                room.unlockDoor(door);
            } else {
                room.lockDoor(door);
            }
        }
    }

}
