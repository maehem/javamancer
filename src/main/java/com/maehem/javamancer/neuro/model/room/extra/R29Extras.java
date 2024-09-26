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
import static com.maehem.javamancer.neuro.model.room.DialogCommand.CHIP;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DIALOG_CLOSE;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.EXIT_T;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.LONG_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.SHORT_DESC;
import com.maehem.javamancer.neuro.model.room.Room;
import com.maehem.javamancer.neuro.model.room.RoomExtras;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R29Extras extends RoomExtras { // Freeside - Spacedock

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC.num}, {SHORT_DESC.num}, //  [0] ::
        {3, 4}, // [2] :: Konnichiwa!  Would you like to buy a ticket?
        {5}, // [3] :: Yes!  Id like to buy a ticket!
        {DIALOG_CLOSE.num}, // [4] :: No!  I just like hanging around ticket agents for no apparent reason!
        {6, 7, 8, 9}, // [5] :: We have a flight departing for Chiba City. The special low super maxi bargain big deal fare is $5.
        {19}, // [6] :: Okay.
        {19}, // [7] :: Great!  That sounds like a real bargain for a change!
        {14}, // [8] :: I dont want to go to Chiba City. I want to go somewhere else.
        {DIALOG_CLOSE.num}, // [9] :: Ive changed my mind. Im staying on Freeside for the rest of my life.
        {CHIP.num, 1000, 19}, // [10] :: Fine! Ill pay the $1000!
        {DIALOG_CLOSE.num}, // [11] :: Ive changed my mind. Im staying on Freeside for the rest of my life.
        {15}, // [12] :: How long would I have to wait?
        {10, 11}, // [13] :: You need to make a reservation 2 years in advance for that fare. All we have now is the regular fare for $1000.
        {12}, // [14] :: If youd like to wait, well have other flights departing for Paris, London, Amsterdam, and Moscow.
        {16, 17}, // [15] :: About 3 years. We havent started that service yet.
        {DIALOG_CLOSE.num}, // [16] :: Ive changed my mind. Im staying on Freeside for the rest of my life.
        {5}, // [17] :: After careful consideration, I think Ill buy the ticket to Chiba City.
        {}, // [18] ::
        {EXIT_T.num}, // [19] :: Enjoy your flight!  The holo-movie is "Airport 2000."
        {DIALOG_CLOSE.num}, // [20] :: Take a hike.  You cant afford it.
    };

    @Override
    public void initRoom(GameState gs) {
        // lock door if still talking to Ratz.
        //gs.doorBottomLocked = gs.roomNpcTalk[gs.room.getIndex()];
        gs.resourceManager.getRoomText(Room.R29).dumpList();
    }

    @Override
    public boolean give(GameState gs, Item item, int aux) {
        return false;
    }

    @Override
    public int[][] getDialogChain() {
        return DIALOG_CHAIN;
    }

    @Override
    public int dialogWarmUp(GameState gs) {
        return 3;

    }

    @Override
    public void dialogNoMore(GameState gs) {
        gs.roomNpcTalk[gs.room.getIndex()] = false;
    }

    @Override
    public boolean chipDeduct(GameState gs, int amt) {
        return super.chipDeduct(gs, amt);
    }


}
