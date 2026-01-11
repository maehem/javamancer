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
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.item.Item.Catalog;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.logging.Level;
import static java.util.logging.Level.FINE;
import java.util.logging.Logger;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class Skill {

    public static final Logger LOGGER = Logging.LOGGER;

//        BARGAINING("Barganing", BargainingSkill.class),
//        COPTALK("Cop Talk", CopTalkSkill.class),
//        WAREZ_ANALYSIS("Warez Analysis", WarezAnalysisSkill.class),
//        DEBUG("Debug", DebugSkill.class),
//        HW_REPAIR("Hardware Repair", HardwareRepairSkill.class),
//        ICE_BREAKING("I.C.E. Breaking", IceBreakingSkill.class),
//        EVASION("Evasion", EvasionSkill.class),
//        CRYPTOLOGY("Cryptology", CryptologySkill.class),
//        JAPANESE("Japanese", JapaneseSkill.class),
//        LOGIC("Logic", LogicSkill.class),
//        PSYCHOANALYSIS("Psychoanalysis", PsychoanalysisSkill.class),
//        PHENOMENOLOGY("Phenomenology", PhenomenologySkill.class),
//        PHILOSOPHY("Philosophy", PhilosophySkill.class),
//        SOPHISTRY("Sophistry", SophistrySkill.class),
//        ZEN("Zen", ZenSkill.class),
//        MUSICIANSHIP("Muscianship", MusicianshipSkill.class);

    public final Item.Catalog catalog;
    public int level;
    public final int MAX_LEVEL;

    public Skill(Item.Catalog catalog, int level, int maxLevel) {
        this.catalog = catalog;
        this.level = level;
        this.MAX_LEVEL = maxLevel;
    }

    public abstract String getDescription();

    public abstract void use();
    
    public int upgrade() {
        LOGGER.log(Level.FINE, "{0} : Level Upgrade called.",new Object[]{ catalog.name() } );
        level++;
        if ( level > MAX_LEVEL ) {
            level = MAX_LEVEL;
            LOGGER.log(Level.FINE, "{0} : Level Upgrade is now Maximum Level of {1}",new Object[]{ catalog.name(), MAX_LEVEL } );
        }
        
        return level;
    }
    
    /**
     * Run duration in mS. Over-ride for effect-over-time.
     * 
     * @return duration in milliseconds.
     */
    public int getRunDuration() {
        return 0; // Instant
    }
    
    /**
     * Damage or other effect amount per second (every 15 ticks).
     * Over-ride for custom effect amount.
     * 
     * @param gs Game State object
     * @return value of effect per 15 ticks().
     */
    public int getEffect(GameState gs) {
        return 0;
    }

    public String getVersionedName() {
        return catalog.itemName + " " + level;
    }

    /**
     *
     * @param prefix
     * @param p
     */
    public void putProps(String prefix, Properties p) {
        p.put(prefix, catalog.name());
        p.put(prefix + ".level", String.valueOf(level));
    }

    public void pullProps(String prefix, Properties p) {
        String get = p.getProperty(prefix + ".level", "1");
        LOGGER.log(Level.INFO, () -> "Restore Skill level value = " + get);
        level = Integer.parseInt(get);
    }

    public static Skill getInstance(Catalog item, int level) {
        LOGGER.log(Level.FINER, "Get Skill instance.");
        try {
            @SuppressWarnings("unchecked")
            Constructor<?> ctor = item.clazz.getConstructor(
                    new Class[]{int.class}
            );

            Object object = ctor.newInstance(
                    new Object[]{level});
            LOGGER.log(Level.CONFIG, "Skill Object created.");
            if (object instanceof Skill sk) {
                return sk;
            } else {
                LOGGER.log(Level.WARNING, "Thing is not a Skill.");
            }
        } catch (InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException
                | NoSuchMethodException
                | SecurityException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return null;
    }
}
