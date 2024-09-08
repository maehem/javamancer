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
import com.maehem.javamancer.neuro.model.ai.SaphireAI;
import com.maehem.javamancer.neuro.model.skill.SophistrySkill;
import com.maehem.javamancer.neuro.model.warez.BlammoWarez;
import com.maehem.javamancer.neuro.model.warez.CenturionWarez;
import com.maehem.javamancer.neuro.model.warez.MegaDeathWarez;
import com.maehem.javamancer.neuro.model.warez.SnailBaitWarez;
import com.maehem.javamancer.neuro.model.warez.ToxinWarez;
import com.maehem.javamancer.neuro.view.ResourceManager;

/**
 * <pre>
 * name: Citizens for a Free Matrix
 * Number: 13
 * Zone: 1
 * ComLink: 4.0
 * LinkCode: freematrix
 * Passwords: (1) CFM (2) only from Cyberspace
 * Warez: Blammo 1.0, everything else is fake
 * Matrix: 352, 112
 * AI: Sapphire
 * Weakness: sophistry
 * ICE: 150
 * Content: nothing
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class FreeMatrixDatabase extends Database {

    {
        bbsMessages.add(new BbsMessage("11/16/58", "Saphire", "Greystoke", 8, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Saphire", "Neuromancer", 9, true));
    }

    public FreeMatrixDatabase(ResourceManager rm) {
        super("Citizens for a Free Matrix", 13,
                1,
                4,
                "freematrix",
                "CFM", null, null, // Higher only from cyberspace.
                352, 112,
                SaphireAI.class, SophistrySkill.class, null,
                150,
                rm
        );

        warez1.put(BlammoWarez.class, 1);
        // Fake Warez (player beware!)
        warez1.put(ToxinWarez.class, 18);
        warez1.put(MegaDeathWarez.class, 4);
        warez1.put(CenturionWarez.class, 5);
        warez1.put(SnailBaitWarez.class, 14);
    }

}
