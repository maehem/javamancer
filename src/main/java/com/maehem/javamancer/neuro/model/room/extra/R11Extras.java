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
import com.maehem.javamancer.neuro.model.room.Room;
import com.maehem.javamancer.neuro.model.room.RoomExtras;
import java.util.Map;
import static java.util.Map.entry;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R11Extras extends RoomExtras { // Zion

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC}, {SHORT_DESC}, //  [0] ::
        {3, 4, 5}, // [2] :: Measure twice, cut once, mon. Have you come up the gravity well out of Babylon to lead the Tribes home?
        {6}, // [3] :: Excuse me?
        {7}, // [4] :: Uh, yeah, sure...
        {8}, // [5] :: I think youve confused me with someone else.
        {9, 10, 11, 12}, // [6] :: Soon come, the Final Days...Voices. Voices cryin inna wilderness, prophesyin ruin unto Babylon....
        {9, 10, 11, 12}, // [7] :: You bring a scourge on Babylon, on its darkest heart, mon. You are the tool of the Final Days.
        {9, 10, 11, 12}, // [8] :: Babylon mothers many demon, I an I know.  Multitude horde!
        {14}, // [9] :: Right. Can I get a ride to Freeside from here?
        {14}, // [10] :: Id like to pay for a ride back to  Chiba City.
        {13}, // [11] :: Do you speak English or what, you crusty old wilson!
        {WORD1}, // [12] :: Do you know anything about @---------------
        {12}, // [13] :: This no m fight, mon. I an I only sit here an a listn to the dub.
        {ITEM_BUY}, // [14] :: For $500, the JAL shuttle take you back down the well, mon.
        {}, // [15] :: Don want you here no mo, mon. Back down the well wit ya.
        {12}, // [16] :: Freeside a Babylon port, mon. Several banks there, ya know?  Which one you askin bout?
        {12}, // [17] :: Call em Winter Mute.  The Mute played us a mighty dub once, ya know?  But now I an I seh Mute be false prophet.
        {12}, // [18] :: Maelcum a rude boy, an a righteous tug pilot, mon.
        {12}, // [19] :: Aerol seh BG1066 get him in th fron door there, mon.
        {12}, // [20] :: Dub be the music, mon. I an I have great respect for dub musicians, ya know?
        {12}, // [21] :: Babylon mothers many demon, I an I know.  Multitude horde!
        {12}, // [22] :: Don know th answer to that one, mon.
        {12}, // [23] :: Righteous dub, mon!  You ver good. Maelcum will help you reach Freeside on his tug, Garvey.
        {12}, // [24] :: I an I don like your kind of music. Sorry, mon.
        {12}, // [25] :: Okay, mon. Look. If you be promisin NOT to play more music, I an I will pay your fare back down the well!
        {12}, // [26] :: Ver funny, mon.  You soun jus like a cop.
        {12}, // [27] :: Or be you the tool o the demons. A tool of the banks.
        {12}, // [28] :: A gorgeous babe I an I once loved.
        {12}, // [29] :: Zion?  Zion be home, mon.
        {12}, // [30] :: You don have the cash, mon?  Join us  and play some dub, then.
    };

    /**
     *
     * Do you know about...
     *
     */
    private static final Map<String, Integer> map1 = Map.ofEntries(
            entry("banking", 16),
            entry("freeside", 16),
            entry("bank", 16),
            entry("banks", 16),
            entry("wintermute", 17),
            entry("maelcum", 18),
            entry("gemeinschaft", 18),
            entry("dub", 23),
            entry("musician", 25),
            entry("music", 25),
            entry("babylon", 27),
            entry("marcy", 28),
            entry("zion", 29)
    );

    @Override
    public int askWord1(GameState gs, String word) {
        Integer index = map1.get(word);
        // Check agains game state for employment.

        if (index == null) {
//            if ( gs.skill == COPTALK ) {
//                return 26;
//            }
            return 22; // Doesn't know.
        }

        return index;
    }

    @Override
    public void initRoom(GameState gs) {
        // lock door if still talking to Ratz.
        //gs.doorBottomLocked = gs.roomNpcTalk[gs.room.getIndex()];
        gs.resourceManager.getRoomText(Room.R11).dumpList();
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

    @Override
    public int exitX(GameState gs) {
        // Exit depending on what ticket was purchased.
        return EXIT_R;
    }

}
