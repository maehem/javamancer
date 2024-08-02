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

import com.maehem.javamancer.neuro.model.warez.BlowTorchWarez;
import com.maehem.javamancer.neuro.model.warez.ComLinkWarez;
import com.maehem.javamancer.neuro.model.warez.DecoderWarez;
import com.maehem.javamancer.neuro.model.warez.ThunderheadWarez;

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

    public PantherModernsDatabase() {
        super(
                "Panther Moderns", 6,
                0,
                2,
                "chaos",
                "mainline", null, null,
                224, 112,
                null, null, null,
                84
        );

        warez1.put(ComLinkWarez.class, 3);
        warez2.put(BlowTorchWarez.class, 3);
        warez2.put(DecoderWarez.class, 2);
        warez2.put(ThunderheadWarez.class, 1);
    }

}
