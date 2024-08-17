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
public class R26Extras extends RoomExtras { // Street Light Pole - Irish Rose

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC}, // [0] :: One of Lonny Zones working girls is standing here in the street, leaning against a light tower. She carefully looks you over.
        {2, 3, 4, 5}, // [1] :: Hey, sailor. New in town?
        {6}, // [2] :: Top of the mornin! Youre under arrest unless you answer some questions.
        {8}, // [3] :: Im not a sailor. Do I look like one?
        {8}, // [4] :: Yes, Im new around here.  Why?
        {7}, // [5] :: Buzz off, sister. Zones a close personal friend of mine.
        {}, // [6] :: Drop dead.
        {}, // [7] :: In your eye, meatball.
        {9, 10, 11, 12, 13}, // [8] :: You look lost. Something I can do for you?
        {19}, // [9] :: Got any good software?
        {}, // [10] :: I doubt it. Im not lost.
        {}, // [11] :: I cant afford your kind of help.
        {WORD1}, // [12] :: Do you know anything about @---------------
        {WORD2}, // [13] :: Where is @---------------
        {}, // [14] ::
        {}, // [15] :: Buzz off, cybermo.
        {}, // [16] :: Some other time, then.
        {}, // [17] ::
        {9, 10, 11, 12}, // [18] :: Lonny was picked up by the feds for  tax evasion.  He said the wrong thing  to the judge and was given the death penalty.
        {10, 11, 12, 13}, // [19] :: Do I look like a Tekkie?
        {12}, // [20] :: Just one word you need to remember: "GENESPLICE".
        {12}, // [21] :: All I can do is give you a number to call: "JUSTICE".
        {12}, // [22] :: Yeah, sure. Ive got a degree in Computer Science and I just work the street for laughs. Give me a break!
        {12}, // [23] :: Hey, man, Im an Expert at massage, if you get my drift....
        {12}, // [24] :: Do I look like an encyclopedia?
        {12}, // [25] :: Dont mess with my mind, man.
        {12}, // [26] :: North of here, next to the Manyusha Wana.
        {12}, // [27] :: Im not good enough for you, is that it?  Find it yourself!
        {12}, // [28] :: Yeah, I thought you looked like one of them.  Its south of Cheap Hotel.
        {12}, // [29] :: Go west. Youll run right into it.  I do a lot of biz there.  Get my drift?
        {12}, // [30] :: East of here, in the high-tech part of town. But you cant get through the gate unless youre an employee.
        {12}, // [31] :: I dont know, man.  Give me a break.
        {DIALOG_END}, // [32] ::  The girl turns and struts away.
        {12}, // [33] :: You dont have enough money, man.
        {12}, // [34] :: I happen to be a friend of the police chief. A close friend if you know what I mean. So dont bug me, man.
        {12}, // [35] :: Akiko knows the link code for the  Bank of Zurich.
        {12}, // [36] :: I heard they set a trap for him in  front of the Matrix Restaurant.
        {12}, // [37] :: I have many "friends" there.  The  code is "BRAINSTORM".
        {12}, // [38] :: Just down the street past Crazy Edos
    };

    /**
     *
     * Do you know about...
     *
     */
    private static final Map<String, Integer> map1 = Map.ofEntries(
            entry("lonny", 36),
            entry("zone", 36),
            entry("lonny zone", 36),
            entry("microsofts", 26),
            entry("hardware", 31),
            entry("software", 31),
            entry("softwarez", 31),
            entry("hitachi", 30),
            entry("justice", 34),
            entry("cyberspace", 31),
            entry("matrix", 38),
            entry("massage", 23),
            entry("banks", 35),
            entry("armitage", 36),
            entry("copenhagen", 37),
            entry("university", 37)
    );

    /**
     *
     * Where is...
     *
     */
    private static final Map<String, Integer> map2 = Map.ofEntries(
            entry("cyberspace", 25),
            entry("shin", 26),
            entry("pawn shop", 26),
            entry("shin's", 26),
            entry("manyusha wana", 35),
            entry("gentleman loser", 28),
            entry("cheap hotel", 29),
            entry("hitachi", 30),
            entry("fuji", 30),
            entry("hosaka", 30),
            entry("musabori", 30),
            entry("sensenet", 30),
            entry("sense/net", 30),
            entry("matrix", 38),
            entry("restaurant", 38)
    );

    @Override
    public int askWord1(String word) {
        Integer index = map1.get(word);
        if (index == null) {
            return 24; // Doesn't know.
        }

        return index;
    }

    @Override
    public void initRoom(GameState gs) {
        // lock door if still talking to Ratz.
        //gs.doorBottomLocked = gs.roomNpcTalk[gs.room.getIndex()];
        gs.resourceManager.getRoomText(Room.R26).dumpList();
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
        return 1;

    }

    @Override
    public void dialogNoMore(GameState gs) {
        gs.roomNpcTalk[gs.room.getIndex()] = false;
    }

}
