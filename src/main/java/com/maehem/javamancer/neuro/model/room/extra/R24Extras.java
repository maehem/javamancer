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
import com.maehem.javamancer.neuro.model.room.DialogCommand;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.CHIP;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DIALOG_CLOSE;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.EXIT_L;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.INFO_BUY;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.LONG_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.MASSAGE_BOT;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.NPC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.SHORT_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.TO_JAIL;
import com.maehem.javamancer.neuro.model.room.RoomExtras;
import java.util.logging.Level;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R24Extras extends RoomExtras {

    protected static final int[][] DIALOG_CHAIN = { //    The massage parlor.
        {LONG_DESC.num}, {SHORT_DESC.num}, // 0, 1
        {3, 4, 5, 6}, // [2] :: Greetings, cowboy.
        {18}, // [3] :: Top of the mornin! Youre under
        {16}, // [4] :: Im sure Ill think of
        {8}, // [5] :: Uh, excuse me, Im just
        {19, CHIP.num, 20}, // [6] :: I wanna buy some info
        {}, // [7] :: // Blank Line
        {DIALOG_CLOSE.num}, // [8] :: You dont know what youre
        {EXIT_L.num}, // [9] :: I just remembered I have
        {NPC.num, 21}, // [10] :: Heres a hot tip. The Panther
        {NPC.num, 21}, // [11] :: The banking center is on the
        {NPC.num, 21}, // [12] :: Be careful when dealing with the
        {NPC.num, 21}, // [13] :: Some cowboy said he avoids court
        {NPC.num, 15}, // [14] :: Just heard that Maas Biolabs finished
        {NPC.num, 21}, // [15] :: CyberEyes, you dont need a regular
        {EXIT_L.num}, // [16] :: Take a hike, meatball!
        {TO_JAIL.num}, // [17] :: Look out!  Its
        {EXIT_L.num}, // [18] :: Get serious!  No discounts here, buckaroo!
        {NPC.num, INFO_BUY.num, 20}, // [19] :: You just bought yourself some
        {DESC.num}, // [20] :: The girl relieves you of <== Place in room description
        {MASSAGE_BOT.num, NPC.num, 17}, // [21] :: I have more info, if you
        {DIALOG_CLOSE.num} // [22] :: You know more than me
    };

    // Animation
    // Load at start:
    // Woman ::
    //        entry01 : anim00
    //
    // Triggered visible by dialog response [17]:
    // Police Bot ::
    //        entry02 : anim00
    // Police Bot Hover Energy ::
    //        entry03 : anim00
    protected final int[][] ANIMATION_FLAGS = {
        {1}, // Akiko
        {0}, // Police Robot
        {0} // Police Robot Hover Exhaust
    };

    @Override
    public int[][] getAnimationFlags() {
        return ANIMATION_FLAGS;
    }

    @Override
    public void initRoom(GameState gs) {
        // lock door if still talking to Ratz.
        //gs.doorBottomLocked = gs.roomNpcTalk[gs.room.getIndex()];
        //gs.resourceManager.getRoomText(Room.R24).dumpList();
    }

    @Override
    public int[][] getDialogChain() {
        return DIALOG_CHAIN;
    }

    @Override
    public int dialogWarmUp(GameState gs) {
        return 2;
    }

    @Override
    public int dialogIndexAdjust(GameState gs, DialogCommand command) {
        LOGGER.log(Level.INFO, "Massage Parlor: Player bought info...");

        if (gs.chipBalance < 20) {
            return 18;
        }

        // Returns a new info index or 22 if all are given.
        int newInfo = getNewInfo(gs);

        // When all info has been bought,  don't deduct money and return 22.
        if (newInfo != 22) {
            gs.chipBalance -= 20; // Deduct some money.
            LOGGER.log(Level.CONFIG, () -> "Akiko gives you info dialog " + newInfo);
        } else {
            LOGGER.log(Level.CONFIG, () -> "Akiko has given you all the info she has.");
        }

        return newInfo;
    }

    @Override
    public int onDialogPreCommand(GameState gs, DialogCommand command) {
        if (command == DialogCommand.MASSAGE_BOT) {
            LOGGER.log(Level.SEVERE, "Set Police Bot Animation flags.");
            // Show police bot.
            getAnimationFlags()[1][0] = 1;
            getAnimationFlags()[2][0] = 1;
            // Animation will update on next RoomPane tick().
        }

        return 0;
    }

    private int getNewInfo(GameState gs) {
        if (gs.massageInfo1
                && gs.massageInfo2
                && gs.massageInfo3
                && gs.massageInfo4
                && gs.massageInfo5) {
            return 22;
        }

        boolean found = false;
        int randVal = 99;
        while (!found) {
            randVal = (int) (Math.random() * 5);
            switch (randVal) {
                case 0 -> {
                    if (!gs.massageInfo1) {
                        gs.massageInfo1 = true;
                        found = true;
                    }
                }
                case 1 -> {
                    if (!gs.massageInfo2) {
                        gs.massageInfo2 = true;
                        found = true;
                    }
                }
                case 2 -> {
                    if (!gs.massageInfo3) {
                        gs.massageInfo3 = true;
                        found = true;
                    }
                }
                case 3 -> {
                    if (!gs.massageInfo4) {
                        gs.massageInfo4 = true;
                        found = true;
                    }
                }
                case 4 -> {
                    if (!gs.massageInfo5) {
                        gs.massageInfo5 = true;
                        found = true;
                    }
                }
            }

        }
        return 10 + randVal;

    }
}
