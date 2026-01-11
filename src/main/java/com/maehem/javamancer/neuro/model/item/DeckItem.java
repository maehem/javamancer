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
package com.maehem.javamancer.neuro.model.item;

import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.JackZone;
import com.maehem.javamancer.neuro.model.warez.CyberspaceWarez;
import com.maehem.javamancer.neuro.model.warez.Warez;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.logging.Level;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class DeckItem extends Item {

    public static final boolean NON_CYBERSPACE = false;
    public static final boolean CYBERSPACE = true;

    public final int nSlots;

    // Software is stored in GameState
    // Slot capacity is the number of slots for the largest deck player has owned.
    private Warez currentSoftwarez = null;
    private Warez rememberedWarez = null;
    public final boolean cyberspaceCapable;

    public enum Mode {
        NONE, LINKCODE, CYBERSPACE
    }

    private Mode mode = Mode.NONE;

    private JackZone zone = null;
    private int cordX = 0;
    private int cordY = 0;
    private boolean noFee = false; // Set when player connects using Cyberspace 1.0 warez.
    public boolean needsRepair;

    public DeckItem(Item.Catalog cat, int nSlots, boolean cyberspace, int startX, int startY) {
        super(cat);
        this.nSlots = nSlots;
        this.cordX = startX;
        this.cordY = startY;
        this.cyberspaceCapable = cyberspace;
        this.needsRepair = false;
    }

    public static DeckItem getInstance(Class<? extends DeckItem> d) {
        try {
            @SuppressWarnings("unchecked")
            Constructor<?> ctor = d.getConstructor();

            Object object = ctor.newInstance(new Object[]{});
            if (object instanceof DeckItem deckItem) {
                LOGGER.log(Level.INFO, "Instance Deck: {0} with {1} slots.",
                        new Object[]{deckItem.getName(), deckItem.nSlots});
                return deckItem;
            } else {
                LOGGER.log(Level.WARNING, "Thing is not a Deck.");
            }
        } catch (InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException
                | NoSuchMethodException
                | SecurityException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            //ex.printStackTrace();
        }
        LOGGER.log(Level.SEVERE,
                "Could not get instance for DeckItem: {0}",
                d.getSimpleName());
        return null;
    }

    public Warez getCurrentWarez() {
        return currentSoftwarez;
    }

    public void setCurrentWarez(Warez w) {
        if (w instanceof CyberspaceWarez) {
            noFee = true;
        }
        currentSoftwarez = w;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        LOGGER.log(Level.CONFIG, "{0}:: set mode to {1}",
                new Object[]{getName(), mode.name()});
        this.mode = mode;
    }

    public JackZone getZone() {
        return zone;
    }

    /**
     * Store the value of the zone this deck started in. When exploring the
     * matrix, player should not be able to move outside this zone unless they
     * are using EasyRiderWarez.
     *
     * @param z
     */
    public void setZone(JackZone z) {
        if (z != null) {
            this.zone = z;
            LOGGER.log(Level.FINE, "Deck: Zone changed: {0}", zone);

            setCordX(z.x);
            // Set start point back one grid sice we animate visual into actual postion.
            setCordY(z.y - GameState.GRID_SIZE);
        } else {
            LOGGER.log(Level.FINE, "Can`t set zone. No cyberspace jack here.");
        }
    }

    public int getCordX() {
        return cordX;
    }

    public void setCordX(int x) {
        this.cordX = x;
        LOGGER.log(Level.FINER, "Deck: X changed: {0}", cordX);
    }

    public int getCordY() {
        return cordY;
    }

    public void setCordY(int y) {
        this.cordY = y;
        LOGGER.log(Level.FINER, "Deck: Y changed: {0}", cordY);
    }

    public void cleanUp() {
        mode = Mode.NONE;
        currentSoftwarez = null;
        noFee = false;
    }

    public boolean isNoFee() {
        return noFee;
    }

    @Override
    public void putProps(String prefix, Properties p) {
        super.putProps(prefix, p);
        p.put(prefix + ".needsRepair", String.valueOf(needsRepair));
    }

    public void pullProps(String prefix, Properties p) {
        String get = p.getProperty(prefix + ".needsRepair", "false");
        LOGGER.log(Level.INFO, () -> "Restore Deck needsRepair value = " + get);
        needsRepair = Boolean.parseBoolean(get);
    }
    
    /**
     * Remember the current Warez to restore later.
     */
    public void pushWarez() {
        rememberedWarez = currentSoftwarez;
    }
    
    /**
     * Restore any remembered Warez
     */
    public void popWarez() {
        currentSoftwarez = rememberedWarez;
    }
}
