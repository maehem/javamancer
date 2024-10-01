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
import com.maehem.javamancer.neuro.model.Person;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DIALOG_CLOSE;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.EXIT_R;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.LONG_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.NPC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.SHORT_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.TO_JAIL;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.WORD1;
import com.maehem.javamancer.neuro.model.room.RoomExtras;
import java.util.logging.Level;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R52Extras extends RoomExtras { // Security Gate

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC.num}, {SHORT_DESC.num}, //  [0][1]
        {3, 4, 5, 6}, // [2] :: Kudasai, by which company are you employed?
        {14}, // [3] :: Top of the mornin!  Im a cop on my daily rounds.  Let me in.
        {12}, // [4] :: Im a volunteer for Hitachi Biotech.
        {DIALOG_CLOSE.num}, // [5] :: Sorry. I just stumbled in here by mistake.
        {WORD1.num}, // [6] :: I work for @---------------
        {}, // [7] ::
        {6}, // [8] :: You are not listed as an employee of the company you named. If you made a mistake, please try again.
        {TO_JAIL.num}, // [9] :: You are also not listed as an employee of that company.  Please remain here while I summon the authorities.
        {EXIT_R.num}, // [10] :: Domo arigato.  You are cleared for entry.
        {DIALOG_CLOSE.num}, // [11] :: This is not a Hitachi Volunteer Day. Come back tomorrow.
        {NPC.num, 13}, // [12] :: You are cleared for limited access. Please proceed directly North to Hitachi Biotech.  Be aware that
        {EXIT_R.num}, // [13] :: you will not be allowed admittance to any other buildings in this zone.
        {DIALOG_CLOSE.num}, // [14] :: We have our own security force. Your assistance is not required, officer.
        {DIALOG_CLOSE.num}, // [15] :: You appear to be lost. That company is not in the Chiba high-tech zone.
    };

    private int wordTries = 0;

    @Override
    public int askWord1(GameState gs, String word) {
        wordTries++;
        switch (word.toLowerCase()) {
            case "fuji" -> {
                if (wordTries > 1) {
                    return 9; // go to jail
                }
                return 8; // not listed
            }
            case "hosaka" -> {
                LOGGER.log(Level.SEVERE, "Matched Word: {0}", word);
                for (Person p : gs.hosakaEmployeeList) {
                    if (p.getBama().equals(gs.PLAYER_BAMA)) {
                        return 10; // Cleared
                    }
                }
                if (wordTries > 1) {
                    return 9; // go to jail
                }
                return 8; // not listed
            }
            case "musabori" -> {
                LOGGER.log(Level.SEVERE, "Matched Word: {0}", word);
                if (wordTries > 1) {
                    return 9; // go to jail
                }
                return 8; // not listed
            }
            case "hitachi" -> {
                LOGGER.log(Level.SEVERE, "Matched Word: {0}", word);
                if (wordTries > 1) {
                    return 9; // go to jail
                }
                return 8; // not listed
            }
            case "sensenet", "sense net", "sense-net", "sense/net" -> {
                LOGGER.log(Level.SEVERE, "Matched Word: {0}", word);
                if (wordTries > 1) {
                    return 9; // go to jail
                }
                return 8; // not listed
            }
        }
        LOGGER.log(Level.SEVERE, "No word match for {0}", word);
        return 15; // Doesn't know
    }

    @Override
    public void initRoom(GameState gs) {
        //gs.resourceManager.getRoomText(Room.R52).dumpList();
    }

    @Override
    public int[][] getDialogChain() {
        return DIALOG_CHAIN;
    }

    @Override
    public int dialogWarmUp(GameState gs) {
        return 2;

    }

}
