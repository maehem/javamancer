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
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DIALOG_CLOSE;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.EXIT_B;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.LONG_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.NPC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.SHORT_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.TO_JAIL;
import com.maehem.javamancer.neuro.model.room.RoomExtras;

/**
 * Bank of Berne Lobby
 * 
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R34Extras extends RoomExtras { // Bank Berne Lobby

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC.num}, {SHORT_DESC.num}, //  [0] ::
        {3, 4, 5, 6}, // [2] :: And what, may I ask, do YOU want? This is a very exclusive bank.....
        {7}, // [3] :: I could use a cup of coffee about now. Why dont you make me a cup?
        {8}, // [4] :: Id like to see the Manager.
        {9}, // [5] :: Id like to open an account with your fine establishment.
        {10}, // [6] :: Id like to hold up the bank. Give me all your money.
        {DIALOG_CLOSE.num}, // [7] :: Why dont you drop dead.
        {11, 12, 13, 14}, // [8] :: I can make an appointment for you in 6 months, if youd like to wait.
        {13, 14}, // [9] :: Ha!  You need money to open an account with this bank.  I doubt you qualify in that category.
        {TO_JAIL.num}, // [10] :: Id like to see you dead, but Ill have to settle for your arrest.
        {15}, // [11] :: Id be happy to wait if it means Ill have the pleasure of your company.
        {16}, // [12] :: I demand to see the Manager right now!
        {16}, // [13] :: I have money! Really! And my greatest desire is to open an account here!
        {DIALOG_CLOSE.num}, // [14] :: Fine! Ill take my business elsewhere!
        {TO_JAIL.num}, // [15] :: Loitering in a bank is a federal offense. Im calling the lawbot.
        {NPC.num, 18}, // [16] :: Well...Ill see if I can find an application for you.  Wait here. This may take a while.
        {EXIT_B.num}, // [17] :: Get out of my bank!
        {DESC.num, DIALOG_CLOSE.num}, // [18] :: You notice that the door to the managers office is slightly ajar.
    };

    // Animation
    protected final int[][] ANIMATION_FLAGS = {
        {1}  // Person
    };

    @Override
    public int[][] getAnimationFlags() {
        return ANIMATION_FLAGS;
    }
    
    @Override
    public void initRoom(GameState gs) {
    }

    @Override
    public int[][] getDialogChain() {
        return DIALOG_CHAIN;
    }

    @Override
    public int dialogWarmUp(GameState gs) {
        // If entering from TOP, run 17 (kicked out).
        return 2;
    }

    @Override
    public int onDialogIndex(GameState gs, int index) {
        if ( index == 18 ) {
            // make lady disappear.
            ANIMATION_FLAGS[0][0] = 0;
        }
        return super.onDialogIndex(gs, index); 
    }
    
    

}
