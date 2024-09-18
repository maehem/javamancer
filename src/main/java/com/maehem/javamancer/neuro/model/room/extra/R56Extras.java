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
import com.maehem.javamancer.neuro.model.room.Room;
import com.maehem.javamancer.neuro.model.room.RoomExtras;
import com.maehem.javamancer.neuro.model.item.Item;
import java.util.Map;
import static java.util.Map.entry;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R56Extras extends RoomExtras { // Sense Net

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC}, {SHORT_DESC}, //  [0][1]
        {}, // [2] :: You have 30 seconds to produce your security pass. Failure to comply will result in your removal.
        {}, // [3] :: Clearance approved. You now have limited access to the Librarian.
        {}, // [4] :: Please enter the identity number of the ROM Construct you require from the Sense/Net library vault:
        {}, // [5] :: Availability verified. Checkout is approved.
        {}, // [6] :: Identity number invalid. Try again if you made an error. You are allowed 3 library access attempts.
        {}, // [7] ::
        {}, // [8] :: Access denied. Security has been alerted. Please remain here until the authorities arrive.
        {}, // [9] :: @---------------
        {}, // [10] :: The computer gives you a ROM Construct.
        {}, // [11] :: You are kicked out of Sense/Net, for not producing a security pass.
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
    public int askWord1(GameState gs, String word) {
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
        gs.resourceManager.getRoomText(Room.R56).dumpList();

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

    @Override
    public int jackZone() {
        return 4;
    }

}
