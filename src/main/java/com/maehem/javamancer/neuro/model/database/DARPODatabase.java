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

import com.maehem.javamancer.neuro.model.warez.ConcreteWarez;
import com.maehem.javamancer.neuro.model.warez.DrillWarez;
import com.maehem.javamancer.neuro.model.warez.InjectorWarez;
import com.maehem.javamancer.neuro.model.warez.JammiesWarez;
import com.maehem.javamancer.neuro.model.warez.ThunderheadWarez;
import com.maehem.javamancer.neuro.view.ResourceManager;

/**
 * <pre>
 * Name: D.A.R.P.O.
 * Number: 31
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

    public DARPODatabase(ResourceManager rm) {
        super("DARPO", 31,
                3,
                -1,
                "darpotest",
                null, "test", null,
                336, 240,
                null,
                null,
                null,
                400,
                rm
        );

        warez1.put(ThunderheadWarez.class, 3);
        warez1.put(InjectorWarez.class, 3);
        warez1.put(JammiesWarez.class, 2);
        warez1.put(ConcreteWarez.class, 2);
        warez1.put(DrillWarez.class, 3);

    }

}
