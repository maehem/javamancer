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

import com.maehem.javamancer.neuro.model.ai.MorphyAI;
import com.maehem.javamancer.neuro.model.skill.LogicSkill;
import com.maehem.javamancer.neuro.model.warez.BattleChessWarez;
import com.maehem.javamancer.neuro.view.ResourceManager;

/**
 * <pre>
 * Name: World Chess
 * Number: 3
 * Zone: 0
 * ComLink: 1.0
 * LinkCode: world chess
 * Passwords: (1) novice (2) member (3) only from Cyberspace
 * Warez: BattleChess 4.0
 * Matrix: 160,80
 * AI: Morphy
 * Weakness: Logic
 * ICE: 84
 * Content: 550 credits at playing chess, read AI Message Buffer (level 3)
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class WorldChessDatabase extends Database {

    public WorldChessDatabase(ResourceManager rm) {
        super(
                "World Chess", 3,
                0,
                1,
                "worldchess",
                "novice", "member", null,
                160, 80,
                MorphyAI.class, LogicSkill.class, null,
                84,
                rm
        );

        warez3.put(BattleChessWarez.class, 4);

    }

}
