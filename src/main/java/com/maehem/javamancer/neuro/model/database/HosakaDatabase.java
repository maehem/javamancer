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

import com.maehem.javamancer.neuro.model.warez.ComLinkWarez;
import com.maehem.javamancer.neuro.model.warez.ConcreteWarez;
import com.maehem.javamancer.neuro.model.warez.HammerWarez;
import com.maehem.javamancer.neuro.model.warez.InjectorWarez;
import com.maehem.javamancer.neuro.model.warez.MimicWarez;
import com.maehem.javamancer.neuro.model.warez.SlowWarez;

/**
 * <pre>
 * Name: Hosaka
 * Zone: 2
 * ComLink: 5.0
 * LinkCode: hosakacorp
 * Passwords: (1) biosoft (2) fungeki (3) only from Cyberspace
 * Warez: Comlink 5.0, Slow 2.0 (level 2), Hammer 4.0 (level 2), Concrete 1.0 (level 2), Mimic 2.0 (level 2), Injector 2.0 (level 2)
 * Matrix: 144, 160
 * AI: none
 * Weakness: none
 * ICE: 260
 * Content: learn about Tozoku and Yakuza. Paycheque
 *
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class HosakaDatabase extends Database {

    public HosakaDatabase() {
        super(
                "Hosaka Corporation", 17,
                2,
                5,
                "hosakacorp",
                "biosoft", "fungeki", null,
                144, 160,
                null, null, null,
                260
        );

        warez1.put(ComLinkWarez.class, 5);
        warez2.put(SlowWarez.class, 2);
        warez2.put(HammerWarez.class, 4);
        warez2.put(ConcreteWarez.class, 1);
        warez2.put(MimicWarez.class, 2);
        warez2.put(InjectorWarez.class, 2);
    }

}
