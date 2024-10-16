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

import com.maehem.javamancer.neuro.view.ResourceManager;

/**
 * <pre>
 * Name: Turing Registry
 * Number: 32
 * Zone: 3
 * ComLink: none
 * LinkCode/Passwords: Only reachable from Cyberspace
 * Warez: none
 * Matrix: 432, 240
 * AI: none
 * Weakness: none
 * ICE: 400
 * Content: learn about AIs, upgrade AI Combat Skills to 5, upgrade Psychoanalysis to 4
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class TuringRegistryDatabase extends Database {

    public TuringRegistryDatabase(ResourceManager rm) {
        super(
                "Turing Registry", 32,
                3,
                -1,
                "turingtest",
                null, "test", null,
                432, 240,
                null, null, null,
                400,
                rm
        );
    }

}
