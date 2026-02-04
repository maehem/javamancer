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
import com.maehem.javamancer.neuro.model.warez.ArmorallWarez;
import com.maehem.javamancer.neuro.model.warez.HammerWarez;
import com.maehem.javamancer.neuro.model.warez.InjectorWarez;
import com.maehem.javamancer.neuro.model.warez.JammiesWarez;
import com.maehem.javamancer.neuro.model.warez.ThunderheadWarez;
import com.maehem.javamancer.neuro.view.ResourceManager;

/**
 * <pre>
 * Name: Gridpoint
 * Number: 27
 * Zone: 4
 * ComLink: none
 * Link Code: Only reachable from Cyberspace
 * Passwords: none
 * Warez: Thunderhead 3.0, Injector 3.0, Jammies 3.0, Hammer 5.0, Armorall 2.0
 * Matrix: 160, 320
 * AI: none
 * Weakness: none
 * ICE: 800
 * Content: interesting text
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class GridpointDatabase extends Database {

    public GridpointDatabase(ResourceManager rm) {
        super(
                "Gridpoint", 27,
                4,
                -1,
                "gridtest", // Added to allow testing.
                null, "test", null, // added to allow testing.
                160, 320,
                null,
                800,
                rm);

        warez1.put(JammiesWarez.class, 3);
        warez1.put(ThunderheadWarez.class, 3);
        warez1.put(HammerWarez.class, 5);
        warez1.put(InjectorWarez.class, 3);
        warez1.put(ArmorallWarez.class, 2);

        bbsMessages.add(new BbsMessage("11/16/58", "Anonymous", "Deathangel's", 5, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Deathangel's", "M. Shaw", 6, true));
        bbsMessages.add(new BbsMessage("11/16/58", "M. Shaw", "El Aguila", 7, true));
        bbsMessages.add(new BbsMessage("11/16/58", "All", "M. Shaw", 8, true));
        bbsMessages.add(new BbsMessage("11/16/58", "All", "M. Shaw", 9, true));
    }

}
