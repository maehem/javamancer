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
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.warez.ComLinkWarez;
import com.maehem.javamancer.neuro.model.warez.SequencerWarez;
import com.maehem.javamancer.neuro.model.warez.ThunderheadWarez;
import com.maehem.javamancer.neuro.view.ResourceManager;

/**
 * <pre>
 * Name: Software Enforcement Agency (SEA)
 * Number: 12
 * Zone: 1
 * ComLink: 3.0
 * LinkCode: soften
 * Passwords: (1) permafrost (2) only from Cyberspace
 * Warez: Comlink 4.0, Sequencer 1.0, Thunderhead 2.0 (level 2)
 * Matrix: 352, 64
 * AI: none
 * Weakness: none
 * ICE: 150
 * Content: upgrade Coptalk to level 2, upgrade Coptalk to level 4 (level 2)
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class SoftwareEnforcementDatabase extends Database {

    public SoftwareEnforcementDatabase(ResourceManager rm) {
        super(
                "Software Enforcement Agency (SEA)", 12,
                1,
                3,
                "soften",
                "permafrost", null, null,
                352, 64,
                null,
                150,
                rm
        );

        warez1.put(ComLinkWarez.class, 4);
        warez1.put(SequencerWarez.class, 1);
        warez3.put(ThunderheadWarez.class, 2);

        bbsMessages.add(new BbsMessage("11/16/58", "SEA", "W. Gibson", 7, true));
        bbsMessages.add(new BbsMessage("11/16/58", "W. Gibson", "SEA", 8, true));
        bbsMessages.add(new BbsMessage("11/16/58", "SEA", "W. Gibson", 9, true));
        bbsMessages.add(new BbsMessage("11/16/58", "W. Gibson", "SEA", 10, true));

    }
    
    // Issue #18
    // TODO:  This might be where we put Player's BAMA if they get money from
    // Armitage.  This could also be for Larry Moe.  The Chiba Police also
    // have a warrant list and either might go on there.  Will need to play
    // acutal game to determine what effects what.
    
    @Override
    public void handlePersonListChanged(GameState gameState) {
        // TODO: What NPCs are affected by this?
//        for (Person p : gameState.seaWantedList) {
//            if (p.getBama().equals(GameState.LARRY_MOE_BAMA)) {
//                gameState.larryMoeWanted = true;
//                return;
//            }
//        }
    }
}
