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
import static com.maehem.javamancer.neuro.model.room.DialogCommand.EXIT_X;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.LONG_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.NPC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.SHORT_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.TO_JAIL;
import com.maehem.javamancer.neuro.model.room.RoomExtras;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R28Extras extends RoomExtras { // JAL - Shuttle To Earth

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC.num}, {SHORT_DESC.num}, //  [0] ::
        {TO_JAIL.num}, // [2] :: Welcome aboard, criminal scum!  Didnt think we knew there was a warrant out for your arrest, eh?  Surprise!
        {NPC.num, 4}, // [3] :: Welcome aboard!  Our entire crew appreciates the fact that you chose to fly on our shuttle!  Domo arigato!
        {NPC.num, 5}, // [4] :: Please note there is only one exit. In the event of a fire on the ground, all passengers will have to fend for
        {NPC.num, 6}, // [5] :: themselves, because the crew and I will be the first ones out that door. In the event of a pressure loss while
        {NPC.num, 7}, // [6] :: were in transit, well all be sucking cold vacuum in a matter of seconds, so we hope you bought flight insurance.
        {NPC.num, 8}, // [7] :: During the flight, we will not be serving beverages or food of any kind.  We used to serve food, but the
        {NPC.num, 9}, // [8] :: portions we served were getting so small that the passengers couldnt see them any more.  Speaking of food,
        {NPC.num, 10}, // [9] :: if you are prone to space sickness, please do not throw up on the person next to you. In fact, wed prefer that
        {NPC.num, 11}, // [10] :: you not throw up at all since it makes quite a mess in weightlessness. If you must, put your head in a bag.
        {NPC.num, 12}, // [11] :: You will note that our in-flight holo- movie has just been zip-shot directly into your brain using psycho-graphics.
        {NPC.num, 13}, // [12] :: We hope you enjoyed it. There will be an additional charge if youd like to "see" the movie again.  And now, Im
        {EXIT_X.num}, // [13] :: sure it will come as big a surprise to you as it did to me that we have just arrived safely at our destination.
    };

    @Override
    public void initRoom(GameState gs) {
        // lock door if still talking to Ratz.
        //gs.doorBottomLocked = gs.roomNpcTalk[gs.room.getIndex()];
        //gs.resourceManager.getRoomText(Room.R28).dumpList();
    }

    @Override
    public double getNpcPosition() {
        return 550;
    }
    
    @Override
    public int[][] getDialogChain() {
        return DIALOG_CHAIN;
    }

    @Override
    public int dialogWarmUp(GameState gs) {
        // Is player to be arrested?  then set 2.

        return 3;

    }


}
