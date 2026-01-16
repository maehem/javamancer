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
import com.maehem.javamancer.neuro.model.warez.AcidWarez;
import com.maehem.javamancer.neuro.model.warez.ThunderheadWarez;
import com.maehem.javamancer.neuro.view.ResourceManager;

/**
 * <pre>
 * Name: Bell Europa
 * Number: 23
 * Zone: 5
 * ComLink: Only reachable from Cyberspace
 * Warez: Thunderhead 4.0, Acid 5.0
 * Matrix: 384, 288
 * AI: none
 * Weakness: none
 * ICE: 1000
 * Content:  interesting text
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class BellEuropaDatabase extends Database {

    public BellEuropaDatabase(ResourceManager rm) {
        super(
                "Bell Europe", 23,
                5,
                -1,
                "belltest",
                null, "test", null,
                384, 288,
                null,
                1000,
                rm
        );

        warez1.put(ThunderheadWarez.class, 4);
        warez1.put(AcidWarez.class, 5);

        bbsMessages.add(new BbsMessage("11/16/58", "B. Adamski", "Service Rep.", 4, true));
        bbsMessages.add(new BbsMessage("11/16/58", "L. Kestrel", "Service Rep.", 5, true));

    }

}
