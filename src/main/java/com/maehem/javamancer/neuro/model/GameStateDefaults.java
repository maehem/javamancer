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

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public enum GameStateDefaults {
    NAME("playerName", "Case"),
    DAMAGE("playerDamage", 0),
    ACTIVE_SKILL("playerSkillActive", null),
    INVENTORY("playerInventory", 0), // Numbered list.
    SKILLS("playerSkill", null),
    BODY_PART("playerSoldBodyPart", null),
    // Time/Date
    DATE_MINUTE("dateMinute", 0),
    DATE_HOUR("dateHour", 12),
    DATE_DAY("dateDay", 16),
    DATE_MONTH("dateMonth", 11),
    DATE_YEAR("dateYear", 58),
    // Money
    CHIP_BALANCE("playerChipBalance", 6),
    BANK_BALANCE("bankBalance", 1000),
    BANK_TRANSACTION("bankTransaction", 0),
    BANK_ZURICH_BALANCE("bankZurichBalance", 0),
    BANK_GEMEIN_BALANCE("bankGemeinBalance", 30000),
    BANK_ZURICH_CREATED("bankZurichCreated", null),
    HOSAKA_DAYS_SINCE_PAID("hosakaDaysSincePaid", -1),
    // PAX / BBS
    BBS_SENT_MESSAGE("bbsSentMessage", null),
    // Person Lists
    SEA_WANTED("listSeaWanted", 0),
    CHIBA_POLICE("listChibaPolice", 0),
    HOSAKA_EMPLOYEE("listHosakaEmployee", 0), // Numbered list elements with sub-items.
    // Deck/Matrix
    DECK_SLOTS("deckSlots", 0),
    DECK_WAREZ("deckWarez", null),
    AI_FIGHT_SKILL("aiFightSkill", 0),
    AI_DEFEATED("aiDefeated", null),
    DATABASE("deckCurrentDatabase", null),
    USING_DECK("deckInUse", null),
    LAST_USED_DECK("deckLastUsed", null),
    MATRIX_POS_X("deckMatrixPosX", 112),
    MATRIX_POS_Y("deckMatrixPosY", 96),
    ROM_INSTALLED("deckRomInstalled", -1), // -1 = none. 0 = dixie, 1 = Toshiro, 2 = ROMBO
    // Room
    ROOM_POS_X("roomPlayerPosX", 160),
    ROOM_POS_Y("roomPlayerPosY", 90),
    ROOM("roomPlayerCurrent", null),
    ROOMS_VISITED("roomPlayerVisited", null),
    ROOM_DIALOG_ALLOWED("roomDialogAllowed", null),
    ROOM_LOCKED("roomLocked", null),
    // VARIOUS GOALS
    MSG_TO_ARMITAGE_SENT("goalBbsMsgToArmitageSent", false),
    BBS_MSG_FROM_ARMITAGE_READ("goalBbsMsgFromArmitageRead", false),
    HITACHI_VOLUNTEER("goalHitachiVolunteer", false),
    RATZ_PAID("goalRatzPaid", false),
    SHIVA_CHIP_MENTIONED("goalShivaChipMentioned", false),
    SHIVA_CHIP_GIVEN("goalShivaGaveChip", false),
    JOYSTICK_GIVEN("goalJoystickGiven", false),
    GAS_MASK_ON("goalGasMaskOn", false),
    BODY_PART_DISCOUNT("goalBodyPartDiscount", false),
    BANK_ZURICH_ROBBED("goalSwissBankRobbed", false),
    COMLINK2_RECIEVED("goalComlink2received", false),
    COMLINK6_UPLOADED("goalComlink6uploaded", false),
    ASANO_DISCOUNT("goalAsanoDiscount", false),
    SECURITY_PASS_GIVEN("goalSecurityPassGiven", false),
    LARRY_MOE_WANTED("goalLarryMoeWanted", false),
    PSYCHO_PROBE_COUNT("goalPsychoProbeCount", 0),
    // Hotel
    HOTEL_CHARGES("hotelCharges", 1000),
    HOTEL_ON_ACCOUNT("hotelOnAccount", 0),
    HOTEL_CAVIAR("hotelCaviar", 1), // Quantity
    HOTEL_SAKE("hotelSake", 2), // Quantity
    HOTEL_DELIVER_CAVIAR("hotelDeliverCaviar", 0),
    HOTEL_DELIVER_SAKE("hotelDeliverSake", 0),
    // Massage Parlor
    MASSAGE_INFO_1("massageInfo1", false), // Panther Moderns
    MASSAGE_INFO_2("massageInfo2", false), // Banking
    MASSAGE_INFO_3("massageInfo3", false), // Court Fees
    MASSAGE_INFO_4("massageInfo4", false), // Panther more
    MASSAGE_INFO_5("massageInfo5", false), // Cyber Eyes

    ;

    public final String value;
    public final String key;

    private GameStateDefaults(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private GameStateDefaults(String key, int value) {
        this.key = key;
        this.value = String.valueOf(value);
    }

    private GameStateDefaults(String key, boolean value) {
        this.key = key;
        this.value = String.valueOf(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
