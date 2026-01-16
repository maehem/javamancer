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
import com.maehem.javamancer.neuro.model.warez.BlowTorchWarez;
import com.maehem.javamancer.neuro.model.warez.ChaosWarez;
import com.maehem.javamancer.neuro.model.warez.ComLinkWarez;
import com.maehem.javamancer.neuro.model.warez.CyberspaceWarez;
import com.maehem.javamancer.neuro.model.warez.DecoderWarez;
import com.maehem.javamancer.neuro.model.warez.MindBenderWarez;
import com.maehem.javamancer.neuro.model.warez.ThunderheadWarez;
import com.maehem.javamancer.neuro.view.ResourceManager;

/**
 * <pre>
 * Name: Panther Moderns
 * Zone: 0
 * ComLink: 2.0
 * LinkCode: chaos
 * Passwords: (1) mainline (2) only from Cyberspace
 * Warez: Comlink 3.0, Blowtorch 3.0 (level 2), Decoder 2.0 (lvl 2), Thunderhead 1.0 (level 2)
 * Matrix: 224, 112
 * AI: none
 * Weakness: none
 * ICE: 84
 * Content: learn about Lupus and Gemeinschaft, linkcodes for Sea and Hitachi
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class PantherModernsDatabase extends Database {

    {
        bbsMessages.add(new BbsMessage("11/16/58", "\1", "Modern Bob", 2, false)); // Hidden until Bob is messaged.
        bbsMessages.add(new BbsMessage("11/16/58", "Everyone", "Mod Yutaka", 3, true));
        bbsMessages.add(new BbsMessage("11/16/58", "All", "Modern Miles", 4, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Everyone", "Polychrome", 5, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Angelo", "Lupus", 6, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Everyone", "Modern Larry", 7, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Modern Miles", "Polychrome", 8, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Everyone", "Modern Bob", 9, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Lupus", "Modern Jane", 15, false)); // Enable after Gemeinshaft incident.
        bbsMessages.add(new BbsMessage("11/16/58", "\1", "Matt Shaw", 16, true)); // Enable after player dies at G-loser.
    }

    public PantherModernsDatabase(ResourceManager rm) {
        super(
                "Panther Moderns", 6,
                0,
                2,
                "chaos",
                "mainline", null, null,
                224, 112,
                null,
                84,
                rm
        );

        warez1.put(ComLinkWarez.class, 3);
        warez1.put(MindBenderWarez.class, 3);
        warez1.put(ChaosWarez.class, 1);

        warez3.put(BlowTorchWarez.class, 3);
        warez3.put(DecoderWarez.class, 2);
        warez3.put(ThunderheadWarez.class, 1);
        warez3.put(CyberspaceWarez.class, 1);

    }


}
