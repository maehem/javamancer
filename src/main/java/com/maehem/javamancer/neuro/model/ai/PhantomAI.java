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
import com.maehem.javamancer.neuro.model.warez.BattleChessWarez;

/**
 * AI for Phantom Database
 *
 * Phantom AI seems to be a reference to something that is much older than the
 * matrix that is neither AI or Human. Perhaps someone who used to go by the
 * name Victor. Phantom might also be a reference to "Phantom of the Opera", a
 * story about a mysterious figure who lurks in catacombs beneath a Paris opera
 * house.
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class PhantomAI extends AI {

    public PhantomAI() {
        super("Phantom", 0, 24576,
                LogicSkill.class, BattleChessWarez.class,
                new int[]{36, 37, 38, 39} // AITALK.txh entries
        );
    }

}
