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
package com.maehem.javamancer.neuro.model.room;

import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.Room;
import com.maehem.javamancer.neuro.model.RoomExtras;
import com.maehem.javamancer.neuro.model.item.Item;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R40Extras extends RoomExtras { // Crazy Edo's

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC}, {SHORT_DESC}, //  [0][1]
        {3, 4}, // [2] :: Hey!  You said youd bring me some caviar the next time you came in!
        {8}, // [3] :: Did I say that?  Sorry.  Guess Im not thinking too clearly.  I spent the night sleeping in spaghetti.
        {12}, // [4] :: Get your own caviar!  Go squeeze a sturgeon!  Im no delivery boy!
        {6, 7}, // [5] :: You brought the caviar, my old friend!  Do you want to trade it?
        {11}, // [6] :: Of course I want to trade it!
        {10}, // [7] :: I think Ill hang on to it right now.
        {9}, // [8] :: Can I interest you in some hardware? Remember, my prices are much better than that pig, Asano, can do.
        {17}, // [9] :: Let me see what youve got.
        {DIALOG_CLOSE}, // [10] :: Im just browsing.
        {DIALOG_CLOSE}, // [11] :: For a can of caviar, Ill give you Comlink 2.0.  Its great software!
        {DIALOG_CLOSE}, // [12] :: You having a rough day or something?
        {ITEM_GET}, // [13] :: Domo arigato gozaimasu!  Heres your Comlink 2.0 software.
        {DIALOG_CLOSE}, // [14] :: All right.  Maybe next time. (Close of Buy menu)
        {EXIT_T}, // [15] :: Try Metro Holografix for software! (After purchase of hardware?)
        {DIALOG_CLOSE}, // [16] :: Come back again when you feel like buying.
        {ITEM_BUY}, // [17] :: This is my current inventory. Of course, none of the decks are cyberspace-capable.
        {ITEM_GET}, // [18] :: Edo installs the software in your deck.
        {DIALOG_CLOSE}, // [19] :: Your deck is too full for us to trade.Erase some softwarez first.
    };

    @Override
    public void initRoom(GameState gs) {
        // lock door if still talking to Ratz.
        //gs.doorBottomLocked = gs.roomNpcTalk[gs.room.getIndex()];
        gs.resourceManager.getRoomText(Room.R40).dumpList();
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
        return 2;

    }

    @Override
    public void dialogNoMore(GameState gs) {
        gs.roomNpcTalk[gs.room.getIndex()] = false;
    }

}
