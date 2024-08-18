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
import com.maehem.javamancer.neuro.model.room.RoomExtras;
import com.maehem.javamancer.neuro.model.item.Item;
import java.util.Map;
import static java.util.Map.entry;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R27Extras extends RoomExtras {

    protected static final int[][] DIALOG_CHAIN = { // Julius Dean
        {LONG_DESC}, {SHORT_DESC}, // 0, 1
        {3, 4, 5, 6}, // [2] :: What brings you around, boyo?  I deal in exotic hardware and information.
        {9}, // [3] :: Sure and begorrah.  Im a cop.
        {7}, // [4] :: Im just looking around right now.
        {8}, // [5] :: My friends tell me that someone is trying to kill me. Heard anything?
        {WORD1}, // [6] :: What do you know about @---------------
        {EXIT_L}, // [7] :: Go hang around someone elses office. Im a busy man.
        {11, 12}, // [8] :: Not always easy to know who your friends are, is it?  I havent heard anything about it.
        {EXIT_L}, // [9] :: Youre no cop. Youre a jerk. Get out of here!
        {}, // [10] ::
        {WORD1}, // [11] :: What do you know about @---------------
        {13}, // [12] :: Maybe the people from Cheap Hotel are after me?  I ran up a big bill there.
        {11}, // [13] :: Of course, if I did hear something, I might not be able to tell you.  Biz being what it is, you understand.
        {ITEM_BUY}, // [14] :: Ive got Bargaining, Psychoanalysis, Philosophy, and Phenomenology at $1000 each. Or I can upgrade certain skills.
        {SKILL_UPGRADE}, // [15] :: I can upgrade you in Cryptology for $2500 per level if you already have the skill chip.
        {11}, // [16] :: Sense/Net has all of those. I hear they even have Dixie Flatline on ROM.
        {11}, // [17] :: Try the Finn at Metro Holografix for that sort of thing.
        {11}, // [18] :: Dont know the name. Of course, if I knew, I might not be able to tell you.
        {11}, // [19] :: Its an old novel by William Gibson.
        {11}, // [20] :: Its a first generation, high-orbit family on Freeside, run like a corporation.  Rich and powerful.
        {11}, // [21] :: Artificial Intelligence.  Smart computers.  Dont mess with them or youll end up being flatlined.
        {11}, // [22] :: Special Forces types. I spent the war in Lisbon. Lovely place. Wonderful what a war can do for ones markets.
        {11}, // [23] :: Ive heard the password is "DUMBO", but thats probably in code.
        {11}, // [24] :: Ive heard the password is "OGRAF",but thats probably in code.
        {11}, // [25] :: Ive heard the password is "VULCAN" but thats probably in code.
        {11}, // [26] :: I think the link code is "YAKUZA".
        {11}, // [27] :: Ive heard the password is "PLEIADES",but thats probably in code.
        {ITEM_BUY, 11}, // [28] :: Only have one thing right now. Maybe youre interested in it.
        {11}, // [29] :: Just one word you need to remember: "GENESPLICE".
        {11}, // [30] :: Thats something I dont know about.
    };

    /**
     *
     * Do you know about...
     *
     */
    private static final Map<String, Integer> map1 = Map.ofEntries(
            entry("chip", 14),
            entry("chips", 14),
            entry("skill", 14),
            entry("skills", 14),
            entry("skill chip", 14),
            entry("bargaining", 14),
            entry("psychoanalysis", 14),
            entry("phenomonology", 14),
            entry("philosophy", 14),
            entry("train", 14),
            entry("training", 14),
            entry("cryptology", 15),
            entry("teach", 15),
            entry("instruct", 15),
            entry("upgrade", 15),
            entry("upgrades", 15),
            entry("rom construct", 16),
            entry("rom", 16),
            entry("rom card", 16),
            entry("software", 17),
            entry("wintermute", 18),
            entry("neuromancer", 19),
            entry("tessier-ashpool", 20),
            entry("ai", 21),
            entry("screaming fist", 22),
            entry("fuji", 23),
            entry("fuji electric", 23),
            entry("irs", 24),
            entry("hosaka", 25),
            entry("tozoku", 26),
            entry("musabori", 27),
            entry("hitachi", 29),
            entry("exotic", 28),
            entry("hardware", 28),
            entry("exotic hardware", 28)
    );

    @Override
    public int askWord1(String word) {
        Integer index = map1.get(word);
        // Check agains game state for employment.

        if (index == null) {
            return 30; // Doesn't know.
        }

        return index;
    }

    /**
     *
     * @param gs
     */
    @Override
    public void initRoom(GameState gs) {
        // lock door if still talking to Ratz.
        //gs.doorBottomLocked = gs.roomNpcTalk[gs.room.getIndex()];
        //gs.resourceManager.getRoomText(Room.R27).dumpList();
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
