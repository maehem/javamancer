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
import com.maehem.javamancer.neuro.model.item.ItemCatalog;
import com.maehem.javamancer.neuro.model.item.RealItem;
import com.maehem.javamancer.neuro.model.item.SkillItem;
import com.maehem.javamancer.neuro.model.room.Room;
import com.maehem.javamancer.neuro.model.room.RoomBounds;
import com.maehem.javamancer.neuro.model.skill.Skill;
import com.maehem.javamancer.neuro.model.warez.AcidWarez;
import com.maehem.javamancer.neuro.model.warez.CyberspaceWarez;
import com.maehem.javamancer.neuro.model.warez.Warez;
import com.maehem.javamancer.neuro.view.ResourceManager;
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

    private boolean flatlined = false;
    private boolean iceBroken;

    public enum BodyShopRecent {
        NONE, BUY, SELL, REVIVED;
    }

    public enum PawnRecent {
        NONE, BUY;
    }

    public final ResourceManager resourceManager;
    public final DatabaseList dbList;// = new DatabaseList();

    public final static int NAME_LEN_MAX = 12;
    public String name = "Case";

    // Money
    public final static String PLAYER_BAMA = "056306118";
    public final static String LARRY_MODE_BAMA = "062788138";
    public int chipBalance = 6;
    public int bankBalance = 2000;
    public final ArrayList<BankTransaction> bankTransactionRecord = new ArrayList<>();
    public int bankZurichBalance = 0;  // Create account by accessing Zurich via sequencer of cyberspace.
    public int bankGemeinBalance = 30000;  // Create account by accessing Zurich via sequencer of cyberspace.
    public String bankZurichCreated = null; // Date string when account created.
    public final static String BANK_ZURICH_ID = "712345450134";
    public final static String BANK_GEMEIN_ID = "646328356481";

    // Health
    public final int CONSTITUTION_MAX = 2000;
    public final int CONSTITUTION_HEAL_RATE = 40;
    public int damage = 0; // Heal after revived, decays HEAL_RATE per 15 ticks.

    // Time/Date
    public int timeMinute = 0;
    public int timeHour = 12;
    public int dateMonth = 11;
    public int dateDay = 16;
    public int dateYear = 2058;

    public final ArrayList<NewsArticle> news = new ArrayList<>();
    public final ArrayList<BbsMessage> bbs = new ArrayList<>();
    public final ArrayList<Item> inventory = new ArrayList<>();
    public final ArrayList<Skill> skills = new ArrayList<>();
    public final ArrayList<BodyPart> soldBodyParts = new ArrayList<>();
    public final ArrayList<Warez> software = new ArrayList<>();
    public final ArrayList<Person> seaWantedList = new ArrayList<>();
    public final ArrayList<Person> chibaWantedList = new ArrayList<>();

    // Deck
    public int deckSlots = 0;

    // Matrix Stuff
    public Database database = null;
    public DeckItem usingDeck = null;
    public boolean usingDeckErase = false;
    public int matrixPosX = 112;
    public int matrixPosY = 96;
    public boolean databaseBattle = false;
    public boolean databaseArrived = false; // Set by explorer when at a DB.
    public boolean databaseBattleBegin = false;
    public final ArrayList<AI> aiList = new ArrayList<>();

    // Room Stuff
    public int roomPosX = 160;
    public int roomPosY = 90;
    public Room room = null;
    public RoomBounds.Door useDoor = RoomBounds.Door.NONE; // Set when player collides with door.

    public final ArrayList<BbsMessage> messageSent = new ArrayList<>();

    // TODO: Use ArrayList for roomsVisited
    public final ArrayList<Room> visited = new ArrayList<>();

    public final boolean roomNpcTalk[] = { // 58 Slots  [0..57] Room # = index+1
        true, true, true, true, false, // 1-5
        true, true, true, false, false, // 6-10
        false, true, false, false, false, // 11-15
        false, false, false, false, false, // 16-20
        false, false, true, true, true, // 21-25
        true, true, false, false, false, //26-30
        false, true, false, false, false, // 31-35
        true, false, false, false, true, // 36-40
        false, false, false, true, true, // 41-45
        true, false, false, false, false, // 46-50
        true, true, false, false, false, // 50-55
        true, true, false // 56-58
    };

    public boolean msgToArmitageSent = false;
    public boolean ratzPaid = false; // Player must give Ratz 46 credits.
    public boolean shivaChipMentioned = false;
    public boolean shivaGaveChip = false;
    public boolean shivaGavePass = false;
    public boolean joystickGiven = false; // Player must give Nolan the joystick.
    public boolean gasMaskIsOn = false;

    // Cheap Hotel
    public int hotelCharges = 1000;
    public int hotelOnAccount = 0;
    public int hotelCaviar = 1; // Stock of item.
    public int hotelSake = 2; // Stock of item.
    public int hotelDeliverCaviar = 0; // Add this many caviar to player inventory upon next hotel visit.
    public int hotelDeliverSake = 0; // Add this many sake to player inventory upon next hotel visit.

    public ItemCatalog activeItem = ItemCatalog.NONE;
    public Skill previousSkill = null;
    public Skill activeSkill = null;
    public int activeSkillLevel = 0;
    public boolean bodyPartDiscount = true;
    public BodyShopRecent bodyShopRecent = BodyShopRecent.NONE; // Fix me. Probably a better way to do.
    public PawnRecent pawnRecent = PawnRecent.NONE;
    public int psychoProbeCount = 0; // Increaase each time player gets probed.
    public boolean swissBankRobbed = false;
    public boolean comlink2recieved = false; // Set by Edo given Caviar
    public boolean comlink6uploaded = false; // Set by uploading to Hosaka.
    public boolean asanoDiscount = false; // Set by talking to Asano
    public boolean securityPassGiven = false; // Sense/Net pass. Given to computer.
    public boolean dixieInstalled = false; // ROM Construct: Dixie Flatline
    public boolean larryMoeWanted = false; // Chiba Tectical Police wanted list

    // Hosaka Emplyee List
    public final ArrayList<Person> hosakaEmployeeList = new ArrayList<>();

    // Sets to 1 when player adds name. Paid on 1, 8, 15, etc.
    // Sets to 0 when player is paid.
    // Sets to -1 if player removes BAMA from list.
    public int hosakaDaysSincePaid = -1;


    // Ephemeral -- Not saved
    public boolean pause = true;
    public boolean requestQuit = false; // Set by Disk Menu Quit option.
    public String showMessage = ""; // RoomMode.tick() will place text in description area.
    public String showMessageNextRoom = ""; // RoomMode.tick() will place text in description area of next room.

    public GameState(ResourceManager rm) {
        this.resourceManager = rm;

        this.dbList = new DatabaseList(rm);

        bankTransactionRecord.add(new BankTransaction("11/15/58", BankTransaction.Operation.Download, 120));
        bankTransactionRecord.add(new BankTransaction("11/15/58", BankTransaction.Operation.Download, 56));
        bankTransactionRecord.add(new BankTransaction("11/15/58", BankTransaction.Operation.Download, 75));
        bankTransactionRecord.add(new BankTransaction("11/15/58", BankTransaction.Operation.Fine, 1000));

        // Standard inventory items
        inventory.add(new CreditsItem());
        inventory.add(new RealItem(Catalog.PAWNTICKET, 0));

        // Game Test Items
        Cyberspace7DeckItem textDeckItem = new Cyberspace7DeckItem();
        inventory.add(textDeckItem); // Test item
        deckSlots = textDeckItem.nSlots;
        addSoftware(new CyberspaceWarez(1));
        addSoftware(new AcidWarez(1)); // Should delete when used.

        inventory.add(new RealItem(Catalog.CAVIAR, 1));

        //bankZurichCreated = "11/16/58"; // Test Item
        //bankZurichBalance = 2000; // Test Item
        chipBalance = 30; // Test Item

    }

    public boolean roomCanTalk() {
        return roomNpcTalk[room.getIndex()];
    }

    public void setRoomTalk(boolean val) {
        roomNpcTalk[room.getIndex()] = val;
    }

    public void addMinute() {
        if (++timeMinute > 59) {
            if (++timeHour > 23) {
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
                timeHour = 0;
            }
            timeMinute = 0;
        }

        if (usingDeck != null && usingDeck.getMode() != DeckItem.Mode.NONE) {
            if (!usingDeck.isNoFee()) {
                if (chipBalance > 0) {
                    chipBalance--;
                    if (chipBalance == 0) {
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
            LOGGER.log(Level.SEVERE, "Heal Constitution.");
            damage -= CONSTITUTION_HEAL_RATE;
            if (damage < 0) {
                damage = 0;
            }
        }
    }

    public void loadSlot(int i) {
        LOGGER.log(Level.SEVERE, "Load Slot {0} requested", i);
    }

    public void saveSlot(int i) {
        LOGGER.log(Level.SEVERE, "Save Slot {0} requested", i);
    }

    public boolean hasInstalledSkill(SkillItem skillItem) {
        for (Skill skill : skills) {
            if (skill.getClass().equals(skillItem.item.clazz)) {
                LOGGER.log(Level.CONFIG, "{0} already installed.", skill.type.itemName);
                return true;
            }
        }
        return false;
    }

    public Skill getInstalledSkill(SkillItem skillItem) {
        for (Skill skill : skills) {
            if (skill.getClass().equals(skillItem.item.clazz)) {
                LOGGER.log(Level.CONFIG, "Found skill {0} is installed.", skill.type.itemName);
                return skill;
            }
        }
        return null;
    }

    public boolean hasInventoryItem(Item checkItem) {
        for (Item item : inventory) {
            if (item.getName().equals(checkItem.getName())) {
                LOGGER.log(Level.SEVERE, "Matched Item name: " + item.getName());
                return true;
            }
        }

        return false;
    }

    public boolean hasInventoryItem(Catalog checkItem) {
        for (Item item : inventory) {
            if (item.item.equals(checkItem)) {
                LOGGER.log(Level.SEVERE, "Matched Item name: " + item.getName());
                return true;
            }
        }

        return false;
    }

    public boolean removeInventoryItem(Catalog removeItem) {
        for (Item item : inventory.toArray(Item[]::new)) {
            if (item.item.equals(removeItem)) {
                LOGGER.log(Level.SEVERE, "Matched Item name: " + item.getName() + ". Removing.");
                inventory.remove(item);
                return true;
            }
        }

        return false;
    }

    public final boolean addSoftware(Warez w) {
        if (software.size() < deckSlots) {
            if (software.add(w)) {
                LOGGER.log(Level.SEVERE, "Deck Warez:\n");
                software.forEach((dw) -> {
                    LOGGER.log(Level.SEVERE, dw.getSimpleName() + "\n");
                });
                return true;
            } else {
                LOGGER.log(Level.SEVERE, "Collection.add did not change software list. Unknown reason.");
                return false;
            }
        } else {
            LOGGER.log(Level.SEVERE, "Deck slots full. Erase items first or buy better deck.");
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

    public void battleStart() { // Handled by next tick() of CYberspacePopup
        databaseBattle = true;
        databaseBattleBegin = true;
        databaseArrived = false;
    }

    public AI getAI(Class<? extends AI> aiClazz) {
        for (AI ai : aiList) {
            if (ai.getClass().equals(aiClazz)) {
                return ai;
            }
        }

        try {
            Constructor<?> ctor = aiClazz.getConstructor();
            Object object = ctor.newInstance(new Object[]{});
            LOGGER.log(Level.SEVERE, "AI Object created: " + aiClazz.getSimpleName());
            if (object instanceof AI freshAI) {
                aiList.add(freshAI);
                return freshAI;
            } else {
                LOGGER.log(Level.SEVERE, "AI Creation Failed: " + aiClazz.getSimpleName());
            }
        } catch (InvocationTargetException
                | InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | NoSuchMethodException
                | SecurityException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            ex.printStackTrace();

        }

        return null;
    }

    public void applyDbAttack() {
        // Apply DB attack effect to constitution.
        int effect = database.getEffect(this);
        damage += effect;
        // If Constitution == 0 then die.
        if (getConstitution() <= 0) {
            LOGGER.log(Level.CONFIG, "Player death. Revive in Body Shop.");
            flatlined = true;
        }
    }

    public boolean isFlatline() {
        return flatlined;
    }

    public void revive() {
        LOGGER.log(Level.SEVERE, "Revive player.");
        damage = getConstitutionUsable() - 10;
        chipBalance = 0;
        flatlined = false;
        bodyShopRecent = BodyShopRecent.REVIVED; // Handled by R4Extras.warmup()
        useDoor = RoomBounds.Door.BODY_SHOP;
    }

    public void setIceBroken(boolean state) {
        this.iceBroken = state;
    }

    public boolean isIceBroken() {
        return iceBroken;
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
        resourceManager.initBbsMessages(
                bbs,
                name
        );

        dialogAllowed.add(R1);
        dialogAllowed.add(R2);
        dialogAllowed.add(R3);
        dialogAllowed.add(R4);
        dialogAllowed.add(R6);
        dialogAllowed.add(R7);
        dialogAllowed.add(R8);
        dialogAllowed.add(R12);
        dialogAllowed.add(R23);
        dialogAllowed.add(R24);
        dialogAllowed.add(R25);
        dialogAllowed.add(R32);
        dialogAllowed.add(R36);
        dialogAllowed.add(R44);
        dialogAllowed.add(R45);
        dialogAllowed.add(R46);
        dialogAllowed.add(R50);
        dialogAllowed.add(R51);
        dialogAllowed.add(R56);
        dialogAllowed.add(R57);

        bankTransactionRecord.add(new BankTransaction("11/15/58", BankTransaction.Operation.Download, 120));
        bankTransactionRecord.add(new BankTransaction("11/15/58", BankTransaction.Operation.Download, 56));
        bankTransactionRecord.add(new BankTransaction("11/15/58", BankTransaction.Operation.Download, 75));
        bankTransactionRecord.add(new BankTransaction("11/15/58", BankTransaction.Operation.Fine, 1000));

    }

    /**
     * Called when ending a game or loading a new game.
     */
    public void cleanUp() {
        LOGGER.log(Level.CONFIG, "GameState: Clean Up called.");
        resourceManager.musicManager.stopAll(); // Fade all?
    }
}
