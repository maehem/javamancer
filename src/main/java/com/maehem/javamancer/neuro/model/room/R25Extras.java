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
import com.maehem.javamancer.neuro.model.RoomExtras;
import com.maehem.javamancer.neuro.model.item.Item;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R25Extras extends RoomExtras { // Shin's Pawn

    protected static final int[][] DIALOG_CHAIN = { //    The massage parlor.
        {LONG_DESC}, {SHORT_DESC}, // 0, 1
        {3, 4, 5}, // [2] :: Ah. You back. I have your deck.
        {7}, // [3] :: Why are you in such a rush to give me my deck back?
        {6}, // [4] :: Okay. Give me the deck. I cant operate without one.
        {8}, // [5] :: I dont have the cash right now, Ill come back later.
        {UXB_BUY, 15}, // [6] :: Give ticket and money. Shin busy. Many customer.
        {3, 4, 5}, // [7] :: Your deck scare away good customer. No more favor.
        {9, 10}, // [8] :: What?  I no want deck!  Here!  Take deck now!  No charge!  Go away!
        {UXB, DESC, 13}, // [9] :: Thanks for my deck, Shin. I really appreciate you looking after it.
        {UXB, DESC, 13}, // [10] :: Okay, pal!  Ill go away!  And Ill tell all my friends about this place!
        {UXB, DESC, 13}, // [11] :: Thanks, Shin.  I knew youd see it my way.
        {EXIT_L, DIALOG_CLOSE}, // [12] ::   Shin slams and bolts the door behind you as you leave.
        {DESC, 12}, // [13] ::   Shin gives you your deck.
        {UXB, DESC, 13}, // [14] :: No have ticket? Shin give deck   anyways.
        {DESC, 12}, // [15] ::   After UXB..
    };

    @Override
    public void initRoom(GameState gs) {
        // lock door if still talking to Ratz.
        //gs.doorBottomLocked = gs.roomNpcTalk[gs.room.getIndex()];
        //gs.resourceManager.getRoomText(Room.R25).dumpList();
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
        if (!gs.roomNpcTalk[gs.room.getIndex()]) {
            return DIALOG_END;
        }
        return 2;
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
