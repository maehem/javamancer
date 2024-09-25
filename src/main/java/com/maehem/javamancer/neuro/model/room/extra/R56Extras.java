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
package com.maehem.javamancer.neuro.model.room.extra;

import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.room.RoomExtras;
import java.util.Map;
import static java.util.Map.entry;
import java.util.logging.Level;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R56Extras extends RoomExtras { // Sense Net

    private final static int COUNTDOWN_TICKS = 10 * 15; // 30 seconds at 15fps
    private int countdown = -1;

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC}, {SHORT_DESC}, //  [0][1]
        {DIALOG_CLOSE}, // [2] :: You have 30 seconds to produce your security pass. Failure to comply will result in your removal.
        {NPC, 4}, // [3] :: Clearance approved. You now have limited access to the Librarian.
        {9}, // [4] :: Please enter the identity number of the ROM Construct you require from the Sense/Net library vault:
        {DIALOG_CLOSE}, // [5] :: Availability verified. Checkout is approved.
        {DIALOG_CLOSE}, // [6] :: Identity number invalid. Try again if you made an error. You are allowed 3 library access attempts.
        {}, // [7] ::
        {TO_JAIL}, // [8] :: Access denied. Security has been alerted. Please remain here until the authorities arrive.
        {WORD1}, // [9] :: @---------------
        {}, // [10] :: The computer gives you a ROM Construct.
        {}, // [11] :: You are kicked out of Sense/Net, for not producing a security pass.
    };

    /**
     *
     * Do you know about...
     *
     */
    private static final Map<String, Integer> map1 = Map.ofEntries(
            entry("0467839", 5)
    );

    private int codeTries = 3;

    @Override
    public int askWord1(GameState gs, String word) {
        Integer index = map1.get(word);

        if (index == null) {
            codeTries--;
            if (codeTries > 0) {
                return 6; // Invalid number.
            } else {
                return 8; // Three strikes, to jail.
            }
        }

        return index;
    }

    @Override
    public void initRoom(GameState gs) {
        // lock door if still talking to Ratz.
        //gs.doorBottomLocked = gs.roomNpcTalk[gs.room.getIndex()];
        //gs.resourceManager.getRoomText(Room.R56).dumpList();

    }

    @Override
    public boolean give(GameState gs, Item item, int aux) {

        if (item.item == Item.Catalog.SECURITYPASS) {
            gs.securityPassGiven = true;
            gs.setRoomTalk(true);
            gs.inventory.remove(item);
            countdown = -1;
            LOGGER.log(Level.SEVERE, "Security Pass is given to NPC.");

            return true;
        }

        return false;
    }

    @Override
    public int[][] getDialogChain() {
        return DIALOG_CHAIN;
    }

    @Override
    public int dialogWarmUp(GameState gs) {
        if (gs.dixieInstalled) {
            gs.setRoomTalk(false);
            return DIALOG_END;
        }

        if (gs.securityPassGiven) {
            countdown = -1;
            return 3;
        }
        LOGGER.log(Level.SEVERE, "DialogWarmup: countdown is: " + countdown);
        if (countdown == 0) {
            countdown = -1;
            // DESC 11
            gs.showMessageNextRoom = gs.resourceManager.getRoomText(gs.room).get(11) + "\n";
            return EXIT_T;
        }
        return 2;

    }

    @Override
    public int jackZone() {
        return 4;
    }

    @Override
    public void onDialog(GameState gs, int dialog) {
        //LOGGER.log(Level.SEVERE, "OnDialog for RoomExtra called. dialog == {0}", dialog);
        if (dialog == 2) {
            LOGGER.log(Level.SEVERE, "Gave 30 second warning.");
            // Begin 30 second timer.
            countdown = COUNTDOWN_TICKS;

            // Player must give pass to stop timer.
            dialogNoMore(gs);
        } else if (dialog == 5) { // Code verified.
            // Get ROM Consctruct
            LOGGER.log(Level.SEVERE, "Player recieves ROM construct.");
            gs.showMessage = gs.resourceManager.getRoomText(gs.room).get(10);
            gs.dixieInstalled = true;
            countdown = -1;
            dialogNoMore(gs);
        }
    }

    @Override
    public void tick(GameState gs) {
        if (countdown > 0) {
            countdown--;
        }
        if (countdown == 0) {
            LOGGER.log(Level.SEVERE, "Security countdown finished. Open dialog, then exit.");
            //countdown = -1;
            //open dialog
            setRequestDialogPopup(true);
            gs.setRoomTalk(true);
        }
    }

}
