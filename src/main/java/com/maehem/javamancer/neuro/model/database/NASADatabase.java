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

import com.maehem.javamancer.neuro.model.ai.HalAI;
import com.maehem.javamancer.neuro.model.skill.LogicSkill;

/**
 * <pre>
 * Name: NASA
 * Zone: 1
 * ComLink: 6.0
 * LinkCode: voyager
 * Passwords: (1) apollo (2) only from Cyberspace
 * Warez: Python 2.0, Blowtorch 4.0, Decoder 4.0
 * Matrix: 448, 32
 * AI: Hal
 * Weakness: Logic
 * ICE: 150
 * Content: interesting text, AI Message Buffer
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class NASADatabase extends Database {

    public NASADatabase() {
        super(
                "NASA",
                1,
                6,
                "voyager",
                "apollo", null, null,
                448, 32,
                HalAI.class, LogicSkill.class, null,
                150
        );
    }

}
