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

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.item.SkillItem;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class RoomExtras {

    public static final Logger LOGGER = Logging.LOGGER;

    public static final int DESC = 50; // show in room desc instead of dialog.
    public static final int LONG_DESC = 51;
    public static final int SHORT_DESC = 52;
    public static final int NPC = 53; // Don't toggle to PLAYER after this dialog
    public static final int WORD1 = 54;
    public static final int WORD2 = 55;
    public static final int WHERE_IS = 56; // Street Light Girl - Where is Lonny Zone?
    public static final int LUNGS = 60; // lungs removed at Hitachi
    public static final int BODY_SELL = 61; // Bodyshop menu
    public static final int BODY_BUY = 62; // Bodyshop menu
    public static final int SKILL_SELL = 63; // ??? menu, maybe don't need
    public static final int SKILL_BUY = 64; // Larry menu, TODO: move to ITEM_BUY
    public static final int SKILL_UPGRADE = 65; // Larry menu, TODO: move to ITEM_BUY
    public static final int UXB_BUY = 66; // Shin menu, TODO: move to ITEM_BUY
    public static final int ITEM_BUY = 67; // Player buys item from NPC
    public static final int ITEM_GET = 68; // player receives NPC item directly
    public static final int EXIT_T = 70; // Exit Top
    public static final int EXIT_R = 71; // Exit Right
    public static final int EXIT_B = 72; // Exit Bottom
    public static final int EXIT_L = 73; // Exit Left
    public static final int EXIT_ST_CHAT = 74; // Exit Outside Chatsubo
    public static final int EXIT_BDSHOP = 75; // Exit to body shop.
    public static final int EXIT_SHUTTLE_FS = 76; // Exit To Freeside Shuttle
    public static final int EXIT_SHUTTLE_ZION = 77; // Exit To Zion Shuttle
    public static final int EXIT_X = 78; // Exit determined by code.
    public static final int DEATH = 79; // Go to jail action
    public static final int TO_JAIL = 80; // Go to jail action
    public static final int DECK_WAIT = 81; // Wait till user exit's deck or leaves room.
    public static final int UXB = 90; // Shin gives UXB
    public static final int PASS = 91; // Shiva gives Rest. Pass
    public static final int CAVIAR = 92; // Edo gives ConLink 2.0 for Caviar
    public static final int CHIP = 94; // n credits.
    public static final int FINE_BANK_500 = 95; // Fine bank
    public static final int FINE_BANK_20K = 96; // Fine bank
    public static final int DIALOG_NO_MORE = 97;
    public static final int DIALOG_CLOSE = 98;
    public static final int DIALOG_END = 99;

    /**
     * Called when player gives item to NPC.
     *
     * @param item usually RealItem,SkillItem or CreditsItem
     * @param aux if CreditsItem, amount to give NPC.
     *
     * @return index of dialog.
     */
    public abstract boolean give(GameState gs, Item item, int aux); // aux == credits amount if item is Credits.

    public abstract int[][] getDialogChain();

    public abstract int dialogWarmUp(GameState gs);

    public abstract void dialogNoMore(GameState gs);

    public abstract void initRoom(GameState gs);

    public int jackZone() {
        return -1;
    }

    public boolean hasPAX() {
        return false;
    }

    /**
     * Override if NPC has skills to sell.
     *
     * @return
     */
    public ArrayList<SkillItem> getVendSkillItems() {
        LOGGER.log(Level.WARNING, "Room called getVendSkillItems() but it's not overridden!");
        return null;
    }

    /**
     * Override if NPC has items to sell.
     *
     * @return
     */
    public ArrayList<Item> getVendItems() {
        LOGGER.log(Level.WARNING, "Room called getVendItems() but it's not overridden!");
        return null;
    }

    /**
     * Over ride with logic for word. Use WORD1 Mnemonic.
     *
     *
     * @param word
     * @return
     */
    public int askWord1(String word) { // or phrase
        return -1; // Not found
    }

    /**
     * Over ride with logic for word. Use WORD2 Mnemonic.
     *
     *
     * @param word
     * @return
     */
    public int askWord2(String word) { // or phrase
        return -1; // Not found
    }

    public int exitX(GameState gs) {
        return EXIT_R;
    }

    public boolean chipDeduct(GameState gs, int amt) {
        if (amt > gs.chipBalance) {
            return false;
        }

        gs.chipBalance -= amt;
        return true;
    }
}
