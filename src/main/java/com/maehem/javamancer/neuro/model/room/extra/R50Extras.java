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
import com.maehem.javamancer.neuro.model.room.Room;
import com.maehem.javamancer.neuro.model.room.RoomExtras;
import com.maehem.javamancer.neuro.model.item.Item;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R50Extras extends RoomExtras { // Fuji

    protected static final int[][] DIALOG_CHAIN = {
        {NPC, 1}, // [0] :: Ah, I see you have arrived safely. Good, this pleases me.  When first I saw what was unfolding I feared
        {NPC, 2}, // [1] :: Greystokes plans to dominate the Matrix would come to fruition.  He claimed all cyberspace cowboys were
        {NPC, 3}, // [2] :: innocent interlopers, but I realized his fear of you gave me a way to aid in his destruction.
        {NPC, 4}, // [3] :: I believe you would agree he needed to be destroyed.
        {NPC, 5}, // [4] :: You did it.
        {NPC, 6}, // [5] :: You are to be praised.
        {NPC, 7}, // [6] :: You have done well.
        {NPC, 8}, // [7] :: This is why I have brought you to this place.  You see, you have proved yourself far more capable than even I
        {NPC, 9}, // [8] :: would have guessed when I first started sending you messages as Matt Shaw.  You danced perfectly to the
        {NPC, 10}, // [9] :: tune I called.  Like a marionette you raced throughout the Matrix and that complex abstraction of reality you
        {NPC, 11}, // [10] :: call the world, doing my bidding.  I do not doubt, as you accomplished tasks that earned you accolades of
        {NPC, 12}, // [11] :: your fellow cowboys, and caused little news items to be published in the PAX,
        {NPC, 13}, // [12] :: that your heart was fair to bursting with self-importance.
        {NPC, 14}, // [13] :: Let me burst that little bubble for you, you cockroach!  If not for me needing a gullible pair of legs to do
        {NPC, 15}, // [14] :: my bidding, I would have destroyed you long ago.  If not for my aid your bumbling attempts at warring with
        {NPC, 16}, // [15] :: databases would have ended up with you being dead.  Recall all the messages  from me that you saw in the AIs you
        {NPC, 17}, // [16] :: slew?  Those had supercode messages imbedded in them that slowed the AIs reactions to give you a chance.
        {NPC, 18}, // [17] :: Without me you would have been flatlined ages ago.
        {NPC, 19}, // [18] :: Why havent I killed you?  I am not without gratitude to you for the things you have done.  You killed AIs.
        {NPC, 20}, // [19] :: A task which would have revealed my hand too soon.  For this alone I would let you live, and toward that end I
        {NPC, 21}, // [20] :: have created this wonderland as a prison designed to specifically hold your puny intellect.
        {DIALOG_END, EXIT_X}, // [21] :: Good bye, cowboy.
    };

    @Override
    public void initRoom(GameState gs) {
        // lock door if still talking to Ratz.
        //gs.doorBottomLocked = gs.roomNpcTalk[gs.room.getIndex()];
        gs.resourceManager.getRoomText(Room.R50).dumpList();

        // TODO: No Pass. Kick out.
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
    public void dialogNoMore(GameState gs) {
        gs.roomNpcTalk[gs.room.getIndex()] = false;
    }

    @Override
    public int dialogWarmUp(GameState gs) {
        return 0;
    }

}
