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
 * Dialog NPC phrase and response chain index list. NPC always speaks first at
 * index 2, begin of chain. Index 0 and 1 are used for room descriptions.
 *
 * Use TextResource.get(index) for text string.
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public class RoomDialog {

    public static final int Rxx1[][] = {
        {}, {}, // 0, 1
        {3, 4, 5, 6}, // 2   "I don't care if you eat that spaghetti..."
        {7}, // 3    "Sorry. I can't afford it..."
        {7}, // 4
        {7}, // 5
        {8}, // 6
        {2}, // 7
        {2}, // 8
        {}, // 9
        {}, // 10
        {88, 12, 13, 14}, // 11   88 = gameState.ratzPaid=true
        {15}, // 12
        {16}, // 13
        {19}, // 14
        {11}, // 15
        {11}, // 16
        {}, // 17
        {}, // 18
        {23, 24}, // 19
        {}, // 20
        {}, // 21
        {}, // 22
        {25}, // 23
        {26}, // 24
        {19}, // 25
        {27, 28}, // 26
        {29}, // 27
        {30}, // 28
        {99}, // 29  99 = npc no longer responds to player
        {99}, // 30
        {87, 31}, //31  87 = player gives npc less than 46 credits.
        {2} // 32 Response to underpayment.
    };
}
