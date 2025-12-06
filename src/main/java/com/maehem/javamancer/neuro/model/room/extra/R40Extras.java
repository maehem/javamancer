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
import com.maehem.javamancer.neuro.model.deck.BlueLightSpecialDeckItem;
import com.maehem.javamancer.neuro.model.deck.BushidoDeckItem;
import com.maehem.javamancer.neuro.model.deck.EdokkoDeckItem;
import com.maehem.javamancer.neuro.model.deck.GaijinDeckItem;
import com.maehem.javamancer.neuro.model.deck.HikiGaeruDeckItem;
import com.maehem.javamancer.neuro.model.deck.Ninja3000DeckItem;
import com.maehem.javamancer.neuro.model.deck.SL350DeckItem;
import com.maehem.javamancer.neuro.model.deck.ZXBDeckItem;
import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.item.Item.Catalog;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.CAVIAR;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DIALOG_CLOSE;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.EXIT_T;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.ITEM_BUY;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.ITEM_GET;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.LONG_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.SHORT_DESC;
import com.maehem.javamancer.neuro.model.room.Room;
import com.maehem.javamancer.neuro.model.room.RoomExtras;
import com.maehem.javamancer.neuro.model.warez.ComLinkWarez;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R40Extras extends RoomExtras { // Crazy Edo's

    private boolean purchasedItem = false;

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC.num}, {SHORT_DESC.num}, //  [0][1]
        {3, 4}, // [2] :: Hey!  You said youd bring me some caviar the next time you came in!
        {8}, // [3] :: Did I say that?  Sorry.  Guess Im not thinking too clearly.  I spent the night sleeping in spaghetti.
        {12}, // [4] :: Get your own caviar!  Go squeeze a sturgeon!  Im no delivery boy!
        {6, 7}, // [5] :: You brought the caviar, my old friend!  Do you want to trade it?
        {13}, // [6] :: Of course I want to trade it!
        {8}, // [7] :: I think Ill hang on to it right now.
        {9, 10}, // [8] :: Can I interest you in some hardware? Remember, my prices are much better than that pig, Asano, can do.
        {17}, // [9] :: Let me see what youve got.
        {DIALOG_CLOSE.num}, // [10] :: Im just browsing.
        {DIALOG_CLOSE.num}, // [11] :: For a can of caviar, Ill give you Comlink 2.0.  Its great software!
        {DIALOG_CLOSE.num}, // [12] :: You having a rough day or something?
        {CAVIAR.num, 8}, // [13] :: Domo arigato gozaimasu!  Heres your Comlink 2.0 software.
        {DIALOG_CLOSE.num}, // [14] :: All right.  Maybe next time. (Close of Buy menu)
        {EXIT_T.num}, // [15] :: Try Metro Holografix for software! (After purchase of hardware?)
        {DIALOG_CLOSE.num}, // [16] :: Come back again when you feel like buying.
        {ITEM_BUY.num}, // [17] :: This is my current inventory. Of course, none of the decks are cyberspace-capable.
        {ITEM_GET.num}, // [18] :: Edo installs the software in your deck.
        {DIALOG_CLOSE.num}, // [19] :: Your deck is too full for us to trade.Erase some softwarez first.
    };

    @Override
    public void initRoom(GameState gs) {
        // lock door if still talking to Ratz.
        //gs.doorBottomLocked = gs.roomNpcTalk[gs.room.getIndex()];
        gs.resourceManager.getRoomText(Room.R40).dumpList();
    }

    /**
     * Crazy Edo will give player ComLink 2.0 if the player has Caviar in their
     * inventory.
     *
     *
     * @param gs game state
     * @param item item to get from NPC
     * @return
     */
    @Override
    public boolean getItem(GameState gs, Item item) {
        if (item.item != Catalog.COMLINK) {
            LOGGER.log(Level.WARNING, "Received item is not ComLink. Player doesn't take anyting else.");
            return false;
        }
        if (gs.hasInventoryItem(Catalog.CAVIAR)) {
            if (gs.addSoftware(new ComLinkWarez(2))) {
                LOGGER.log(Level.FINE, "Warez installed.");
                gs.removeInventoryItem(Catalog.CAVIAR);
                return true;
            } else {
                // Not installed, why?
                LOGGER.log(Level.WARNING, "Could not install software! Unknown error.");
            }
        } else {
            LOGGER.log(Level.INFO, "Player does not have Caviar in inventory.");
        }

        return false;
    }

    @Override
    public int[][] getDialogChain() {
        return DIALOG_CHAIN;
    }

    @Override
    public int dialogWarmUp(GameState gs) {
        if (gs.comlink2recieved) {
            return 8;
        }
        if (gs.hasInventoryItem(Item.Catalog.CAVIAR)) {
            return 5;
        }
        return 2;

    }

    @Override
    public ArrayList<Item> getVendItems(GameState gs) {
        ArrayList<Item> list = new ArrayList<>();
        list.add(new BlueLightSpecialDeckItem());
        list.add(new HikiGaeruDeckItem());
        list.add(new GaijinDeckItem());
        list.add(new ZXBDeckItem());
        list.add(new SL350DeckItem());
        list.add(new BushidoDeckItem());
        list.add(new Ninja3000DeckItem());
        list.add(new EdokkoDeckItem());
        return list;
    }

    @Override
    public boolean onVendItemsFinished(GameState gs) {
        if (!purchasedItem) {
            // set dialog to 32 // Come back when you're ready
            LOGGER.log(Level.WARNING, "No item was purchased.");
        } else {
            LOGGER.log(Level.FINE, "Item was purchased.");
        }
        return false;
    }

}
