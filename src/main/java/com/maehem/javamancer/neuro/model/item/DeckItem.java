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

import com.maehem.javamancer.neuro.model.warez.CyberspaceWarez;
import com.maehem.javamancer.neuro.model.warez.Warez;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class DeckItem extends Item {

    public static final boolean NON_CYBERSPACE = false;
    public static final boolean CYBERSPACE = true;

    public final int nSlots;
    public final ArrayList<Warez> softwarez = new ArrayList<>();
    private Warez currentSoftwarez = null;
    public final boolean cyberspaceCapable;

    public enum Mode {
        NONE, LINKCODE, CYBERSPACE
    }

    private Mode mode = Mode.NONE;

    private int zone = -1;
    private int cordX = 0;
    private int cordY = 0;
    private boolean noFee = false; // Set when player connects using Cyberspace 1.0 warez.

    public DeckItem(Item.Catalog cat, int nSlots, boolean cyberspace, int startX, int startY) {
        super(cat);
        this.nSlots = nSlots;
        this.cordX = startX;
        this.cordY = startY;
        this.cyberspaceCapable = cyberspace;
    }

    public static DeckItem getInstance(Class<? extends DeckItem> d) {
        try {
            @SuppressWarnings("unchecked")
            Constructor<?> ctor = d.getConstructor();

            Object object = ctor.newInstance(new Object[]{});
            if (object instanceof DeckItem deckItem) {
                LOGGER.log(Level.SEVERE, "Instance Deck: {0} with {1} slots.",
                        new Object[]{deckItem.getName(), deckItem.nSlots});
                return deckItem;
            } else {
                LOGGER.log(Level.SEVERE, "Thing is not a Deck.");
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
        LOGGER.log(Level.SEVERE, "Could not get instance for DeckItem: " + d.getSimpleName());
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
        this.mode = mode;
    }

    public int getZone() {
        return zone;
    }

    public int getCordX() {
        return cordX;
    }

    public int getCordY() {
        return cordY;
    }

    public void setZone(int z) {
        this.zone = z;
        LOGGER.log(Level.FINER, "Deck: Zone changed: {0}", zone);
    }

    public void setCordX(int x) {
        this.cordX = x;
        LOGGER.log(Level.FINER, "Deck: X changed: {0}", cordX);
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

    public boolean addWarez(Warez warez) {
        if (softwarez.size() < nSlots) {
            return softwarez.add(warez);
        } else {
            LOGGER.log(Level.SEVERE, "Deck slots full. Erase items first.");
            return false;
        }

    }

    public boolean erase(Warez w) {
        currentSoftwarez = null;
        return softwarez.remove(w);
    }

    public boolean isNoFee() {
        return noFee;
    }
}
