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

import com.maehem.javamancer.neuro.model.Room;
import com.maehem.javamancer.neuro.model.RoomBounds;

/**
 *
 * @author Mark J Koch ( @maehem on GitHub )
 */
public enum RoomMap {
    R1(Room.R1, null, null, Room.R2, null),
    R2(Room.R2, null, Room.R5, null, null),
    R3(Room.R3, null, null, null, null), // Justice
    R4(Room.R4, null, null, Room.R3, null), // Body Shop
    R5(Room.R5, Room.R4, Room.R13, Room.R6, Room.R1), // Street Body Shop
    R6(Room.R6, Room.R5, null, null, null), // Donut World
    R7(Room.R7, null, Room.R15, null, null), // Cheap Hotel
    R8(Room.R8, null, Room.R16, null, null), // Gentleman Loser
    R9(Room.R9, null, null, null, null), // Maas Biolabs
    R10(Room.R10, null, null, null, null), // JAL Shuttle
    R11(Room.R11, null, null, null, null), // Zion
    R12(Room.R12, Room.R23, null, Room.R13, null), // Larrys
    R13(Room.R13, Room.R12, Room.R24, Room.R14, Room.R5), // Street Microsofts
    R14(Room.R14, Room.R13, Room.R25, Room.R16, null), // Street Shin's
    R15(Room.R15, Room.R14, Room.R26, Room.R16, Room.R7), // Street Cheap Hotel
    R16(Room.R16, Room.R15, null, Room.R17, Room.R8), // Street G-Loser
    R17(Room.R17, Room.R16, Room.R27, Room.R18, Room.R9), // Street Maas Bio
    R18(Room.R18, Room.R17, null, null, Room.R19), // Street Spaceport
    R19(Room.R19, null, Room.R18, null, null), // Spaceport
    R20(Room.R20, null, null, null, null), // Marcus Garvey
    R21(Room.R21, null, null, null, null), // Villa Straylight, ext.
    R22(Room.R22, null, null, null, null), // Villa Straylight
    R23(Room.R23, null, null, Room.R12, null), // Panther Moderns (in Larry's)
    R24(Room.R24, null, null, null, Room.R13), // Brothel
    R25(Room.R25, null, null, null, Room.R14), // Shin's Pawn
    R26(Room.R26, null, Room.R31, null, Room.R15), // Street Light Pole, Zone's Girl
    R27(Room.R27, null, null, null, Room.R17), // Julius Dean
    R28(Room.R28, null, null, null, null), // JAL Shuttle
    R29(Room.R29, null, null, null, null), // Freeside Spacedock
    R30(Room.R30, null, null, null, null), // Spacedock Ext.
    R31(Room.R31, Room.R32, Room.R39, null, Room.R26), // Street Metro Holo
    R32(Room.R32, null, null, Room.R31, null), // Metro Holo
    R33(Room.R33, null, null, null, null), // Bank Berne, ext.
    R34(Room.R34, null, null, null, null), // Bank Berne Lobby
    R35(Room.R35, null, null, null, null), // Bank Berne Mgr. Office
    R36(Room.R36, null, null, Room.R37, null), // House of Pong
    R37(Room.R37, Room.R36, null, Room.R38, null), // Street House Pong
    R38(Room.R38, Room.R37, Room.R44, Room.R39, null), // Street Asano's
    R39(Room.R39, Room.R38, Room.R45, Room.R40, Room.R31), // Street Crazy Edos
    R40(Room.R40, Room.R39, null, null, null), // Crazy Edo's
    R41(Room.R41, null, null, null, null), // Bank Gemeinschaft
    R42(Room.R42, null, null, null, null), // Bank Gemeinschaft, ext.
    R43(Room.R43, null, null, null, null), // unused
    R44(Room.R44, null, null, null, Room.R38), // Asano's
    R45(Room.R45, null, Room.R49, Room.R46, Room.R39), // Street Security Robot/ Matrix Rest.
    R46(Room.R46, Room.R45, null, null, null), // Matrix Rest.
    R47(Room.R47, null, null, null, null), // Bank Gemeinschaft Vault
    R48(Room.R48, null, null, null, null), // unused
    R49(Room.R49, null, Room.R52, null, Room.R45), // Street Burned Building
    R50(Room.R50, null, null, null, null), // Cyberspace Beach
    R51(Room.R51, null, Room.R54, null, null), // Fuji Electric
    R52(Room.R52, null, Room.R55, null, null), // Security Gate
    R53(Room.R53, null, null, null, null), // Hitachi Biotech
    R54(Room.R54, Room.R53, Room.R57, null, Room.R51), // High tech Area 1
    R55(Room.R55, null, Room.R58, Room.R56, Room.R52), // High tech Area 2
    R56(Room.R56, Room.R55, null, null, null), // SenseNet HQ
    R57(Room.R57, null, null, null, Room.R54), // Hosaka HQ
    R58(Room.R58, null, null, null, Room.R55), // Musabori HQ
    ; // Donut World

    public final Room room;
    public final Room t;
    public final Room r;
    public final Room b;
    public final Room l;

    private RoomMap(Room room, Room t, Room r, Room b, Room l) {
        this.room = room;
        this.t = t;
        this.r = r;
        this.b = b;
        this.l = l;
    }

    public static Room getRoom(Room r, RoomBounds.Door d) {
        for (RoomMap rm : values()) {
            if (r.equals(rm.room)) {
                switch (d) {
                    case TOP -> {
                        return rm.t;
                    }
                    case RIGHT -> {
                        return rm.r;
                    }
                    case BOTTOM -> {
                        return rm.b;
                    }
                    case LEFT -> {
                        return rm.l;
                    }
                }
            }
        }
        return null;
    }
}
