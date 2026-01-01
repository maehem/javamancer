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
import com.maehem.javamancer.neuro.model.deck.BJB188DeckItem;
import com.maehem.javamancer.neuro.model.deck.BlueLightSpecialDeckItem;
import com.maehem.javamancer.neuro.model.deck.Cyberspace2DeckItem;
import com.maehem.javamancer.neuro.model.deck.Cyberspace3DeckItem;
import com.maehem.javamancer.neuro.model.deck.Cyberspace7DeckItem;
import com.maehem.javamancer.neuro.model.deck.EdokkoDeckItem;
import com.maehem.javamancer.neuro.model.deck.GaijinDeckItem;
import com.maehem.javamancer.neuro.model.deck.HikiGaeruDeckItem;
import com.maehem.javamancer.neuro.model.deck.KatanaDeckItem;
import com.maehem.javamancer.neuro.model.deck.Ninja2000DeckItem;
import com.maehem.javamancer.neuro.model.deck.Ninja3000DeckItem;
import com.maehem.javamancer.neuro.model.deck.Ninja4000DeckItem;
import com.maehem.javamancer.neuro.model.deck.Ninja5000DeckItem;
import com.maehem.javamancer.neuro.model.deck.SamuraiSevenDeckItem;
import com.maehem.javamancer.neuro.model.deck.ShogunDeckItem;
import com.maehem.javamancer.neuro.model.deck.TofuDeckItem;
import com.maehem.javamancer.neuro.model.deck.UXBDeckItem;
import com.maehem.javamancer.neuro.model.item.Item;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DIALOG_CLOSE;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DISCOUNT;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.EXIT_L;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.ITEM_BUY;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.LONG_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.SHORT_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.WORD1;
import com.maehem.javamancer.neuro.model.room.RoomExtras;
import java.util.ArrayList;
import java.util.Map;
import static java.util.Map.entry;
import java.util.logging.Level;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R44Extras extends RoomExtras { // Asano's

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC.num}, {SHORT_DESC.num}, //  [0][1]
        {5, 6, 7}, // [2] :: Welcome to my humble shop!
        {DIALOG_CLOSE.num}, // [3] :: Leave me alone!  I said Im just
        {WORD1.num}, // [4] :: Tell me about the @---------------
        {17}, // [5] :: Uh, whats the, uh...cheapest
        {18}, // [6] :: Im just browsing right now.
        {19}, // [7] :: Why does Crazy Edo call you
        {20}, // [8] :: Sure and begorrah. Im looking
        {WORD1.num}, // [9] :: Tell me about the... npc:{ 27 || 28 || 29 }
        {17}, // [10] :: Uh, whats the, uh...cheapest..
        {19}, // [11] :: Why does Crazy Edo call
        {32}, // [12] :: Thanks for your help. COPTALK = 33
        {25}, // [13] :: Well, yes, kind of....
        {16}, // [14] :: Edo is my oldest
        {25}, // [15] :: Well, no, not really....
        {26}, // [16] :: Edo is a gnats eyeball!
        {ITEM_BUY.num}, // [17] :: Cheapest?  The Blue Light Special. [32 on no buy]
        {3, 4, 5, 7}, // [18] :: Certainly. Can I answer any questions?
        {21, 22}, // [19] :: A pig?  Edo is the son of a turtle
        {ITEM_BUY.num}, // [20] :: Police?  Uh, I can sell you any legal
        {23}, // [21] :: You dont like Edo, do you?  Im pretty good at noticing these things.
        {23}, // [22] :: Ive heard Edo is a pretty good guy.
        {13, 14, 15, 16}, // [23] :: Edo is a goats armpit!
        {EXIT_L.num}, // [24] :: Edo sleeps with small animals!
        {9, 10, 11, 12}, // [25] :: Good.  I dont allow Crazy Edos friends
        {DISCOUNT.num, ITEM_BUY.num}, // [26] :: I see you are a wise person. // Apply discount.
        {9}, // [27] :: Thats a low-end model.
        {9}, // [28] :: Thats a cyberspace deck.
        {9}, // [29] :: You cant do any better than that.
        {EXIT_L.num}, // [30] :: Try Metro Holografix for softwarez! Come back soon!
        {9}, // [31] :: Never heard of that deck.
        {EXIT_L.num}, // [32] :: Come back when youre ready to buy.
        {EXIT_L.num}, // [33] :: Well, come back anytime, officer.
        {14, 16}, // [34] :: I hope you have reconsidered who your friends are, now.
    };

    // Animation
    protected final int[][] ANIMATION_FLAGS = {
        {1},  // Face
        {1}   // Body
    };

    @Override
    public int[][] getAnimationFlags() {
        return ANIMATION_FLAGS;
    }
    
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
    public int askWord1(GameState gs, String word) {
        Integer index = map1.get(word);
        if (index == null) {
            return 31; // Doesn't know.
        }

        return index;
    }

    @Override
    public void initRoom(GameState gs) {
        gs.allowDialog(gs.room);
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
        list.add(new BlueLightSpecialDeckItem());
        list.add(new BJB188DeckItem());
        list.add(new UXBDeckItem());
        list.add(new HikiGaeruDeckItem());
        list.add(new GaijinDeckItem());
        list.add(new Ninja2000DeckItem());
        list.add(new Ninja3000DeckItem());
        list.add(new EdokkoDeckItem());
        list.add(new Cyberspace2DeckItem());
        list.add(new KatanaDeckItem());
        list.add(new Cyberspace3DeckItem());
        list.add(new TofuDeckItem());
        list.add(new Ninja4000DeckItem());
        list.add(new ShogunDeckItem());
        list.add(new Ninja5000DeckItem());
        list.add(new SamuraiSevenDeckItem());
        list.add(new Cyberspace7DeckItem());
        return list;
    }

    @Override
    public boolean onVendItemsFinished(GameState gs, boolean purchased) {

        if (purchased) {
            // Potentially increase deck slots if a deck was purchased.
            DeckUtils.computeMaxSlots(gs);
        } else {
            // set dialog to 32 // Come back when you're ready
        }
        
        return false;
    }

    @Override
    public void applyDiscount(GameState gs) {
        LOGGER.log(Level.FINE, "Apply discount because Asano likes you.");
        gs.asanoDiscount = true;
    }

    @Override
    public int getDiscount(GameState gs) {
        if (gs.asanoDiscount) {
            LOGGER.log(Level.FINE, "Discount is 20%.");
            return 20;
        }
        return 0;
    }

}
