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
import static com.maehem.javamancer.neuro.model.room.DialogCommand.CHIP;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DIALOG_CLOSE;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DIALOG_END;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.EXIT_L;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.INFO_BUY;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.LONG_DESC;
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
        {}, // [7] ::
        {DIALOG_END.num}, // [8] :: You dont know what youre
        {EXIT_L.num}, // [9] :: I just remembered I have
        {NPC.num, 17}, // [10] :: Heres a hot tip. The Panther
        {NPC.num, 17}, // [11] :: The banking center is on the
        {NPC.num, 17}, // [12] :: Be careful when dealing with the
        {NPC.num, 17}, // [13] :: Some cowboy said he avoids court
        {NPC.num, 17}, // [14] :: Just heard that Maas Biolabs finished
        {NPC.num, 17}, // [15] :: CyberEyes, you dont need a regular
        {EXIT_L.num}, // [16] :: Take a hike, meatball!
        {TO_JAIL.num}, // [17] :: Look out!  Its
        {EXIT_L.num}, // [18] :: Get serious!  No discounts here, buckaroo!
        {INFO_BUY.num, 20}, // [19] :: You just bought yourself some
        {}, // [20] :: The girl relieves you of <== Place in room description
        {DIALOG_CLOSE.num}, // [21] :: I have more info, if you
        {} // [22] :: You know more than me
    };

    @Override
    public void initRoom(GameState gs) {
        // lock door if still talking to Ratz.
        //gs.doorBottomLocked = gs.roomNpcTalk[gs.room.getIndex()];
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
    public int onInfoBuy(GameState gs) {
        LOGGER.log(Level.SEVERE, "Massage Parlor: Player bought info...");

        return 10 + (int) (Math.random() * 6);
    }


}
