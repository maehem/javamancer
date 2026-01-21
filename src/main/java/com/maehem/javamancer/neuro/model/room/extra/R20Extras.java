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
import static com.maehem.javamancer.neuro.model.room.DialogCommand.EXIT_R;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.EXIT_X;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.LONG_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.SHORT_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.WORD1;
import com.maehem.javamancer.neuro.model.room.RoomExtras;
import com.maehem.javamancer.neuro.model.skill.CopTalkSkill;
import com.maehem.javamancer.neuro.model.skill.MusicianshipSkill;
import java.util.Map;
import static java.util.Map.entry;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R20Extras extends RoomExtras { // Tug Marcus Garvey

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC.num}, {SHORT_DESC.num}, //  [0] ::
        {3, 4, 5, 6, 7, 8}, // [2] :: Maelcum a rude boy an a righteous tugpilot, mon.  I an I come a  spacedock soon.
        {9}, // [3] :: How long will it be before we arrive?
        {10}, // [4] :: What do you know about Freeside, man?
        {WORD1.num}, // [5] :: Do you know anything about @--------------- 
        {19}, // [6] :: Can you help me on Freeside, Maelcum?
        {20}, // [7] :: Have you got a jack I can use, man?
        {30}, // [8] :: I dont understand a word youve said. I feel like I need a Rasta Language skill chip.

        {17}, // [9] :: Don be long now, mseh dat.
        {6}, // [10] :: Freeside be a Babylon port, mon. Lots a banks there, ya know?
        {5}, // [11] :: Freeside be a Babylon port, mon. Lots a banks there, ya know?
        {5}, // [12] :: Not on this boy tug, mon.
        {5}, // [13] :: Call em Winter Mute.  The Mute played us a mighty dub once, ya know?  But Founders seh Mute be false prophet.
        {5}, // [14] :: That be Babylon, mon. You dealin wi th darkness there.
        {5, 7, 9}, // [15] :: Maelcum a rude boy, an a righteous tug pilot, mon.  I an I the Rastafarian navy, believe it.
        {5}, // [16] :: Don know th answer to that one, mon.

        {18}, // [17] :: Dont you guys ever think in hours?
        {3, 4, 5, 6, 7, 8}, // [18] :: Time, it be time, ya know wha mean? Dread at control, mon.
        {3, 4, 5, 6, 7, 8}, // [19] :: You listen, Babylon mon. I a warrior. But this no m fight, no Zion fight. Babylon eatin iself, ya know
        {3, 4, 5, 6, 7, 8}, // [20] :: Sorry, mon.  Jack burned out when th Case-mon here awhile back.
        {}, // [21] :: Don stan you, mon, but we mus move by Jah love, each one.
        {}, // [22] :: Righteous dub, mon. You ver good. [Use MUSICIANSHIP skill]
        {}, // [23] :: Ver funny, mon.  You soun jus like a cop. [Use COPTALK skill]
        {25, 26}, // [24] :: Okay, mon. I an I in Freeside spacedock.  Jah love, mon.  Get off m tug.     

        {EXIT_X.num}, // [25] :: Thanks for the ride, man.
        {27}, // [26] :: We finally arrived?  I thought youd never let me out of here!
        {EXIT_X.num}, // [27] :: Get off m tug, mon.  An don come back.
        {5}, // [28] :: Zion?  Zion be home, mon.
        {5}, // [29] :: Aerol seh the code is BOZOBANK, mon
        {3,4,5,6,7,8}, // [30] :: Use more than one skill, mon, and  cycle through the ones ya got.
    };

    /**
     *
     * Do you know about...
     *
     *
     */
    private static final Map<String, Integer> map1 = Map.ofEntries(
            entry("banking", 11),
            entry("bank", 11),
            entry("banks", 11),
            entry("virus", 12),
            entry("wintermute", 13),
            entry("maelcum", 15),
            entry("cyberspace", 14),
            entry("matrix", 25),
            entry("zion", 28),
            entry("link codes", 13),
            entry("link code", 25),
            entry("ai", 25)
    );

    @Override
    public int askWord1(GameState gs, String word) {
        Integer index = map1.get(word);

        if (gs.activeSkill instanceof CopTalkSkill) {
            return 23;
        }
        if ((index == 25 || index == 26) && gs.activeSkill instanceof MusicianshipSkill) {
            return 22;
        }
        if (index == null) {
            return 16; // Doesn't know.
        }

        return index;
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
        return 2;
    }

    @Override
    public int exitX(GameState gs) {
        // Exit depending on what ticket was purchased.
        return EXIT_R.num;
    }

}
