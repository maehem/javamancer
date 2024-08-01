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

import com.maehem.javamancer.neuro.model.warez.Hammer2Warez;
import com.maehem.javamancer.neuro.model.warez.Jammies1Warez;
import com.maehem.javamancer.neuro.model.warez.Mimic1Warez;

/**
 * <pre>
 * Name: IRS
 * Zone: 1
 * ComLink: 2.0
 * LinkCode: irs
 * Passwords: (1) taxinfo (2) audit (3) only from Cyberspace
 * Warez: Jammies 1.0 (level 3), Hammer 2.0 (level 3), Mimic 1.0
 * Matrix: 272, 64
 * AI: none
 * Weakness: none
 * ICE: 150
 * Content: interesting text
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class IRSDatabase extends Database {

    public IRSDatabase() {
        super(
                "IRS", 7,
                1,
                2,
                "irs",
                "taxinfo", "audit", null,
                272, 64,
                null, null, null,
                150
        );

        warez3.add(Jammies1Warez.class);
        warez3.add(Hammer2Warez.class);
        warez3.add(Mimic1Warez.class);
    }

}
