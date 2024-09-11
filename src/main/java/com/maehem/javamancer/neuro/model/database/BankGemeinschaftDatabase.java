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
import com.maehem.javamancer.neuro.model.warez.BudgetPalWarez;
import com.maehem.javamancer.neuro.model.warez.DecoderWarez;
import com.maehem.javamancer.neuro.model.warez.ReceiptForgerWarez;
import com.maehem.javamancer.neuro.view.ResourceManager;

/**
 * <pre>
 * Name: Bank Gemeinschaft
 * Number: 18
 * Zone: 5
 * ComLink: 5.0
 * LinkCode: bankgemein
 * Passwords: (1) eintritt (2) verboten
 * Warez: none
 * Matrix: 304, 320
 * AI: none
 * Weakness: none
 * ICE: 1000
 * Content: transfer 30,000 credit from account number 646328356481
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class BankGemeinschaftDatabase extends Database {

    public BankGemeinschaftDatabase(ResourceManager rm) {
        super(
                "Bank Gemeinschaft", 18,
                5,
                5,
                "bankgemein",
                "eintritt", "verboten", null,
                304, 320,
                null, null, null,
                1000,
                rm
        );

        warez2.put(DecoderWarez.class, 1);
        warez2.put(BudgetPalWarez.class, 24);
        warez2.put(ReceiptForgerWarez.class, 7);

        bbsMessages.add(new BbsMessage("11/16/58", "Geistjager", "M. Godot", 11, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Geistjager", "R. Kaliban", 12, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Epkot", "R. Kaliban", 13, true));
        bbsMessages.add(new BbsMessage("11/16/58", "\1", "M. Shaw", 14, true));
        bbsMessages.add(new BbsMessage("11/16/58", "A. Finch", "T. Cole", 15, true));
        bbsMessages.add(new BbsMessage("11/16/58", "T. Cole", "A. Finch", 16, true));

    }

}
