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
import java.util.Map;
import static java.util.Map.entry;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R46Extras extends RoomExtras { // Matrix Restaurant

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC}, {SHORT_DESC}, //  [0][1]
        {}, // [2] :: ...so I was larking around Rio heavy commerce sector when I see this white cube. It was an AI, listed on the
        {}, // [3] :: Turing Registry. Figured Id try to cut the ice. Hit the first layer and flatlined. My joeboy smelled the skin
        {}, // [4] :: frying and pulled the trodes off me.
        {}, // [5] :: I know what you mean. Tried it myself once. That ice was bad news. Maybe an AI got old Bosch.  He was worried when
        {}, // [6] :: I last talked to him at Gridpoint. He thought some AI had found a way to make itself smarter!  You believe it?
        {}, // [7] :: No. Nobody trusts an AI. Every AI ever built has an electromagnetic shotgun wired to its forehead. Turing would
        {9, 10}, // [8] :: wipe it right away....Hey!  Look who wandered in when we left the door open!
        {12}, // [9] :: Got any old chips you want to sell?
        {WORD1}, // [10] :: Hey, what do you know about @---------------
        {}, // [11] ::
        {ITEM_BUY}, // [12] :: Have I got chips!  Ive got Logic, Software Analysis, and Musicianship. For you, Ill charge $1000 each.
        {}, // [13] ::
        {10}, // [14] :: Nobodys seen Bosch lately. We think maybe he hit some black ice out in cyberspace. Matt Shaw saw him last.
        {10}, // [15] :: Sense/Net has em all. And the only people who ever got in there were the Panther Moderns.
        {10}, // [16] :: Cant help you with those. Try Julius.
        {10}, // [17] :: Sure, we can raise a few of your skills to level 2, and Debug to  level 4. $100 for each skill level.
        {10}, // [18] :: Great guy. Hes been real helpful in the past. Big ego, though.
        {10}, // [19] :: I know them for the Loser, ESFA, and NASA.  Which ones do you need?
        {10}, // [20] :: The password for level 2 is "TURNIP" You can decode it yourself.
        {10}, // [21] :: The password is "EGGPLANT" You can decode it yourself.
        {10}, // [22] :: The password is "GALILEO" You can decode it yourself.  It has the only AI I know of that never killed anyone.
        {10}, // [23] :: Some kinda computer game, isnt it?
        {10}, // [24] :: Artificial Intelligence. Hang around cyberspace long enough and youll meet one. Never annoy an AI.
        {10}, // [25] :: You must know what a wilson is!
        {10}, // [26] :: Some things youll have to figure out for yourself.  This is one of them.
        {}, // [27] ::
        {EXIT_T}, // [28] :: Youre kicked out for not having a pass.
    };

    /**
     *
     * Do you know about...
     *
     */
    private static final Map<String, Integer> map1 = Map.ofEntries(
            entry("anonymous", 14),
            entry("bosch", 14),
            entry("anonymous bosch", 14),
            entry("rom", 15),
            entry("rom construct", 15),
            entry("dixie", 15),
            entry("bargaining", 16),
            entry("psychoanalysis", 16),
            entry("phenomenology", 16),
            entry("upgrade", 17),
            entry("upgrades", 17),
            entry("train", 17),
            entry("teach", 17),
            entry("instruct", 17),
            entry("zen", 17),
            entry("evasion", 17),
            entry("analysis", 17),
            entry("repair", 17),
            entry("debug", 17),
            entry("matt shaw", 18),
            entry("passwords", 19),
            entry("password", 19),
            entry("loser", 20),
            entry("gentleman loser", 20),
            entry("esfa", 21),
            entry("eastern seaboard", 21),
            entry("nasa", 22),
            entry("neuromancer", 23),
            entry("ai", 24),
            entry("wilson", 25)
    );

    @Override
    public int askWord1(String word) {
        Integer index = map1.get(word);
        if (index == null) {
            return 26; // Doesn't know.
        }

        return index;
    }

    @Override
    public void initRoom(GameState gs) {
        // lock door if still talking to Ratz.
        //gs.doorBottomLocked = gs.roomNpcTalk[gs.room.getIndex()];
        //gs.resourceManager.getRoomText(Room.R46).dumpList();

        // TODO: No Pass. Kick out.
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
