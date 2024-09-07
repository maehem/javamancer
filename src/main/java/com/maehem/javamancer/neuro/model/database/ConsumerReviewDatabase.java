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

import com.maehem.javamancer.neuro.model.warez.BlammoWarez;
import com.maehem.javamancer.neuro.view.ResourceManager;

/**
 * <pre>
 * Name: Consumer Review
 * Zone: 0
 * ComLink: 1.0
 * LinkCode: consumerev
 * Passwords: (1) review (2) only from Cyberspace
 * Warez: Blammo 1.0, which is fake
 * Matrix: 32, 64
 * AI: none
 * Weakness: none
 * ICE: 84
 * Content: learn about decks and decks with Cyberspace access
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class ConsumerReviewDatabase extends Database {

    public ConsumerReviewDatabase(ResourceManager rm) {
        super("Consumer Review", 1,
                0,
                1,
                "consumerev",
                "review", null, null, // 2 cyberspace
                32, 64,
                null, null, null,
                84,
                rm
        );

        warez1.put(BlammoWarez.class, 1);
    }

}
