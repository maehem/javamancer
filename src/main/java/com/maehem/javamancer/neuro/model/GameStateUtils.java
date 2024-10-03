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
import com.maehem.javamancer.neuro.model.ai.AI;
import com.maehem.javamancer.neuro.model.item.CreditsItem;
import com.maehem.javamancer.neuro.model.item.DeckItem;
import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.item.Item.Catalog;
import com.maehem.javamancer.neuro.model.item.RealItem;
import com.maehem.javamancer.neuro.model.room.Room;
import com.maehem.javamancer.neuro.model.skill.Skill;
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

        putSkills(gs, props);
        putSoldBodyParts(gs, props);

//        putSoftware();
//        putSeaWanted();
//        putChibaWanted();
//        putHosakaEmployees();
//        putRoomVisited();
//        putDialogAllowed();
        putAIList(gs, props);
//        putMessageSent();

        //props.put(DECK_SLOTS.key, String.valueOf(gs.deckSlots));
        pPut(props, DECK_SLOTS, gs.deckSlots);

        if (gs.usingDeck != null) {
            pPut(props, USING_DECK, gs.usingDeck.item.name());
        } else {
            pPut(props, USING_DECK, "null");
        }

        pPut(props, MATRIX_POS_X, gs.matrixPosX);
        pPut(props, MATRIX_POS_Y, gs.matrixPosY);

        pPut(props, DIXIE_INSTALLED, gs.dixieInstalled);

        pPut(props, ROOM_POS_X, gs.roomPosX);
        pPut(props, ROOM_POS_Y, gs.roomPosY);

        props.put(ROOM.key, gs.room.name());

        return props;
    }

    private static void pPut(Properties p, GameStateDefaults k, String value) {
        p.put(k.key, value);
    }

    private static void pPut(Properties p, GameStateDefaults k, int value) {
        p.put(k.key, String.valueOf(value));
    }

    private static void pPut(Properties p, GameStateDefaults k, boolean value) {
        p.put(k.key, String.valueOf(value));
    }

    /**
     * Restore GameState settings from Properties. The elements here should
     * match those in gatherProperties().
     *
     * @param gs
     * @param p
     */
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

        restoreSkills(gs, p);
        restoreSoldBodyParts(gs, p);
        restoreAIList(gs, p);

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

    private static void putSkills(GameState gs, Properties p) {
        int i = 0;
        for (Skill skill : gs.skills) {
            LOGGER.log(Level.SEVERE, "Put skill: " + skill.catalog.name());
            skill.putProps("skills." + i, p);
            i++;
        }
    }

    private static void restoreSkills(GameState gs, Properties p) {
        int i = 0;
        String val;
        while ((val = p.getProperty("skills." + i)) != null) {
            LOGGER.log(Level.SEVERE, "Restore skill: " + val);
            Catalog lookup = Item.lookup(val);

            LOGGER.log(Level.SEVERE, "Create Skill Item");
            Skill skill = Skill.getInstance(lookup, 1);
            skill.pullProps("skills." + i, p);

            gs.skills.add(skill);

            i++;
        }
    }

    private static void putSoldBodyParts(GameState gs, Properties p) {
        int i = 0;
        for (BodyPart part : gs.soldBodyParts) {
            LOGGER.log(Level.SEVERE, "Put body part: " + part.name());
            p.put("bodyPart." + i, part.name());
            i++;
        }
    }

    private static void restoreSoldBodyParts(GameState gs, Properties p) {
        int i = 0;
        String val;
        while ((val = p.getProperty("bodyPart." + i)) != null) {
            LOGGER.log(Level.SEVERE, "Restore sold body part: " + val);
            BodyPart lookup = BodyPart.lookup(val);

            gs.soldBodyParts.add(lookup);

            i++;
        }
    }

    private static void putAIList(GameState gs, Properties p) {
        int i = 0;
        for (AI ai : gs.aiList) {
            LOGGER.log(Level.SEVERE, "Put AI: " + ai.getClass().getSimpleName());
            ai.putProps("ai." + i, p);
            i++;
        }
    }

    private static void restoreAIList(GameState gs, Properties p) {
        int i = 0;
        String val;
        while ((val = p.getProperty("ai." + i)) != null) {
            LOGGER.log(Level.SEVERE, "Restore AI: " + val);
            AI lookup = AI.lookup(val);

//            LOGGER.log(Level.SEVERE, "Create AI");
//            Skill skill = Skill.getInstance(lookup, 1);
            lookup.pullProps("ai." + i, p);

            gs.aiList.add(lookup);

            i++;
        }
    }

}
