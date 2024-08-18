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
import com.maehem.javamancer.neuro.model.database.Database;
import com.maehem.javamancer.neuro.model.database.DatabaseList;
import com.maehem.javamancer.neuro.model.item.DeckItem;
import com.maehem.javamancer.neuro.model.item.Item;
import com.maehem.javamancer.neuro.model.item.ItemCatalog;
import com.maehem.javamancer.neuro.model.item.SkillItem;
import com.maehem.javamancer.neuro.model.skill.Skill;
import com.maehem.javamancer.neuro.view.ResourceManager;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class GameState {

    public static final Logger LOGGER = Logging.LOGGER;

    public enum BodyShopRecent {
        NONE, BUY, SELL
    }

    public final ResourceManager resourceManager;
    public final DatabaseList dbList = new DatabaseList();

    public String name = "Case";

    // Money
    public String bamaId = "056306118"; // Final?
    public int chipBalance = 346;
    public int bankBalance = 2000;
    public final ArrayList<BankTransaction> bankTransactionRecord = new ArrayList<>();

    // Health
    public int constitution = 2000;

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

    public int roomPosX = 160;
    public int roomPosY = 90;
    public Room room = null;
    public Database database = null;
    public DeckItem usingDeck = null;

    public RoomBounds.Door useDoor = RoomBounds.Door.NONE; // Set when player collides with door.
    public boolean doorTopLocked = false;
    public boolean doorRightLocked = false;
    public boolean doorBottomLocked = false;
    public boolean doorLeftLocked = false;

    // TODO: Use ArrayList for roomsVisited
    public final ArrayList<Room> visited = new ArrayList<>();
//    public final boolean roomIsVisited[] = { // 58 Slots  [0..57]
//        false, false, false, false, false, false, false, false, false,
//        false, false, false, false, false, false, false, false, false,
//        false, false, false, false, false, false, false, false, false,
//        false, false, false, false, false, false, false, false, false,
//        false, false, false, false, false, false, false, false, false,
//        false, false, false, false, false, false, false, false, false,
//        false, false, false, false
//    };
    public final boolean roomNpcTalk[] = { // 58 Slots  [0..57]
        true, true, true, true, false, // 1-5
        true, false, true, false, false, // 6-10
        false, true, false, false, false, // 11-15
        false, false, false, false, false, // 16-20
        false, false, false, true, true, // 21-25
        true, true, false, false, false, //26-30
        false, false, false, false, false, // 31-35
        false, false, false, false, true, // 36-40
        false, false, false, true, true, // 41-45
        true, false, false, false, false, // 46-50
        true, false, true, false, false, // 50-55
        true, false, false // 56-58
    };

    public boolean msgToArmitageSent = false;
    public boolean ratzPaid = false; // Player must give Ratz 46 credits.
    public boolean joystickGiven = false; // Player must give Nolan the joystick.
    public boolean gasMaskIsOn = false;
    public ItemCatalog activeItem = ItemCatalog.NONE;
    public Skill previousSkill = null;
    public Skill activeSkill = null;
    public int activeSkillLevel = 0;
    public boolean bodyPartDiscount = true;
    public BodyShopRecent bodyShopRecent = BodyShopRecent.NONE;

    // Ephemeral -- Not saved
    public boolean pause = true;
    public boolean requestQuit = false; // Set by Disk Menu Quit option.

    public GameState(ResourceManager rm) {
        this.resourceManager = rm;

        bankTransactionRecord.add(new BankTransaction("11/16/58", BankTransaction.Operation.Download, 120));
        bankTransactionRecord.add(new BankTransaction("11/16/58", BankTransaction.Operation.Download, 56));
        bankTransactionRecord.add(new BankTransaction("11/16/58", BankTransaction.Operation.Download, 75));
        bankTransactionRecord.add(new BankTransaction("11/16/58", BankTransaction.Operation.Fine, 1000));
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
                timeHour = 0;
            }
            timeMinute = 0;
        }
    }

    public String getDateString() {
        String month = String.format("%02d", dateMonth);
        String day = String.format("%02d", dateDay);
        String year = String.format("%04d", dateYear).substring(2);

        return month + "/" + day + "/" + year;
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

    public boolean hasInventoryItem(Item checkItem) {
        for (Item item : inventory) {
            if (item.getName().equals(checkItem.getName())) {
                LOGGER.log(Level.SEVERE, "Matched Item name: " + item.getName());
                return true;
            }
        }

        return false;
    }
}

/*
<pre><code>
typedef struct x4bae_t {
	uint8_t x4bae[16];
	uint8_t active_dialog_reply; // 0x4BBE
	uint8_t x4bbf;
*	uint16_t active_item;     // 0x4BC0
*	uint32_t cash_withdrawal; // 0x4BC2
*	uint16_t time_m;  // 0x4BC6
*	uint8_t time_h;   // 0x4BC8
*	uint8_t date_day; // 0x4BC9
	uint8_t x4bca[2]; // padding
	uint8_t x4bcc;
	uint8_t x4bcd[38];
	uint8_t x4bf3;
*	uint8_t active_skill; // 0x4BF4
*	uint8_t active_skill_level; // 0x4BF5
	uint8_t x4bf6[6];
	uint8_t x4bfc;
	uint8_t x4bfd;
	uint8_t x4bfe;
	uint8_t x4bff;
	uint8_t x4c00;
	uint8_t x4c01[3]; // padding
	uint8_t x4c04;
	uint8_t x4c05;
	uint16_t x4c06;
	uint8_t x4c08[8];
	uint8_t x4c10;
	uint8_t x4c11[8];
*	uint8_t gas_mask_is_on;
	uint8_t x4c1a;
	uint16_t x4c1b;
	uint8_t x4c1d[4];
*	uint16_t msg_to_armitage_sent;
	uint8_t x4c23[2];
	uint16_t x4c25;
	uint8_t x4c27[4];
	uint16_t x4c2b;
	uint8_t x4c2d[2];
	uint16_t x4c2f;
	uint16_t x4c31;
	uint8_t x4c33[4];
	uint8_t x4c37;
	uint16_t x4c38;
	uint8_t x4c3a;
	uint8_t x4c3b;
	uint8_t x4c3c;
	uint16_t x4c3d;
	uint8_t x4c3f; // padding
	uint16_t x4c40;
	uint8_t x4c42[2]; // padding
	uint8_t x4c44;
	uint8_t x4c45;
	uint8_t x4c46;
	uint16_t x4c47;
	uint16_t x4c49;
	uint16_t x4c4b;
	uint16_t x4c4d;
	uint8_t x4c4f[8];
	uint16_t x4c57;
	uint8_t x4c59[3]; // padding
	uint8_t x4c5c;
	uint8_t x4c5d[12];
	uint8_t x4c69;
	uint8_t x4c6a;
	uint16_t x4c6b;
	uint8_t x4c6d[7];
	uint8_t x4c74;
	uint8_t x4c75;
	uint8_t x4c76[2]; // padding
*	uint32_t chipBalance; // 0x4C78
	uint16_t x4c7c;
	uint8_t x4c7e[4];
	uint16_t x4c82;
*	uint8_t sold_body_parts_bitstring[3];
	uint16_t x4c87;
*	uint32_t bank_account; // 0x4C89
*	uint8_t bank_last_transacton_record_index; // 0x4C8D
	uint16_t x4c8e;
	uint16_t x4c90;
*	char name[13];    // 0x4C92
*	uint16_t constitution; // 0x4C9F
	uint16_t level_n; // 0x4CA1
*	uint16_t roompos_x; // 0x4CA3
*	uint16_t roompos_y; // 0x4CA5
	uint16_t x4ca7;
*	transaction_record_t bank_transaction_record[4]; // 0x4CA9
	uint16_t ui_type; // 0x4CC1
	uint16_t x4cc3;
	uint16_t x4cc5;
	uint16_t x4cc7;
	uint16_t x4cc9;
	uint8_t x4ccb;
	uint8_t x4ccc;
	uint8_t x4ccd;
	uint8_t x4cce;
	uint16_t frame_sc_index; // 0x4CCF
} x4bae_t;

</code></pre>
 */
