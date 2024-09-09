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
import com.maehem.javamancer.neuro.model.ai.GreystokeAI;
import com.maehem.javamancer.neuro.model.warez.HemlockWarez;
import com.maehem.javamancer.neuro.model.warez.KuangElevenWarez;
import com.maehem.javamancer.neuro.view.ResourceManager;

/**
 * <pre>
 * Name: Musabori
 * Number: 19
 * Zone: 2
 * ComLink: 5.0
 * LinkCode: musaborind
 * Passwords: (1) subaru (2) only from Cyberspace
 * Warez: Kuang Eleven 1.0 (level 3)
 * Matrix: 208, 208
 * AI: Greystoke
 * Weakness: Hemlock 1.0
 * ICE: 260
 * Content: interesting text, AI Message Buffer (level 3)
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class MusaboriDatabase extends Database {

    public MusaboriDatabase(ResourceManager rm) {
        super("Musabori", 19,
                2,
                5,
                "musaborind",
                "subaru", "test", null,
                208, 208,
                GreystokeAI.class, null, HemlockWarez.class,
                260,
                rm
        );

        warez2.put(KuangElevenWarez.class, 1);

        bbsMessages.add(new BbsMessage("11/16/58", "Greystoke", "Chrome", 11, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Greystoke", "Neuromancer", 12, true));
    }

}
