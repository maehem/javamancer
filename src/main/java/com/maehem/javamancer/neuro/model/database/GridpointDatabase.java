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

import com.maehem.javamancer.neuro.model.warez.Armorall2Warez;
import com.maehem.javamancer.neuro.model.warez.Hammer5Warez;
import com.maehem.javamancer.neuro.model.warez.Injector3Warez;
import com.maehem.javamancer.neuro.model.warez.Jammies3Warez;
import com.maehem.javamancer.neuro.model.warez.Thunderhead3Warez;

/**
 * <pre>
 * Name: Gridpoint
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

    public GridpointDatabase() {
        super(
                "Gridpoint",
                4,
                -1,
                null,
                null, null, null,
                160, 320,
                null, null, null,
                0);

        warez1.add(Thunderhead3Warez.class);
        warez1.add(Injector3Warez.class);
        warez1.add(Jammies3Warez.class);
        warez1.add(Hammer5Warez.class);
        warez1.add(Armorall2Warez.class);

    }

}
