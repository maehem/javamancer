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
public class R52Extras extends RoomExtras { // Security Gate

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC}, {SHORT_DESC}, //  [0][1]
        {3, 4, 5, 6}, // [2] :: Kudasai, by which company are you employed?
        {14}, // [3] :: Top of the mornin!  Im a cop on my daily rounds.  Let me in.
        {12}, // [4] :: Im a volunteer for Hitachi Biotech.
        {DIALOG_CLOSE}, // [5] :: Sorry. I just stumbled in here by mistake.
        {WORD1}, // [6] :: I work for @---------------
        {}, // [7] ::
        {6}, // [8] :: You are not listed as an employee of the company you named. If you made a mistake, please try again.
        {TO_JAIL}, // [9] :: You are also not listed as an employee of that company.  Please remain here while I summon the authorities.
        {EXIT_R}, // [10] :: Domo arigato.  You are cleared for entry.
        {DIALOG_CLOSE}, // [11] :: This is not a Hitachi Volunteer Day. Come back tomorrow.
        {NPC, 13}, // [12] :: You are cleared for limited access. Please proceed directly North to Hitachi Biotech.  Be aware that
        {EXIT_R}, // [13] :: you will not be allowed admittance to any other buildings in this zone.
        {DIALOG_CLOSE}, // [14] :: We have our own security force. Your assistance is not required, officer.
        {DIALOG_CLOSE}, // [15] :: You appear to be lost. That company is not in the Chiba high-tech zone.
    };

    /**
     *
     * Do you know about...
     *
     */
    private static final Map<String, Integer> map1 = Map.ofEntries(
            entry("fuji", 10),
            entry("hosaka", 10),
            entry("musabori", 10),
            entry("hitachi", 10),
            entry("sense/net", 10),
            entry("sensenet", 10),
            entry("sense-net", 10),
            entry("sense net", 10)
    );

    @Override
    public int askWord1(String word) {
        Integer index = map1.get(word);
        // Check agains game state for employment.

        if (index == null) {
            return 15; // Doesn't know.
        }

        return index;
    }

    @Override
    public void initRoom(GameState gs) {
        // lock door if still talking to Ratz.
        //gs.doorBottomLocked = gs.roomNpcTalk[gs.room.getIndex()];
        gs.resourceManager.getRoomText(Room.R52).dumpList();

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
