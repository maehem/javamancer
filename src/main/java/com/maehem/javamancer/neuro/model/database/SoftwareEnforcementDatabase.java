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

import com.maehem.javamancer.neuro.model.warez.ComLink4Warez;
import com.maehem.javamancer.neuro.model.warez.Sequencer1Warez;
import com.maehem.javamancer.neuro.model.warez.Thunderhead2Warez;

/**
 * <pre>
 * Name: Software Enforcement Agency (SEA)
 * Zone: 1
 * ComLink: 3.0
 * LinkCode: soften
 * Passwords: (1) permafrost (2) only from Cyberspace
 * Warez: Comlink 4.0, Sequencer 1.0, Thunderhead 2.0 (level 2)
 * Matrix: 352, 64
 * AI: none
 * Weakness: none
 * ICE: 150
 * Content: upgrade Coptalk to level 2, upgrade Coptalk to level 4 (level 2)
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class SoftwareEnforcementDatabase extends Database {

    public SoftwareEnforcementDatabase() {
        super(
                "Software Enforcement Agency (SEA)", 12,
                1,
                3,
                "soften",
                "permafrost", null, null,
                352, 64,
                null, null, null,
                150
        );

        warez1.add(ComLink4Warez.class);
        warez1.add(Sequencer1Warez.class);
        warez2.add(Thunderhead2Warez.class);
    }

}
