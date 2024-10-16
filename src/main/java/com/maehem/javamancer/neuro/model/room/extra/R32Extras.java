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
import com.maehem.javamancer.neuro.model.item.RealItem;
import com.maehem.javamancer.neuro.model.item.SkillItem;
import com.maehem.javamancer.neuro.model.item.SoftwareItem;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DIALOG_CLOSE;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.ITEM_BUY;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.LONG_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.SHORT_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.SKILL_BUY;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.SOFTWARE_BUY;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.WORD1;
import com.maehem.javamancer.neuro.model.room.RoomExtras;
import java.util.ArrayList;
import java.util.Map;
import static java.util.Map.entry;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R32Extras extends RoomExtras { // Metro Holographix

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC.num}, {SHORT_DESC.num}, //  [0] :: Youre in a narrow canyon
        {3, 4, 5, 6}, // [2] :: Hey, kid!  You need chips or software?  I just got some new stuff from those bridge-and-tunnel kids in Jersey.
        {10}, // [3] :: Sure and begorrah. You wouldnt be selling illegal software, would you?
        {20}, // [4] :: Yeah, Finn, Im looking for some hot softwarez.
        {8}, // [5] :: Im just browsing right now.
        {9}, // [6] :: I need a scan, Finn. Then, maybe Ill buy something.
        {}, // [7] ::
        {11, 12}, // [8] :: Hope youre not allergic to dust. Can I answer any questions?
        {11, 12}, // [9] :: Scanned when you came in. No implants,no biologicals. Youre clean.
        {DIALOG_CLOSE.num}, // [10] :: Geez, my mistake, Im fresh out of inventory today, officer!  Sorry.
        {WORD1.num, 15}, // [11] :: Tell me about @---------------
        {13}, // [12] :: Leave me alone!  I said Im just browsing!
        {14, 15}, // [13] :: Well, excuse me! Sounds like someones been having a rough day!
        {16}, // [14] :: Hey, Finn, did anyone ever tell you your head looks like it was designed in a wind tunnel?
        {WORD1.num}, // [15] :: Okay, what do you know about @---------------
        {DIALOG_CLOSE.num}, // [16] :: You got about as much class as those yahoos from Jersey. Get out of here!
        {SKILL_BUY.num}, // [17] :: Ive got Icebreaking and Debug skill chips for $1,000 each.
        {ITEM_BUY.num}, // [18] :: So, youre on a holy mission, eh? I got what you need.
        {15}, // [19] :: Youll have to hit Sense/Net for one of those.
        {SOFTWARE_BUY.num}, // [20] :: You want software, you got software.
        {15}, // [21] :: Its an AI with Swiss citizenship. Built for Tessier-Ashpool.
        {15}, // [22] :: Its an old novel by William Gibson.
        {15}, // [23] :: Its a first generation, high-orbit family on Freeside, run like a corporation.  Rich and powerful.
        {15}, // [24] :: Artificial Intelligence.  A smart computer.  They design all the ice.
        {15}, // [25] :: Military. Not too successful. Didnt they teach you history in school?
        {15}, // [26] :: Dont know about that.
    };

    /**
     *
     * Do you know about...
     *
     */
    private static final Map<String, Integer> map1 = Map.ofEntries(
            entry("chip", 17),
            entry("chips", 17),
            entry("skill", 17),
            entry("skills", 17),
            entry("skill chip", 17),
            entry("debug", 17),
            entry("icebreaking", 17),
            entry("ice", 17),
            entry("joystick", 18),
            entry("holy joystick", 18),
            entry("pong", 18),
            entry("rom construct", 19),
            entry("rom", 19),
            entry("software", 20),
            entry("softwarez", 20),
            entry("warez", 37),
            entry("wintermute", 21),
            entry("neuromancer", 22),
            entry("tessier-ashpool", 23),
            entry("ai", 24),
            entry("screaming fist", 25)
    );

    @Override
    public int askWord1(GameState gs, String word) {
        Integer index = map1.get(word);
        if (index == null) {
            return 26; // Doesn't know.
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
    public ArrayList<Item> getVendItems(GameState gs) {
        ArrayList<Item> list = new ArrayList<>();
        list.add(new RealItem(Item.Catalog.JOYSTICK, 20, 1));
        return list;
    }

    @Override
    public ArrayList<SkillItem> getVendSkillItems(GameState gs) {
        ArrayList<SkillItem> list = new ArrayList<>();
        list.add(new SkillItem(Item.Catalog.ICEBREAKING, 1, 1000));
        list.add(new SkillItem(Item.Catalog.DEBUG, 1, 1000));
        return list;
    }

    @Override
    public ArrayList<SoftwareItem> getVendSoftwareItems(GameState gs) {
        ArrayList<SoftwareItem> list = new ArrayList<>();
        list.add(new SoftwareItem(Item.Catalog.DECODER, 1, 750));
        list.add(new SoftwareItem(Item.Catalog.BLOWTORCH, 1, 750));
        list.add(new SoftwareItem(Item.Catalog.DRILL, 1, 1500));
        list.add(new SoftwareItem(Item.Catalog.PROBE, 1, 500));
        list.add(new SoftwareItem(Item.Catalog.COMLINK, 1, 100));
        return list;
    }

}
