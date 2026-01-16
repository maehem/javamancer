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

import com.maehem.javamancer.neuro.model.ai.ChromeAI;
import com.maehem.javamancer.neuro.model.skill.PhilosophySkill;
import com.maehem.javamancer.neuro.model.warez.ThunderheadWarez;
import com.maehem.javamancer.neuro.view.ResourceManager;

/**
 * <pre>
 * Name: Psychologist
 * Zone: 0
 * Comlink: 2.0
 * LinkCode: psycho
 * Passwords: (1) new mo (2) babylon (3) only from Cyberspace
 * Warez: none
 * Matrix: 96, 32
 * AI: Chrome
 * Weakness: Philosophy
 * ICE: 84
 * Content: read about Yakuza, AI Message Buffer (level 3)
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class PsychologistDatabase extends Database {

    public PsychologistDatabase(ResourceManager rm) {
        super(
                "Psychologist", 5,
                0,
                2,
                "psycho",
                "new mo", "babylon", null,
                96, 32,
                ChromeAI.class,
                84,
                rm
        );
        
        warez3.put(ThunderheadWarez.class, 1);
        
    }

}
