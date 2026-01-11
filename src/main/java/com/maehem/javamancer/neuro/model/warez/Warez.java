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
import static java.util.logging.Level.FINE;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * <pre>
 * TODO: 10% (guessing) chance that a random software program of will
 * get buggy and can no longer be used reliably, if at all.
 *
 * The game randomly chooses which program gets damaged.
 *
 * To repair software, player must use the Debug skill, which only can be
 * used outside of cyberspace or with the assistance of a ROM construct.
 *
 * For more see @DebugSkill source code.
 *
 * </pre>
 * @see @IceBreakerWarez source for more notes on that type of warez.
 * 
 * 
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class Warez {

    public static final Logger LOGGER = Logging.LOGGER;

    public static final String USE_OK = "OK";
    public static final String REQUIRES_ICE = "Requires ICE.";
    // Run forever or until finished/aborted.
    public static final int RUN_FOREVER = Integer.MAX_VALUE;

    public final Item.Catalog item;
    public int version;
    //private boolean running;
    private int runRemaining = 0;
    private boolean damaged = false;
    private EventHandler<ActionEvent> finishedHandler;

    public Warez(Item.Catalog catItem, int version) {
        this.item = catItem;
        this.version = version;
    }

    // TODO: Change to canUse()
    public String use(GameState gs) {
        LOGGER.log(Level.FINE, "Warez: Use(): {0} USE_OK", item.name());
        return USE_OK;
    }

    /**
     * Activate the Warez. Runs immediately if runDuration is 0 or less.
     *
     * @param running activate warez
     */
    public void start() {
        runRemaining = getRunDuration();
    }

    /**
     * Finish run of Warez and call finish handler (if one exists).
     */
    public void finish(GameState gs) {
        abort(gs);
        if (finishedHandler != null) {
            finishedHandler.handle(new ActionEvent());
        }
    }
    
    /**
     * Stop Warez running. Do not run finish handler.
     * 
     * Overridden by @VirusWarez and @CorruptorWarez to self delete.
     * 
     */
    public void abort(GameState gs) {
        runRemaining = 0;
    }

    public void tick(GameState gs) {
        LOGGER.log(FINE, "{0} Warez.tick()", this.item.name());
        if (runRemaining != RUN_FOREVER) {
            runRemaining -= 15;
            if (runRemaining <= 0) { // run finish handler now.
                finish(gs);
            }
        }
    }

    public boolean isRunning() {
        return runRemaining > 0;
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
     * @return duration in milliseconds. Use Integer.MAX_VALUE for forever run.
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
