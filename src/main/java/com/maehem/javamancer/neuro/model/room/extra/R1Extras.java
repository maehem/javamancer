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
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DIALOG_CLOSE;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DIALOG_END;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.LONG_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.SHORT_DESC;
import com.maehem.javamancer.neuro.model.room.RoomExtras;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R1Extras extends RoomExtras {

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC.num}, {SHORT_DESC.num}, // 0, 1
        {3, 4, 5, 6}, // 2   "I don't care if you eat that spaghetti..."
        {7}, // 3    "Sorry. I can't afford it..."
        {7}, // 4
        {7}, // 5
        {8}, // 6
        {3, 4, 5, 6}, // 7
        //{3, 4, 5, 6}, // 8
        {DIALOG_CLOSE.num}, // 8
        {}, // 9
        {}, // 10
        {12, 13, 14}, // 11
        {15}, // 12
        {16}, // 13
        {19}, // 14
        {12, 13, 14}, // 15
        {12, 13, 14}, // 16
        {}, // 17
        {}, // 18
        {23, 24}, // 19
        {}, // 20
        {}, // 21
        {}, // 22
        {25}, // 23
        {26}, // 24
        {24}, // 25
        {27, 28}, // 26
        {29}, // 27
        {30}, // 28
        {DIALOG_END.num}, // 29 ::  99 == npc no longer responds to player
        {DIALOG_END.num}, // 30
        {31}, //31  87 = player gives npc less than 46 credits.
        {2} // 32 Response to underpayment.
    };

    // Animation
    // Load at start:
    // Ratz Face    :: entry1 :
    //                anim00
    // Ratz Arm     :: entry2 :
    //                anim00
    // Sign Dots    :: entry3 :
    //                anim00,anim01,anim02
    // Sign Letters :: entry4 :
    //                anim00 - anim07
    
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
        if (gs.roomCanTalk()) { // TODO: whoever calls this should check roomCanTalk()
            if (gs.ratzPaid) {
                return 11;
            } else {
                return 2;
            }
        } else {
            return DIALOG_END.num;
        }
    }

    @Override
    public boolean hasPAX() {
        return true;
    }

}
