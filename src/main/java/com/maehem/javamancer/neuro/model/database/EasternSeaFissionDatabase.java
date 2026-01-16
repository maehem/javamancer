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
import com.maehem.javamancer.neuro.model.warez.ComLinkWarez;
import com.maehem.javamancer.neuro.model.warez.ThunderheadWarez;
import com.maehem.javamancer.neuro.view.ResourceManager;

/**
 * <pre>
 * Name: Eastern Seaboard Fission Authority
 * Number: 14
 * Zone: 1
 * ComLink: 4.0
 * LinkCode: eastseabod
 * Passwords: (1) longisland (2) only from Cyberspace
 * Warez: Comlink 5.0, Thunderhead 2.0
 * Matrix: 384, 32
 * AI: none
 * Weakness: none
 * ICE: 150
 * Content: passwords for Gentleman Loser, Finn has the joystick
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class EasternSeaFissionDatabase extends Database {

    {
        bbsMessages.add(new BbsMessage("11/16/58", "Inspector B", "The Chairman", 6, true));
        bbsMessages.add(new BbsMessage("11/16/58", "The Chairman", "Grn Mtn Power", 7, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Grn Mtn Power", "The Chairman", 8, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Mrs. Waxman", "Inspector B", 9, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Inspector B", "Mrs. Waxman", 10, true));
        bbsMessages.add(new BbsMessage("11/16/58", "The Chairman", "Grn Mtn Power", 11, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Mrs. Waxman", "Inspector B", 12, true));
        bbsMessages.add(new BbsMessage("11/16/58", "All", "Deathangel's", 13, true));
        bbsMessages.add(new BbsMessage("11/16/58", "\1", "Deathangel's", 14, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Matt Shaw", "Sumdiv Kid", 15, true));
        bbsMessages.add(new BbsMessage("11/16/58", "All", "Modern Miles", 16, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Deathangel's", "Gabby", 17, true));
    }

    public EasternSeaFissionDatabase(ResourceManager rm) {
        super("Eastern Seaboard Fission Authority", 14,
                1,
                4,
                "eastseabod",
                "longisland", null, null,
                384, 32,
                null,
                150,
                rm
        );

        warez1.put(ComLinkWarez.class, 5);
        warez3.put(ThunderheadWarez.class, 2);
    }

}
