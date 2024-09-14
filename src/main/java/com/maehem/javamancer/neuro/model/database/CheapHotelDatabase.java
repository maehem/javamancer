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
import com.maehem.javamancer.neuro.view.ResourceManager;

/**
 * <pre>
 * Name: Cheap Hotel
 * Number: 4
 * Zone: 0
 * ComLink: 1.0
 * LinkCode: cheapo
 * Passwords: (1) guest (2) cockroach
 * Warez: none
 * Matrix: 112, 112
 * AI: none
 * Weakness: none
 * ICE: 84
 * Content: pay / edit bill, order caviar from Room Service
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class CheapHotelDatabase extends Database {

    public CheapHotelDatabase(ResourceManager rm) {
        super("Cheap Hotel", 4,
                0,
                1,
                "cheapo", "guest", "cockroach", null,
                112, 112,
                null, null, null,
                84,
                rm);

        bbsMessages.add(new BbsMessage("11/16/58", null, "Donut World", 4, true));
        bbsMessages.add(new BbsMessage("11/16/58", null, "Manyusha Wanna Massage", 5, true));
        bbsMessages.add(new BbsMessage("11/16/58", null, "Psychologist", 5, true));
        bbsMessages.add(new BbsMessage("11/16/58", null, "Crazy Edo's", 7, true));
    }

}
