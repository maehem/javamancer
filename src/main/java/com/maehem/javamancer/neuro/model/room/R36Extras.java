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
import com.maehem.javamancer.neuro.model.RoomExtras;
import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.item.Item.Catalog;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R36Extras extends RoomExtras { // House of Pong

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC}, {SHORT_DESC}, //  [0] :: Youre in a narrow canyon
        {3, 4, 5}, // [2] :: Greetings, Pilgrim. Have you come to worship the One True Computer Game?
        {6}, // [3] :: I am but dust on your feet, O Great Nolan. I seek to learn the ways of the One True Computer Game.
        {8}, // [4] :: Worship a computer game?  Get real!
        {9}, // [5] :: Oh, sorry, I thought this was the massage parlor.
        {10, 11}, // [6] :: Apprentice Monks must contemplate the mysteries of the One True Game for 20 years before they are allowed to play.
        {}, // [7] ::
        {EXIT_B}, // [8] :: Blasphemer!  Heathen!  Remove your unworthy self from this holy place!
        {DIALOG_CLOSE}, // [9] :: Go in peace, wilson.
        {12}, // [10] :: 20 years!  Are you out of your mind! It didnt take me that long to play Wasteland!
        {13}, // [11] :: 20 years.  I see.  Thats a bit longer than I had planned to wait....
        {EXIT_B}, // [12] :: Then you are obviously unworthy of an apprenticeship with the House of Pong.Good day!
        {14, 15}, // [13] :: It is a long and hard road, but one that must be traveled. A caterpillar does not become a butterfly overnight.
        {DIALOG_CLOSE}, // [14] :: Youre nuts!  It already seems like Ive been playing THIS game for 20 years!
        {16}, // [15] :: All right.  You must be leading up to something.  What is it?  I have to make a fool of myself, right?
        {17, 18}, // [16] :: Before you may become an apprentice Monk, you must go on a Great Quest for the Holy Joystick.
        {EXIT_B}, // [17] :: Holy Joystick!  Now Ive heard everything.  Im leaving.
        {19}, // [18] :: Okay, say I bring you this Holy Joystick.  Then what happens?
        {DIALOG_CLOSE}, // [19] :: Then the Masters can play Pong again. Our Joystick is worn down to a nub! Then Ill teach you Zen and Sophistry.
        {ITEM_GET, DIALOG_END}, // [20] :: You have the Holy Joystick! The Masters will be pleased! As a token of our gratitude, please accept these.
    };

    @Override
    public void initRoom(GameState gs) {
        // lock door if still talking to Ratz.
        //gs.doorBottomLocked = gs.roomNpcTalk[gs.room.getIndex()];
        //gs.resourceManager.getRoomText(Room.R36).dumpList();
    }

    @Override
    public boolean give(GameState gs, Item item, int aux) {
        // Player give is joystick.
        if (item.item == Catalog.JOYSTICK) {
            gs.joystickGiven = true;
            gs.inventory.remove(item);
            return true;
        }

        return false;
    }

    @Override
    public int[][] getDialogChain() {
        return DIALOG_CHAIN;
    }

    @Override
    public int dialogWarmUp(GameState gs) {
        if (!gs.roomNpcTalk[gs.room.getIndex()]) {
            return DIALOG_END;
        }
        if (gs.joystickGiven) {
            return 20;
        }
        return 2;

    }

    @Override
    public void dialogNoMore(GameState gs) {
        gs.roomNpcTalk[gs.room.getIndex()] = false;
    }

}
