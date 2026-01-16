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
import com.maehem.javamancer.neuro.model.warez.ComLinkWarez;
import com.maehem.javamancer.neuro.model.warez.DecoderWarez;
import com.maehem.javamancer.neuro.model.warez.DoorStopWarez;
import com.maehem.javamancer.neuro.model.warez.JammiesWarez;
import com.maehem.javamancer.neuro.model.warez.ProbeWarez;
import com.maehem.javamancer.neuro.view.ResourceManager;

/**
 * <pre>
 * Name: Copenhagen University
 * Number: 11
 * Zone: 1
 * ComLink: 3.0
 * LinkCode: brainstorm
 * Passwords: (1) perilous (2) only from Cyberspace
 * Warez: probe 4.0 (level 2), jammies 1.0 (level 2), doorstop 1.0 (level 2)
 * Matrix: 320, 32
 * AI: none
 * Weakness: none
 * ICE: 150
 * learn about ICEbraking software (good, better, best)
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class CopenhagenUniversityDatabase extends Database {

    /*
     * [9] :: To:   Lars Mbutu From: Deathangels Shadow    Great game, Lars.  Sorry about those fingers you lost in the last period.  I guess you wont be typing back a reply that quickly, will you? Hope the spare parts shop in Copenhagen is better than the one in Chiba City.  This pancreas I got sucks.
     * [10] :: To:   Dr. Marsha Sanderson From: Habitual User    Saw a vid of your paper delivery the other day.  Think I got most of the French.  Hit the nail on the head -- no harm in decks.  Promote intellectual development.
     * [11] :: To:   Deathangels Shadow From: Lars Mbutu    Thnk you for your msg bout th gm. you r right tht loing fingr on my lft hn ill mk for iffikult riting, but i ill try.  i njoy th gm.  my lg i lot bttr no.  ont brly limp.  By for no.
     * [12] :: To:    From: Deathangels Shadow    Its getting really spooky out here.  Was supposed to get some information from the Sumdiv Kid, but hes gone null.  Have you seen him?
     * [13] :: To:   All From: Deathangels Shadow    All you new moes remember that all ICE breakers arent created equal. So being the cool guy that I am, I leave the following info for all.  Good:   Decoder, BlowTorch, Hammer Better: DoorStop, Drill Best:   Concrete, DepthCharge,         Logic Bomb  Good Luck
     */
    {
        bbsMessages.add(new BbsMessage("11/16/58", "Lars Mbutu", "Deathangel's", 9, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Dr. Sanderson", "Habitual", 10, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Deathangel's", "Lars Mbutu", 11, true));
        bbsMessages.add(new BbsMessage("11/16/58", "\1", "Deathangel's", 12, true));
        bbsMessages.add(new BbsMessage("11/16/58", "All", "Deathangel's", 13, true));
    }

    public CopenhagenUniversityDatabase(ResourceManager rm) {
        super(
                "Copenhagen University", 11,
                1,
                3,
                "brainstorm",
                "perilous", null, null,
                320, 32,
                null,
                150,
                rm
        );
        warez2.put(ComLinkWarez.class, 4);
        warez2.put(DecoderWarez.class, 1);

        warez3.put(ProbeWarez.class, 4);
        warez3.put(JammiesWarez.class, 1);
        warez3.put(DoorStopWarez.class, 1);
    }

}
