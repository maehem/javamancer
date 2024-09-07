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

import com.maehem.javamancer.neuro.model.warez.ArmorallWarez;
import com.maehem.javamancer.neuro.model.warez.DepthChargeWarez;
import com.maehem.javamancer.neuro.model.warez.EasyRiderWarez;
import com.maehem.javamancer.neuro.model.warez.KGBWarez;
import com.maehem.javamancer.neuro.model.warez.PythonWarez;
import com.maehem.javamancer.neuro.model.warez.SlowWarez;
import com.maehem.javamancer.neuro.view.ResourceManager;

/**
 * <pre>
 * Name: Screaming Fist
 * Zone: 3
 * ComLink: none
 * LinkCode/Passwords: Only reachable from Cyberspace
 * Warez: KGB 1.0, Easy Rider 1.0, Python 3.0, Slow 3.0, Armorall 1.0, Depthcharge 3.0
 * Matrix: 464, 160
 * AI: none
 * Weakness: none
 * ICE: 400
 * Content: interesting text
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class ScreamingFistDatabase extends Database {

    public ScreamingFistDatabase(ResourceManager rm) {
        super(
                "Screaming Fist", 33,
                3,
                -1,
                null,
                null, null, null,
                464, 160,
                null, null, null,
                400,
                rm
        );
        warez1.put(KGBWarez.class, 1);
        warez1.put(EasyRiderWarez.class, 1);
        warez1.put(PythonWarez.class, 3);
        warez1.put(SlowWarez.class, 3);
        warez1.put(ArmorallWarez.class, 1);
        warez1.put(DepthChargeWarez.class, 3);
    }

}
