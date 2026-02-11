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
import com.maehem.javamancer.neuro.model.BbsMessage;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.TextResource;
import com.maehem.javamancer.neuro.model.ai.AI;
import com.maehem.javamancer.neuro.model.skill.Skill;
import com.maehem.javamancer.neuro.model.warez.Warez;
import com.maehem.javamancer.neuro.view.ResourceManager;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
    public final LinkedHashMap<Class<? extends Warez>, Integer> warez1 = new LinkedHashMap<>();
    public final LinkedHashMap<Class<? extends Warez>, Integer> warez2 = new LinkedHashMap<>();
    public final LinkedHashMap<Class<? extends Warez>, Integer> warez3 = new LinkedHashMap<>();
    public final int shotDuration = 2500; // mS
    private final int effect = 100;

    public final int matrixX;
    public final int matrixY;
    public final Class<? extends AI> aiClazz;
    private AI ai;
    public final int ICE_MAX;
    private int ice;

    public final ArrayList<BbsMessage> bbsMessages = new ArrayList<>();
    public final ArrayList<BbsMessage> bbsMessages2 = new ArrayList<>(); // GentlemanLoser

    public Database(String name, int number, int zone, int comlink,
            String linkCode, String password1, String password2, String password3,
            int matrixX, int matrixY,
            Class<? extends AI> ai,
            int ice,
            ResourceManager rm
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
        this.aiClazz = ai;
        this.ICE_MAX = ice;
        this.ice = ice;

        initMessages(rm.getDatabaseText(number));
    }

    private void initMessages(TextResource tr) {
        for (BbsMessage msg : bbsMessages) {
            if (msg.prefillIndex >= 0) {
                msg.body = tr.get(msg.prefillIndex);
            }
        }
        for (BbsMessage msg : bbsMessages2) {
            if (msg.prefillIndex >= 0) {
                msg.body = tr.get(msg.prefillIndex);
            }
        }
    }

    public void enableMessage(BbsMessage item) {
        item.show = true;
        if (bbsMessages.contains(item)) {
            bbsMessages.remove(item);
            bbsMessages.addLast(item);
        } else {
            bbsMessages2.remove(item);
            bbsMessages2.addLast(item);
        }
    }

    public int getIce() {
        return ice;
    }

    public void resetIce() {
        ice = ICE_MAX;
    }
    
    /**
     * Used by final Neuromancer battle.
     * 
     */
    public void zeroIce() {
        ice = 0;
    }

    /**
     * Returns ICE health if above 0. Otherwise AI health if AI present.
     *
     * @param gs
     * @return 0 - 100 percent of either ICE or AI.
     */
    public int getBattleHealth(GameState gs) {
        if (getIce() > 0) {
            return 100 * (1 - getIce() / ICE_MAX);
        } else {
            if (aiClazz != null) {
                if (ai == null) {
                    ai = gs.getAI(aiClazz);
                }
                return 100 * (1 - ai.getConstitution() / ai.MAX_CONSTITUTION);
            }
        }

        return 0;
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
        LOGGER.log(Level.FINE, "Apply {0} damage to database.  Ice now: {1}", new Object[]{damage, ice});

        return damage;
    }

    public int applySkillAttack(Skill skill, GameState gs) {
        // TODO: Each Warez, Apply Skill buff.

        int damage = skill.getEffect(gs);
        ice -= damage;
        if (ice < 0) {
            damage += ice; // ice is negative, so lower damage reported.
            ice = 0;
        }
        LOGGER.log(Level.FINE, "Apply {0} Skill damage of {1} to database.", new Object[]{skill.catalog.name(), damage});
        LOGGER.log(Level.FINE, "    Database ice  = {0}", ice);

        return damage;
    }

    public int getEffect(GameState gs) {
        // TODO: Consider player attributes (skill, etc.)
        return effect;
    }

    public void handlePersonListChanged(GameState gs) {
        // Over-ride in subclass to take action when person list is edited.
    }

}
