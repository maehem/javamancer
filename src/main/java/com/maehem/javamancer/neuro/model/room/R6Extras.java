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
 * Room 6 -- Donut World
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R6Extras extends RoomExtras {

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC}, {SHORT_DESC}, // 0, 1
        {5, 6, 7, 8, 9}, // 2   "Hey we don't allow...." CopTalk add, 3,4
        {15}, // 3    "St. Patty's Day...." CopTalk 2.0
        {14}, // 4  " Sure and beorrah ..." CopTalk 1.
        {11}, // 5  "I came in for a donut.."
        {12}, // 6 "I'm looking for pirated..."
        {12}, // 7 "Drop dead flatfoot."
        {12}, // 8 "You'll never take me alive."
        {13}, // 9 "Forgive me sir...."
        {12}, // 10 "Am I ever going to get a break...."
        {7, 8, 9, 10}, // 11 "This is a donut shop..."
        {TO_JAIL}, // 12 "That's it you're under arrest...."
        {EXIT_T}, // 13 "Just don't let me catch...."
        {24, 25, 26,}, // 14 "Finnegan old pal...."
        {18, 19, 20, 21, 22}, // 15 "Mulligan! I can barely...."
        {30}, // 16 "Back again?...."
        {30}, // 17 "St. Patty's Day. I'm looking...."
        {30}, // 18 "Sure and begorrah...."
        {30}, // 19 "Well now....."
        {30}, // 20 "Can't a man get a donut...."
        {30}, // 21 "Yeah I'm looking for trouble...."
        {30}, // 22 "Sorry my mistake...."
        {30}, // 23 "I've warned you about this before...."
        {30}, // 24 "Begorrah! I forgot the...."
        {30}, // 25 "What's the password for...."
        {30}, // 26 "O'Riley, I heard they changed...."
        {30}, // 27 "Have you heard any news...."
        {30}, // 28 "Fergus gave me the second level...."
        {30}, // 29 "Don't worry. It's KEISATSU...."
        {30}, // 30 "Wild Irish Rose...."
        {30}, // 31 "The coded Fuji password is...."
        {30}, // 32 "Just got through questioning...."
        {30}, // 33 "You seem to be forgetting alot...."
        {30}, // 34 "Mulligan! Did you hear about...."
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
        //gs.doorBottomLocked = false; // Unlock door.
    }

    @Override
    public int jackZone() {
        return 0; // TODO: Remove/Set to -1 after sufficient deck testing.
    }

    @Override
    public boolean hasPAX() {
        return true;
    }

}
