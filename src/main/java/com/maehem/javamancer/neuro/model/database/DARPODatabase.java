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

import com.maehem.javamancer.neuro.model.warez.Concrete2Warez;
import com.maehem.javamancer.neuro.model.warez.Drill3Warez;
import com.maehem.javamancer.neuro.model.warez.Injector3Warez;
import com.maehem.javamancer.neuro.model.warez.Jammies2Warez;
import com.maehem.javamancer.neuro.model.warez.Thunderhead3Warez;

/**
 * <pre>
 * Name: D.A.R.P.O.
 * Zone: 3
 * ComLink: Only reachable from Cyberspace
 * LinkCode; none
 * Passwords: none
 * Warez: Thunderhead 3.0, Injector 3.0, Jammies 2.0, Concrete 2.0, Drill 3.0
 * Matrix: 336,240
 * AI: none
 * Weakness: none
 * ICE: 400
 * Content: interesting text
 *
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class DARPODatabase extends Database {

    public DARPODatabase() {
        super(null,
                3,
                -1,
                null, null, null, null,
                336, 240,
                null,
                null,
                null,
                400
        );

        warez1.add(Thunderhead3Warez.class);
        warez1.add(Injector3Warez.class);
        warez1.add(Jammies2Warez.class);
        warez1.add(Concrete2Warez.class);
        warez1.add(Drill3Warez.class);

    }

}
