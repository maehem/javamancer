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
package com.maehem.javamancer.neuro.model.database;

import com.maehem.javamancer.neuro.model.BbsMessage;
import com.maehem.javamancer.neuro.model.warez.BattleChessWarez;
import com.maehem.javamancer.neuro.model.warez.ProbeWarez;
import com.maehem.javamancer.neuro.model.warez.ScoutWarez;
import com.maehem.javamancer.neuro.view.ResourceManager;

/**
 * <pre>
 * Name: Regular Fellows
 * Zone: 0
 * ComLink: 1.0
 * LinkCode: regfellow
 * Passwords: (1) visitor (2) only from Cyberspace
 * Warez: Scout 1.0, Battle Chess 2.0, Probe 3.0 (level 2)
 * Matrix: 208, 32
 * AI: none
 * Weakness: none
 * ICE: 84
 * Content: Level password from Cheap Hotel, learn how to use Scout 1.0
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class RegularFellowsDatabase extends Database {

    /*

     */
    {
        bbsMessages.add(new BbsMessage("11/16/58", "Raphael", "Deathangel's", 9, true));
        bbsMessages.add(new BbsMessage("11/16/58", "All", "Matt Shaw", 10, true));
        bbsMessages.add(new BbsMessage("11/16/58", "All", "Mo #243", 11, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Mo #243", "Deathangel's", 12, true));
        bbsMessages.add(new BbsMessage("11/16/58", "All", "Harpo", 13, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Harpo", "Matt Shaw", 14, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Red Snake", "Scorpion", 15, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Scorpion", "Red Snake", 16, true));
        bbsMessages.add(new BbsMessage("11/16/58", "All", "Deathangel's", 17, true));
    }

    public RegularFellowsDatabase(ResourceManager rm) {
        super(
                "Regular Fellows", 0,
                0,
                1,
                "regfellow",
                "visitor", "fellow", "fellow", // Remove #3 (for testing)
                208, 32,
                null, null, null,
                84,
                rm
        );
        warez1.put(BattleChessWarez.class, 2);
        warez1.put(ScoutWarez.class, 1);

        warez2.put(ProbeWarez.class, 3);
    }

}
