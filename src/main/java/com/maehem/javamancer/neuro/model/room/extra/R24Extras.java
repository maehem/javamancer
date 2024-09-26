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
import com.maehem.javamancer.neuro.model.item.CreditsItem;
import com.maehem.javamancer.neuro.model.item.Item;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.CHIP;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DIALOG_END;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.LONG_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.SHORT_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.TO_JAIL;
import com.maehem.javamancer.neuro.model.room.RoomExtras;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R24Extras extends RoomExtras {

    protected static final int[][] DIALOG_CHAIN = { //    The massage parlor.
        {LONG_DESC.num}, {SHORT_DESC.num}, // 0, 1
        {3, 4, 5, 6}, // [2] :: Greetings, cowboy.
        {}, // [3] :: Top of the mornin! Youre under
        {17}, // [4] :: Im sure Ill think of
        {8}, // [5] :: Uh, excuse me, Im just
        {19, CHIP.num, 20}, // [6] :: I wanna buy some info
        {}, // [7] ::
        {DIALOG_END.num}, // [8] :: You dont know what youre
        {}, // [9] :: I just remembered I have
        {TO_JAIL.num}, // [10] :: Heres a hot tip. The Panther
        {}, // [11] :: The banking center is on the
        {}, // [12] :: Be careful when dealing with the
        {}, // [13] :: Some cowboy said he avoids court
        {}, // [14] :: Just heard that Maas Biolabs finished
        {}, // [15] :: CyberEyes, you dont need a regular
        {}, // [16] :: Take a hike, meatball!
        {TO_JAIL.num}, // [17] :: Look out!  Its
        {}, // [18] :: Get serious!  No discounts here, buckaroo!
        {10}, // [19] :: You just bought yourself some
        {}, // [20] :: The girl relieves you of <== Place in room description
        {}, // [21] :: I have more info, if you
        {} // [22] :: You know more than me
    };

    @Override
    public void initRoom(GameState gs) {
        // lock door if still talking to Ratz.
        //gs.doorBottomLocked = gs.roomNpcTalk[gs.room.getIndex()];
    }

    @Override
    public boolean give(GameState gs, Item item, int aux) {
        // Player give is credits.
        if (item instanceof CreditsItem cr) {
            // Credit amount is 46
            if (aux == 46) {
                gs.ratzPaid = true;
                gs.chipBalance -= 46;
                return true;
            }
        }

        return false;
    }

    @Override
    public int[][] getDialogChain() {
        return DIALOG_CHAIN;
    }

    @Override
    public int dialogWarmUp(GameState gs) {
        if (!gs.roomNpcTalk[gs.room.getIndex()]) {
            return DIALOG_END.num;
        }
        if (gs.ratzPaid) {
            return 11;
        } else {
            return 2;
        }
    }

    @Override
    public void dialogNoMore(GameState gs) {
        gs.roomNpcTalk[gs.room.getIndex()] = false;
        //gs.doorBottomLocked = false; // Unlock door.
    }

    @Override
    public boolean hasPAX() {
        return false;
    }

}
