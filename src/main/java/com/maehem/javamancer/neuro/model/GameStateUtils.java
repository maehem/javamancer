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
        props.put(TIME_MINUTE.key, String.valueOf(gs.dateMinute));
        props.put(TIME_HOUR.key, String.valueOf(gs.dateHour));
        props.put(DATE_MONTH.key, String.valueOf(gs.dateMonth));
        props.put(DATE_DAY.key, String.valueOf(gs.dateDay));
        props.put(DATE_YEAR.key, String.valueOf(gs.dateYear));

        // Money
        props.put(CHIP_BALANCE.key, String.valueOf(gs.chipBalance));
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
        pPut(props, MSG_TO_ARMITAGE_SENT, gs.msgToArmitageSent);
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
        gs.dateMinute = getInt(TIME_MINUTE, p);
        gs.dateHour = getInt(TIME_HOUR, p);
        gs.dateMonth = getInt(DATE_MONTH, p);
        gs.dateDay = getInt(DATE_DAY, p);
        gs.dateYear = getInt(DATE_YEAR, p);

        // Money
        gs.chipBalance = getInt(CHIP_BALANCE, p);
        gs.bankBalance = getInt(BANK_BALANCE, p);
        restorebankTransactions(gs.bankTransactionRecord, p);
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

        // Matrix Stuff
        gs.usingDeck = DeckUtils.getUsingDeck(gs, getStr(USING_DECK, p));
        gs.matrixPosX = getInt(MATRIX_POS_X, p);
        gs.matrixPosY = getInt(MATRIX_POS_Y, p);
        gs.dixieInstalled = getBool(DIXIE_INSTALLED, p);

        // Need to configure deck as if entering cyberspace
        // need to set deck location?
        //gs.usingDeck =
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

        gs.msgToArmitageSent = getBool(MSG_TO_ARMITAGE_SENT, p);
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
        int i = 0;
        for (Item item : gs.inventory) {
            LOGGER.log(Level.INFO, "Put inventory item: {0}", item.item.name());
            item.putProps("inventory." + i, p);
            i++;
        }
    }

    @SuppressWarnings("unchecked")
    private static void restoreInventory(GameState gs, Properties p) {
        int i = 0;
        String val;
        while ((val = p.getProperty("inventory." + i)) != null) {
            LOGGER.log(Level.INFO, "Restore inventory item: {0}", val);
            Catalog lookup = Item.lookup(val);
            if (RealItem.class.isAssignableFrom(lookup.clazz)) {
                LOGGER.log(Level.FINER, "Found RealItem");
                RealItem ri = new RealItem(lookup, 0);
                gs.inventory.add(ri);
            } else if (CreditsItem.class.isAssignableFrom(lookup.clazz)) {
                LOGGER.log(Level.FINER, "Create Credits Item");
                CreditsItem ci = new CreditsItem();
                gs.inventory.add(ci);
            } else if (DeckItem.class.isAssignableFrom(lookup.clazz)) {
                LOGGER.log(Level.FINER, "Create Deck Item");
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
            LOGGER.log(Level.INFO, "Put skill: {0}", skill.catalog.name());
            skill.putProps("skills." + i, p);
            i++;
        }
    }

    private static void restoreSkills(GameState gs, Properties p) {
        int i = 0;
        String val;
        while ((val = p.getProperty("skills." + i)) != null) {
            LOGGER.log(Level.INFO, "Restore skill: {0}", val);
            Catalog lookup = Item.lookup(val);

            LOGGER.log(Level.INFO, "Create Skill Item");
            Skill skill = Skill.getInstance(lookup, 1);
            skill.pullProps("skills." + i, p);

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
        int i = 0;
        for (BodyPart part : gs.soldBodyParts) {
            LOGGER.log(Level.INFO, "Put sold body part: {0}", part.name());
            p.put("bodyPart." + i, part.name());
            i++;
        }
    }

    private static void restoreSoldBodyParts(GameState gs, Properties p) {
        int i = 0;
        String val;
        while ((val = p.getProperty("bodyPart." + i)) != null) {
            LOGGER.log(Level.INFO, "Restore sold body part: {0}", val);
            BodyPart lookup = BodyPart.lookup(val);

            gs.soldBodyParts.add(lookup);

            i++;
        }
    }

    private static void putAIList(GameState gs, Properties p) {
        int i = 0;
        for (AI ai : gs.aiList) {
            LOGGER.log(Level.INFO, "Put AI: {0}", ai.getClass().getSimpleName());
            ai.putProps("ai." + i, p);
            i++;
        }
    }

    private static void restoreAIList(GameState gs, Properties p) {
        int i = 0;
        String val;
        while ((val = p.getProperty("ai." + i)) != null) {
            LOGGER.log(Level.INFO, "Restore AI: {0}", val);
            AI lookup = AI.lookup(val);

            lookup.pullProps("ai." + i, p);

            gs.aiList.add(lookup);

            i++;
        }
    }

    private static void putWarez(GameState gs, Properties p) {
        int i = 0;
        for (Warez w : gs.software) {
            LOGGER.log(Level.INFO, "Put warez: {0}", w.item.name());
            w.putProps("warez." + i, p);
            i++;
        }
    }

    private static void restoreWarez(GameState gs, Properties p) {
        int i = 0;
        String val;
        while ((val = p.getProperty("warez." + i)) != null) {
            LOGGER.log(Level.INFO, "Restore warez: {0}", val);
            Catalog lookup = Item.lookup(val);

            LOGGER.log(Level.INFO, "Create Warez Item");
            Warez w = Warez.getInstance(lookup, 1);
            w.pullProps("warez." + i, p);

            gs.software.add(w);

            i++;
        }
    }

    private static void putPersonList(ArrayList<Person> list, String prefix, Properties p) {
        int i = 0;
        for (Person person : list) {
            LOGGER.log(Level.FINE, "Put {0} person: {1}", new Object[]{prefix, person.getName()});
            person.putProps(prefix + "." + i, p);
            i++;
        }
    }

    private static void restorePersonList(ArrayList<Person> list, String prefix, Properties p) {
        int i = 0;

        while (p.getProperty(prefix + "." + i + ".name") != null) {
            String itemPrefix = prefix + "." + i;

            LOGGER.log(Level.FINE, "Restore person: {0}.{1}", new Object[]{prefix, i});
            Person person = Person.pullPerson(itemPrefix, p);
            list.add(person);

            i++;
        }
    }

    private static void putVisitedRooms(GameState gs, Properties p) {
        StringBuilder sb = new StringBuilder();
        for (Room r : gs.visited) {
            LOGGER.log(Level.INFO, "Put visited room: {0}", r.name());
            if (!sb.isEmpty()) {
                sb.append(",");
            }

            sb.append(r.name());
        }
        p.put("visited", sb.toString());
    }

    private static void restoreVisitedRooms(GameState gs, Properties p) {
        String visited[] = ((String) (p.get("visited"))).split(",");
        for (String rStr : visited) {
            if (rStr.isBlank()) {
                continue;
            }
            LOGGER.log(Level.INFO, "Restore visited room: {0}", rStr);
            gs.visited.add(Room.lookup(rStr));
        }
    }

    private static void putDialogRooms(GameState gs, Properties p) {
        StringBuilder sb = new StringBuilder();
        for (Room r : gs.dialogAllowed) {
            LOGGER.log(Level.INFO, "Put dialog room: {0}", r.name());
            if (!sb.isEmpty()) {
                sb.append(",");
            }

            sb.append(r.name());
        }
        p.put("dialogAllowed", sb.toString());
    }

    private static void restoreDialogRooms(GameState gs, Properties p) {
        String visited[] = ((String) (p.get("dialogAllowed"))).split(",");
        for (String rStr : visited) {
            LOGGER.log(Level.INFO, "Restore dialog room: {0}", rStr);
            gs.dialogAllowed.add(Room.lookup(rStr));
        }
    }

    private static void putSentMessageList(ArrayList<BbsMessage> list, Properties p) {
        int i = 0;
        for (BbsMessage msg : list) {
            LOGGER.log(Level.INFO, "Put sent BBS message: {0} ==> to: {1}", new Object[]{i, msg.to});
            msg.putProps("sentMessage." + i, p);
            i++;
        }
    }

    private static void restoreSentMessageList(GameState gs, Properties p) {
        int i = 0;

        while (p.getProperty("sentMessage." + i + ".to") != null) {
            String itemPrefix = "sentMessage." + i;

            LOGGER.log(Level.INFO, "Restore sent BBS message: {0}", new Object[]{itemPrefix});
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
        int i = 0;
        for (BankTransaction transaction : list) {
            LOGGER.log(Level.INFO, "Put {0} bank transaction: {1}", new Object[]{BANK_TRANSACTIONS.key, transaction.toString()});
            transaction.putProps(BANK_TRANSACTIONS.key + "." + i, p);
            i++;
        }
    }

    private static void restorebankTransactions(ArrayList<BankTransaction> list, Properties p) {
        int i = 0;

        while (p.getProperty(BANK_TRANSACTIONS.key + "." + i + ".name") != null) {
            String itemPrefix = BANK_TRANSACTIONS.key + "." + i + ".name";

            LOGGER.log(Level.INFO, "Restore bank transaction: {0}.{1}", new Object[]{BANK_TRANSACTIONS.key, i});
            BankTransaction transaction = BankTransaction.pullTransaction(itemPrefix, p);
            list.add(transaction);

            i++;
        }
    }

    /**
     * Format example: locked = R12:T|R|B|L,R22:T|L,R45:R|B
     *
     * Only rooms with one or more door locked are written.
     *
     * @param gs
     * @param p
     */
    private static void putLockedRooms(GameState gs, Properties p) {
        StringBuilder mainSb = new StringBuilder();
        for (Room r : Room.values()) {
            StringBuilder sb = new StringBuilder();
//            if (!sb.isEmpty()) {
//                sb.append(",");
//            }

            for (RoomBounds.Door d : RoomBounds.Door.values()) {
                if (r.isDoorLocked(d)) {
                    if (!sb.isEmpty()) {
                        sb.append("|");
                    }
                    sb.append(d.name().substring(0, 1));
                }
            }
            if (!sb.isEmpty()) {
                LOGGER.log(Level.INFO, "Put locked room: {0}", r.name());
                if (!mainSb.isEmpty()) {
                    mainSb.append(",");
                }
                mainSb.append(r.name());
                mainSb.append(":");
                mainSb.append(sb.toString());
            }
        }
        p.put("locked", mainSb.toString());
    }

    private static void restoreLockedRooms(GameState gs, Properties p) {
        String lockedDoorProps = (String) p.get("locked");
        if (lockedDoorProps == null) {
            return;
        }

        String locked[] = lockedDoorProps.split(",");
        for (String rStr : locked) { // Rxx:T|R|B|L
            if (rStr.isBlank()) {
                continue;
            }
            String elements[] = rStr.split(":");
            LOGGER.log(Level.INFO, "Restore locked room: {0}", elements[0]);
            Room room = Room.lookup(elements[0]);
            for (String dStr : elements[1].split("|")) {
                if (dStr.startsWith("T")) {
                    room.lockDoor(RoomBounds.Door.TOP);
                } else if (dStr.startsWith("R")) {
                    room.lockDoor(RoomBounds.Door.RIGHT);
                } else if (dStr.startsWith("B")) {
                    room.lockDoor(RoomBounds.Door.BOTTOM);
                } else if (dStr.startsWith("L")) {
                    room.lockDoor(RoomBounds.Door.LEFT);
                }
            }

        }
    }

}
