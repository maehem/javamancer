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
 * Name: Bank of Zurich Orbital
 * Number: 21
 * Zone: 5
 * ComLink: 6.0
 * bozobank
 * no pw, must use Sequencer
 * Warez: none
 * 336, 368
 * AI: none
 * Weakness: none
 * ICE: 1000
 * Content: prepare account with number 712345450134
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class BankZurichDatabase extends Database {

    public BankZurichDatabase(ResourceManager rm) {
        super("Bank of Zurich Orbital", 21,
                5,
                6,
                "bozobank",
                null, "test", null, // Must use Sequencer
                336, 368,
                null, null, null,
                1000,
                rm
        );
        /*
        [5] :: To:  From: Phillip dArgent      Thank you for opening an account with us.  I am certain you will find us the soul of discretion.  We look forward to a long and fruitful relationship with you.
        [6] :: To:  From: Matt Shaw      Theres definitely some strange things going on in the matrix, thats for certain.  Ive not heard from Distress Damsel or the Sumdiv Kid lately.  Have you?  Its really strange not to have them out here messing around.
        [7] :: To: Graceland Foundation From: Phillip dArgent      I have taken the steps you have requested to squash the unauthorized  cloning attempt that concerned you. Our experts agree that obtaining another DNA sample will be difficult. We have planted a story within the Presley underground that the clone attempt was by a Rastafarian group wishing to use the King as a symbol.     We do suggest you reinforce the concrete over the grave and add  cyberhounds to patrol at night to prevent another attempt at resurrection. Our experts have been paid from your account and we feel certain the spectacular reports of the Delhi explosion will prevent others from attempting to clone your client.  Your obedient servant, PdA
        [8] :: To: Phillip dArgent From: Thomas Cole  Phillip,     Thank you for being so frank about your losses.  We have an excellent security man, Roger Kaliban, would could be of service to you, if you need him.  I appreciate your sharing your information with me. We shall be on the look-out for any strangeness that occurs in our accounts.

         */
        bbsMessages.add(new BbsMessage("11/16/58", "\1", "Matt Shaw", 6, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Graceland", "P. d'Argent", 7, true));
        bbsMessages.add(new BbsMessage("11/16/58", "P. d'Argent", "T. Cole", 8, true));
        bbsMessages.add(new BbsMessage("00/00/00", "\1", "P. d'Argent", 5, false));
    }

}
