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

import com.maehem.javamancer.neuro.model.ai.GoldAI;
import com.maehem.javamancer.neuro.model.skill.PhilosophySkill;
import com.maehem.javamancer.neuro.model.warez.ArmorallWarez;
import com.maehem.javamancer.neuro.model.warez.ProbeWarez;
import com.maehem.javamancer.neuro.model.warez.SlowWarez;

/**
 * <pre>
 * Bank of Berne
 * Zone: 3
 * Passwords: Only reachable from Cyberspace
 * Warez: Slow 3.0, Probe 10.0, Armorall 1.0
 * Matrix: 336, 160
 * AI: Gold
 * Weakness: philosophy
 * ICE: 400
 * Content: transfer 500,000 credits to your Bozobank account number: 121519831200
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class BankBerneDatabase extends Database {

    public BankBerneDatabase() {
        super(
                "Bank of Berne", 30,
                3,
                -1,
                null,
                null, null, null,
                336, 160,
                GoldAI.class,
                PhilosophySkill.class,
                null,
                400
        );

        warez1.put(SlowWarez.class, 3);
        warez1.put(ProbeWarez.class, 1);
        warez1.put(ArmorallWarez.class, 1);

    }

}
