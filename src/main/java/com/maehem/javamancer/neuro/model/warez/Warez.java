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
package com.maehem.javamancer.neuro.model.warez;

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.item.Item.Catalog;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * TODO: 
 * There is a (10% guessing) chance that a random software program of will 
 * get buggy and can no longer be used reliably, if at all.
 * 
 * The game randomly chooses which program gets damaged. 
 * 
 * To repair your software, you need to use  the Debug skill, which you can only use outside of cyberspace or with the
 * assistance of a ROM construct. 
 * 
 * Debug level 4 has a 100% success rate at debugging software,
 * Debug level 3 has a 75% success rate at debugging software,
 * Debug level 2 has a 50% success rate at debugging software,
 * Debug level 1 has a 25% success rate at debugging software,
 * 
 * ROMs have a 75% chance of debugging software (Debug 3). 
 * 
 * If you attempt to debug a program and it fails, subsequent
 * attempts will always fail to debug that same piece of software except Debug 4, 
 * 
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class Warez {

    public static final Logger LOGGER = Logging.LOGGER;

    public static final String USE_OK = "OK";
    public static final String REQUIRES_ICE = "Requires ICE.";

    public final Item.Catalog item;
    public int version;
    private boolean running;
    private boolean damaged;
    private EventHandler<ActionEvent> finishedHandler;

    public Warez(Item.Catalog catItem, int version) {
        this.item = catItem;
        this.version = version;
    }

    public String use(GameState gs) {
        LOGGER.log(Level.FINE, "Warez: Use(): " + getClass().getSimpleName() + " USE_OK");
        return USE_OK;
    }

    public void setRunning(boolean running) {
        if (this.running && !running && finishedHandler != null) {
            finishedHandler.handle(new ActionEvent());
        }
        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean value) {
        this.damaged = value;
    }

    /**
     * How long the software runs for before applying the effect.
     *
     * @return
     */
    public abstract int getRunDuration();

    /**
     * Effect amount to apply to target (usually ICE's constitution).
     *
     * @return
     */
    public abstract int getEffect(GameState gameState);

    public void setOnFinished(EventHandler<ActionEvent> handler) {
        this.finishedHandler = handler;
    }

    public String getMenuString() {
        return String.format("%-12s", item.itemName)
                + String.format("%2s", String.valueOf(version))
                + ".0";
    }

    public String getSimpleName() {
        return item.itemName + " " + version + ".0";
    }

    /**
     *
     * @param prefix
     * @param p
     */
    public void putProps(String prefix, Properties p) {
        p.put(prefix, item.name());
        p.put(prefix + ".version", String.valueOf(version));
    }

    public void pullProps(String prefix, Properties p) {
        String get = p.getProperty(prefix + ".version", "1");
        LOGGER.log(Level.FINE, "Restore Warez version value = " + get);
        version = Integer.parseInt(get);
    }

    public static Warez getInstance(Catalog item, int version) {
        LOGGER.log(Level.FINER, "Get Warez instance.");
        try {
            @SuppressWarnings("unchecked")
            Constructor<?> ctor = item.clazz.getConstructor(
                    new Class[]{int.class}
            );

            Object object = ctor.newInstance(
                    new Object[]{version});
            LOGGER.log(Level.CONFIG, "Warez Object created.");
            if (object instanceof Warez w) {
                return w;
            } else {
                LOGGER.log(Level.WARNING, "Thing is not a Warez.");
            }
        } catch (InstantiationException
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
