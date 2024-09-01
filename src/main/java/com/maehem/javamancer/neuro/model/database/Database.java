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

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.ai.AI;
import com.maehem.javamancer.neuro.model.skill.Skill;
import com.maehem.javamancer.neuro.model.warez.Warez;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class Database {

    public static final Logger LOGGER = Logging.LOGGER;

    public final String name;
    public final int number;
    public final int zone;
    public final int comlink;
    public final String linkCode;
    public final String password1;
    public final String password2;
    public final String password3;
    public final HashMap<Class<? extends Warez>, Integer> warez1 = new HashMap<>();
    public final HashMap<Class<? extends Warez>, Integer> warez2 = new HashMap<>();
    public final HashMap<Class<? extends Warez>, Integer> warez3 = new HashMap<>();

    public final int matrixX;
    public final int matrixY;
    public final Class<? extends AI> ai;
    public final Class<? extends Skill> weaknessSkill;
    public final Class<? extends Warez> weaknessWarez;
    public final int ICE_MAX;
    private int ice;

    public Database(String name, int number, int zone, int comlink,
            String linkCode, String password1, String password2, String password3,
            int matrixX, int matrixY,
            Class<? extends AI> ai,
            Class<? extends Skill> weaknessSkill,
            Class<? extends Warez> weaknessWarez,
            int ice
    ) {
        this.name = name;
        this.number = number;
        this.zone = zone;
        this.comlink = comlink;
        this.linkCode = linkCode;
        this.password1 = password1;
        this.password2 = password2;
        this.password3 = password3;
        this.matrixX = matrixX;
        this.matrixY = matrixY;
        this.ai = ai;
        this.weaknessSkill = weaknessSkill;
        this.weaknessWarez = weaknessWarez;
        this.ICE_MAX = ice;
        this.ice = ice;
    }

    public int getIce() {
        return ice;
    }

    public int applyWarezAttack(Warez w, GameState gs) {
        // TODO: Each Warez, Apply Skill buff.

        // TODO: Decide if attack is ICE, AI or something else.
        int damage = w.getEffect(gs);
        ice -= damage;
        if (ice < 0) {
            damage += ice; // ice is negative, so lower damage reported.
            ice = 0;
        }
        LOGGER.log(Level.SEVERE, "Apply " + damage + " to database.");
        if (ice == 0) {
            LOGGER.log(Level.SEVERE, "Database reached 0 ice.");
        } else {
            LOGGER.log(Level.SEVERE, "Database ice  = " + ice);
        }
        return damage;
    }
}
