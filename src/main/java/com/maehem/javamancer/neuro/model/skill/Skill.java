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
package com.maehem.javamancer.neuro.model.skill;

import com.maehem.javamancer.logging.Logging;
import java.util.logging.Logger;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class Skill {

    public static final Logger LOGGER = Logging.LOGGER;
    public final Type type;

    public enum Type {
        NONE("none", "..."),
        BARGAINING("Barganing", "Use for getting better pricing."),
        COPTALK("Cop Talk", "Freeze dirtag!!!"),
        WAREZ_ANALYSIS("Warez Analysis", "What version of PowerPoint is this?"),
        DEBUG("Debug", "More like whack-a-mole."),
        HW_REPAIR("Hardware Repair", "Turn it off and then turn it back on."),
        ICE_BREAKING("I.C.E. Breaking", "I'm in!"),
        EVASION("Evasion", "Slippery little bugger!"),
        CRYPTOLOGY("Cryptology", "I know PGP!"),
        JAPANESE("Japanese", "In Ohio, goats are a mess."),
        LOGIC("Logic", "result = 2B | !2B"),
        PSYCHOANALYSIS("Psychoanalysis", "Come lay on the couch..."),
        PHENOMENOLOGY("Phenomenology", "Why are we here?"),
        PHILOSOPHY("Philosophy", "excuse me, I was still talking."),
        SOPHISTRY("Sophistry", "Gaslight Express! Arriving on track One."),
        ZEN("Zen", "Mommy needs some \"quiet time\"."),
        MUSICIANSHIP("Muscianship", "Name that tune...");

        public final String itemName;
        public final String description;

        private Type(String name, String description) {
            this.itemName = name;
            this.description = description;
        }

    };

    public Skill(Type type) {
        this.type = type;
    }

    public abstract void use();
}
