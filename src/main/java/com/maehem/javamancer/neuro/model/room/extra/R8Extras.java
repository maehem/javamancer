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
import com.maehem.javamancer.neuro.model.JackZone;
import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.item.RealItem;
import com.maehem.javamancer.neuro.model.item.SkillItem;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.*;
import com.maehem.javamancer.neuro.model.room.RoomExtras;
import java.util.ArrayList;
import java.util.Map;
import static java.util.Map.entry;
import java.util.logging.Level;

/**
 * Gentleman Loser -- Room Extras
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R8Extras extends RoomExtras {

    protected static final int[][] DIALOG_CHAIN = { // G-Loser
        {LONG_DESC.num}, {SHORT_DESC.num}, // 0, 1
        {ON_FILTER_1.num}, // [2] :: Hey, geek!  Cmere!  I got somethin for ya!
        {8}, // [3] :: Sure and begorrah. Youre under arrest unless you answer some questions.
        {6}, // [4] :: Whatever it is, I hope its not contagious.
        {7}, // [5] :: Later. Ive got biz to attend to right now.
        {9, 10, 11, 12}, // [6] :: Anonymous was here earlier. If youre a friend of his, you know what Ive got for you.
        {DIALOG_CLOSE.num}, // [7] :: Suit yourself, cowboy.
        {EXIT_R.num}, // [8] :: Youre no cop!  Youre using a CopTalk skill chip!  Get out of here!
        {13}, // [9] :: A social disease?
        {14}, // [10] :: Is it smaller than a breadbox?
        {15}, // [11] :: Animal, vegetable, or mineral?
        {WORD1.num}, // [12] :: Ah!  You must be referring to the @---------------
        {DIALOG_CLOSE.num}, // [13] :: Beat it, cyberjerk!
        {DIALOG_CLOSE.num}, // [14] :: Its even smaller than your head, which is pretty small....
        {DIALOG_CLOSE.num}, // [15] :: No, actually I was referrin to somethin else, so get lost, wilson!
        {SKILL_BUY.num}, // [16] :: Yeah. You must be . I got your chip here for ya.
        {WORD1.num}, // [17] :: Okay.  What do you know about @---------------
        {SKILL_BUY.num}, // [18] :: Hey, Babe, I want to buy the chip.
        {22}, // [19] :: Maybe you could answer some questions for me?
        {DIALOG_CLOSE.num}, // [20] :: You already gave me something. I dont want anything else.
        {23}, // [21] :: How about coming back to my place?
        {17}, // [22] :: Sure. Itll be more fun than a poke in the eye with a sharp stick....
        {17, 18, 19, 20}, // [23] :: Forget it. You live at Cheap Hotel. I know all about your kind, wilson....
        {SKILL_BUY.num, 17}, // [24] :: All Ive got is Hardware Repair for $1000.
        {17}, // [25] :: Try Julius Deane for those chips.
        {17}, // [26] :: Julius Deane can upgrade your Cryptology skill chip.
        {17}, // [27] :: Hot stuff. Sense/Net has em all. They even have Dixie Flatline on ROM.
        {17}, // [28] :: Try the Finn at Metro Holografix for that sort of thing.
        {17}, // [29] :: Some kinda computer game, isnt it?
        {17}, // [30] :: Artificial Intelligence.  Smart computers owned by big companies. Just hope you never meet one, wilson.
        {17}, // [31] :: The Loser link code is LOSER. The password is "WILSON", which is a term you should be familiar with.
        {17}, // [32] :: Only a wilson would ask a question like that.
        {17}, // [33] :: I only have the coded password for Hitachi: "SELIM".
        {17}, // [34] :: The coded password for Copenhagen University is "KIKENNA".
        {ITEM_BUY.num}, // [35] :: Emperor Norton left you a Guest Pass for the Matrix Restaurant. He mumbled something about skills and upgrades.
        {17}, // [36] :: Ya got me. I dont know anythin about that.
        {17}, // [37] :: Shiva gives you a guest pass for the Matrix Restaurant.
        {39}, // [38] :: Shiva gives you your Cryptology chip.
        {SKILL_BUY.num, 19}, // [39] :: I also have Hardware Repair for sale for $1000.
        {DIALOG_CLOSE.num} // [40] :: I already gave it to you, cowboy.
    };

    /**
     * Do you know about...
     *
     */
    private static final Map<String, Integer> map1 = Map.ofEntries(
            entry("cryptology", 16),
            entry("skill", 16),
            entry("chip", 16),
            entry("skill chip", 16)
    );

    /**
     * Do you know about... level 2
     */
    private static final Map<String, Integer> map2 = Map.ofEntries(
            entry("chips", 24),
            entry("skill", 24),
            entry("chip", 24),
            entry("skill chip", 24),
            entry("skills", 24),
            entry("hardware", 24),
            entry("repair", 24),
            entry("bargaining", 24),
            entry("psychoanalysis", 24),
            entry("phenomenology", 25),
            entry("train", 26),
            entry("training", 26),
            entry("upgrade", 26),
            entry("upgrades", 26),
            entry("cryptology", 26),
            entry("teach", 26),
            entry("instruct", 26),
            entry("rom construct", 27),
            entry("rom", 27),
            entry("romcard", 27),
            entry("software", 28),
            entry("neuromancer", 29),
            entry("ai", 30),
            entry("loser", 31),
            entry("gentleman loser", 31),
            entry("loser database", 31),
            entry("loser db", 31),
            entry("wilson", 32),
            entry("hitachi", 33),
            entry("hitachi biotech", 33),
            entry("copenhagen", 34),
            entry("university", 34),
            entry("matrix", 35),
            entry("guest pass", 35),
            entry("pass", 35),
            entry("restaurant", 35),
            entry("norton", 35),
            entry("emperor", 35),
            entry("emperor norton", 35)
    );

    @Override
    public JackZone jackZone() {
        return JackZone.ONE;
    }

    @Override
    public boolean hasPAX() {
        return true;
    }

    @Override
    public int askWord1(GameState gs, String word) {
        LOGGER.log(Level.FINE, "RoomExtra8: Ask Word: {0}", word);
        Integer index;
        if (gs.shivaGaveChip) {
            index = map2.get(word);
            LOGGER.log(Level.INFO, () -> "Return map2 index: " + index);
        } else {
            index = map1.get(word);
        }
        if (index == null) {
            return 36; // Doesn't know.
        }

        return index;
    }

    /**
     *
     * @param gs
     */
    @Override
    public void initRoom(GameState gs) {
        //gs.resourceManager.getRoomText(Room.R8).dumpList();
        onVendFinishedOpenDialog = true;
    }

    @Override
    public int[][] getDialogChain() {
        return DIALOG_CHAIN;
    }

    @Override
    public int dialogWarmUp(GameState gs) {
        if (gs.shivaChipMentioned) {
            if (gs.shivaGaveChip) {
                return 2; // Maybe answer some questions.
            } else {
                return 16; // You must be ...
            }
        }

        return 2;
    }

    @Override
    public ArrayList<SkillItem> getVendSkillItems(GameState gs) {
        ArrayList<SkillItem> list = new ArrayList<>();
        if (gs.shivaGaveChip) {
            list.add(new SkillItem(Item.Catalog.HARDWAREREPAIR, 1, 1000));
        } else {
            gs.shivaChipMentioned = true;
            list.add(new SkillItem(Item.Catalog.CRYPTOLOGY, 1, 0));
        }
        return list;
    }

    @Override
    public boolean onSkillVendFinished(GameState gs) {
        if (gs.hasInventoryItem(Item.Catalog.CRYPTOLOGY)) {
            gs.shivaGaveChip = true;
        }

        return false; // Don't open new dialog.
    }

    @Override
    public ArrayList<Item> getVendItems(GameState gs) {
        ArrayList<Item> list = new ArrayList<>();

        list.add(new RealItem(Item.Catalog.GUESTPASS, 0, 1));

        return list;
    }

    @Override
    public boolean onVendItemsFinished(GameState gs) {
        if (gs.hasInventoryItem(Item.Catalog.GUESTPASS)) {
            gs.shivaGavePass = true;
            return true;
        }
        return false;
    }

    @Override
    public int[] onFilter1(GameState gs) {
        if (!gs.shivaGaveChip) {
            return new int[]{4, 5};
        } else {
            return new int[]{17, 18}; // What do you know about
        }
    }

    @Override
    public double getNpcPosition() {
        return 100;
    }

}
