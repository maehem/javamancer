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
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DECK_WAIT;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.EXIT_B;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.LONG_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.LUNGS;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.NPC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.SHORT_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.TO_JAIL;
import com.maehem.javamancer.neuro.model.room.Room;
import com.maehem.javamancer.neuro.model.room.RoomExtras;
import java.util.Map;
import static java.util.Map.entry;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R53Extras extends RoomExtras { // Hitachi

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC.num}, {SHORT_DESC.num}, //  [0][1]
        {3, 4, 5, 6}, // [2] :: Hello!  Im the lab technician. You must be a volunteer for the lung experiment?
        {7}, // [3] :: Er...yes.  I suppose so....
        {8}, // [4] :: Im so embarrassed. I just stumbled inhere by mistake.  Excuse me.
        {9}, // [5] :: Actually, Im just here to steal some time on your cyberspace jack. I know its illegal, but what the hell....
        {10}, // [6] :: Actually, Im just here to see you. Ive heard youre the kind of woman who likes to have fun....
        {DECK_WAIT.num, 7}, // [7] :: Great!  Were currently paying our volunteers $3000 apiece.  Wait here and Ill be back in a few minutes.
        {EXIT_B.num}, // [8] :: You should be embarrassed, you fool! Get out of here!
        {TO_JAIL.num}, // [9] :: Youre right. It is illegal. Have a nice trip to the Justice Booth.
        {DESC.num, 15}, // [10] :: Ive heard youre the kind of turnip- head who likes getting arrested.
        {NPC.num, 12}, // [11] :: Thanks for waiting. This wont hurt a bit. Well, maybe a little bit, but you wont feel anything after the
        {NPC.num, 13}, // [12] :: anesthetic takes effect. At least, not while youre asleep. After youre awake, of course, thats another
        {DESC.num, 14, LUNGS.num}, // [13] :: matter entirely. Then itll hurt like hell. But, hey, you volunteered for this. Nobody forced you into it.
        {NPC.num, 16}, // [14] :: The Technician painfully removes both of your lungs.
        {EXIT_B.num}, // [15] :: The Technician kicks you out of the lab.
        {EXIT_B.num}, // [16] :: Thank you, goodbye.
        {EXIT_B.num}, // [17] :: Hey you dont have organic lungs. Get out of here!
    };

    /**
     *
     * Do you know about...
     *
     */
    private static final Map<String, Integer> map1 = Map.ofEntries(
            entry("fuji", 10),
            entry("hosaka", 10),
            entry("musabori", 10),
            entry("hitachi", 10),
            entry("sense/net", 10),
            entry("sensenet", 10),
            entry("sense-net", 10),
            entry("sense net", 10)
    );

    @Override
    public JackZone jackZone() {
        return JackZone.TWO;
    }

    @Override
    public int askWord1(GameState gs, String word) {
        Integer index = map1.get(word);
        // Check agains game state for employment.

        if (index == null) {
            return 15; // Doesn't know.
        }

        return index;
    }

    @Override
    public void initRoom(GameState gs) {
        // lock door if still talking to Ratz.
        //gs.doorBottomLocked = gs.roomNpcTalk[gs.room.getIndex()];
        gs.resourceManager.getRoomText(Room.R53).dumpList();

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
    public int dialogWarmUp(GameState gs) {
        return 2;

    }

    @Override
    public void dialogNoMore(GameState gs) {
        gs.roomNpcTalk[gs.room.getIndex()] = false;
    }

}
