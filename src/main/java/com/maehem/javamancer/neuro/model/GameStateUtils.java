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
package com.maehem.javamancer.neuro.model;

import com.maehem.javamancer.AppProperties;
import com.maehem.javamancer.logging.Logging;
import static com.maehem.javamancer.neuro.model.GameStateDefaults.*;
import com.maehem.javamancer.neuro.model.item.CreditsItem;
import com.maehem.javamancer.neuro.model.item.DeckItem;
import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.item.Item.Catalog;
import com.maehem.javamancer.neuro.model.item.RealItem;
import com.maehem.javamancer.neuro.model.room.Room;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Load and Save model functions go here.
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class GameStateUtils {

    public static final Logger LOGGER = Logging.LOGGER;

    /**
     * Save GameState into a file.
     *
     * @param gs GameState
     * @param slot number (1-4)
     * @return true if file was saved.
     */
    public static boolean saveModel(GameState gs, int slot) {
        try {
            AppProperties appProps = AppProperties.getInstance();
            String saveFileName = "game" + slot + ".properties";

            gatherProperties(gs).store(
                    new FileWriter(new File(appProps.getSaveFolder(), saveFileName)),
                    "Javamancer Game Save File.  Slot: " + slot
            );
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean loadModel(GameState gs, int slot) {
        AppProperties appProps = AppProperties.getInstance();
        String loadFileName = "game" + slot + ".properties";

        File file = new File(appProps.getSaveFolder(), loadFileName);
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                Properties loadedProperties = new Properties();
                loadedProperties.load(fileReader);
                restoreFromProperties(gs, loadedProperties);
                return true;
            } catch (FileNotFoundException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                ex.printStackTrace();
                return false;
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
                ex.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    private static Properties gatherProperties(GameState gs) {
        Properties props = new Properties();
        // Player
        props.put(NAME.key, gs.name);
        props.put(DAMAGE.key, String.valueOf(gs.damage));

        putInventory(gs, props);

        // Time/Date
        props.put(TIME_MINUTE.key, String.valueOf(gs.timeMinute));
        props.put(TIME_HOUR.key, String.valueOf(gs.timeHour));
        props.put(DATE_MONTH.key, String.valueOf(gs.dateMonth));
        props.put(DATE_DAY.key, String.valueOf(gs.dateDay));
        props.put(DATE_YEAR.key, String.valueOf(gs.dateYear));

        // Money
        props.put(CHIP_BALANCE.key, String.valueOf(gs.chipBalance));
        props.put(BANK_BALANCE.key, String.valueOf(gs.bankBalance));
        props.put(BANK_ZURICH_BALANCE.key, String.valueOf(gs.bankZurichBalance));
        props.put(BANK_GEMEIN_BALANCE.key, String.valueOf(gs.bankGemeinBalance));
        if (gs.bankZurichCreated != null) {
            props.put(BANK_ZURICH_CREATED.key, gs.bankZurichCreated);
        }

        props.put(DECK_SLOTS.key, String.valueOf(gs.deckSlots));

        props.put(ROOM_POS_X.key, String.valueOf(gs.roomPosX));
        props.put(ROOM_POS_Y.key, String.valueOf(gs.roomPosY));

        props.put(ROOM.key, gs.room.name());

        props.put(DIXIE_INSTALLED.key, String.valueOf(gs.dixieInstalled));

        return props;
    }

    private static void restoreFromProperties(GameState gs, Properties p) {
        // Player
        gs.name = getStr(NAME, p);
        gs.damage = getInt(DAMAGE, p);

        // Inventory
        restoreInventory(gs, p);

        // Time/Date
        gs.timeMinute = getInt(TIME_MINUTE, p);
        gs.timeHour = getInt(TIME_HOUR, p);
        gs.dateMonth = getInt(DATE_MONTH, p);
        gs.dateDay = getInt(DATE_DAY, p);
        gs.dateYear = getInt(DATE_YEAR, p);

        // Money
        gs.chipBalance = getInt(CHIP_BALANCE, p);
        gs.bankBalance = getInt(BANK_BALANCE, p);
        gs.bankZurichBalance = getInt(BANK_ZURICH_BALANCE, p);
        gs.bankGemeinBalance = getInt(BANK_GEMEIN_BALANCE, p);
        gs.bankZurichCreated = getStr(BANK_ZURICH_CREATED, p);

        // Deck
        gs.deckSlots = getInt(DECK_SLOTS, p);
        //gs.database = gs.dbList.lookup(getStr(DATABASE, p));

        // Need to configure deck as if entering cyberspace
        // need to set deck location?
        //gs.usingDeck =
        gs.roomPosX = getInt(ROOM_POS_X, p);
        gs.roomPosY = getInt(ROOM_POS_Y, p);

        // Need to configure room ???
        gs.room = Room.lookup(getStr(ROOM, p));
        gs.dixieInstalled = getBool(DIXIE_INSTALLED, p);
    }

    private static String getStr(GameStateDefaults gsd, Properties p) {
        return p.getProperty(gsd.key, gsd.value);
    }

    private static int getInt(GameStateDefaults gsd, Properties p) {
        return Integer.parseInt(getStr(gsd, p));
    }

    private static boolean getBool(GameStateDefaults gsd, Properties p) {
        return Boolean.getBoolean(getStr(gsd, p));
    }

    private static void putInventory(GameState gs, Properties p) {
        int i = 0;
        for (Item item : gs.inventory) {
            LOGGER.log(Level.SEVERE, "Put inventory item: " + item.item.name());
            item.putProps("inventory." + i, p);
            //p.put("inventory." + i, item.item.name());
            i++;
        }
    }

    @SuppressWarnings("unchecked")
    private static void restoreInventory(GameState gs, Properties p) {
        int i = 0;
        String val;
        while ((val = p.getProperty("inventory." + i)) != null) {
            LOGGER.log(Level.SEVERE, "Restore inventory item: " + val);
            Catalog lookup = Item.lookup(val);
            if (RealItem.class.isAssignableFrom(lookup.clazz)) {
                LOGGER.log(Level.SEVERE, "Found RealItem");
                RealItem ri = new RealItem(lookup, 0);
                gs.inventory.add(ri);
            } else if (CreditsItem.class.isAssignableFrom(lookup.clazz)) {
                LOGGER.log(Level.SEVERE, "Create Credits Item");
                CreditsItem ci = new CreditsItem();
                gs.inventory.add(ci);
            } else if (DeckItem.class.isAssignableFrom(lookup.clazz)) {
                LOGGER.log(Level.SEVERE, "Create Deck Item");
                DeckItem deck = DeckItem.getInstance(lookup.clazz);
                gs.inventory.add(deck);
                deck.pullProps("inventory." + i, p);

            }

            i++;
        }
    }

}
