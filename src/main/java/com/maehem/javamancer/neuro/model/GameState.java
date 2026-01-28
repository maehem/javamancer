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

import com.maehem.javamancer.logging.Logging;
import com.maehem.javamancer.neuro.model.ai.AI;
import com.maehem.javamancer.neuro.model.database.Database;
import com.maehem.javamancer.neuro.model.database.DatabaseList;
import com.maehem.javamancer.neuro.model.deck.Cyberspace7DeckItem;
import com.maehem.javamancer.neuro.model.item.CreditsItem;
import com.maehem.javamancer.neuro.model.item.DeckItem;
import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.item.Item.Catalog;
import com.maehem.javamancer.neuro.model.item.RealItem;
import com.maehem.javamancer.neuro.model.item.SkillItem;
import com.maehem.javamancer.neuro.model.room.Room;
import static com.maehem.javamancer.neuro.model.room.Room.*;
import com.maehem.javamancer.neuro.model.room.RoomBounds;
import com.maehem.javamancer.neuro.model.skill.Skill;
import com.maehem.javamancer.neuro.model.warez.AcidWarez;
import com.maehem.javamancer.neuro.model.warez.ComLinkWarez;
import com.maehem.javamancer.neuro.model.warez.CyberspaceWarez;
import com.maehem.javamancer.neuro.model.warez.Warez;
import com.maehem.javamancer.neuro.view.ResourceManager;
import com.maehem.javamancer.neuro.view.RoomMode;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class GameState {

    public static final Logger LOGGER = Logging.LOGGER;
    public static final int GRID_MAX = 512;
    public static final int GRID_SIZE = 16;

    public static final Room ROOM_START = Room.R1;

    public enum BodyShopRecent {
        NONE, BUY, SELL, REVIVED;
    }

    public enum PawnRecent {
        NONE, BUY;
    }

    public final ResourceManager resourceManager;
    public final DatabaseList dbList;// = new DatabaseList();
    public int loadSlot = -1; // Handled each loop() by NeuroGamePane.

    public final ArrayList<NewsArticle> news = new ArrayList<>();
    public final ArrayList<BbsMessage> bbs = new ArrayList<>();

    public final static int NAME_LEN_MAX = 12;
    public String name = "Case";

    // Health
    public static final int CONSTITUTION_MAX = 2000;
    public static final int CONSTITUTION_HEAL_RATE = 10;
    public int damage = 0; // Heal after revived, decays HEAL_RATE per 15 ticks.

    public final ArrayList<Item> inventory = new ArrayList<>();

    // Time/Date
    public int dateMinute = 0;
    public int dateHour = 12;
    public int dateMonth = 11;
    public int dateDay = 16;
    public int dateYear = 2058;

    // Money
    public final static String PLAYER_BAMA = "056306118";
    public final static String LARRY_MOE_BAMA = "062788138";
    public int moneyChipBalance = 6;
    public int bankBalance = 2000;
    public final ArrayList<BankTransaction> bankTransactionRecord = new ArrayList<>();
    public int bankZurichBalance = 0;  // Create account by accessing Zurich via sequencer in cyberspace.
    public int bankGemeinBalance = 30000;  // Create account by accessing Zurich via sequencer in cyberspace.
    public String bankZurichCreated = null; // Date string when account created.
    public final static String BANK_ZURICH_ID = "712345450134";
    public final static String BANK_GEMEIN_ID = "646328356481";

    // Lists
    public final ArrayList<Skill> skills = new ArrayList<>();
    public final ArrayList<BodyPart> soldBodyParts = new ArrayList<>();
    public final ArrayList<Warez> software = new ArrayList<>();
    public final ArrayList<Person> seaWantedList = new ArrayList<>();
    public final ArrayList<Person> chibaWantedList = new ArrayList<>();
    public final ArrayList<Person> hosakaEmployeeList = new ArrayList<>();
    public final ArrayList<Room> visited = new ArrayList<>();
    public final ArrayList<Room> dialogAllowed = new ArrayList<>();
    public final ArrayList<Database> defeatedDbList = new ArrayList<>(); // TODO: For stats only. ICE never really dies.
    public final ArrayList<AI> defeatedAiList = new ArrayList<>();
    public final ArrayList<BbsMessage> messageSent = new ArrayList<>();

    // Deck & Matrix Stuff
    public static final int AI_FIGHT_SKILL_MAX = 8; // TODO: Not needed?
    public int deckSlots = 0;
    public DeckItem usingDeck = null;
    public boolean usingDeckErase = false; // Ephemeral
    public int matrixPosX = 112;
    public int matrixPosY = 96;
    public int romInstalled = -1; // ROM Construct: None
    public int aiFightSkill = 0; // Philosophy skill. +1 after every AI win. 8 max. TODO. Remove. Not used.
    public Database database = null; // Ephemeral
    public boolean databaseBattle = false; // Ephemeral. No save during battle
    public boolean databaseArrived = false; // Ephemeral. Set by explorer when at a DB.
    public boolean databaseBattleBegin = false; // Ephemeral.
    private boolean playerFlatlined = false; // Ephemaral

    // Room Stuff
    public RoomMode roomMode;
    public int roomPosX = 160;
    public int roomPosY = 90;
    public Room room = null;
    public Room roomPrevious;
    public RoomBounds.Door useDoor = RoomBounds.Door.NONE; // Ephemeral. Set when player collides with door.

    // Cheap Hotel
    public int hotelCharges = 1000;
    public int hotelOnAccount = 0;
    public int hotelCaviar = 1; // Stock of item.
    public int hotelSake = 2; // Stock of item.
    public int hotelDeliverCaviar = 0; // Add this many caviar to player inventory upon next hotel visit.
    public int hotelDeliverSake = 0; // Add this many sake to player inventory upon next hotel visit.

    // Massage Parlor
    public boolean massageInfo1 = false;
    public boolean massageInfo2 = false;
    public boolean massageInfo3 = false;
    public boolean massageInfo4 = false;
    public boolean massageInfo5 = false;

    // Hi-Tech Zone visitor (Hitachi)
    public boolean hitachiVolunteer = false;

    // Misc.
    public boolean bbsMsgFromArmitageRead = false;
    public boolean bbsMsgToArmitageSent = false;
    public boolean ratzPaid = false; // Player must give Ratz 46 credits.
    public boolean shivaChipMentioned = false;
    public boolean shivaGaveChip = false;
    public boolean joystickGiven = false; // Player must give Nolan the joystick.
    public boolean gasMaskIsOn = false;
    //public boolean gasMaskGiven = false; // Not used.

    public boolean bodyPartDiscount = false;
    public Skill activeSkill = null;
    public boolean bankZurichRobbed = false;
    public boolean comlink2recieved = false; // Set by Edo given Caviar
    public boolean comlink6uploaded = false; // Set by uploading to Hosaka.
    public boolean asanoDiscount = false; // Set by talking to Asano
    public boolean securityPassGiven = false; // Sense/Net pass. Given to computer.
    public boolean larryMoeWanted = false; // Chiba Tectical Police wanted list
    public int psychoProbeCount = 0; // Increaase each time player gets probed.

    // Sets to 1 when player adds name. Paid on 1, 8, 15, etc.
    // Sets to 0 when player is paid.
    // Sets to -1 if player removes BAMA from list.
    public int hosakaDaysSincePaid = -1;

    // Ephemeral. Not game saved.
    public Skill previousSkill = null;
    public int activeSkillLevel = 0;
    public BodyShopRecent bodyShopRecent = BodyShopRecent.NONE; // Fix me. Probably a better way to do.
    public PawnRecent pawnRecent = PawnRecent.NONE;

    public RoomBounds.Door shuttleDest = null; // Ephemeral. Used at spaceports.

    // Ephemeral -- Not saved
    public boolean pause = true; // Ephemeral
    public boolean requestQuit = false; // Ephemeral. Set by Disk Menu Quit option.
    public String showMessage = ""; // Ephemeral. RoomMode.tick() will place text in description area.
    public String showMessageNextRoom = ""; // Ephemeral. RoomMode.tick() will place text in description area of next room.

    public GameState(ResourceManager rm) {
        this.resourceManager = rm;

        this.dbList = new DatabaseList(rm);
    }

    /**
     * Called by NeuroGamePane near the end of NEW_GAME action.
     *
     * This should be empty for any release version.
     */
    public void initTestItems() {

        // Game Test Items
        //room = Room.R40; // Street Metro Holo
        //chipBalance = 2000;
        //inventory.add(new RealItem(Catalog.CAVIAR, 0)); // Test item
        // Test examples:
        //massageInfo3 = true;
        // Make a Deck, put it into inventory and put some software on it.
//        Cyberspace7DeckItem testDeckItem = new Cyberspace7DeckItem();
//        testDeckItem.needsRepair = true;
//        inventory.add(testDeckItem); // Test item
//        deckSlots = testDeckItem.nSlots;
//        addSoftware(new ComLinkWarez(2));
//        addSoftware(new CyberspaceWarez(1));
//        addSoftware(new AcidWarez(3)); // Should delete when used.
    }

    public boolean roomCanTalk() {
        return dialogAllowed.contains(room);
    }

    public void setRoomTalk(boolean val) {
        //roomNpcTalk[room.getIndex()] = val;
        if (val) {
            if (!roomCanTalk()) {
                dialogAllowed.add(room);
            }
        } else {
            dialogAllowed.remove(room);
        }
    }

//    public void allowDialog(Room room) {
//        if (!dialogAllowed.contains(room)) {
//            dialogAllowed.add(room);
//        }
//    }
//
    public void addMinute() {
        if (++dateMinute > 59) {
            if (++dateHour > 23) {
                if (++dateDay > 30) {
                    if (++dateMonth > 12) {
                        dateYear++;
                        dateMonth = 1;
                    }
                    dateDay = 1;
                }
                if (hosakaDaysSincePaid >= 0) { // < 0 means unconfigured
                    hosakaDaysSincePaid++;
                }
                dateHour = 0;
            }
            dateMinute = 0;
        }

        if (usingDeck != null && usingDeck.getMode() != DeckItem.Mode.NONE) {
            if (!usingDeck.isNoFee()) {
                if (moneyChipBalance > 0) {
                    moneyChipBalance--;
                    if (moneyChipBalance == 0) {
                        showMessage = "\n\nNot enough credits to use deck.\n";
                    }
                } // Deck Popup should check balance and exit if needed.
            }

        }
    }

    public String getDateString() {
        String month = String.format("%02d", dateMonth);
        String day = String.format("%02d", dateDay);
        String year = String.format("%04d", dateYear).substring(2);

        return month + "/" + day + "/" + year;
    }

    public int getConstitution() {
        return getConstitutionUsable() - damage;
    }

    /**
     * Max constitution after subtracting sold body parts.
     *
     * @return constitution after subtracting sold body parts
     */
    public int getConstitutionUsable() {
        int partsToll = 0;
        for (BodyPart p : soldBodyParts) {
            partsToll += p.constDamage;
        }

        return CONSTITUTION_MAX - partsToll;
    }

    public void updateConstitution() {
        if (damage > 0 && usingDeck == null) {
            LOGGER.log(Level.FINER, "Heal Constitution: {0}", CONSTITUTION_HEAL_RATE);
            damage -= CONSTITUTION_HEAL_RATE;
            if (damage < 0) {
                damage = 0;
            }
        }
    }

    public void loadSlot(int i) {
        LOGGER.log(Level.FINE, "Load Slot {0} requested", i);

        loadSlot = i; // Causes NeuroModePane.loop() to load new game.
    }

    public void saveSlot(int i) {
        LOGGER.log(Level.CONFIG, "Save Slot {0} requested", i);
    }

    public boolean hasInstalledSkill(SkillItem skillItem) {
        for (Skill skill : skills) {
            if (skill.getClass().equals(skillItem.item.skillClazz)) {
                LOGGER.log(Level.CONFIG, "{0} already installed.", skill.catalog.itemName);
                return true;
            }
        }
        return false;
    }

    public Skill getInstalledSkill(SkillItem skillItem) {
        for (Skill skill : skills) {
            if (skill.getClass().equals(skillItem.item.skillClazz)) {
                LOGGER.log(Level.CONFIG, "Found skill {0} is installed.", skill.catalog.itemName);
                return skill;
            }
        }
        return null;
    }

    public boolean hasInventoryItem(Item checkItem) {
        for (Item item : inventory) {
            if (item.getName().equals(checkItem.getName())) {
                LOGGER.log(Level.FINER, () -> "Matched Item name: " + item.getName());
                return true;
            }
        }

        return false;
    }

    public boolean hasInventoryItem(Catalog checkItem) {
        for (Item item : inventory) {
            if (item.item.equals(checkItem)) {
                LOGGER.log(Level.FINER, () -> "Matched Item name: " + item.getName());
                return true;
            }
        }

        return false;
    }

    public Item getInventoryItem(Catalog checkItem) {
        for (Item item : inventory) {
            if (item.item.equals(checkItem)) {
                LOGGER.log(Level.FINER, () -> "Matched Item name: " + item.getName());
                return item;
            }
        }

        return null;
    }

    public boolean removeInventoryItem(Catalog removeItem) {
        for (Item item : inventory.toArray(Item[]::new)) {
            if (item.item.equals(removeItem)) {
                LOGGER.log(Level.FINER, () -> "Matched Item name: " + item.getName() + ". Removing.");
                inventory.remove(item);
                return true;
            }
        }

        return false;
    }

    public final boolean addSoftware(Warez w) {
        if (software.size() < deckSlots) {
            if (software.add(w)) {
                LOGGER.log(Level.FINE, "Deck Warez:");
                software.forEach((dw) -> {
                    LOGGER.log(Level.FINE, () -> "    " + dw.getSimpleName());
                });
                return true;
            } else {
                LOGGER.log(Level.SEVERE, "Collection.add did not change software list. Unknown reason.");
                return false;
            }
        } else {
            LOGGER.log(Level.WARNING, "Deck slots full. Erase items first or buy better deck.");
            return false;
        }
    }

    public final boolean eraseSoftware(Warez w) {
        if (usingDeck != null && usingDeck.getCurrentWarez() != null
                && usingDeck.getCurrentWarez().item == w.item) {
            usingDeck.setCurrentWarez(null);
        }
        return software.remove(w);
    }

    public final boolean hasInstalledSoftware(Warez w) {
        for (Warez sw : software) {
            if (sw.item.equals(w.item) && sw.version == w.version) {
                return true;
            }
        }

        return false;
    }

    /**
     * Called by YES/NO popup when player nears DB in Cyberspace.
     *
     */
    public void battleStart() { // Handled by next tick() of CyberspacePopup
        database = dbList.whatsAt(
                usingDeck.getCordX(), usingDeck.getCordY()
        );
        databaseBattle = true;
        databaseBattleBegin = true;
        databaseArrived = false;

        // Push any used warez (like EadyRider) so that we can pop it when 
        // player goes back to explore.
        usingDeck.pushWarez();

        // Next CyberspacePopup.tick() will cause BattleGrid to show and begin ICE battle.
    }

    public AI getAI(Class<? extends AI> aiClazz) {
        if (aiClazz == null) {
            return null;
        }

        for (AI ai : defeatedAiList) {
            if (ai.getClass().equals(aiClazz)) {
                return ai;
            }
        }

        try {
            Constructor<?> ctor = aiClazz.getConstructor();
            Object object = ctor.newInstance(new Object[]{});
            LOGGER.log(Level.FINE, () -> "AI Object created: " + aiClazz.getSimpleName());
            if (object instanceof AI freshAI) {
                defeatedAiList.add(freshAI);
                return freshAI;
            } else {
                LOGGER.log(Level.SEVERE, () -> "AI Creation Failed: " + aiClazz.getSimpleName());
            }
        } catch (InvocationTargetException
                | InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | NoSuchMethodException
                | SecurityException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return null;
    }

    public void applyEnemyAttack(int amount) {
        // Apply DB attack effect to constitution.
        //int effect = database.getEffect(this);
        damage += amount;
        // If Constitution == 0 then die.
        if (getConstitution() <= 0) {
            LOGGER.log(Level.CONFIG, "Player death. Revive in Body Shop.");
            playerFlatlined = true;
        }
    }

    public boolean isFlatline() {
        return playerFlatlined;
    }

    public void revive() {
        LOGGER.log(Level.CONFIG, "Revive player.");
        damage = getConstitutionUsable() - 10;
        moneyChipBalance = 0;
        playerFlatlined = false;
        bodyShopRecent = BodyShopRecent.REVIVED; // Handled by R4Extras.warmup()
        useDoor = RoomBounds.Door.BODY_SHOP;
    }

    public void initNewGame() {
        // Standard inventory items
        inventory.add(new CreditsItem());
        inventory.add(new RealItem(Catalog.PAWNTICKET, 0));

        resourceManager.initNewsArticles(
                news,
                name,
                getDateString()
        );
        resourceManager.initPaxBbsMessages(
                bbs,
                name
        );

        visited.add(R10); // TODO: Shuttle doesn't work as "firstTime" visit.

        bankTransactionRecord.add(new BankTransaction("11/15/58", BankTransaction.Operation.Download, 120));
        bankTransactionRecord.add(new BankTransaction("11/15/58", BankTransaction.Operation.Download, 56));
        bankTransactionRecord.add(new BankTransaction("11/15/58", BankTransaction.Operation.Download, 75));
        bankTransactionRecord.add(new BankTransaction("11/15/58", BankTransaction.Operation.Fine, 1000));

        Room.R17.lockDoor(RoomBounds.Door.LEFT); // Lock Maas Biolabs
    }

    /**
     * Called when ending a game or loading a new game.
     */
    public void cleanUp() {
        LOGGER.log(Level.CONFIG, "GameState: Clean Up called.");
        resourceManager.musicManager.stopAll(); // Fade all?
    }
}
