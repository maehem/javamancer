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

import com.maehem.javamancer.neuro.model.warez.DoorStop1Warez;
import com.maehem.javamancer.neuro.model.warez.Jammies1Warez;
import com.maehem.javamancer.neuro.model.warez.Probe4Warez;

/**
 * <pre>
 * Name: Copenhagen University
 * Zone: 1
 * ComLink: 3.0
 * LinkCode: brainstorm
 * Passwords: (1) perilous (2) only from Cyberspace
 * Warez: probe 4.0 (level 2), jammies 1.0 (level 2), doorstop 1.0 (level 2)
 * Matrix: 320, 32
 * AI: none
 * Weakness: none
 * ICE: 150
 * learn about ICEbraking software (good, better, best)
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class CopenhagenUniversityDatabase extends Database {

    public CopenhagenUniversityDatabase() {
        super(
                "Copenhagen University", 11,
                1,
                3,
                "brainstorm",
                "perilous", null, null,
                0, 0,
                null,
                null, null,
                150
        );

        getWarez2().add(Probe4Warez.class);
        getWarez2().add(Jammies1Warez.class);
        getWarez2().add(DoorStop1Warez.class);
    }

}
