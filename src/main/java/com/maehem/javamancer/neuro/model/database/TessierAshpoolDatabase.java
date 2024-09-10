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
import com.maehem.javamancer.neuro.model.ai.WintermuteAI;
import com.maehem.javamancer.neuro.model.skill.SophistrySkill;
import com.maehem.javamancer.neuro.view.ResourceManager;

/**
 * <pre>
 * Name: Tessier-Ashpool
 * Number: 37
 * Zone: 7
 * ComLink: none
 * LinkCode/Passwords: Only reachable from Cyberspace
 * Warez: none
 * Matrix: 384, 416
 * AI: Wintermute
 * Weakness: sophistry
 * ICE: 2000
 * Content: interesting text
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class TessierAshpoolDatabase extends Database {

    public TessierAshpoolDatabase(ResourceManager rm) {
        super(
                "Tessier-Ashpool", 37,
                7,
                -1,
                "tessiertest",
                null, "test", null,
                384, 416,
                WintermuteAI.class, SophistrySkill.class, null,
                2000,
                rm
        );

        bbsMessages.add(new BbsMessage("11/16/58", "Wintermute", "Greystoke", 4, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Wintermute", "Neuromancer", 5, true));
        bbsMessages.add(new BbsMessage("11/16/58", "\1", "Wintermute", 6, true));
    }

}
