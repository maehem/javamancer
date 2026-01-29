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
import com.maehem.javamancer.neuro.model.database.Database;
import com.maehem.javamancer.neuro.model.deck.DeckUtils;
import com.maehem.javamancer.neuro.model.item.CreditsItem;
import com.maehem.javamancer.neuro.model.item.DeckItem;
import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.item.Item.Catalog;
import com.maehem.javamancer.neuro.model.item.RealItem;
import com.maehem.javamancer.neuro.model.item.SkillItem;
import com.maehem.javamancer.neuro.model.room.Room;
import com.maehem.javamancer.neuro.model.room.RoomBounds;
import com.maehem.javamancer.neuro.model.skill.Skill;
import com.maehem.javamancer.neuro.model.warez.Warez;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
        props.put(DATE_MINUTE.key, String.valueOf(gs.dateMinute));
        props.put(DATE_HOUR.key, String.valueOf(gs.dateHour));
        props.put(DATE_MONTH.key, String.valueOf(gs.dateMonth));
        props.put(DATE_DAY.key, String.valueOf(gs.dateDay));
        props.put(DATE_YEAR.key, String.valueOf(gs.dateYear));

        // Money
        props.put(CHIP_BALANCE.key, String.valueOf(gs.moneyChipBalance));
        props.put(BANK_BALANCE.key, String.valueOf(gs.bankBalance));
        putBankTransactions(gs.bankTransactionRecord, props);

        props.put(BANK_ZURICH_BALANCE.key, String.valueOf(gs.bankZurichBalance));
        props.put(BANK_GEMEIN_BALANCE.key, String.valueOf(gs.bankGemeinBalance));
        if (gs.bankZurichCreated != null) {
            props.put(BANK_ZURICH_CREATED.key, gs.bankZurichCreated);
        }

        putSkills(gs, props);
        putSoldBodyParts(gs, props);

        putWarez(gs, props);
        putPersonList(gs.seaWantedList, SEA_WANTED.key, props);
        putPersonList(gs.chibaWantedList, CHIBA_POLICE.key, props);
        putPersonList(gs.hosakaEmployeeList, HOSAKA_EMPLOYEE.key, props);

        putVisitedRooms(gs, props);
        putDialogRooms(gs, props);
        putLockedRooms(gs, props);

        putAIList(gs, props);
        putSentMessageList(gs.messageSent, props);

        pPut(props, DECK_SLOTS, gs.deckSlots);
        pPut(props, AI_FIGHT_SKILL, gs.aiFightSkill);

        if (gs.usingDeck != null) {
            pPut(props, USING_DECK, gs.usingDeck.item.name());
        } else {
            pPut(props, USING_DECK, "null");
        }

        pPut(props, MATRIX_POS_X, gs.matrixPosX);
        pPut(props, MATRIX_POS_Y, gs.matrixPosY);

        pPut(props, ROM_INSTALLED, gs.romInstalled);

        pPut(props, ROOM_POS_X, gs.roomPosX);
        pPut(props, ROOM_POS_Y, gs.roomPosY);

        pPut(props, ROOM, gs.room.name());

        // Hotel
        pPut(props, HOTEL_CHARGES, gs.hotelCharges);
        pPut(props, HOTEL_ON_ACCOUNT, gs.hotelOnAccount);
        pPut(props, HOTEL_CAVIAR, gs.hotelCaviar);
        pPut(props, HOTEL_SAKE, gs.hotelSake);
        pPut(props, HOTEL_DELIVER_CAVIAR, gs.hotelDeliverCaviar);
        pPut(props, HOTEL_DELIVER_SAKE, gs.hotelDeliverSake);

        // Massage Parlor
        pPut(props, MASSAGE_INFO_1, gs.massageInfo1);
        pPut(props, MASSAGE_INFO_2, gs.massageInfo2);
        pPut(props, MASSAGE_INFO_3, gs.massageInfo3);
        pPut(props, MASSAGE_INFO_4, gs.massageInfo4);
        pPut(props, MASSAGE_INFO_5, gs.massageInfo5);

        // Misc. Flags
        pPut(props, MSG_TO_ARMITAGE_SENT, gs.bbsMsgToArmitageSent);
        pPut(props, BBS_MSG_FROM_ARMITAGE_READ, gs.bbsMsgFromArmitageRead);
        pPut(props, RATZ_PAID, gs.ratzPaid);
        pPut(props, SHIVA_CHIP_MENTIONED, gs.shivaChipMentioned);
        pPut(props, SHIVA_CHIP_GIVEN, gs.shivaGaveChip);
        pPut(props, JOYSTICK_GIVEN, gs.joystickGiven);
        pPut(props, GAS_MASK_ON, gs.gasMaskIsOn);

        pPut(props, BODY_PART_DISCOUNT, gs.bodyPartDiscount);
        pPut(props, ACTIVE_SKILL, gs.activeSkill != null ? gs.activeSkill.catalog.name() : "null");
        pPut(props, BANK_ZURICH_ROBBED, gs.bankZurichRobbed);
        pPut(props, COMLINK2_RECIEVED, gs.comlink2recieved);
        pPut(props, COMLINK6_UPLOADED, gs.comlink6uploaded);
        pPut(props, ASANO_DISCOUNT, gs.asanoDiscount);
        pPut(props, SECURITY_PASS_GIVEN, gs.securityPassGiven);
        pPut(props, LARRY_MOE_WANTED, gs.larryMoeWanted);
        pPut(props, PSYCHO_PROBE_COUNT, gs.psychoProbeCount);

        pPut(props, HITACHI_VOLUNTEER, gs.hitachiVolunteer);
        pPut(props, HOSAKA_DAYS_SINCE_PAID, gs.hosakaDaysSincePaid);

        return props;
    }

    private static void pPut(Properties p, GameStateDefaults k, String value) {
        // Only put the prop if it is not a default value.
        if (k.value == null || !k.value.equals(value)) {
            p.put(k.key, value);
        }
    }

    private static void pPut(Properties p, GameStateDefaults k, int value) {
        // Only put the prop if it is not a default value.
        String strValue = String.valueOf(value);
        if (!k.value.equals(strValue)) {
            p.put(k.key, strValue);
        }
    }

    private static void pPut(Properties p, GameStateDefaults k, boolean value) {
        // Only put the prop if it is not a default value.
        String strValue = String.valueOf(value);
        if (!k.value.equals(strValue)) {
            p.put(k.key, strValue);
        }
    }

    /**
     * Restore GameState settings from Properties. The elements here should
     * match those in gatherProperties().
     *
     * @param gs
     * @param p
     */
    private static void restoreFromProperties(GameState gs, Properties p) {
        LOGGER.log(Level.CONFIG, "Restore Game Save Properties...");
        // Player
        gs.name = getStr(NAME, p);
        gs.damage = getInt(DAMAGE, p);

        // Set News to "New Game" state.
        gs.resourceManager.initNewsArticles(
                gs.news,
                gs.name,
                gs.getDateString()
        );

        // Set PAX BBS messages to "New Game" state.
        gs.resourceManager.initPaxBbsMessages(
                gs.bbs,
                gs.name
        );

        // Inventory
        restoreInventory(gs, p);

        // Time/Date
        gs.dateMinute = getInt(DATE_MINUTE, p);
        gs.dateHour = getInt(DATE_HOUR, p);
        gs.dateMonth = getInt(DATE_MONTH, p);
        gs.dateDay = getInt(DATE_DAY, p);
        gs.dateYear = getInt(DATE_YEAR, p);

        // Money
        gs.moneyChipBalance = getInt(CHIP_BALANCE, p);
        gs.bankBalance = getInt(BANK_BALANCE, p);
        restoreBankTransactions(gs.bankTransactionRecord, p);
        gs.bankZurichBalance = getInt(BANK_ZURICH_BALANCE, p);
        gs.bankGemeinBalance = getInt(BANK_GEMEIN_BALANCE, p);
        gs.bankZurichCreated = getStr(BANK_ZURICH_CREATED, p);

        restoreSkills(gs, p);
        restoreSoldBodyParts(gs, p);
        restoreAIList(gs, p);
        restoreWarez(gs, p);
        restorePersonList(gs.seaWantedList, SEA_WANTED.key, p);
        restorePersonList(gs.chibaWantedList, CHIBA_POLICE.key, p);
        restorePersonList(gs.hosakaEmployeeList, HOSAKA_EMPLOYEE.key, p);

        restoreVisitedRooms(gs, p);
        restoreDialogRooms(gs, p);
        restoreLockedRooms(gs, p);

        restoreSentMessageList(gs, p);

        // Deck
        gs.deckSlots = getInt(DECK_SLOTS, p);
        gs.aiFightSkill = getInt(AI_FIGHT_SKILL, p);

        // Matrix Stuff
        gs.usingDeck = DeckUtils.getUsingDeck(gs, getStr(USING_DECK, p));
        gs.matrixPosX = getInt(MATRIX_POS_X, p);
        gs.matrixPosY = getInt(MATRIX_POS_Y, p);
        gs.romInstalled = getInt(ROM_INSTALLED, p);

        // Room Stuff
        gs.roomPosX = getInt(ROOM_POS_X, p);
        gs.roomPosY = getInt(ROOM_POS_Y, p);
        gs.room = Room.lookup(getStr(ROOM, p));

        // Cheap Hotel
        gs.hotelCharges = getInt(HOTEL_CHARGES, p);
        gs.hotelOnAccount = getInt(HOTEL_ON_ACCOUNT, p);
        gs.hotelCaviar = getInt(HOTEL_CAVIAR, p);
        gs.hotelSake = getInt(HOTEL_SAKE, p);
        gs.hotelDeliverCaviar = getInt(HOTEL_DELIVER_CAVIAR, p);
        gs.hotelDeliverSake = getInt(HOTEL_DELIVER_SAKE, p);

        // Massage Parlor
        gs.massageInfo1 = getBool(MASSAGE_INFO_1, p);
        gs.massageInfo2 = getBool(MASSAGE_INFO_2, p);
        gs.massageInfo3 = getBool(MASSAGE_INFO_3, p);
        gs.massageInfo4 = getBool(MASSAGE_INFO_4, p);
        gs.massageInfo5 = getBool(MASSAGE_INFO_5, p);

        gs.bbsMsgToArmitageSent = getBool(MSG_TO_ARMITAGE_SENT, p);
        gs.bbsMsgFromArmitageRead = getBool(BBS_MSG_FROM_ARMITAGE_READ, p);
        gs.ratzPaid = getBool(RATZ_PAID, p);
        gs.shivaChipMentioned = getBool(SHIVA_CHIP_MENTIONED, p);
        gs.shivaGaveChip = getBool(SHIVA_CHIP_GIVEN, p);
        gs.joystickGiven = getBool(JOYSTICK_GIVEN, p);
        gs.gasMaskIsOn = getBool(GAS_MASK_ON, p);

        gs.bodyPartDiscount = getBool(BODY_PART_DISCOUNT, p);
        gs.activeSkill = skillLookup(gs, getStr(ACTIVE_SKILL, p));
        gs.bankZurichRobbed = getBool(BANK_ZURICH_ROBBED, p);
        gs.comlink2recieved = getBool(COMLINK2_RECIEVED, p);
        gs.comlink6uploaded = getBool(COMLINK6_UPLOADED, p);
        gs.asanoDiscount = getBool(ASANO_DISCOUNT, p);
        gs.securityPassGiven = getBool(SECURITY_PASS_GIVEN, p);
        gs.larryMoeWanted = getBool(LARRY_MOE_WANTED, p);
        gs.psychoProbeCount = getInt(PSYCHO_PROBE_COUNT, p);

        gs.hitachiVolunteer = getBool(HITACHI_VOLUNTEER, p);
        gs.hosakaDaysSincePaid = getInt(HOSAKA_DAYS_SINCE_PAID, p);

        LOGGER.log(Level.CONFIG, "*** Restore Game Save Properties finsished.");
    }

    private static String getStr(GameStateDefaults gsd, Properties p) {
        return p.getProperty(gsd.key, gsd.value);
    }

    private static int getInt(GameStateDefaults gsd, Properties p) {
        return Integer.parseInt(getStr(gsd, p));
    }

    private static boolean getBool(GameStateDefaults gsd, Properties p) {
        return Boolean.parseBoolean(getStr(gsd, p));
    }

    private static void putInventory(GameState gs, Properties p) {
        LOGGER.log(Level.FINE, "Put Player Inventory...");
        String key = INVENTORY.key;
        int i = 0;
        for (Item item : gs.inventory) {
            LOGGER.log(Level.FINER, "    Put {0} item: {1}", new Object[]{key, item.item.name()});
            item.putProps(key + "." + i, p);
            i++;
        }
    }

    @SuppressWarnings("unchecked")
    private static void restoreInventory(GameState gs, Properties p) {
        LOGGER.log(Level.FINE, "Restore Player Inventory...");
        String key = INVENTORY.key;
        int i = 0;
        String val;
        while ((val = p.getProperty(key + "." + i)) != null) {
            LOGGER.log(Level.FINER, "    Restore {0} item: {1}", new Object[]{key, val});
            Catalog lookup = Item.lookup(val);
            if (RealItem.class.isAssignableFrom(lookup.clazz)) {
                LOGGER.log(Level.FINEST, "        Create RealItem");
                RealItem ri = new RealItem(lookup, 0);
                gs.inventory.add(ri);
            } else if (CreditsItem.class.isAssignableFrom(lookup.clazz)) {
                LOGGER.log(Level.FINEST, "        Create Credits Item");
                CreditsItem ci = new CreditsItem();
                gs.inventory.add(ci);
            } else if (DeckItem.class.isAssignableFrom(lookup.clazz)) {
                LOGGER.log(Level.FINEST, "        Create Deck Item");
                DeckItem deck = DeckItem.getInstance(lookup.clazz);
                gs.inventory.add(deck);
                deck.pullProps(key + "." + i, p);
            } else if (SkillItem.class.isAssignableFrom(lookup.clazz)) {
                LOGGER.log(Level.FINEST, "        Create SkillItem");
                SkillItem ri = new SkillItem(lookup, 1); // These are always level 1 before installed.
                gs.inventory.add(ri);
            } else {
                LOGGER.log(Level.SEVERE, "    Item {0} could not be restored.", lookup.name());
            }

            i++;
        }
    }

    private static void putSkills(GameState gs, Properties p) {
        LOGGER.log(Level.FINE, "Put Skills...");
        String key = SKILLS.key;
        int i = 0;
        for (Skill skill : gs.skills) {
            LOGGER.log(Level.FINER, "Put {0}: {1}", new Object[]{key, skill.catalog.name()});
            skill.putProps(key + "." + i, p);
            i++;
        }
    }

    private static void restoreSkills(GameState gs, Properties p) {
        LOGGER.log(Level.FINE, "Restore Skills...");
        int i = 0;
        String val;
        String key = SKILLS.key;
        while ((val = p.getProperty(key + "." + i)) != null) {
            LOGGER.log(Level.FINER, "Restore {0}: {1}", new Object[]{key, val});
            Catalog lookup = Item.lookup(val);

            LOGGER.log(Level.FINEST, "Create {0} Item", key);
            Skill skill = Skill.getInstance(lookup, 1);
            skill.pullProps(key + "." + i, p);

            gs.skills.add(skill);

            i++;
        }
    }

    private static Skill skillLookup(GameState gs, String str) {
        for (Skill skill : gs.skills) {
            if (skill.catalog.name().equals(str)) {
                return skill;
            }
        }

        return null;
    }

    private static void putSoldBodyParts(GameState gs, Properties p) {
        LOGGER.log(Level.FINE, "Put Sold Body Parts...");
        String key = BODY_PART.key;
        int i = 0;
        for (BodyPart part : gs.soldBodyParts) {
            String val = part.name();
            LOGGER.log(Level.FINER, "Put {0}: {1}", new Object[]{key, val});
            p.put(key + "." + i, val);
            i++;
        }
    }

    private static void restoreSoldBodyParts(GameState gs, Properties p) {
        LOGGER.log(Level.FINE, "Restore Sold Body Parts...");
        String key = BODY_PART.key;
        int i = 0;
        String val;
        while ((val = p.getProperty(key + "." + i)) != null) {
            LOGGER.log(Level.FINER, "Restore {0}: {1}", new Object[]{key, val});
            BodyPart lookup = BodyPart.lookup(val);

            gs.soldBodyParts.add(lookup);

            i++;
        }
    }

    // TODO: Change to list like RoomVisited
    private static void putAIList(GameState gs, Properties p) {
        LOGGER.log(Level.FINE, "Put AI List...");
        int i = 0;
        for (AI ai : gs.defeatedAiList) {
            LOGGER.log(Level.FINER, "Put Defeated AI: {0}", ai.getClass().getSimpleName());
            ai.putProps(AI_DEFEATED + "." + i, p);
            i++;
        }
    }

    private static void restoreAIList(GameState gs, Properties p) {
        LOGGER.log(Level.FINE, "Restore AI List...");
        int i = 0;
        String val;
        String key = AI_DEFEATED + ".";
        while ((val = p.getProperty(key + i)) != null) {
            LOGGER.log(Level.FINER, "Restore Defeated AI: {0}", val);
            AI lookup = AI.lookup(val);

            lookup.pullProps(key + i, p);

            gs.defeatedAiList.add(lookup);

            i++;
        }
    }

    private static void putWarez(GameState gs, Properties p) {
        LOGGER.log(Level.FINE, "Put Warez...");
        String key = DECK_WAREZ.key;
        int i = 0;
        for (Warez w : gs.software) {
            LOGGER.log(Level.INFO, "Put " + key + ": {0}", w.item.name());
            w.putProps(key + "." + i, p);
            i++;
        }
    }

    private static void restoreWarez(GameState gs, Properties p) {
        LOGGER.log(Level.FINE, "Restore Warez...");
        int i = 0;
        String val;
        String key = DECK_WAREZ.key;
        while ((val = p.getProperty(key + "." + i)) != null) {
            LOGGER.log(Level.FINER, "Restore {0}: {1}", new Object[]{key, val});
            Catalog lookup = Item.lookup(val);

            LOGGER.log(Level.FINEST, "Create {0} Item", val);
            Warez w = Warez.getInstance(lookup, 1);
            w.pullProps(key + "." + i, p);

            gs.software.add(w);

            i++;
        }
    }

    private static void putPersonList(ArrayList<Person> list, String prefix, Properties p) {
        LOGGER.log(Level.FINE, "Put Person List {0}...", prefix);
        int i = 0;
        for (Person person : list) {
            LOGGER.log(Level.FINER, "Put {0} person: {1}", new Object[]{prefix, person.getName()});
            person.putProps(prefix + "." + i, p);
            i++;
        }
    }

    private static void restorePersonList(ArrayList<Person> list, String prefix, Properties p) {
        LOGGER.log(Level.FINE, "Restore Person List {0}...", prefix);
        int i = 0;

        while (p.getProperty(prefix + "." + i + ".name") != null) {
            String itemPrefix = prefix + "." + i;

            LOGGER.log(Level.FINER, "    Restore {0}: {1}.{2}", new Object[]{prefix, prefix, i});
            Person person = Person.pullPerson(itemPrefix, p);
            list.add(person);

            i++;
        }
    }

    private static void putVisitedRooms(GameState gs, Properties p) {
        LOGGER.log(Level.FINE, "Put Visited Rooms...");
        String key = ROOMS_VISITED.key;
        StringBuilder sb = new StringBuilder();
        for (Room r : gs.getVisitedRooms()) {
            LOGGER.log(Level.FINER, "    Put room: {0}", r.name());
            if (!sb.isEmpty()) {
                sb.append(",");
            }

            sb.append(r.name());
        }
        p.put(key, sb.toString());
    }

    private static void restoreVisitedRooms(GameState gs, Properties p) {
        LOGGER.log(Level.FINE, "Restore Visited Rooms...");
        String key = ROOMS_VISITED.key;
        String visited[] = ((String) (p.get(key))).split(",");
        for (String rStr : visited) {
            if (rStr.isBlank()) {
                continue;
            }
            LOGGER.log(Level.FINER, "    Restore: {0}", rStr);
            gs.setVisited(Room.lookup(rStr));
        }
    }

    private static void putDialogRooms(GameState gs, Properties p) {
        LOGGER.log(Level.FINE, "Put Dialog Rooms...");
        String key = ROOM_DIALOG_ALLOWED.key;
        StringBuilder sb = new StringBuilder();
        for (Room r : gs.dialogAllowed) {
            LOGGER.log(Level.FINER, "    Put {0}: {1}", new Object[]{key, r.name()});
            if (!sb.isEmpty()) {
                sb.append(",");
            }

            sb.append(r.name());
        }
        p.put(key, sb.toString());
    }

    private static void restoreDialogRooms(GameState gs, Properties p) {
        LOGGER.log(Level.FINE, "Restore Dialog Rooms...");
        String key = ROOM_DIALOG_ALLOWED.key;
        String visited[] = ((String) (p.get(key))).split(",");
        for (String rStr : visited) {
            LOGGER.log(Level.FINER, "    Restore {0}: {1}", new Object[]{key, rStr});
            gs.dialogAllowed.add(Room.lookup(rStr));
        }
    }

    private static void putSentMessageList(ArrayList<BbsMessage> list, Properties p) {
        LOGGER.log(Level.FINE, "Put Sent BBS Messages...");
        String key = BBS_SENT_MESSAGE.key;
        int i = 0;
        for (BbsMessage msg : list) {
            LOGGER.log(Level.FINER, "    Put {0}: {1} ==> to: {2}", new Object[]{key, i, msg.to});
            msg.putProps(key + "." + i, p);
            i++;
        }
    }

    private static void restoreSentMessageList(GameState gs, Properties p) {
        LOGGER.log(Level.FINE, "Restore Sent BBS Messages...");
        String key = BBS_SENT_MESSAGE.key;
        int i = 0;

        while (p.getProperty(key + "." + i + ".to") != null) {
            String itemPrefix = key + "." + i;

            LOGGER.log(Level.FINER, "    Restore: {1}", new Object[]{itemPrefix});
            BbsMessage msg = BbsMessage.pullMessage(itemPrefix, p);
            gs.messageSent.add(msg);
            if (msg.dbNumber == 99) { // PAX BBS messages.
                gs.bbs.add(msg.prefillIndex, msg);
            } else {
                Database db = gs.dbList.lookup(msg.dbNumber);
                db.bbsMessages.add(msg);
            }

            i++;
        }

    }

    private static void putBankTransactions(ArrayList<BankTransaction> list, Properties p) {
        LOGGER.log(Level.FINE, "Put Bank Transactions...");
        String key = BANK_TRANSACTION.key;
        int i = 0;
        for (BankTransaction transaction : list) {
            LOGGER.log(Level.FINER, "    Put {0}.{1}: {2}", new Object[]{key, i, transaction.toString()});
            transaction.putProps(key + "." + i, p);
            i++;
        }
    }

    private static void restoreBankTransactions(ArrayList<BankTransaction> list, Properties p) {
        LOGGER.log(Level.FINE, "Restore Bank Transactions...");
        String key = BANK_TRANSACTION.key;
        int i = 0;

        while (p.getProperty(key + "." + i + ".name") != null) {
            String itemPrefix = key + "." + i + ".name";

            LOGGER.log(Level.FINER, "    Restore : {0}.{1}", new Object[]{key, i});
            BankTransaction transaction = BankTransaction.pullTransaction(itemPrefix, p);
            list.add(transaction);

            i++;
        }
    }

    /**
     * Format example: locked = R12_TRBL,R22_TL,R45_RB
     *
     * Only rooms with one or more door locked are written.
     *
     * @param gs
     * @param p
     */
    private static void putLockedRooms(GameState gs, Properties p) {
        LOGGER.log(Level.FINE, "Put Locked Rooms...");
        String key = ROOM_LOCKED.key;
        StringBuilder mainSb = new StringBuilder();
        for (Room r : Room.values()) {
            StringBuilder sb = new StringBuilder();

            for (RoomBounds.Door d : RoomBounds.Door.values()) {
                if (r.isDoorLocked(d)) {
                    sb.append(d.name().substring(0, 1));
                }
            }
            if (!sb.isEmpty()) {
                LOGGER.log(Level.FINER, "    Put {0}: {1}", new Object[]{key, r.name()});
                if (!mainSb.isEmpty()) {
                    mainSb.append(",");
                }
                mainSb.append(r.name());
                mainSb.append("_");
                mainSb.append(sb.toString());
            }
        }
        p.put(key, mainSb.toString());
    }

    private static void restoreLockedRooms(GameState gs, Properties p) {
        LOGGER.log(Level.FINE, "Restore Locked Rooms...");
        String key = ROOM_LOCKED.key;
        String lockedDoorProps = (String) p.get(key);
        if (lockedDoorProps == null) {
            return;
        }

        String locked[] = lockedDoorProps.split(",");
        for (String rStr : locked) { // Rxx:T|R|B|L
            if (rStr.isBlank()) {
                continue;
            }

            String elements[] = rStr.split("_");
            LOGGER.log(Level.FINER, "    Restore {0}: {1}", new Object[]{key, elements[0]});
            Room room = Room.lookup(elements[0]);
            //for (String dStr : elements[1].split("|")) {
            for (char dStr : elements[1].toCharArray()) {
                switch (dStr) {
                    case 'T' -> {
                        room.lockDoor(RoomBounds.Door.TOP);
                    }
                    case 'R' -> {
                        room.lockDoor(RoomBounds.Door.RIGHT);
                    }
                    case 'B' -> {
                        room.lockDoor(RoomBounds.Door.BOTTOM);
                    }
                    case 'L' -> {
                        room.lockDoor(RoomBounds.Door.LEFT);
                    }
                }
            }

        }
    }

}
