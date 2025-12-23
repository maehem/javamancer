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
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DIALOG_CLOSE;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DIALOG_END;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.EXIT_B;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.LONG_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.NPC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.SHORT_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.SKILL_BUY;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.TO_JAIL;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.WORD1;
import com.maehem.javamancer.neuro.model.room.RoomBounds;
import com.maehem.javamancer.neuro.model.room.RoomExtras;
import java.util.ArrayList;
import java.util.Map;
import static java.util.Map.entry;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R12Extras extends RoomExtras {

    protected static final int[][] DIALOG_CHAIN = { // Larry
        {LONG_DESC.num}, {SHORT_DESC.num}, // 0, 1
        {3, 4, 5, 6, 7}, // 2 You looking to by?
        {15}, //[3] :: Got anything good?
        {10}, //[4] :: Im looking for the Panther Moderns.
        {8}, //[5] :: Im a cop.
        {9}, //[6] :: Its cold outside.
        {WORD1.num, 7}, //[7] :: Do you know anything about
        {TO_JAIL.num}, //[8] :: Youre not a cop.
        {EXIT_B.num}, //[9] :: Ill warm you up with
        {11, 12, 13, 14}, //[10] :: The Moderns dont like
        {16}, //[11] :: Does that include wealthy
        {15}, //[12] :: You know, I have a sudden
        {2}, //[13] :: Youre calling ME strange?
        {WORD1.num}, //[14] :: Do you know anything about
        {3, 4, 5, 6, 7}, //[15] :: All my softs are top quality.
        {17, 18, 19, 20}, //[16] :: How much would you pay for a
        {21}, //[17] :: 100
        {21}, //[18] :: 200
        {21}, //[19] :: 300
        {23}, //[20] :: Whoops! I dont have
        {NPC.num, 22}, //[21] :: All right.  // Give sets dialog to 22.
        {EXIT_B.num}, //[22] :: What a rube!  You really
        {EXIT_B.num}, //[23] :: No money, no meeting.
        {}, //[24] :: Im a cop,
        {}, //[25] :: Maybe. Got anyth
        {}, //[26] :: Im looking for the
        {WORD1.num}, //[27] :: Do you know anything about
        {}, //[28] :: I dont network with cops.
        {}, //[29] :: Okay. Dont get excited.
        {}, //[30] :: Ill remember this, Larry.
        {}, //[31] :: Yeah, you sound like a cop.
        {10}, //[32] :: Im looking for the
        {35}, //[33] :: Heard any unusual rumors?
        {WORD1.num}, //[34] :: Do you know anything about
        {DIALOG_CLOSE.num}, //[35] :: Somethings happening in cyberspace.
        {DIALOG_CLOSE.num}, //[36] :: How fascinating.
        {DIALOG_CLOSE.num}, //[37] :: Youd know more than
        {DIALOG_CLOSE.num}, //[38] :: Its the matrix, man.
        {DIALOG_CLOSE.num}, //[39] :: A good ROM is
        {SKILL_BUY.num, DIALOG_CLOSE.num}, //[40] :: I can sell you a
    };

    /**
     *
     * Do you know about...
     *
     * matrix, cyberspace, rom, construct, rom construct, constructs, construct
     * rom coptalk, sea, skill, skills, chip, chips, fuji, police, comlink, code
     */
    private static final Map<String, Integer> map = Map.ofEntries(
            entry("matrix", 38),
            entry("cyberspace", 38),
            entry("rom", 39),
            entry("construct", 39),
            entry("rom construct", 39),
            entry("constructs", 39),
            entry("construct rom", 39),
            entry("coptalk", 40),
            entry("cop", 40),
            entry("sea", 40),
            entry("skill", 40),
            entry("skills", 40),
            entry("chip", 40),
            entry("chips", 40),
            entry("fuji", 40),
            entry("police", 40),
            entry("comlink", 40),
            entry("code", 40)
    );

    // Animation
    // Load at start:
    // Larry ::
    //        entry01 : anim00
    //
    // Larry Fades in:
    // Invisible Larry ::
    //        entry02 : anim00
    //
    // Back Room Door ::
    //        entry03 : anim00
    protected final int[][] ANIMATION_FLAGS = {
        {1}, // Larry Normal
        {0}, // Larry Invisible
        {0} // Back Room Door
    };

    @Override
    public int[][] getAnimationFlags() {
        return ANIMATION_FLAGS;
    }

    @Override
    public int askWord1(GameState gs, String word) {
        Integer index = map.get(word);
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
        // lock door if still talking to Ratz.
        //gs.doorBottomLocked = gs.roomNpcTalk[gs.room.getIndex()];
        //gs.resourceManager.getRoomText(Room.R12).dumpList();
        if (gs.larryMoeWanted) {
            // Top door unlocked.
            gs.room.unlockDoor(RoomBounds.Door.TOP);
            gs.room.extras.dialogNoMore(gs);
        } else {
            gs.room.lockDoor(RoomBounds.Door.TOP);
        }
    }

    @Override
    public int[][] getDialogChain() {
        return DIALOG_CHAIN;
    }

    @Override
    public int dialogWarmUp(GameState gs) {
        if (gs.larryMoeWanted) {
            dialogNoMore(gs);
            return DIALOG_END.num;
        }
        return 2;
    }

    @Override
    public boolean hasPAX() {
        return false;
    }

    @Override
    public ArrayList<SkillItem> getVendSkillItems(GameState gs) {
        ArrayList<SkillItem> list = new ArrayList<>();
        list.add(new SkillItem(Item.Catalog.COPTALK, 1, 100));
        return list;
    }

}
