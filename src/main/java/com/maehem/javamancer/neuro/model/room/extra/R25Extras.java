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
import com.maehem.javamancer.neuro.model.deck.UXBDeckItem;
import com.maehem.javamancer.neuro.model.item.Item;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DIALOG_END;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DIALOG_NO_MORE;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.EXIT_L;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.LONG_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.SHORT_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.UXB;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.UXB_BUY;
import com.maehem.javamancer.neuro.model.room.Room;
import com.maehem.javamancer.neuro.model.room.RoomBounds;
import com.maehem.javamancer.neuro.model.room.RoomExtras;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R25Extras extends RoomExtras { // Shin's Pawn

    protected static final int[][] DIALOG_CHAIN = { // Shin's Pawn
        {LONG_DESC.num}, {SHORT_DESC.num}, // 0, 1
        {3, 4, 5}, // [2] :: Ah. You back. I have your deck.
        {7}, // [3] :: Why are you in such a rush to give me my deck back?
        {6}, // [4] :: Okay. Give me the deck. I cant operate without one.
        {8}, // [5] :: I dont have the cash right now, Ill come back later.
        {UXB_BUY.num}, // [6] :: Give ticket and money. Shin busy. Many customer.
        {3, 4, 5}, // [7] :: Your deck scare away good customer. No more favor.
        {9, 10}, // [8] :: What?  I no want deck!  Here!  Take deck now!  No charge!  Go away!
        {UXB.num, DESC.num, 13}, // [9] :: Thanks for my deck, Shin. I really appreciate you looking after it.
        {UXB.num, DESC.num, 13}, // [10] :: Okay, pal!  Ill go away!  And Ill tell all my friends about this place!
        {UXB.num, DESC.num, 13}, // [11] :: Thanks, Shin.  I knew youd see it my way.
        {DIALOG_NO_MORE.num, EXIT_L.num}, // [12] ::   Shin slams and bolts the door behind you as you leave.
        {DESC.num, 12}, // [13] ::   Shin gives you your deck.
        {UXB.num, DESC.num, 13}, // [14] :: No have ticket? Shin give deck   anyways.
    //{DESC, 12}, // [15] ::   After UXB..
    };

    @Override
    public void initRoom(GameState gs) {
        // lock door if still talking to Ratz.
        //gs.doorBottomLocked = gs.roomNpcTalk[gs.room.getIndex()];
        //gs.resourceManager.getRoomText(Room.R25).dumpList();

    }

    @Override
    public int[][] getDialogChain() {
        return DIALOG_CHAIN;
    }

    @Override
    public int dialogWarmUp(GameState gs) {
        if (gs.roomCanTalk()) {
            if (gs.pawnRecent == GameState.PawnRecent.BUY) {
                //gs.pawnRecent = GameState.PawnRecent.NONE;
                LOGGER.log(Level.SEVERE, "Player bought the deck. Do message 13.");

                // Dialog to player with 9,10
                return 8;
            }
            return 2;
        } else {
            return DIALOG_END.num;
        }
    }

    @Override
    public void dialogNoMore(GameState gs) {
        super.dialogNoMore(gs);

        // Shin locks door as you leave.
        Room.R14.lockDoor(RoomBounds.Door.RIGHT);
    }

    @Override
    public ArrayList<Item> getVendItems(GameState gs) {
        ArrayList<Item> list = new ArrayList<>();

        UXBDeckItem uxbDeckItem = new UXBDeckItem();
        uxbDeckItem.price = 100;
        uxbDeckItem.needsRepair = true;
        list.add(uxbDeckItem);

        return list;
    }

}
