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

import com.maehem.javamancer.neuro.model.skill.PhenomenologySkill;

/**
 * AI for Free Sex Union Database
 *
 * Fun Fact! This AI namesake and inference to the Free Sex Union Database is a
 * reference to Penthouse Magazine writer Xaviera Hollander, a real person.
 * Riding on the popularity from her 1971 published memoir entitled "The Happy
 * Hooker", Xaviera agreed to write a monthly sexual advice column for Penthouse
 * in 1972. The Penthouse column featuring advice to those to mailed her letters
 * was popular enough to last more than a decade in Penthouse and Forum
 * magazines.
 *
 * The game designers were way ahead with their concept of an AI that could give
 * sex advice, yet here we are in the mid-2020s with AI's that do exactly that.
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class XavieraAI extends AI {

    public XavieraAI() {
        super("Xaviera", 0, 768,
                PhenomenologySkill.class, null,
                new int[]{20, 21, 22, 23} // AITALK.txh entries
        );
    }

}
