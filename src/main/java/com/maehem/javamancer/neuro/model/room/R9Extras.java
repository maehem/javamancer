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
public class R9Extras extends RoomExtras {

    protected static final int[][] DIALOG_CHAIN = { // Maas Biolabs
        {LONG_DESC}, {SHORT_DESC}, // 0, 1
        {NPC, 3}, // [2] :: Warning!  You are entering an area protected by bioengineered defensive agents!
        {NPC, 4}, // [3] :: Although I cant identify you through the gas mask, I have deduced that you are Doctor Yomiuri.
        {5, 6, 7}, // [4] :: Do you wish to test the CyberEyes implantation system, Doctor?
        {8}, // [5] :: Yes.
        {}, // [6] :: No.
        {9}, // [7] :: Actually, Im just here to check your security functions.
        {}, // [8] :: CyberEyes implantation system is now activated.  Are you to be the test subject, Doctor?
        {10, 11}, // [9] :: Certainly, Doctor Yomiuri. Which functions are of interest to you?
        {12}, // [10] :: Explain the procedure you follow if the door alarm is triggered.
        {14}, // [11] :: How many times per hour do you check the operation of the alarm system?
        {NPC, 13}, // [12] :: As usual, if the alarm is triggered, I will immediately notify the police and security.
        {NPC, 14}, // [13] :: Then Ill notify you over at that blondes apartment.
        {NPC, 15}, // [14] :: Using the inefficient decimal system, which you seem to prefer for some obscure reason, the alarm system is
        {DIALOG_CLOSE}, // [15] :: checked 651,983,225 times per hour.
        {NPC, 17}, // [16] :: CyberEyes implanted.
        {EXIT_R}, // [17] :: Thank you for stopping by, Doctor. I get lonely sometimes.
        {TO_JAIL}, // [18] :: Youre under arrest intruder!
        {DEATH}, // [19] :: Your body is set ablaze with excruciating pain, you collapse to the floor and...die.
        {DIALOG_CLOSE}, // [20] :: Unable to implant CyberEyes.
    };

    /**
     *
     * @param gs
     */
    @Override
    public void initRoom(GameState gs) {
        // lock door if still talking to Ratz.
        //gs.doorBottomLocked = gs.roomNpcTalk[gs.room.getIndex()];
        //gs.resourceManager.getRoomText(Room.R9).dumpList();
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

}
