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
import com.maehem.javamancer.neuro.model.warez.DoorStopWarez;
import com.maehem.javamancer.neuro.model.warez.HammerWarez;
import com.maehem.javamancer.neuro.model.warez.InjectorWarez;
import com.maehem.javamancer.neuro.model.warez.LogicBombWarez;
import com.maehem.javamancer.neuro.view.ResourceManager;

/**
 * <pre>
 * Name: I.N.S.A.
 * Number: 25
 * Zone: 5
 * ComLink, linkcode, password: Only reachable from Cyberspace
 * Warez: Injector 5.0, Armorall 3.0, Hammer 6.0, Doorstop 4.0, Logic bomb 3.0
 * Matrix: 448, 320
 * AI: none
 * Weakness: none
 * ICE: 1000
 * Content: interesting text
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class INSADatabase extends Database {

    public INSADatabase(ResourceManager rm) {
        super(
                "I.N.S.A", 25,
                5,
                -1,
                "insatest",
                null, "test", null,
                448, 320,
                null, null, null,
                1000,
                rm
        );

        warez1.put(ArmorallWarez.class, 3);
        warez1.put(HammerWarez.class, 6);
        warez1.put(LogicBombWarez.class, 3);
        warez1.put(InjectorWarez.class, 5);
        warez1.put(DoorStopWarez.class, 4);

    }


}
