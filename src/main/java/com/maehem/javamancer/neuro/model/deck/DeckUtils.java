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
package com.maehem.javamancer.neuro.model.deck;

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.neuro.model.GameState;
import com.maehem.javamancer.neuro.model.item.DeckItem;
import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.item.SoftwareItem;
import com.maehem.javamancer.neuro.model.warez.Warez;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class DeckUtils {

    public static final Logger LOGGER = Logging.LOGGER;

    public final static ArrayList<DeckItem> getDecks(GameState gs) {
        ArrayList<DeckItem> decks = new ArrayList<>();
        for (Item item : gs.inventory) {
            if (item instanceof DeckItem deck) {
                decks.add(deck);
            }
        }

        return decks;
    }

    /**
     * Compute max slots for holding software. Never goes down. Increase based
     * on maximum capacity deck in inventory. Game quirk.
     *
     * Discarding a deck does not lower the number. Existing software is
     * available based on this non-shrinking number.
     *
     * @param gs
     * @return
     */
    public final static int computeMaxSlots(GameState gs) {
        for (Item item : gs.inventory) {
            if (item instanceof DeckItem deck) {
                if (deck.nSlots > gs.deckSlots) {
                    gs.deckSlots = deck.nSlots;
                }
            }
        }

        return gs.deckSlots;
    }

    public static boolean hasSoftware(GameState gameState, SoftwareItem item) {
        for (Warez w : gameState.software) {
            if (w.item == item.item) {
                return true;
            }
        }

        return false;
    }

    /**
     * Turns a SoftwareItem into a Warez instantiation and places it into the
     * GameState software list.
     *
     * @param gs
     * @param softwareItem
     * @return
     */
    public static boolean installSoftware(GameState gs, SoftwareItem softwareItem) {
        LOGGER.log(Level.CONFIG, "Start install software item: " + softwareItem.getSimpleName());
        ArrayList<Warez> software = gs.software;

        for (Warez w : software) {
            if (w.getClass().equals(softwareItem.item.clazz) && softwareItem.version == w.version) {
                LOGGER.log(Level.CONFIG, "{0} already installed.", w.getSimpleName());
                return false;
            }
        }

        LOGGER.log(Level.FINER, "Attempt Software install...");
        try {
            @SuppressWarnings("unchecked")
            Constructor<?> ctor = softwareItem.item.clazz.getConstructor(
                    new Class[]{int.class}
            );

            Object object = ctor.newInstance(
                    new Object[]{softwareItem.version});
            LOGGER.log(Level.FINER, "Object created.");
            if (object instanceof Warez w) {
                LOGGER.log(Level.FINER, "Added to GameState software...");
                software.add(w);
                return true;
            } else {
                LOGGER.log(Level.WARNING, "Thing is not a Software.");
            }
        } catch (InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException
                | NoSuchMethodException
                | SecurityException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            ex.printStackTrace();
            LOGGER.log(Level.SEVERE, "Software could not be installed.");
            return false;
        }

        return false;
    }

    public static DeckItem getUsingDeck(GameState gs, String deckName) {
        for (Item item : gs.inventory) {
            if (item.item.name().equals(deckName)) {
                return (DeckItem) item;
            }
        }

        return null;
    }

}
