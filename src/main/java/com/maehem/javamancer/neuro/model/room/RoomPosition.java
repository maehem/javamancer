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

import com.maehem.javamancer.logging.Logging;
import java.util.logging.Logger;

/**
 * Start position (upper left) for player and face position for NPC.
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public enum RoomPosition {

    R1(300, 204, 120, 40), // Chatsubo
    R2(420, 204, 110, 150), // Street Cahtsubo
    R3(300, 204, 150, 150), // Street Cahtsubo
    R4(300, 204, 400, 150), // Body Shop
    R6(300, 204, 500, 150), // Donut World
    R8(420, 204, 80, 150), // G-Loser
    R9(500,204, 200,150), // Maas Biolabs
    R10(300,204,500,200),  // JAL Shuttle to Orbit
    R11(300,204,200,200),  // Zion
    R12(300,204,160,200), // Microsofts
    R19(300,204,500,200), // Spaceport Chiba
    R22(200,204,300,200), // Straylight Bust
    R23(400, 204, 200, 200), // Panther Modern meeting
    R24(170,204,280,150), // Massage Parlor
    R25(320,204, 170,150), // Shin's Pawn
    R26(320,204, 460,150), // Street - Zone's Lady 
    R27(400,204, 210,150), // Julius Dean 
    R28(300,204, 550,150), // JAL Shuttle - to Earth
    R29(300,204, 500,150), // Freeside - Space Dock
    R32(300,204, 200,150), // Metro Holographix
    R34(200,204, 300,150), // Bank Berne - Lobby
    R36(170,204, 320,150), // House of Pong
    R40(340,204, 200,150), // Crazy Edo's
    R44(330,204, 200,150), // Asano's
    R45(300,204, 200,150), // Asano's
    R46(400,204, 160,150), // Matrix Restuarant
    R51(200,204, 80,150), // Fuji Electric
    R52(200,204, 330,150), // Security Gate
    R53(100,204, 300,150), // Hitachi
    R56(200,204, 300,150), // Sense Net
    R57(200,204, 0,0), // Hosaka
    R58(450,204, 0,0), // Musabori
    DEFAULT(300, 204, 0, 0);

    public static final Logger LOGGER = Logging.LOGGER;

    public final int playerX;
    public final int playerY;
    public final int npcX;
    public final int npcY;

    private RoomPosition(int playerX, int playerY, int npcX, int npcY ) {
        this.playerX = playerX;
        this.playerY = playerY;
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
