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
import com.maehem.javamancer.neuro.model.item.SkillItem;
import com.maehem.javamancer.neuro.model.room.RoomExtras;
import java.util.ArrayList;
import java.util.Map;
import static java.util.Map.entry;

/**
 * Gentleman Loser -- Room Extras
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R8Extras extends RoomExtras {

    protected static final int[][] DIALOG_CHAIN = { // G-Loser
        {LONG_DESC}, {SHORT_DESC}, // 0, 1
        {4, 5}, // [2] :: Hey, geek!  Cmere!  I got somethin for ya!
        {8}, // [3] :: Sure and begorrah. Youre under arrest unless you answer some questions.
        {6}, // [4] :: Whatever it is, I hope its not contagious.
        {7}, // [5] :: Later. Ive got biz to attend to right now.
        {9, 10, 11, 12}, // [6] :: Anonymous was here earlier. If youre a friend of his, you know what Ive got for you.
        {DIALOG_CLOSE}, // [7] :: Suit yourself, cowboy.
        {EXIT_R}, // [8] :: Youre no cop!  Youre using a CopTalk skill chip!  Get out of here!
        {13}, // [9] :: A social disease?
        {14}, // [10] :: Is it smaller than a breadbox?
        {13}, // [11] :: Animal, vegetable, or mineral?
        {WORD1}, // [12] :: Ah!  You must be referring to the @---------------
        {DIALOG_CLOSE}, // [13] :: Beat it, cyberjerk!
        {DIALOG_CLOSE}, // [14] :: Its even smaller than your head, which is pretty small....
        {DIALOG_CLOSE}, // [15] :: No, actually I was referrin to somethin else, so get lost, wilson!
        {17, 18, 19, 20, 21}, // [16] :: Yeah. You must be . I got your chip here for ya.
        {WORD1}, // [17] :: Okay.  What do you know about @---------------
        {ITEM_BUY}, // [18] :: Hey, Babe, I want to buy the chip.
        {22}, // [19] :: Maybe you could answer some questions for me?
        {DIALOG_CLOSE}, // [20] :: You already gave me something. I dont want anything else.
        {23}, // [21] :: How about coming back to my place?
        {17}, // [22] :: Sure. Itll be more fun than a poke in the eye with a sharp stick....
        {17, 18, 19, 20}, // [23] :: Forget it. You live at Cheap Hotel. I know all about your kind, wilson....
        {ITEM_BUY}, // [24] :: All Ive got is Hardware Repair for $1000.
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
        {38}, // [35] :: Emperor Norton left you a Guest Pass for the Matrix Restaurant. He mumbled something about skills and upgrades.
        {17}, // [36] :: Ya got me. I dont know anythin about that.
        {ITEM_GET, 17}, // [37] :: Shiva gives you a guest pass for the Matrix Restaurant.
        {ITEM_GET, 17}, // [38] :: Shiva gives you your Cryptology chip.
        {ITEM_BUY}, // [39] :: I also have Hardware Repair for sale for $1000.
        {DIALOG_CLOSE}, // [40] :: I already gave it to you, cowboy.
    };

    /**
     * Do you know about...
     *
     */
    private static final Map<String, Integer> map1 = Map.ofEntries(
            entry("cryptology", 38),
            entry("skill", 38),
            entry("chip", 39),
            entry("skill chip", 39)
    );

    /**
     * Do you know about... level 2
     */
    private static final Map<String, Integer> map2 = Map.ofEntries(
            entry("chips", 38),
            entry("skill", 38),
            entry("chip", 39),
            entry("skill chip", 39),
            entry("skills", 39),
            entry("hardware repair", 39),
            entry("bargaining", 39),
            entry("psychoanalysis", 39),
            entry("phenomenology", 39),
            entry("train", 39),
            entry("training", 39),
            entry("upgrade", 39),
            entry("upgrades", 39),
            entry("cryptology", 39),
            entry("teach", 39),
            entry("instruct", 39),
            entry("rom construct", 39),
            entry("rom", 39),
            entry("romcard", 39),
            entry("software", 39),
            entry("neuromancer", 39),
            entry("ai", 39),
            entry("loser", 39),
            entry("gentleman loser", 39),
            entry("loser database", 39),
            entry("loser db", 39),
            entry("wilson", 39),
            entry("hitachi", 39),
            entry("hitachi biotech", 39),
            entry("copenhagen", 39),
            entry("university", 39),
            entry("matrix", 39),
            entry("guest pass", 39),
            entry("pass", 39),
            entry("restaurant", 39),
            entry("norton", 39),
            entry("emperor", 39),
            entry("emperor norton", 39)
    );

    @Override
    public int askWord1(String word) {
        Integer index = map1.get(word);
        if (index == null) {
            return 37; // Doesn't know.
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

    @Override
    public int jackZone() {
        return 1;
    }

    @Override
    public boolean hasPAX() {
        return true;
    }

    @Override
    public ArrayList<SkillItem> getVendSkillItems() {
        ArrayList<SkillItem> list = new ArrayList<>();
        list.add(new SkillItem(Item.Catalog.HARDWAREREPAIR, 1000));
        return list;
    }

}
