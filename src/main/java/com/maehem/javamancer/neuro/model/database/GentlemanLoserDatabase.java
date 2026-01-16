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
import com.maehem.javamancer.neuro.model.warez.BlowTorchWarez;
import com.maehem.javamancer.neuro.model.warez.DrillWarez;
import com.maehem.javamancer.neuro.model.warez.HammerWarez;
import com.maehem.javamancer.neuro.model.warez.InjectorWarez;
import com.maehem.javamancer.neuro.model.warez.PickUpGirlsWarez;
import com.maehem.javamancer.neuro.model.warez.ProbeWarez;
import com.maehem.javamancer.neuro.model.warez.SlowWarez;
import com.maehem.javamancer.neuro.view.ResourceManager;

/**
 * <pre>
 * Name: Gentleman Loser
 * Number: 15
 * Zone: 1
 * ComLink: 4.0
 * LinkCode: loser
 * (1) wilson (2) loser (3) only from Cyberspace
 * Warez: Blowtorch 1.0, Hammer 1.0, Probe 3.0, Slow 1.0 (lvl 2), Injector 1.0 (level 2), Drill 1.0 (level 2)
 * Matrix: 416, 64
 * AI: none
 * Weakness: none
 * ICE: 150
 * Content: Linkcode for Eastern Seabod, 2nd level Pw for Bankgemeinschaft
 * </pre>
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class GentlemanLoserDatabase extends Database {

    public GentlemanLoserDatabase(ResourceManager rm) {
        super(
                "Gentleman Loser", 15,
                1,
                4,
                "loser",
                "wilson", "loser", null,
                416, 64,
                null,
                150,
                rm
        );
        warez1.put(PickUpGirlsWarez.class, 1);
        warez1.put(BlowTorchWarez.class, 1);
        warez1.put(HammerWarez.class, 1);
        warez1.put(ProbeWarez.class, 3);

        warez2.put(SlowWarez.class, 1);
        warez2.put(InjectorWarez.class, 1);
        warez2.put(DrillWarez.class, 1);

        // TODO: Double check order of these against original game.
        bbsMessages2.add(new BbsMessage("11/16/58", "P. Oconner", "Osric", 9, true));
        bbsMessages2.add(new BbsMessage("11/16/58", "U. Kris", "Lord B4", 8, true));
        bbsMessages2.add(new BbsMessage("11/16/58", "Bleys", "Keefer", 7, true));
        bbsMessages2.add(new BbsMessage("11/16/58", "Keefer", "Bleys", 6, true));
        bbsMessages2.add(new BbsMessage("11/16/58", "Bleys", "Chaos", 5, true));

        bbsMessages.add(new BbsMessage("11/16/58", "Everyone", "Red Jack", 10, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Red Jack", "Someone", 18, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Ernie", "Bert", 17, true));
        bbsMessages.add(new BbsMessage("11/16/58", "\1", "Matt Shaw", 16, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Chipdancer", "Count Floyd", 14, true));
        bbsMessages.add(new BbsMessage("11/16/58", "8.D. Quixote", "Chipdancer", 15, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Count Floyd", "Matt Shaw", 13, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Dr. Asano", "Matsumoto", 11, true));
        bbsMessages.add(new BbsMessage("11/16/58", "Matsumoto", "Turing", 12, true));
    }

    /*
11/16/58.P. Oconner  .Osric
11/16/58.U. Kris     .Lord B4     .....
11/16/58.Bleys       .Keefer
11/16/58.Keefer   .Bleys
11/16/58.Bleys       .Chaos       ....
11/16/58.Everyone    .Red Jack    .........
 11/16/58.Red Jack    .Someone
11/16/58.Ernie       .Bert        ......
11/16/58 \1 Matt Shaw   .........
11/16/58.Chipdancer  .Count Floyd
11/16/5 8.D. Quixote  .Chipdancer  .....
11/16/58.Count Floyd .Matt Shaw
11/16/58.Dr. Asano   .Matsumoto
11/16/58 Matsumoto   Turing
     */

}
