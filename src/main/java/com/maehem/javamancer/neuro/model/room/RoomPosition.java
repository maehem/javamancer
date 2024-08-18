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
 * Start position (upper left) for player and face position for NPC.
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public enum RoomPosition {

    R1(300, 210, 60, 40),
    R2(300, 210, 20, 150),
    DEFAULT(300, 200, 0, 0);

    public final int playerX;
    public final int playerY;
    public final int npcX;
    public final int npcY;

    private RoomPosition(int pX, int pY, int npcX, int npcY ) {
        this.playerX = pX;
        this.playerY = pY;
        this.npcX = npcX;
        this.npcY = npcY;
    }

    public static RoomPosition get(Room room) {
        for (RoomPosition rp : values()) {
            if (rp.name().equals(room.name())) {
                return rp;
            }
        }
        return DEFAULT;
    }
}
