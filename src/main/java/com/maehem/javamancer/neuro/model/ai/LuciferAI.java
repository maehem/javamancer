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
package com.maehem.javamancer.neuro.model.ai;

import com.maehem.javamancer.neuro.model.skill.LogicSkill;

/**
 * AI for KGB Database
 *
 * Lucifer AI namesake likely refers to Operation Luch (Lightbeam), a supposed
 * KGB program in the Cold War focused on recruiting officials, stealing
 * secrets, and influencing reforms, with "Lucifer" potentially being a codename
 * or a metaphorical descriptor for a powerful, deceptive, or disruptive
 * operation, rather than the biblical figure. The name might imply a deep,
 * hidden aspect of Soviet intelligence's reach and tactics, especially as it
 * ties into KGB operations in East Germany.
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class LuciferAI extends AI {

    public LuciferAI() {
        super("Lucifer", 6, 3072,
                LogicSkill.class, null,
                new int[]{32, 33, 34, 35} // AITALK.txh entries
        );
    }

}
