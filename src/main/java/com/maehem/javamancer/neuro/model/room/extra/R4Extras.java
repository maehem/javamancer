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
package com.maehem.javamancer.neuro.model.room.extra;

import com.maehem.javamancer.neuro.model.GameState;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.BODY_BUY;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.BODY_SELL;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.DIALOG_CLOSE;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.EXIT_B;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.LONG_DESC;
import static com.maehem.javamancer.neuro.model.room.DialogCommand.SHORT_DESC;
import com.maehem.javamancer.neuro.model.room.RoomExtras;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class R4Extras extends RoomExtras { // Body Shop

    protected static final int[][] DIALOG_CHAIN = {
        {LONG_DESC.num}, {SHORT_DESC.num}, // 0, 1 // Room descriptions
        {4, 5, 6, 7}, // 2   "Can I be of service? ..."
        {4, 5, 6, 7}, // 3    "Hello again ..."
        {8}, // 4  "Yes"
        {9}, // 5  "No"
        {10}, // 6  "I feel an attachment ..."
        {12}, // 7  "Oh look at the time...
        {BODY_SELL.num}, // 8  "Wonderfull. We have need for..." // Sell Organ
        {DIALOG_CLOSE.num}, // 9  "Let me know if you change your mind ..." // Close dialog
        {BODY_BUY.num}, // 10 "Let's see if it's still in stock"
        {EXIT_B.num}, // 11 "Come back real soon."
        {EXIT_B.num}, // 12 "Come back when you have money."
        {4, 5, 6, 7}, // 13 "Kickstarted brain"
        {EXIT_B.num}, // 14 "I'd be happy to buy parts if you need money."
        {EXIT_B.num}, // 15 "Enjoy your cheap plastic replacement."
        {BODY_BUY.num} // 16 // Well sell parts back at a discount.
    };

    // Animation
    protected final int[][] ANIMATION_FLAGS = {
        {1}, // Chin's Face
        {1}, // Chin's Body
        {1} // Tank Bubbles
    };

    @Override
    public int[][] getAnimationFlags() {
        return ANIMATION_FLAGS;
    }

    @Override
    public void initRoom(GameState gs) {
    }

    @Override
    public int[][] getDialogChain() {
        return DIALOG_CHAIN;
    }

    @Override
    public int dialogWarmUp(GameState gs) {

        if (null != gs.bodyShopRecent) {
            switch (gs.bodyShopRecent) {
                case BUY -> {
                    return 11; // Thianks for stopping by.
                }
                case SELL -> {
                    return 15; // Enjoy your cheap plastic replacement.
                }
                case REVIVED -> {
                    gs.bodyShopRecent = GameState.BodyShopRecent.NONE;
                    return 13; // I jumpstarted your brain...
                }
                default -> {
                }
            }
        }

        if (gs.visited.contains(gs.room)) {
            return 3; // Welcome back.
        }

        return 2;
    }

}
