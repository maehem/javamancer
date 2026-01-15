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
package com.maehem.javamancer.neuro.model.ai;

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.TextResource;
import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.skill.Skill;
import com.maehem.javamancer.neuro.model.warez.Warez;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <pre>
 * There are specific anti-AI software and skills. Each AI is weak to one
 * particular software or skill and hitting them with their weakness will either
 * kill them outright or open them up to further attacks.
 *
 *      Psychoanalysis skill will occasionally reveal whether an AI is weak
 *      against a particular skill.
 *      Software weaknesses are revealed through text messages in databases.
 *
 * The four anti-AI @Skill(s) are: Philosophy, Sophistry, Phenomenology and Logic.
 * The three anti-AI @Warez are BattleChess, Hemlock and KuangEleven.
 *      When you use an AI’s weakness against it, it will insult you.
 *      If it survives being struck by its weakness, player can then use any of the
 *      other anti-AI skills against it. Like with ICE battles, you should cycle
 *      through the skills but more for the purpose of levelling up your skills
 *      than maximising damage.
 *</pre>
 * 
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class AI {

    public static final Logger LOGGER = Logging.LOGGER;

    public final String name;
    public final int index; // matching the sprite face graphic
    private int constitution;
    public final int MAX_CONSTITUTION;

    public AI(String name, int index, int constitution) {
        this.name = name;
        this.index = index;
        this.MAX_CONSTITUTION = constitution;
        this.constitution = constitution;
        // For future reference:
        // constitution formula :: (0x30 << index)
        // except Greystoke and Neuromancer:
        //     Greystoke :: (0x30 << index) - 1
        //   Neuromancer :: ( 0x30 << (index-1) )

    }

    public void setConstitution(int value) {
        this.constitution = value;
    }

    public int getConstitution() {
        return constitution;
    }

    public void applyWarezAttack(Warez warez, GameState gs) {
        int effect = warez.getEffect(gs);
        this.constitution -= effect;
        if (constitution < 0) {
            constitution = 0;
        }
        LOGGER.log(Level.INFO, "AI Takes Damage from {0} Warez of {1}.  AI constitution: {2}", new Object[]{warez.item.name(), effect, constitution});
    }

    public void applySkillAttack(Skill skill, GameState gs) {
        int effect = skill.getEffect(gs);
        this.constitution -= effect;
        if (constitution < 0) {
            constitution = 0;
        }
        LOGGER.log(Level.INFO, "AI Takes Damage from {0} Skill of {1}.  AI constitution: {2}", new Object[]{skill.catalog.name(), effect, constitution});
    }

    public int getEffect() {
        return MAX_CONSTITUTION / 10;
    }

    public abstract Item.Catalog getWeakness();

    /**
     *
     * @param prefix
     * @param p
     */
    public void putProps(String prefix, Properties p) {
        p.put(prefix, getClass().getSimpleName());
        //p.put(prefix + ".constitution", String.valueOf(constitution));
    }

    public void pullProps(String prefix, Properties p) {
        String get = p.getProperty(prefix + ".constitution", "1");
        LOGGER.log(Level.INFO, () -> "Restore constitution value = " + get);
        constitution = 0; // If it was stored in save game, it's a dead AI.
        //constitution = Integer.parseInt(get);
    }

    public static AI lookup(String clazzName) {
        LOGGER.log(Level.FINER, "Get AI instance.");
        try {
            Class<?> clazz = Class.forName(AI.class.getPackageName() + "." + clazzName);
            if (AI.class.isAssignableFrom(clazz)) {
                LOGGER.log(Level.FINER, "It's an AI class.");
            } else {
                LOGGER.log(Level.WARNING, "It's NOT an AI class.");
            }
            @SuppressWarnings("unchecked")
            Constructor<?> ctor = clazz.getConstructor();

            Object object = ctor.newInstance();
            LOGGER.log(Level.CONFIG, "AI Object created: {0}", object.getClass().getSimpleName());
            if (object instanceof AI ai) {
                return ai;
            } else {
                LOGGER.log(Level.SEVERE, "Thing is not a AI.");
            }
        } catch (ClassNotFoundException
                | InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException
                | NoSuchMethodException
                | SecurityException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return null;
    }

    public TextResource getDialogs(GameState gs) {
        return gs.resourceManager.getTxhText("AITEXT");
    }
}
