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

import com.maehem.javamancer.neuro.model.item.Item;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public abstract class RoomExtras {

    public static final int LONG_DESC = 55;
    public static final int SHORT_DESC = 56;
    public static final int TO_JAIL = 69; // Go to jail action
    public static final int BODY_SELL = 88; // Bodyshop menu
    public static final int BODY_BUY = 89; // Bodyshop menu
    public static final int EXIT_T = 90; // Exit Top
    public static final int EXIT_R = 91; // Exit Right
    public static final int EXIT_B = 92; // Exit Bottom
    public static final int EXIT_L = 93; // Exit Left
    public static final int DIALOG_CLOSE = 98;
    public static final int DIALOG_END = 99;

    /**
     * Called when player gives item to NPC.
     *
     * @param item usually RealItem,SkillItem or CreditsItem
     * @param aux if CreditsItem, amount to give NPC.
     *
     * @return index of dialog.
     */
    public abstract boolean give(GameState gs, Item item, int aux); // aux == credits amount if item is Credits.

    public abstract int[][] getDialogChain();

    public abstract int dialogWarmUp(GameState gs);

    public abstract void dialogNoMore(GameState gs);

    public abstract void initRoom(GameState gs);

    public int jackZone() {
        return -1;
    }

    public boolean hasPAX() {
        return false;
    }


}
