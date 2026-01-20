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
import com.maehem.javamancer.neuro.model.JackZone;
import com.maehem.javamancer.neuro.model.item.Item;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.*;
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
    private int romNum = -1;

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC.num}, {SHORT_DESC.num}, //  [0][1]
        {DIALOG_CLOSE.num}, // [2] :: You have 30 seconds to produce your security pass. Failure to comply will result in your removal.
        {NPC.num, 4}, // [3] :: Clearance approved. You now have limited access to the Librarian.
        {9}, // [4] :: Please enter the identity num of the ROM Construct you require from the Sense/Net library vault:
        {DIALOG_CLOSE.num}, // [5] :: Availability verified. Checkout is approved.
        {DIALOG_CLOSE.num}, // [6] :: Identity num invalid. Try again if you made an error. You are allowed 3 library access attempts.
        {}, // [7] ::
        {TO_JAIL.num}, // [8] :: Access denied. Security has been alerted. Please remain here until the authorities arrive.
        {WORD1.num}, // [9] :: @---------------
        {}, // [10] :: The computer gives you a ROM Construct.
        {}, // [11] :: You are kicked out of Sense/Net, for not producing a security pass.
    };

    /**
     *
     * Enter Identity Number...
     * 
     */
    private static final Map<String, Integer> map1 = Map.ofEntries(
            entry("0467839", 5), // Dixie
            entry("55214260", 5), // ROMBO
            entry("6905984", 5)  // Toshiro
    );

    private int codeTries = 3;

    @Override
    public JackZone jackZone() {
        return JackZone.FOUR;
    }

    @Override
    public int askWord1(GameState gs, String word) {
        Integer index = map1.get(word);

        if (index == null) {
            codeTries--;
            if (codeTries > 0) {
                return 6; // Invalid num.
            } else {
                return 8; // Three strikes, to jail.
            }
        }

        romNum = index;
        
        return index;
    }

    @Override
    public void initRoom(GameState gs) {
        gs.allowDialog(gs.room);
    }

    @Override
    public boolean give(GameState gs, Item item, int aux) {

        if (item.item == Item.Catalog.SECURITYPASS) {
            gs.securityPassGiven = true;
            gs.setRoomTalk(true);
            gs.inventory.remove(item);
            countdown = -1;
            LOGGER.log(Level.FINE, "Security Pass is given to NPC.");

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
        if (gs.romInstalled >= 0) { // Player already has a ROM installed.
            gs.setRoomTalk(false);
            return DIALOG_END.num;
        }

        if (gs.securityPassGiven) {
            countdown = -1;
            return 3;
        }
        LOGGER.log(Level.INFO, () -> "DialogWarmup: countdown is: " + countdown);
        if (countdown == 0) {
            countdown = -1;
            // DESC 11
            gs.showMessageNextRoom = gs.resourceManager.getRoomText(gs.room).get(11) + "\n";
            return EXIT_T.num;
        }
        return 2;

    }

    @Override
    public int onDialogIndex(GameState gs, int dialog) {
        if (dialog == 2) {
            LOGGER.log(Level.INFO, "Gave 30 second warning.");
            // Begin 30 second timer.
            countdown = COUNTDOWN_TICKS;

            // Player must give pass to stop timer.
            dialogNoMore(gs);
        } else if (dialog == 5) { // Code verified.
            // Get ROM Consctruct
            LOGGER.log(Level.FINE, "Player recieves ROM construct.");
            gs.showMessage = gs.resourceManager.getRoomText(gs.room).get(10);
            gs.romInstalled = romNum;
            countdown = -1;
            dialogNoMore(gs);
        }
        
        return dialog;
    }

    @Override
    public void tick(GameState gs) {
        if (countdown > 0) {
            countdown--;
        }
        if (countdown == 0) {
            LOGGER.log(Level.FINE, "Security countdown finished. Open dialog, then exit.");
            //countdown = -1;
            //open dialog
            setRequestDialogPopup(true);
            gs.setRoomTalk(true);
        }
    }

}
