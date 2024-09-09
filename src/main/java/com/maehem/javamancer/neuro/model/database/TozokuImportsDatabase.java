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
import com.maehem.javamancer.neuro.model.warez.AcidWarez;
import com.maehem.javamancer.neuro.model.warez.BlowTorchWarez;
import com.maehem.javamancer.neuro.model.warez.ComLinkWarez;
import com.maehem.javamancer.neuro.model.warez.DecoderWarez;
import com.maehem.javamancer.neuro.model.warez.DrillWarez;
import com.maehem.javamancer.neuro.view.ResourceManager;

/**
 * <pre>
 * name: Tozoku Imports
 * Zone: 1
 * ComLink: 5.0
 * LinkCode: yakuza
 * Passwords: (1) yak (2) only from Cyberspace
 * Warez: Comlink 6.0, Blowtorch 1.0, Decoder 1.0, Blowtorch 3.0, (level 2), Drill 2.0 (level 2), Acid 1.0 (level 2)
 * Matrix: 480, 80
 * AI: none
 * Weakness: none
 * ICE: 150
 * Content: order status, job listings
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class TozokuImportsDatabase extends Database {

    public TozokuImportsDatabase(ResourceManager rm) {
        super(
                "Tozoku Imports", 16,
                1,
                5,
                "yakuza",
                "yak", "test", null,
                480, 80,
                null, null, null,
                150,
                rm
        );

        warez1.put(ComLinkWarez.class, 6);
        warez1.put(BlowTorchWarez.class, 1);
        warez1.put(DecoderWarez.class, 1);

        warez2.put(BlowTorchWarez.class, 3);
        warez2.put(DrillWarez.class, 2);
        warez2.put(AcidWarez.class, 1);

        bbsMessages.add(new BbsMessage("11/16/58", "Iemoto", "Tanenaga", 10, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Tanenaga", "Iemoto", 11, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Iemoto", "Tanenaga", 12, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Iemoto", "P. d'Argen", 14, true)); // Triggered later in game?

    }

}
