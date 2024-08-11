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
public class R4Extras extends RoomExtras {

    protected static final int[][] DIALOG_CHAIN = {
        {55}, {56}, // 0, 1 // Room descriptions
        {4, 5, 6, 7}, // 2   "Can I be of service? ..."
        {4, 5, 6, 7}, // 3    "Hello again ..."
        {8}, // 4  "Yes"
        {9}, // 5  "No"
        {11}, // 6  "I feel an attachment ..."
        {12}, // 7  "Oh look at the time...
        {BODY_SELL}, // 8  "Wonderfull. We have need for..." // Sell Organ
        {DIALOG_CLOSE}, // 9  "Let me know if you change your mind ..." // Close dialog
        {BODY_BUY}, // 10 "Great deals!"
        {BODY_BUY}, // 11 "Let's see if it's still in stock"
        {EXIT_B}, // 12
        {EXIT_B}, // 13
        {4, 5, 6, 7}, // 14
        {EXIT_B}, // 15
        {BODY_BUY} // 16 // Well sell parts back at a discount.
    };

    @Override
    public void initRoom(GameState gs) {
        // lock door if still talking to Ratz.
        //gs.doorBottomLocked = gs.roomNpcTalk[gs.room.getIndex()];
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
