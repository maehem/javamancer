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
    NAME("name", "Case"),
    DAMAGE("damage", 0),
    // Time/Date
    TIME_MINUTE("timeMinute", 0),
    TIME_HOUR("timeHour", 12),
    DATE_DAY("dateDay", 16),
    DATE_MONTH("dateMonth", 11),
    DATE_YEAR("dateYear", 58),
    // Money
    CHIP_BALANCE("chipBalance", 6),
    BANK_BALANCE("bankBalance", 1000),
    BANK_TRANSACTIONS("bankTransactions", 0),
    BANK_ZURICH_BALANCE("bankZurichBalance", 0),
    BANK_GEMEIN_BALANCE("bankGemeinBalance", 30000),
    BANK_ZURICH_CREATED("bankZurichCreated", null),
    HOSAKA_DAYS_SINCE_PAID("hosakaDaysSincePaid", -1),
    // Person Lists
    SEA_WANTED("seaWanted", 0),
    CHIBA_POLICE("chibaPolice", 0),
    HOSAKA_EMPLOYEE("hosakaEmployee", 0),
    // Deck
    DECK_SLOTS("deckSlots", 0),
    DATABASE("database", null),
    USING_DECK("usingDeck", null),
    MATRIX_POS_X("matrixPosX", 112),
    MATRIX_POS_Y("matrixPosY", 96),
    DIXIE_INSTALLED("dixieInstalled", false),
    // Room
    ROOM_POS_X("roomPosX", 160),
    ROOM_POS_Y("roomPosY", 90),
    ROOM("room", null),
    // VARIOUS
    MSG_TO_ARMITAGE_SENT("msgToArmitageSent", false),
    RATZ_PAID("ratzPaid", false),
    SHIVA_CHIP_MENTIONED("shivaChipMentioned", false),
    SHIVA_CHIP_GIVEN("shivaGaveChip", false),
    RESTAURANT_PASS_GIVEN("shivaGavePass", false),
    JOYSTICK_GIVEN("joystickGiven", false),
    GAS_MASK_ON("gasMaskOn", false),
    BODY_PART_DISCOUNT("bodyPartDiscount", false),
    ACTIVE_SKILL("activeSkill", null),
    BANK_ZURICH_ROBBED("swissBankRobbed", false),
    COMLINK2_RECIEVED("comlink2received", false),
    COMLINK6_UPLOADED("comlink6uploaded", false),
    ASANO_DISCOUNT("asanoDiscount", false),
    SECURITY_PASS_GIVEN("securityPassGiven", false),
    LARRY_MOE_WANTED("larryMoeWanted", false),
    PSYCHO_PROBE_COUNT("psychoProbeCount", 0),
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
