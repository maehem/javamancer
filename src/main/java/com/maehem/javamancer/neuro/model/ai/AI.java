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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class AI {
    public static final Logger LOGGER = Logging.LOGGER;

    public final String name;
    public final int index; // matching the sprite face
    private int constitution = 2000;

    public AI(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public void setConstitution(int value) {
        this.constitution = value;
    }

    public int getConstitution() {
        return constitution;
    }

    public void applyDamage(int value) {
        this.constitution -= value;
        if (constitution < 0) {
            constitution = 0;
        }
    }

    /**
     *
     * @param prefix
     * @param p
     */
    public void putProps(String prefix, Properties p) {
        p.put(prefix, getClass().getSimpleName());
        p.put(prefix + ".constitution", String.valueOf(constitution));
    }

    public void pullProps(String prefix, Properties p) {
        String get = p.getProperty(prefix + ".constitution", "1");
        LOGGER.log(Level.INFO, () -> "Restore Skill level value = " + get);
        constitution = Integer.parseInt(get);
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
            ex.printStackTrace();
        }

        return null;
    }
}
