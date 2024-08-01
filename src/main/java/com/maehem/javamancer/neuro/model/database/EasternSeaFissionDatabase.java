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

import com.maehem.javamancer.neuro.model.warez.ComLink5Warez;
import com.maehem.javamancer.neuro.model.warez.Thunderhead2Warez;

/**
 * <pre>
 * Name: Eastern Seaboard Fission Authority
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

    public EasternSeaFissionDatabase() {
        super("Eastern Seaboard Fission Authority", 14,
                1,
                4,
                "eastseabod",
                "longisland", null, null,
                384, 32,
                null, null, null,
                150
        );

        warez1.add(ComLink5Warez.class);
        warez1.add(Thunderhead2Warez.class);
    }

}
