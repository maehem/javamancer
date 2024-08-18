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
import java.util.Map;
import static java.util.Map.entry;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R23Extras extends RoomExtras { // Panther Moderns

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC}, {SHORT_DESC}, //  [0] ::
        {3, 4, 5, 6}, // [2] :: You got past Larry. Thats good. You wont get past me. Thats business.
        {10}, // [3] :: Top of the mornin.  I arrested your friend and Ill do the same to you unless you answer some questions.
        {7}, // [4] :: Lupus, my man!  I hear youre the kind of guy who helps stray cowboys.  Can you answer some questions for me?
        {8}, // [5] :: Geez, youre really a funny-looking dweeb, arent you?
        {9}, // [6] :: Exactly what is a Panther Modern?
        {11}, // [7] :: Matt Shaw says youre all right.  So talk.  What do you want to know?
        {11}, // [8] :: Dont like you, either. Not Modern. Biz will cost more now.
        {4, 5}, // [9] :: Chaos. That is our mode and modus. That is our central kick. Believe it.
        {EXIT_B}, // [10] :: Its not morning and youre not a cop.Drop the act and take a hike.
        {WORD1}, // [11] :: What do you know about @---------------
        {ITEM_BUY}, // [12] :: I can sell you an Evasion skill chip for $5000.  Youll need it for protection in cyberspace.
        {11}, // [13] :: Maybe youre okay. Anything else you want to ask me?
        {}, // [14] :: Have it your way. // Closes evasion buy
        {ITEM_BUY, 11}, // [15] :: If you want a ROM Construct from Sense/Net, I can sell you a Security Pass to get you into the building.
        {11}, // [16] :: Sense/Net has all the ROM Constructs in their vault. Hard to get in there.
        {ITEM_BUY}, // [17] :: I can sell you an Evasion skill chip for $2000.  Youll need it for protection in cyberspace.
        {11}, // [18] :: Dont know what Evasion skill does. I bought it from a cowboy.
        {11}, // [19] :: Ive be siphoning from account number 646328356481, for years.
        {11}, // [20] :: They have a three letter password: "GNU"  Might be in code.
        {11}, // [21] :: Try Julius Deane.
        {11}, // [22] :: Talk to Julius Deane.
        {11}, // [23] :: Try the Finn. I hear Drill 1.0 is a great icebreaker.
        {11}, // [24] :: Sounds familiar. Its an old computer game, or a book, or something like that, right?
        {11}, // [25] :: Turing knows about them.  Try cycling through your skills in combat.  I hear that one is not enough.
        {11}, // [26] :: Only a wilson would ask a question like that.
        {11}, // [27] :: Big company. Zaibatsu. Real high-tech.
        {11}, // [28] :: Matt Shaw goes there sometimes. Really likes the place.
        {11}, // [29] :: Cant help you with that.
        {}, // [30] ::
        {}, // [31] ::
        {11}, // [32] :: Dont be a smart mouth this time.
        {11}, // [33] :: Cyberspace is reality, wilson.
    };

    /**
     *
     * Do you know about...
     *
     */
    private static final Map<String, Integer> map1 = Map.ofEntries(
            entry("sense/net", 16),
            entry("sensenet", 16),
            entry("sense net", 16),
            entry("security", 16),
            entry("pass", 16),
            entry("rom construct", 16),
            entry("rom", 16),
            entry("chip", 12),
            entry("chips", 12),
            entry("skill", 12),
            entry("skills", 12),
            entry("evasion", 18),
            entry("bank", 19),
            entry("gemeinschaft", 19),
            entry("tozoku", 20),
            entry("bargaining", 21),
            entry("psychoanalysis", 21),
            entry("phenomenology", 21),
            entry("upgrade", 21),
            entry("upgrades", 21),
            entry("train", 22),
            entry("teach", 22),
            entry("instruct", 22),
            entry("cryptology", 22),
            entry("software", 23),
            entry("easy rider", 23),
            entry("neuromancer", 24),
            entry("ai", 25),
            entry("wilson", 26),
            entry("hitachi", 27),
            entry("fuji", 27),
            entry("hosaka", 27),
            entry("musabori", 27),
            entry("matrix", 28),
            entry("guest pass", 29),
            entry("restaurant", 29),
            entry("cyberspace", 33)
    );

    @Override
    public void initRoom(GameState gs) {
        // lock door if still talking to Ratz.
        //gs.doorBottomLocked = gs.roomNpcTalk[gs.room.getIndex()];
        gs.resourceManager.getRoomText(Room.R23).dumpList();
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

}
