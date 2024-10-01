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
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DEATH;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DIALOG_END;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.EXIT_BDSHOP;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.EXIT_ST_CHAT;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.FINE_BANK_20K;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.FINE_BANK_500;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.LONG_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.NPC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.SHORT_DESC;
import com.maehem.javamancer.neuro.model.room.RoomExtras;

/**
 * TODO: Last few dialog elements need tuning and testing.
 *
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R3Extras extends RoomExtras {

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC.num}, {SHORT_DESC.num}, // 0, 1
        {NPC.num, 3}, // 2   "You have been charged"
        {4}, // 3    "I'd like to offer my services"
        {5, 6, 7, 8}, // 4  Judge: He's right
        {NPC.num, 18}, // 5  Fine, I'll pay lawyer fee.
        {NPC.num, 9}, // 6  I can't afford lawyer.
        {NPC.num, 9}, // 7  I don't need lawyer.
        {10}, // 8  This is an outrage.
        {10}, // 9  OK. defend yourself.
        {11, 12, 13, 14}, // 10 How do you plead.
        {15}, // 11 This is an outrage. I haven't...
        {15}, // 12 I'm innocent.
        {15}, // 13 I'm guilty.
        {15}, // 14 I'm insane.
        {NPC.num, 16}, // 15 The court notes your plea.
        {NPC.num, 17}, // 16 Ha! Guilty.
        {FINE_BANK_500.num, EXIT_ST_CHAT.num}, // 17 Guilty. You must remain.
        {NPC.num, 19}, // 18 Wise choice..
        {20}, // 19 How does the defendant plead.
        {21, 22, 23, 24}, // 20 (lawyer)Guilty your honor.
        {26}, // 21 What? This is an outrage.
        {26}, // 22 I throw myself on the mercy of the court.
        {26}, // 23 I'm insane!.
        {NPC.num, 25}, // 24 You call yourself a lawyer?
        {26}, // 25 Trust me I know what I'm doing.
        {27, 28, 29}, // 26 The court finds the defendant guilty. 500 fine.
        {30}, // 27 This is a blantant miscarriage of justice.
        {30}, // 28 I'll get you for this.
        {30}, // 29 I'm sorry. I've learned the error of my ways..
        {FINE_BANK_500.num, EXIT_ST_CHAT.num}, // 30 You are free to go.
        {32}, // 31 You are becoming a familiar face.
        {33, 34, 35, 36, 37, 38, 39}, // 32 Death penalty. Give him the death penalty.
        {44}, // 33 Oh put a sock in it.
        {40}, // 34 It's not my fault! I'm the product of...
        {40}, // 35 I can't help it.
        {41}, // 36 Come on. Let's get this over with.
        {42}, // 37 Does it matter.
        {43}, // 38 I'm innocent.
        {44}, // 39 I'm guilty.
        {44}, // 40 That's what they all say.
        {45}, // 41 It's your life.
        {NPC.num, 43}, // 42 Stop groveling.
        {NPC.num, 45}, // 43 Innocent? Ha!
        {NPC.num, 45}, // 44 This is obviously a hardened criminal...
        {NPC.num, 46}, // 45 In view of your criminal history...
        {DEATH.num, EXIT_BDSHOP.num}, // 46 I hereby sentence you to death...
        {FINE_BANK_20K.num, EXIT_ST_CHAT.num}, // 43 I hereby pronouce you guilty as charged. 20000 fine.
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
        if (!gs.roomCanTalk()) {
            return DIALOG_END.num;
        }
        if (gs.ratzPaid) {
            return 11;
        } else {
            return 2;
        }
    }

}
