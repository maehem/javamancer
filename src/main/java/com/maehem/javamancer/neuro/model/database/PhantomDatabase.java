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

import com.maehem.javamancer.neuro.model.ai.PhantomAI;
import com.maehem.javamancer.neuro.model.warez.HemlockWarez;
import com.maehem.javamancer.neuro.view.ResourceManager;

/**
 * <pre>
 * Name: Phantom
 * Numer: 36
 * Zone: 7
 * ComLink: none
 * LinkCode/Passwords: Only reachable from Cyberspace
 * Warez: Hemlock 1.0
 * Matrix: 320, 464
 * AI: Phantom
 * Weakness: Logic or Battle Chess 4.0
 * ICE: 2000
 * Content: owns the program to destroy Greystoke
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class PhantomDatabase extends Database {

    public PhantomDatabase(ResourceManager rm) {
        super("Phantom", 36,
                7,
                -1,
                "phantomtest",
                null, "test", null,
                320, 464,
                PhantomAI.class,
                2000,
                rm
        );

        warez1.put(HemlockWarez.class, 1);
    }

}
