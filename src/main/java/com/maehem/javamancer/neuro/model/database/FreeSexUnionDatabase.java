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

import com.maehem.javamancer.neuro.model.BbsMessage;
import com.maehem.javamancer.neuro.model.ai.XavieraAI;
import com.maehem.javamancer.neuro.model.skill.PhenomenologySkill;
import com.maehem.javamancer.neuro.view.ResourceManager;

/**
 * <pre>
 * Name: Free Sex Union
 * Number: 29
 * Zone: 3
 * ComLink: none
 * Passwords: Only reachable from Cyberspace
 * Warez: none
 * Matrix: 288, 208
 * AI: Xaviera
 * Weakness: Phenomenology
 * ICE: 400
 * Content: interesting text
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class FreeSexUnionDatabase extends Database {

    public FreeSexUnionDatabase(ResourceManager rm) {
        super("Free Sex Union", 29,
                3,
                -1,
                "freesextest", // Set to null before release
                null, "test", null,
                288, 208,
                XavieraAI.class, PhenomenologySkill.class, null,
                400,
                rm
        );

        bbsMessages.add(new BbsMessage("11/16/58", "All", "SM, 41", 4, true));
        bbsMessages.add(new BbsMessage("11/16/58", "All", "SF, 21", 5, true));
        bbsMessages.add(new BbsMessage("11/16/58", "All", "SF, 44", 6, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Xaviera", "SM, 15", 7, true));
        bbsMessages.add(new BbsMessage("11/16/58", "SM, 15", "Xaviera", 8, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Xaviera", "SM, 15", 9, true));
        bbsMessages.add(new BbsMessage("11/16/58", "SM, 15", "Xaviera", 10, true));

        bbsMessages2.add(new BbsMessage("11/16/58", "Xaviera", "Greystoke", 11, true));
        bbsMessages2.add(new BbsMessage("11/16/58", "Xaviera", "Neuromancer", 12, true));
    }

}
