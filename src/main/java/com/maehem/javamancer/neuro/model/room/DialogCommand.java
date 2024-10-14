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
package com.maehem.javamancer.neuro.model.room;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public enum DialogCommand {

    //public static final Logger LOGGER //= Logging.LOGGER// ),
    DESC(50), // show in room desc instead of dialog.
    LONG_DESC(51),
    SHORT_DESC(52),
    NPC(53), // Don't toggle to PLAYER after this dialog
    PLAYER(54), // Don't toggle to PLAYER after this dialog
    WORD1(55),
    WORD2(56),
    WHERE_IS(57), // Street Light Girl - Where is Lonny Zone?
    DISCOUNT(58), // Apply vendor discount (asano 20%)
    LUNGS(60), // lungs removed at Hitachi
    BODY_SELL(61), // Bodyshop menu
    BODY_BUY(62), // Bodyshop menu
    SKILL_SELL(63), // ??? menu, maybe don't need
    SKILL_BUY(64), // Larry menu, TODO: move to ITEM_BUY
    SKILL_UPGRADE(65), // Larry menu, TODO: move to ITEM_BUY
    INFO_BUY(66), // Massage parlor buy info
    ITEM_BUY(67), // Player buys item from NPC
    ITEM_GET(68), // player receives NPC item directly
    SOFTWARE_BUY(69), // player buys software  (Metro Holo)
    EXIT_T(70), // Exit Top
    EXIT_R(71), // Exit Right
    EXIT_B(72), // Exit Bottom
    EXIT_L(73), // Exit Left
    EXIT_ST_CHAT(74), // Exit Outside Chatsubo
    EXIT_BDSHOP(75), // Exit to body shop.
    EXIT_SHUTTLE_FS(76), // Exit To Freeside Shuttle
    EXIT_SHUTTLE_ZION(77), // Exit To Zion Shuttle
    EXIT_X(78), // Exit determined by code.
    DEATH(79), // Go to jail action
    TO_JAIL(80), // Go to jail action
    DECK_WAIT(81), // Wait till user exit's deck or leaves room.
    UXB(90), // Shin gives UXB
    PASS(91), // Shiva gives Rest. Pass
    CAVIAR(92), // Edo gives ConLink 2.0 for Caviar
    CHIP(94), // n credits.
    FINE_BANK_500(95), // Fine bank
    FINE_BANK_20K(96), // Fine bank
    DIALOG_NO_MORE(97), // Like DIALOG_END but leave dialog open so next command can run.
    DIALOG_CLOSE(98),
    DIALOG_END(99),
    DESC_DIRECT(500); // Subtract 500 and put remainder(index) in DESC box.// Subtract 500 and put remainder(index) in DESC box.

    public final int num;

    private DialogCommand(int num) {
        this.num = num;
    }

    public static DialogCommand getCommand(int num) {
        for (DialogCommand cmd : values()) {
            if (num == cmd.num) {
                return cmd;
            }
        }

        return null;
    }
}
