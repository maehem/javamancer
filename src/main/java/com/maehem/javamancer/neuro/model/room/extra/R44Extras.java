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
import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.room.RoomExtras;
import java.util.Map;
import static java.util.Map.entry;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R44Extras extends RoomExtras { // Asano's

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC}, {SHORT_DESC}, //  [0][1]
        {3, 4, 5, 6, 7, 8}, // [2] :: Welcome to my humble shop!
        {DIALOG_CLOSE}, // [3] :: Leave me alone!  I said Im just
        {WORD1}, // [4] :: Tell me about the @---------------
        {17}, // [5] :: Uh, whats the, uh...cheapest
        {18}, // [6] :: Im just browsing right now.
        {19}, // [7] :: Why does Crazy Edo call you
        {20}, // [8] :: Sure and begorrah. Im looking
        {WORD1}, // [9] :: Tell me about the... npc:{ 27 || 28 || 29 }
        {17}, // [10] :: Uh, whats the, uh...cheapest..
        {19}, // [11] :: Why does Crazy Edo call
        {32}, // [12] :: Thanks for your help. COPTALK = 33
        {25}, // [13] :: Well, yes, kind of....
        {16}, // [14] :: Edo is my oldest
        {25}, // [15] :: Well, no, not really....
        {25}, // [16] :: Edo is a gnats eyeball!
        {ITEM_BUY}, // [17] :: Cheapest?  The Blue Light Special. [32 on no buy]
        {9, 21, 22, 23, 24}, // [18] :: Certainly. Can I answer any questions?
        {21, 22}, // [19] :: A pig?  Edo is the son of a turtle
        {ITEM_BUY}, // [20] :: Police?  Uh, I can sell you any legal
        {23}, // [21] :: You dont like Edo, do you?  Im pretty good at noticing these things.
        {24}, // [22] :: Ive heard Edo is a pretty good guy.
        {13, 14, 15, 16}, // [23] :: Edo is a goats armpit!
        {EXIT_L}, // [24] :: Edo sleeps with small animals!
        {9, 10, 11, 12}, // [25] :: Good.  I dont allow Crazy Edos friends
        {9}, // [26] :: I see you are a wise person.
        {9}, // [27] :: Thats a low-end model.
        {9}, // [28] :: Thats a cyberspace deck.
        {9}, // [29] :: You cant do any better than that.
        {EXIT_L}, // [30] :: Try Metro Holografix for softwarez! Come back soon!
        {9}, // [31] :: Never heard of that deck.
        {EXIT_L}, // [32] :: Come back when youre ready to buy.
        {EXIT_L}, // [33] :: Well, come back anytime, officer.
        {14, 16}, // [34] :: I hope you have reconsidered who your friends are, now.
    };

    /**
     *
     * Do you know about...
     *
     */
    private static final Map<String, Integer> map1 = Map.ofEntries(
            entry("blue light", 27),
            entry("188 bjb", 27),
            entry("350 sl", 27),
            entry("440 sdi", 27),
            entry("550 gt", 27),
            entry("uxb", 27),
            entry("xxb", 27),
            entry("zxb", 27),
            entry("software", 30),
            entry("softwarez", 30),
            entry("debug", 30),
            entry("comlink", 30),
            entry("repair", 30),
            entry("hardware repair", 30),
            entry("hiki-gaeru", 27),
            entry("gaijin", 27),
            entry("ninja 2000", 27),
            entry("ninja 3000", 27),
            entry("edokko", 27),
            entry("cyberspace ii", 28),
            entry("katana", 27),
            entry("cyberspace iii", 28),
            entry("tofu", 28),
            entry("ninja 4000", 28),
            entry("shogun", 28),
            entry("ninja 5000", 29),
            entry("samurai seven", 29),
            entry("samurai 7", 29),
            entry("cyberspace vii", 29),
            entry("cyberspace 7", 29),
            entry("ono-sendai", 28),
            entry("ono sendai", 28)
    );

    @Override
    public int askWord1(String word) {
        Integer index = map1.get(word);
        if (index == null) {
            return 31; // Doesn't know.
        }

        return index;
    }

    @Override
    public void initRoom(GameState gs) {
        // lock door if still talking to Ratz.
        //gs.doorBottomLocked = gs.roomNpcTalk[gs.room.getIndex()];
        //gs.resourceManager.getRoomText(Room.R44).dumpList();
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
