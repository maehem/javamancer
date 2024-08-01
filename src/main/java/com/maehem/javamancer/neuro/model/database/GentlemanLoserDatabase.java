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

/**
 * <pre>
 * Name: Gentleman Loser
 * Zone: 1
 * ComLink: 4.0
 * LinkCode: loser
 * (1) wilson (2) loser (3) only from Cyberspace
 * Warez: Blowtorch 1.0, Hammer 1.0, Probe 3.0, Slow 1.0 (lvl 2), Injector 1.0 (level 2), Drill 1.0 (level 2)
 * Matrix: 416, 64
 * AI: none
 * Weakness: none
 * ICE: 150
 * Content: Linkcode for Eastern Seabod, 2nd level Pw for Bankgemeinschaft
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class GentlemanLoserDatabase extends Database {

    public GentlemanLoserDatabase() {
        super(
                "Gentleman Loser", 15,
                1,
                4,
                "loser",
                "wilson", "loser", null,
                416, 64,
                null, null, null,
                150
        );
    }

}
